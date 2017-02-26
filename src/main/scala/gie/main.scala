package gie.gltest01

import slogging._


object Main extends LazyLogging {
    def main(args: Array[String]): Unit={

        LoggerConfig.factory = PrintLoggerFactory
        LoggerConfig.level = LogLevel.TRACE

        logger.info("main()")
    }

}
