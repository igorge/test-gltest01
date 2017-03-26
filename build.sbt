
lazy val root = project.in(file(".")).
  aggregate(appJS, appJVM).
  settings(
    publish := {},
    publishLocal := {}
  )

  
lazy val osName = System.getProperty("os.name").split(" ")(0).toLowerCase()  

val lwjglVersion = "3.1.2-SNAPSHOT"

lazy val nativeExtractions = SettingKey[Seq[(String, NameFilter, File)]](
    "native-extractions", "(jar name partial, sbt.NameFilter of files to extract, destination directory)"
)

lazy val extractNatives = TaskKey[Unit]("extract-natives", "Extracts native files")

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

lazy val app = crossProject.in(file(".")).
  settings(
    organization := "gie",
    name := "gltest01",
    version := "0.1",
    scalaVersion := "2.12.1",
    scalacOptions := Seq("-Xlint", "-unchecked", "-deprecation", "-encoding", "utf8", "-feature"), //"-optimise"
    libraryDependencies += "com.lihaoyi" %%% "utest" % "0.4.5" % "test",
    libraryDependencies += "biz.enef" %%% "slogging" % "0.5.2",
    libraryDependencies += "gie" %%% "gielib" % "0.1-SNAPSHOT",
    testFrameworks += new TestFramework("utest.runner.Framework")
  ).
  jvmSettings(
    // Add JVM-specific settings here
    javaOptions ++= {
        val options =
            s"-Djava.library.path=${baseDirectory.value}/lib/${osName}"  ::
            "-Xdebug" :: "-agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n" ::
             Nil
    
        options
    },
    fork in run := true,
    updateOptions := updateOptions.value.withLatestSnapshots(false),
    resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
    ivyConfigurations += config("natives"),
    libraryDependencies += "com.jsuereth" %%% "scala-arm" % "2.0",
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
    },
    nativeExtractions := {
        lwjglDependencies.flatMap{ case (_, ns, name)=>
            (s"${name}-${lwjglVersion}-natives-linux.jar", AllPassFilter, baseDirectory.value / "lib/linux") ::
            (s"${name}-${lwjglVersion}-natives-windows.jar", AllPassFilter, baseDirectory.value / "lib/windows") ::
            (s"${name}-${lwjglVersion}-natives-macos.jar", AllPassFilter, baseDirectory.value / "lib/macos") ::
            Nil
        }
    },
    extractNatives := {
        val ne = nativeExtractions.value
        val up = update.value

        val jars = up.select(configurationFilter("natives"))

        ne foreach { case (jarName, fileFilter, outputPath) =>
            jars find(_.getName.contains(jarName)) map { jar =>
                IO.unzip(jar, outputPath)
            }
        }
    },
    compile in Compile := ((compile in Compile) dependsOn (extractNatives)).value
  ).
  jsSettings(
      // Add JS-specific settings here
      jsDependencies += RuntimeDOM,
      libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.1",
      
      persistLauncher in Compile := true,
      skip in packageJSDependencies := false//,
      //mainClass := Some("gie.gltest01.app")
  )

lazy val appJVM = app.jvm
lazy val appJS = app.js
