package gie.gltest01


import org.lwjgl.system.MemoryUtil.NULL
import org.lwjgl.glfw.{GLFW, GLFWErrorCallback}
import slogging._


object Main extends LazyLogging {
    def main(args: Array[String]): Unit={

        LoggerConfig.factory = PrintLoggerFactory
        LoggerConfig.level = LogLevel.TRACE

        logger.info("main()")

        GLFWErrorCallback.createPrint(System.err).set()
        val initResult=GLFW.glfwInit()
        assume(initResult)

        val window = GLFW.glfwCreateWindow(1024, 1024, "Hello World!", NULL, NULL)

        assume(window != NULL)
        GLFW.glfwDestroyWindow(window)
    }

}
