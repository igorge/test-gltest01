name := "gltest01"

fork in run := true

version := "0.1"

scalaVersion := "2.12.1"

scalacOptions := Seq("-optimise", "-Xlint", "-unchecked", "-deprecation", "-encoding", "utf8", "-feature")

libraryDependencies += "biz.enef" %% "slogging" % "0.5.2"

libraryDependencies += "com.jsuereth" %% "scala-arm" % "2.0"

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


// 1 -- has native & non native dependencies
// 2 -- native only dependency
// 3 -- non native only dependency
lazy val lwjglDependencies =
    (3, "org.lwjgl", "lwjgl-egl") ::
    (1, "org.lwjgl", "lwjgl") ::
    (1, "org.lwjgl", "lwjgl-glfw") ::
    (1, "org.lwjgl", "lwjgl-jemalloc") ::
    (1, "org.lwjgl", "lwjgl-openal") ::
    (1, "org.lwjgl", "lwjgl-opengles") ::
    (1, "org.lwjgl", "lwjgl-stb") ::
    Nil


lazy val lwjglNativeDependencies = lwjglDependencies.filter{
    case(usage, _, _) => if (usage==1 || usage==2) true else false
}

lazy val lwjglNonNativeDependencies = lwjglDependencies.filter{
    case(usage, _, _) => if (usage==1 || usage==3) true else false
}


libraryDependencies ++= {

    def libraryDependenciesNativeClassifier(cl: String) =  lwjglNativeDependencies.map{ case (_, ns, name)=>
        (ns % name % lwjglVersion % "natives" classifier cl)
    }

    lwjglNonNativeDependencies.map{ case (_, ns, name)=>
            ns % name % lwjglVersion
    } ++
    libraryDependenciesNativeClassifier("natives-windows") ++
    libraryDependenciesNativeClassifier("natives-linux") ++
    libraryDependenciesNativeClassifier("natives-macos")
}


nativeExtractions := {
    lwjglDependencies.flatMap{ case (_, ns, name)=>
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

