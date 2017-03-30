package gie.gltest01


import gie.concurrent.PostingExecutionContextRunner
import gie.gl.Context
import org.lwjgl.system.MemoryUtil.NULL
import org.lwjgl.glfw.{GLFW, GLFWErrorCallback}
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengles.GLES
//import org.lwjgl.opengles.GLES20._
import slogging._

import scala.concurrent.{Await, ExecutionContext, Promise}
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

import scala.concurrent.Future

import scala.async.Async.{async, await}


object app extends RenderingTrait with LazyLogging {

    case class ExtT(window: Long)
    type GLT = gie.gl.LwjglContext

    val glRunner  = new PostingExecutionContextRunner

    implicit def glEc: ExecutionContext = glRunner.executionContext

    def init(): Future[(GLT,ExtT)] = async {
        val initResult=GLFW.glfwInit()
        assume(initResult)
        GLFWErrorCallback.createPrint(System.err).set()

        val gl = new gie.gl.LwjglContext
        val window = glfwCreateWindow(1024, 1024, "Hello World!", NULL, NULL)

        glfwMakeContextCurrent(window)
        //glfwSwapInterval(1)
        glfwShowWindow(window)

        GLES.createCapabilities()
        gl.clearColor(0.0f, 1.0f, 0.0f, 1.0f)

        ( gl, ExtT(window) )
    }


    def terminate(cause: Option[Throwable]): Unit = {
        glRunner.close()
    }

    def asyncRenderLoop(gl: GLT, window: Long): Unit = async {

        glfwSwapBuffers(window)
        glfwPollEvents()

        if (!glfwWindowShouldClose(window) ) {
            renderFrame(gl)
            asyncRenderLoop(gl, window)  // post new and return current, we are not waiting here
        } else {
            glfwDestroyWindow(window)
            terminate(None)
        }

    } onComplete {
        case Success(_) =>
        case Failure (e) =>
            logger.warn( s"RENDER LOOP EXCEPTION: ${gie.getExceptionStackTrace(e)}" )
            terminate(Some(e))
    }

    def main(args: Array[String]): Unit={

        LoggerConfig.factory = PrintLoggerFactory
        LoggerConfig.level = LogLevel.TRACE

        logger.info("main()")

        Await.result( async {

            val (gl, ext) = await( init() )

            asyncRenderLoop(gl, ext.window)

        }, Duration.Inf)


        logger.debug("main() terminated.")
    }


}
