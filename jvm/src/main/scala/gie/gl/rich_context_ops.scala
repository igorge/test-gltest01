package gie.gl


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
}

trait RichOpsTrait {

    implicit def makeBufferOps[T <: Context](gl: T) = new RichOps[gl.type](gl)

}