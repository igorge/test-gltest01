name := "gltest01"

fork in run := true

version := "0.1"

scalaVersion := "2.12.1"

scalacOptions := Seq("-optimise", "-Xlint", "-unchecked", "-deprecation", "-encoding", "utf8", "-feature")

libraryDependencies += "biz.enef" %% "slogging" % "0.5.2"

libraryDependencies += "gie" %% "sml" % "0.1-SNAPSHOT"

//exportJars := true

lazy val osName = System.getProperty("os.name").split(" ")(0).toLowerCase()



/// lwjgl

val lwjglVersion = "3.1.1"

ivyConfigurations += config("natives")

lazy val nativeExtractions = SettingKey[Seq[(String, NameFilter, File)]](
    "native-extractions", "(jar name partial, sbt.NameFilter of files to extract, destination directory)"
)

lazy val extractNatives = TaskKey[Unit]("extract-natives", "Extracts native files")

javaOptions ++= {
    val options = List ( s"-Djava.library.path=${baseDirectory.value}/lib/${osName}"  )
    
    options
}




lazy val lwjglNativeDependencies =
    ("org.lwjgl", "lwjgl") ::
    ("org.lwjgl", "lwjgl-glfw") ::
    ("org.lwjgl", "lwjgl-jemalloc") ::
    ("org.lwjgl", "lwjgl-openal") ::
    ("org.lwjgl", "lwjgl-opengles") ::
    ("org.lwjgl", "lwjgl-stb") ::
    Nil


libraryDependencies ++= {
    def libraryDependenciesNativeClassifier(cl: String) = lwjglNativeDependencies.map{ case (ns, name)=>
        (ns % name % lwjglVersion % "natives" classifier cl)
    }

    Seq(
        "org.lwjgl" % "lwjgl"           % lwjglVersion,
        "org.lwjgl" % "lwjgl-egl"       % lwjglVersion,
        "org.lwjgl" % "lwjgl-glfw"      % lwjglVersion,
        "org.lwjgl" % "lwjgl-jemalloc"  % lwjglVersion,
        "org.lwjgl" % "lwjgl-openal"    % lwjglVersion,
        "org.lwjgl" % "lwjgl-opengles"  % lwjglVersion,
        "org.lwjgl" % "lwjgl-stb"       % lwjglVersion) ++
    libraryDependenciesNativeClassifier("natives-windows") ++
    libraryDependenciesNativeClassifier("natives-linux") ++
    libraryDependenciesNativeClassifier("natives-macos")
}


nativeExtractions := {
    lwjglNativeDependencies.flatMap{ case (ns, name)=>
        (s"${name}-${lwjglVersion}-natives-linux.jar", AllPassFilter, baseDirectory.value / "lib/linux") ::
        (s"${name}-${lwjglVersion}-natives-windows.jar", AllPassFilter, baseDirectory.value / "lib/windows") ::
        (s"${name}-${lwjglVersion}-natives-macos.jar", AllPassFilter, baseDirectory.value / "lib/macos") ::
        Nil
    }
}


extractNatives := {
    val ne = nativeExtractions.value
    val up = update.value

    val jars = up.select(configurationFilter("natives"))

    ne foreach { case (jarName, fileFilter, outputPath) =>
        jars find(_.getName.contains(jarName)) map { jar =>
            IO.unzip(jar, outputPath)
        }
    }
}

compile in Compile := ((compile in Compile) dependsOn (extractNatives)).value

// END lwjgl

