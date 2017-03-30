package gie.gltest01


import gie.concurrent.PostingExecutionContext
import org.scalajs.dom

import scala.concurrent.{ExecutionContext, Future}
import scala.scalajs.js.JSApp
import slogging._

import scala.async.Async.{async, await}
import scala.util.{Failure, Success}

object RequestAnimationFrameExecutionContext extends PostingExecutionContext( runnable=> {  dom.window.requestAnimationFrame( (_)=>{ runnable.run() } ) })

object app extends JSApp with RenderingTrait with LazyLogging {

    type GLT = gie.gl.WebGLContext
    type ExtT = this.type

    def queueExecutionContext: ExecutionContext = scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

    implicit def glEc: ExecutionContext = RequestAnimationFrameExecutionContext

    var rawGL:dom.raw.WebGLRenderingContext = null

    def init(): Future[(GLT,ExtT)] = async {

        val canvas = dom.document.getElementById("render-canvas").asInstanceOf[dom.html.Canvas]
        assume(canvas ne null)

        rawGL = canvas.getContext("webgl").asInstanceOf[dom.raw.WebGLRenderingContext]

        val gl = new GLT(rawGL)

        gl.clearColor(0.0f, 1.0f, 0.0f, 1.0f)

        (gl, null)
    }

    def terminate(cause: Option[Throwable]): Unit = {
        logger.debug(s"terminate() called: ${cause}")

    }


    def asyncRenderLoop(gl: GLT): Unit = async {
        assume(gl ne null)
        logger.debug("!!")

        renderFrame(gl)

        asyncRenderLoop(gl)
    } onComplete {
        case Success(_) =>
        case Failure (e) =>
            logger.warn( s"RENDER LOOP EXCEPTION: ${gie.getExceptionStackTrace(e)}" )
            terminate(Some(e))
    }


    def main(): Unit = {

        LoggerConfig.factory = ConsoleLoggerFactory
        LoggerConfig.level = LogLevel.TRACE

        logger.info("gie.gltest01.app.main()")


        dom.document.addEventListener("DOMContentLoaded", (e:dom.Event)=> {

            init().onComplete {
                case Success(r) =>
                    asyncRenderLoop(r._1)
                case Failure(ex) =>
                    logger.debug(s">> ${ex}")
            }
        })


        logger.debug("main() terminated.")
    }

}
