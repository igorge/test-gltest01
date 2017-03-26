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


object app extends LazyLogging {

    import gie.gl.RichImplicits._
    val triangle = Array(-1f,0f,0f, 0f,1f,0f, 1f,0f,0f)

    def renderFrame[GLT <: Context](gl: GLT, window: Long)(implicit ec: ExecutionContext): Future[Unit] = async {

        println(Thread.currentThread().getId())

        gl.clear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)

        glfwSwapBuffers(window)
        glfwPollEvents()

        if (!glfwWindowShouldClose(window) ) {
            await{ renderFrame(gl, window) }
        } else {
             glfwDestroyWindow(window)
        }

    }

    def main(args: Array[String]): Unit={

        println(Thread.currentThread().getId())

        LoggerConfig.factory = PrintLoggerFactory
        LoggerConfig.level = LogLevel.TRACE

        logger.info("main()")


        //glfwMakeContextCurrent(NULL)

        acquire( new PostingExecutionContextRunner) { glRunner =>
            val glContextExecutor = glRunner.executionContext

            val rr = async {

                println(Thread.currentThread().getId())

                val initResult=GLFW.glfwInit()
                assume(initResult)
                GLFWErrorCallback.createPrint(System.err).set()

                val gl = new gie.gl.LwjglContext
                import gl.BufferDataDispatch._

                val window = glfwCreateWindow(1024, 1024, "Hello World!", NULL, NULL)

                println(Thread.currentThread().getId())

                glfwMakeContextCurrent(window)
                glfwSwapInterval(1)
                glfwShowWindow(window)

                GLES.createCapabilities()
                gl.clearColor(0.0f, 1.0f, 0.0f, 0.0f)

                await(renderFrame(gl, window)(glContextExecutor))

            }(glContextExecutor)

            Await.result(rr, Duration.Inf)

        }

    }

}
