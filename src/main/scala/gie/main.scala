package gie.gltest01


//import org.lwjgl.opengles.GLES20.cre
import org.lwjgl.system.MemoryUtil.NULL
import org.lwjgl.glfw.{GLFW, GLFWErrorCallback}
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengles.GLES
import org.lwjgl.opengles.GLES20._
import slogging._
import resource._


object Main extends LazyLogging {

    val gl = new gie.gl.LwjglContext

    //def

    def main(args: Array[String]): Unit={

        LoggerConfig.factory = PrintLoggerFactory
        LoggerConfig.level = LogLevel.TRACE

        logger.info("main()")

        GLFWErrorCallback.createPrint(System.err).set()
        val initResult=GLFW.glfwInit()
        assume(initResult)

        for {
            window <- makeManagedResource{ val w = glfwCreateWindow(1024, 1024, "Hello World!", NULL, NULL); assume(w != NULL); w }(glfwDestroyWindow)(Nil)
        } {
            assume(window != NULL)

            glfwMakeContextCurrent(window)
            glfwSwapInterval(1)
            glfwShowWindow(window)

            GLES.createCapabilities()
            gl.clearColor(0.0f, 1.0f, 0.0f, 0.0f)


            while ( !glfwWindowShouldClose(window) ) {
                gl.clear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)

                glfwSwapBuffers(window)
                glfwPollEvents()
            }

        }

    }

}
