package gie.gltest01


//import org.lwjgl.opengles.GLES20.cre
import gie.concurrent.PostingExecutionContextRunner
import gie.utils.loan.{acquire, makeManagedResource}
import org.lwjgl.system.MemoryUtil.NULL
import org.lwjgl.glfw.{GLFW, GLFWErrorCallback}
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengles.GLES
import org.lwjgl.opengles.GLES20._
import slogging._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
//import resource._

import scala.concurrent.Future

import scala.async.Async.{async, await}


object app extends LazyLogging {

    import gie.gl.RichImplicits._
    val triangle = Array(-1f,0f,0f, 0f,1f,0f, 1f,0f,0f)

    def main(args: Array[String]): Unit={

        LoggerConfig.factory = PrintLoggerFactory
        LoggerConfig.level = LogLevel.TRACE

        logger.info("main()")

        acquire( new PostingExecutionContextRunner) { glRunner =>
            val glContextExecutor = glRunner.executionContext

            Await.result(async {

                GLFWErrorCallback.createPrint(System.err).set()
                val initResult=GLFW.glfwInit()
                assume(initResult)

                val gl = new gie.gl.LwjglContext
                import gl.BufferDataDispatch._

                acquire( makeManagedResource{glfwCreateWindow(1024, 1024, "Hello World!", NULL, NULL)}{glfwDestroyWindow}() ) { window =>

                }

            }(glContextExecutor), Duration.Inf)

        }



//        acquire(
//            makeManagedResource{glfwCreateWindow(1024, 1024, "Hello World!", NULL, NULL)}{glfwDestroyWindow}(),
//            new PostingExecutionContextRunner)
//        { (window, glRunner) =>
//
//            val glContextExecutor = glRunner.executionContext
//
//
//
//            assume(window.get != NULL)
//
//            glfwMakeContextCurrent(window.get)
//            glfwSwapInterval(1)
//            glfwShowWindow(window.get)
//
//            GLES.createCapabilities()
//            gl.clearColor(0.0f, 1.0f, 0.0f, 0.0f)
//
//            gl.createBuffer(gl.const.ARRAY_BUFFER, triangle, gl.const.STATIC_DRAW)
//
//            while ( !glfwWindowShouldClose(window.get) ) {
//                gl.clear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
//
//                glfwSwapBuffers(window.get)
//                glfwPollEvents()
//            }
//
//            gl.gcAllOnQueue()
//            gl.dispose()
//
//
//        }


    }

}
