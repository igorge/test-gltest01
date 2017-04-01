package gie.yaro

object shaderSource {

    val vertexShaderAA =
        """
          |invariant gl_Position;
          |
          |uniform mat4 u_mv;
          |uniform mat4 u_projection;
          |
          |attribute vec3 a_position;
          |attribute vec2 a_tex_coordinate;
          |attribute vec4 a_color;
          |
          |varying vec4 v_color;
          |varying vec2 v_tex_coordinate;
          |
          |void main() {
          |   v_color = a_color;
          |   v_tex_coordinate = a_tex_coordinate;
          |   gl_Position = u_projection*u_mv*vec4(a_position, 1);
          |}
          |
    """.stripMargin

    val fragmentShaderAA =
        """
          |precision mediump float;
          |
          |varying vec4 v_color;
          |varying vec2 v_tex_coordinate;
          |
          |uniform sampler2D u_texture;
          |
          |void main() {
          |   gl_FragColor = texture2D(u_texture, v_tex_coordinate);
          |}
          |
    """.stripMargin


    val vertexShader =
        """
          |invariant gl_Position;
          |
          |uniform mat4 u_mv;
          |uniform mat4 u_projection;
          |
          |attribute vec3 a_color;
          |attribute vec2 a_tex_coordinate;
          |attribute vec3 a_position;
          |
          |varying vec4 v_color;
          |varying vec2 v_tex_coordinate;
          |
          |void main() {
          |   v_color = vec4(a_color, 1);
          |   v_tex_coordinate = a_tex_coordinate;
          |   gl_Position = u_projection*u_mv*vec4(a_position, 1);
          |}
          |
    """.stripMargin

    val fragmentShader =
        """
          |precision mediump float;
          |
          |varying vec4 v_color;
          |varying vec2 v_tex_coordinate;
          |
          |uniform sampler2D u_texture;
          |
          |void main() {
          |   gl_FragColor = texture2D(u_texture, v_tex_coordinate);
          |}
          |
    """.stripMargin
}


