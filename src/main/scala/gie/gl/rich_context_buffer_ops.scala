package gie.gl


import scala.language.implicitConversions

final class BufferOps[CTX <: Context](val gl: CTX) extends AnyVal {

    @inline def bindNullBuffer(target: Int): Unit={
        gl.bindBuffer(target, gl.buffer_null)
    }


    @inline final def createBuffer[T <: AnyRef](target: Int, data: T, usage: Int)(implicit dispatch: gl.BufferDataDispatch[T]): gl.GLBuffer={
        val buffer = gl.createBuffer()
        gl.bindBuffer(target, buffer)
        gl.bufferData(target, data, usage)
        bindNullBuffer(target)

        buffer
    }

}

trait BufferOpsTrait {

    implicit def makeBufferOps[T <: Context](gl: T) = new BufferOps[gl.type](gl)

}