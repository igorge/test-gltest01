package gie.gl

import org.lwjgl.opengles.GLES20._

final object LwjglContext extends Constants {
    final val NO_ERROR = GL_NO_ERROR
    final val TRIANGLES: Int = GL_TRIANGLES
    final val COLOR_BUFFER_BIT: Int = GL_COLOR_BUFFER_BIT
    final val DEPTH_BUFFER_BIT: Int = GL_DEPTH_BUFFER_BIT
    final val MAX_VERTEX_ATTRIBS: Int = GL_MAX_VERTEX_ATTRIBS
    final val CURRENT_PROGRAM: Int = GL_CURRENT_PROGRAM
    final val VERTEX_SHADER: Int = GL_VERTEX_SHADER
    final val FRAGMENT_SHADER: Int = GL_FRAGMENT_SHADER
    final val SHADER_TYPE: Int = GL_SHADER_TYPE
    final val DELETE_STATUS: Int = GL_DELETE_STATUS
    final val COMPILE_STATUS: Int = GL_COMPILE_STATUS
    final val LINK_STATUS: Int = GL_LINK_STATUS
    final val TEXTURE_2D: Int = GL_TEXTURE_2D
    final val TEXTURE_CUBE_MAP: Int = GL_TEXTURE_CUBE_MAP
    final val TEXTURE0: Int = GL_TEXTURE0
    final val TEXTURE1: Int = GL_TEXTURE1
    final val TEXTURE2: Int = GL_TEXTURE2
    final val TEXTURE3: Int = GL_TEXTURE3
    final val TEXTURE4: Int = GL_TEXTURE4
    final val TEXTURE5: Int = GL_TEXTURE5
    final val TEXTURE6: Int = GL_TEXTURE6
    final val TEXTURE7: Int = GL_TEXTURE7
    final val ARRAY_BUFFER: Int = GL_ARRAY_BUFFER
    final val ELEMENT_ARRAY_BUFFER: Int = GL_ELEMENT_ARRAY_BUFFER
    final val STATIC_DRAW: Int = GL_STATIC_DRAW
    final val DYNAMIC_DRAW: Int = GL_DYNAMIC_DRAW
    final val STREAM_DRAW: Int = GL_STREAM_DRAW
    final val FLOAT: Int = GL_FLOAT
    final val RGB: Int = GL_RGB
    final val RGBA: Int = GL_RGBA
    final val BYTE: Int = GL_BYTE
    final val UNSIGNED_BYTE: Int = GL_UNSIGNED_BYTE
    final val UNSIGNED_SHORT: Int = GL_UNSIGNED_SHORT
    final val INT: Int = GL_INT
    final val TEXTURE_MAG_FILTER: Int = GL_TEXTURE_MAG_FILTER
    final val TEXTURE_MIN_FILTER: Int = GL_TEXTURE_MIN_FILTER
    final val NEAREST: Int = GL_NEAREST
    final val BLEND: Int = GL_BLEND
    final val DEPTH_TEST: Int = GL_DEPTH_TEST
    final val CULL_FACE: Int = GL_CULL_FACE
    final val ONE: Int = GL_ONE
    final val SRC_ALPHA: Int = GL_SRC_ALPHA
    final val ONE_MINUS_SRC_ALPHA: Int = GL_ONE_MINUS_SRC_ALPHA

    final val NEVER: Int = GL_NEVER
    final val LESS: Int = GL_LESS
    final val EQUAL: Int = GL_EQUAL
    final val LEQUAL: Int = GL_LEQUAL
    final val GREATER: Int = GL_GREATER
    final val NOTEQUAL: Int = GL_NOTEQUAL
    final val GEQUAL: Int = GL_GEQUAL
    final val ALWAYS: Int = GL_ALWAYS

}


class LwjglContext extends Context {
    type GLShader = this.type
    type GLProgram = this.type
    type GLBuffer = this.type
    type GLUniformLocation = this.type
    type GLTexture = this.type

    def uniformLocation_null: LwjglContext.this.type = ???

    def uniformLocation_null_?(x: LwjglContext.this.type): Boolean = ???

    def program_null: LwjglContext.this.type = ???

    def program_null_?(x: LwjglContext.this.type): Boolean = ???

    def buffer_null: LwjglContext.this.type = ???

    def buffer_null_?(x: LwjglContext.this.type): Boolean = ???

    def texture_null: LwjglContext.this.type = ???

    def texture_null_?(x: LwjglContext.this.type): Boolean = ???

    val const: Constants = LwjglContext

    def impl_glGetError(): GLVertexAttributeLocation = glGetError()

    def impl_glClear(mask: GLVertexAttributeLocation): Unit = glClear(mask)

    def impl_glClearColor(red: Float, green: Float, blue: Float, alpha: Float): Unit = glClearColor(red, green, blue, alpha)

    def impl_glViewport(x: GLVertexAttributeLocation, y: GLVertexAttributeLocation, width: GLVertexAttributeLocation, height: GLVertexAttributeLocation): Unit = ???

    def impl_glEnable(cap: GLVertexAttributeLocation): Unit = ???

    def impl_glDisable(cap: GLVertexAttributeLocation): Unit = ???

    def impl_glBlendFunc(sfactor: GLVertexAttributeLocation, dfactor: GLVertexAttributeLocation): Unit = ???

    def impl_glDepthFunc(func: GLVertexAttributeLocation): Unit = ???

    def impl_glGetIntegerv(pname: GLVertexAttributeLocation): GLVertexAttributeLocation = ???

    def impl_glCreateShader(shaderType: GLVertexAttributeLocation): LwjglContext.this.type = ???

    def impl_glDeleteShader(shader: LwjglContext.this.type): Unit = ???

    def impl_glShaderSource(shader: LwjglContext.this.type, src: String): Unit = ???

    def impl_glCompileShader(shader: LwjglContext.this.type): Unit = ???

    def impl_glGetShaderiv(shader: LwjglContext.this.type, pname: GLVertexAttributeLocation): GLVertexAttributeLocation = ???

    def impl_glGetShaderbv(shader: LwjglContext.this.type, pname: GLVertexAttributeLocation): Boolean = ???

    def impl_getShaderInfoLog(shader: LwjglContext.this.type): String = ???

    def impl_glCreateProgram(): LwjglContext.this.type = ???

    def impl_glDeleteProgram(program: LwjglContext.this.type): Unit = ???

    def impl_getProgramInfoLog(program: LwjglContext.this.type): String = ???

    def impl_glGetProgramiv(program: LwjglContext.this.type, pname: GLVertexAttributeLocation): GLVertexAttributeLocation = ???

    def impl_glGetProgrambv(program: LwjglContext.this.type, pname: GLVertexAttributeLocation): Boolean = ???

    def impl_glAttachShader(program: LwjglContext.this.type, shader: LwjglContext.this.type): Unit = ???

    def impl_glBindAttribLocation(program: LwjglContext.this.type, index: GLVertexAttributeLocation, name: String): Unit = ???

    def impl_glLinkProgram(program: LwjglContext.this.type): Unit = ???

    def impl_glUseProgram(program: LwjglContext.this.type): Unit = ???

    def impl_glBindBuffer(target: GLVertexAttributeLocation, buffer: LwjglContext.this.type): Unit = ???

    def impl_glCreateBuffer(): LwjglContext.this.type = ???

    def impl_glDeleteBuffer(buffer: LwjglContext.this.type): Unit = ???

    def impl_glBufferDataFloat(target: GLVertexAttributeLocation, data: Array[Float], usage: GLVertexAttributeLocation): Unit = ???

    def impl_glBufferDataFloat(target: GLVertexAttributeLocation, data: Seq[Float], usage: GLVertexAttributeLocation): Unit = ???

    def impl_glBufferDataInt(target: GLVertexAttributeLocation, data: Array[GLVertexAttributeLocation], usage: GLVertexAttributeLocation): Unit = ???

    def impl_glBufferDataInt(target: GLVertexAttributeLocation, data: Seq[GLVertexAttributeLocation], usage: GLVertexAttributeLocation): Unit = ???

    def impl_glBufferDataUnsignedShort(target: GLVertexAttributeLocation, data: Array[GLVertexAttributeLocation], usage: GLVertexAttributeLocation): Unit = ???

    def impl_glBufferDataUnsignedShort(target: GLVertexAttributeLocation, data: Seq[GLVertexAttributeLocation], usage: GLVertexAttributeLocation): Unit = ???

    def impl_glVertexAttribPointer(indx: GLVertexAttributeLocation, size: GLVertexAttributeLocation, componentType: GLVertexAttributeLocation, normalized: Boolean, stride: GLVertexAttributeLocation, offset: GLVertexAttributeLocation): Unit = ???

    def impl_glEnableVertexAttribArray(index: GLVertexAttributeLocation): Unit = ???

    def impl_glDisableVertexAttribArray(index: GLVertexAttributeLocation): Unit = ???

    def impl_glGetUniformLocation(program: LwjglContext.this.type, name: String): LwjglContext.this.type = ???

    def impl_glGetAttribLocation(program: LwjglContext.this.type, name: String): GLVertexAttributeLocation = ???

    def impl_glUniform1f(location: LwjglContext.this.type, x: Float): Unit = ???

    def impl_glUniform4fv(location: LwjglContext.this.type, v: Array[Float]): Unit = ???

    def impl_glUniform1i(location: LwjglContext.this.type, v: GLVertexAttributeLocation): Unit = ???

    def impl_glUniformMatrix4fv(location: LwjglContext.this.type, transpose: Boolean, v: Array[Float]): Unit = ???

    def impl_glDrawArrays(mode: GLVertexAttributeLocation, first: GLVertexAttributeLocation, count: GLVertexAttributeLocation): Unit = ???

    def impl_glDrawElements(mode: GLVertexAttributeLocation, count: GLVertexAttributeLocation, `type`: GLVertexAttributeLocation, offset: GLVertexAttributeLocation): Unit = ???

    def impl_glGenTexture(): LwjglContext.this.type = ???

    def impl_glDeleteTextures(texture: LwjglContext.this.type): Unit = ???

    def impl_glIsTexture(texture: LwjglContext.this.type): Boolean = ???

    def impl_glActiveTexture(texture: GLVertexAttributeLocation): Unit = ???

    def impl_glBindTexture(target: GLVertexAttributeLocation, texture: LwjglContext.this.type): Unit = ???

    def impl_glTexParameterf(target: GLVertexAttributeLocation, pname: GLVertexAttributeLocation, param: Float): Unit = ???

    def impl_glTexParameteri(target: GLVertexAttributeLocation, pname: GLVertexAttributeLocation, param: GLVertexAttributeLocation): Unit = ???

    def impl_glTexImage2D(target: GLVertexAttributeLocation, level: GLVertexAttributeLocation, internalformat: GLVertexAttributeLocation, width: GLVertexAttributeLocation, height: GLVertexAttributeLocation, border: GLVertexAttributeLocation, format: GLVertexAttributeLocation, `type`: GLVertexAttributeLocation, pixels: Array[Byte]): Unit = ???

    def currentProgram(): LwjglContext.this.type = ???
}