package gie.gl


import gie.sml.MatrixRead4F

import scala.language.implicitConversions

final class RichOps[CTX <: Context](val gl: CTX) extends AnyVal {

    import gl._

    @inline def bindNullBuffer(target: Int): Unit={
        gl.bindBuffer(target, gl.buffer_null)
    }


    @inline final def createBuffer[T <: AnyRef](target: Int, data: T, usage: Int)(implicit dispatch: gl.BufferDataDispatch[T]): GLBuffer={
        val buffer = gl.createBuffer()
        gl.bindBuffer(target, buffer)
        gl.bufferData(target, data, usage)
        bindNullBuffer(target)

        buffer
    }


    @inline final def createVertexShader(): GLShader =  createShader(const.VERTEX_SHADER)
    @inline final def createFragmentShader(): GLShader =  createShader(const.FRAGMENT_SHADER)
    @inline final def compilationStatus(shader: GLShader): Boolean = getShaderbv(shader, const.COMPILE_STATUS)

    @inline final def get_maxVertexAttribs() = getInteger(const.MAX_VERTEX_ATTRIBS)


    @inline final def uniformMatrix4fv(location: GLUniformLocation, transpose: Boolean, m: MatrixRead4F): Unit={

        val v = if(transpose) Array(
            m.m00, m.m01, m.m02, m.m03,
            m.m10, m.m11, m.m12, m.m13,
            m.m20, m.m21, m.m22, m.m23,
            m.m30, m.m31, m.m32, m.m33
        ) else  Array( //column major
            m.m00, m.m10, m.m20, m.m30,
            m.m01, m.m11, m.m21, m.m31,
            m.m02, m.m12, m.m22, m.m32,
            m.m03, m.m13, m.m23, m.m33
        )

        gl.uniformMatrix4fv(location, false, v)
    }

}

trait RichOpsTrait {

    implicit def makeBufferOps[T <: Context](gl: T) = new RichOps[gl.type](gl)

}