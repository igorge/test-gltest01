package gie.gltest01


import scala.concurrent.ExecutionContext
import scala.scalajs.js.JSApp
import slogging._

object app extends JSApp with LazyLogging {
    implicit val appExecutionContext:ExecutionContext = scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

    def main(): Unit = {

        LoggerConfig.factory = ConsoleLoggerFactory
        LoggerConfig.level = LogLevel.TRACE

        logger.info("gie.gltest01.app.main()")
    }

}
