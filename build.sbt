name := "gltest01"

fork in run := true

//javaOptions in run += "-Djava.library.path=???"

version := "0.1"

scalaVersion := "2.12.1"

scalacOptions := Seq("-optimise", "-Xlint", "-unchecked", "-deprecation", "-encoding", "utf8", "-feature")

libraryDependencies += "biz.enef" %% "slogging" % "0.5.2"


//sourceGenerators in Compile += Def.task {
//    Djinni.djinni((sourceManaged in Compile).value / "demo")
//}.taskValue
