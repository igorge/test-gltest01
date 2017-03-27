package gie.gltest01


//import org.lwjgl.opengles.GLES20.cre
import gie.concurrent.PostingExecutionContextRunner
import gie.gl.Context
import gie.utils.loan.{acquire, makeManagedResource}
import org.lwjgl.system.MemoryUtil.NULL
import org.lwjgl.glfw.{GLFW, GLFWErrorCallback}
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengles.GLES
import org.lwjgl.opengles.GLES20._
import slogging._

import scala.concurrent.{Await, ExecutionContext, Promise}
import scala.concurrent.duration.Duration
import scala.util.Success
//import resource._

import scala.concurrent.Future

import scala.async.Async.{async, await}

trait RenderingTrait { this: LoggerHolder =>
    type GLT <: Context
    type ExtT <: AnyRef

    implicit def glEc: ExecutionContext

    def init(): Future[(GLT,ExtT)]

    def renderFrame(gl: GLT): Future[Unit] = async {
        gl.clear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
    }


}


object app extends RenderingTrait with LazyLogging {

    import gie.gl.RichImplicits._

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
        gl.clearColor(0.0f, 1.0f, 0.0f, 0.0f)

        ( gl, ExtT(window) )
    }

    val triangle = Array(-1f,0f,0f, 0f,1f,0f, 1f,0f,0f)

    def asyncRenderLoop(gl: GLT, window: Long)(implicit ec: ExecutionContext): Future[Unit] = async {

        //gl.clear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)

        glfwSwapBuffers(window)
        glfwPollEvents()

        if (!glfwWindowShouldClose(window) ) {
            await{ renderFrame(gl) }
            await{ asyncRenderLoop(gl, window)(ec) }
        } else {
             glfwDestroyWindow(window)
        }

    }(ec)

    def main(args: Array[String]): Unit={

        LoggerConfig.factory = PrintLoggerFactory
        LoggerConfig.level = LogLevel.TRACE

        logger.info("main()")

        Await.result( async {

            val (gl, ext) = await( init() )

            await(asyncRenderLoop(gl, ext.window))

        }, Duration.Inf)

    }


}
