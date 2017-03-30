package gie.gltest01

import gie.gl.Context
import slogging.LoggerHolder
import scala.async.Async.{async, await}
import scala.concurrent.{ExecutionContext, Future}


trait RenderingTrait { this: LoggerHolder =>


    type GLT <: Context
    type ExtT <: AnyRef

    implicit def glEc: ExecutionContext

    def init(): Future[(GLT,ExtT)]

    def renderFrame(gl: GLT): Unit = {
        gl.clear (gl.const.COLOR_BUFFER_BIT | gl.const.DEPTH_BUFFER_BIT)
    }


    def terminate(cause: Option[Throwable]): Unit


}
