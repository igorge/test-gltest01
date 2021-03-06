package gie.gl

import org.lwjgl.opengles.GLES20._
import slogging.{LazyLogging, Logger, LoggerHolder, StrictLogging}

import scala.annotation.switch

final object LwjglContext extends Constants {
    override type GL_BOOLEAN = Int

    final val TRUE: GL_BOOLEAN = GL_TRUE
    final val FALSE: GL_BOOLEAN = GL_FALSE
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


class LwjglContext extends Context with resource.ResourceContext with LazyLogging {


    private class GLBufferResource(handle: Int) extends resource.DisposableOnceAbstract[Int](handle){

        protected def release(): Unit = {
            assume(this.get != 0)
            glDeleteBuffers(this.get)
        }
    }

    class GLBuffer(resource: GLBufferResource){
        def get = resource.get
        def apply() = get
        def dispose() = resource.dispose()
    }



    private class GLShaderResource(handle: Int) extends resource.DisposableOnceAbstract[Int](handle){
        protected def release(): Unit = {
            assume(this.get != 0)
            glDeleteShader(this.get)
        }
    }

    class GLShader(resource: GLShaderResource){
        def get = resource.get
        def apply() = get
        def dispose() = resource.dispose()
    }


    private class GLProgramResource(handle: Int) extends resource.DisposableOnceAbstract[Int](handle){
        protected def release(): Unit = {
            assume(this.get != 0)
            glDeleteProgram(this.get)
        }
    }

    class GLProgram(resource: GLProgramResource){
        def get = resource.get
        def apply() = get
        def dispose() = resource.dispose()
    }

    type GLUniformLocation = Int
    type GLTexture = this.type



    private var m_currentProgram:GLProgram = null


    def uniformLocation_null: GLUniformLocation = ???

    def uniformLocation_null_?(x: GLUniformLocation): Boolean = ???

    def program_null: GLProgram = null

    def program_null_?(x: GLProgram): Boolean = x eq null

    def buffer_null: GLBuffer = null

    def buffer_null_?(x: GLBuffer): Boolean = x eq null

    def texture_null: LwjglContext.this.type = ???

    def texture_null_?(x: LwjglContext.this.type): Boolean = ???

    val const: Constants = LwjglContext

    def impl_glGetError(): Int = glGetError()

    def impl_glClear(mask: Int): Unit = glClear(mask)

    def impl_glClearColor(red: Float, green: Float, blue: Float, alpha: Float): Unit = glClearColor(red, green, blue, alpha)

    def impl_glViewport(x: Int, y: Int, width: Int, height: Int): Unit = ???

    def impl_glEnable(cap: Int): Unit = ???

    def impl_glDisable(cap: Int): Unit = ???

    def impl_glBlendFunc(sfactor: Int, dfactor: Int): Unit = ???

    def impl_glDepthFunc(func: Int): Unit = ???

    def impl_glGetIntegerv(pname: Int): Int = {
        glGetInteger(pname)
    }

    def impl_glCreateShader(shaderType: Int): GLShader = {
        checkGlError()

        val glShaderId = glCreateShader(shaderType)
        checkGlError()
        assume(glShaderId!=0)

        try {
            val resource = new GLShaderResource( glShaderId )
            val shader = new GLShader( resource )
            this.registerResourceReference(shader, resource)

            shader
        } catch {
            case ex: Throwable =>
                glDeleteShader(glShaderId)
                throw ex
        }

    }

    def impl_glDeleteShader(shader: GLShader): Unit = {
        shader.dispose()
    }

    def impl_glShaderSource(shader: GLShader, src: String): Unit = {
        glShaderSource(shader.get, src)
    }

    def impl_glCompileShader(shader: GLShader): Unit = {
        glCompileShader(shader.get)
    }

    def impl_glGetShaderiv(shader: GLShader, pname: Int): Int = {
        val ret = new Array[Int](0)
        
        glGetShaderiv(shader.get, pname, ret)
        assume(ret.size==1)

        return ret(0)
    }

    def impl_glGetShaderbv(shader: GLShader, pname: Int): Boolean = {
        val iret = impl_glGetShaderiv(shader, pname)

        if(iret==const.TRUE){
            true
        } else if (iret==const.FALSE){
            false
        } else {
            ???
        }
    }

    def impl_getShaderInfoLog(shader: GLShader): String = {
        glGetShaderInfoLog(shader.get)
    }

    def currentProgram(): GLProgram = {
        val glProg = this.getInteger(const.CURRENT_PROGRAM)

        if( program_null_?(m_currentProgram) ){
            assert( glProg==0 )
            return program_null
        } else {
            assert( glProg==m_currentProgram.get)
            return m_currentProgram
        }


    }

    def impl_glCreateProgram(): GLProgram = ???

    def impl_glDeleteProgram(program: GLProgram): Unit = ???

    def impl_getProgramInfoLog(program: GLProgram): String = ???

    def impl_glGetProgramiv(program: GLProgram, pname: GLVertexAttributeLocation): GLVertexAttributeLocation = ???

    def impl_glGetProgrambv(program: GLProgram, pname: GLVertexAttributeLocation): Boolean = ???

    def impl_glAttachShader(program: GLProgram, shader: GLShader): Unit = ???

    def impl_glBindAttribLocation(program: GLProgram, index: GLVertexAttributeLocation, name: String): Unit = ???

    def impl_glLinkProgram(program: GLProgram): Unit = ???

    def impl_glUseProgram(program: GLProgram): Unit = {
        if( program_null_?(program) ) {
            glUseProgram(0)
            m_currentProgram = null
        } else {
            val glProg = program.get
            assume(glProg!=0)
            glUseProgram(glProg)
            m_currentProgram = program
        }
    }

    def impl_glBindBuffer(target: GLVertexAttributeLocation, buffer: GLBuffer): Unit = {
        glBindBuffer(target, if(buffer eq null) 0 else buffer.get)
    }

    def impl_glCreateBuffer(): GLBuffer = {
        val resource = new GLBufferResource (glGenBuffers() )
        val buffer = new GLBuffer( resource )
        this.registerResourceReference(buffer, resource)

        buffer
    }

    def impl_glDeleteBuffer(buffer: GLBuffer): Unit = {
        buffer.dispose()
    }

    def impl_glBufferDataFloat(target: Int, data: Array[Float], usage: Int): Unit ={
        glBufferData(target, data, usage)
        checkGlError()
    }

    def impl_glBufferDataFloat(target: Int, data: Seq[Float], usage: Int): Unit = ???

    def impl_glBufferDataInt(target: Int, data: Array[Int], usage: Int): Unit = ???

    def impl_glBufferDataInt(target: Int, data: Seq[Int], usage: Int): Unit = ???

    def impl_glBufferDataUnsignedShort(target: Int, data: Array[Int], usage: Int): Unit = ???

    def impl_glBufferDataUnsignedShort(target: GLVertexAttributeLocation, data: Seq[GLVertexAttributeLocation], usage: GLVertexAttributeLocation): Unit = ???

    def impl_glVertexAttribPointer(indx: GLVertexAttributeLocation, size: GLVertexAttributeLocation, componentType: GLVertexAttributeLocation, normalized: Boolean, stride: GLVertexAttributeLocation, offset: GLVertexAttributeLocation): Unit = ???

    def impl_glEnableVertexAttribArray(index: GLVertexAttributeLocation): Unit = ???

    def impl_glDisableVertexAttribArray(index: GLVertexAttributeLocation): Unit = ???

    def impl_glGetUniformLocation(program: GLProgram, name: String): GLUniformLocation = ???

    def impl_glGetAttribLocation(program: GLProgram, name: String): GLVertexAttributeLocation = ???

    def impl_glUniform1f(location: GLUniformLocation, x: Float): Unit = ???

    def impl_glUniform4fv(location: GLUniformLocation, v: Array[Float]): Unit = ???

    def impl_glUniform1i(location: GLUniformLocation, v: GLVertexAttributeLocation): Unit = ???

    def impl_glUniformMatrix4fv(location: GLUniformLocation, transpose: Boolean, v: Array[Float]): Unit = {
        glUniformMatrix4fv(location,transpose,v)
    }

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

}