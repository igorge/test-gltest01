package gie.gltest01

import gie.gl.Context
import slogging.LoggerHolder
import scala.async.Async.{async, await}
import scala.concurrent.{ExecutionContext, Future}

import gie.gl.RichImplicits._

import scala.language.existentials


trait RenderingTrait { this: LoggerHolder =>


    type GLT <: Context
    type ExtT <: AnyRef

    implicit def glEc: ExecutionContext

    var gl: GLT = _

    def init(): Future[(GLT,ExtT)]
    def terminate(cause: Option[Throwable]): Unit

    var renderFrame: ()=>Unit = renderFrame_Init _

    private def renderFrame_Init(): Unit = {

        val program = gl.createProgram()
        val vertexShader = gl.createVertexShader()
        val fragmentShader = gl.createFragmentShader()

        gl.attachShader(program,vertexShader)

        renderFrame = renderFrame1 _
    }

    private def renderFrame1(): Unit = {
        gl.clear (gl.const.COLOR_BUFFER_BIT | gl.const.DEPTH_BUFFER_BIT)
    }


}
