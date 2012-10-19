import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "OnlineDashboard"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
        "postgresql" % "postgresql" % "9.1-901.jdbc4",
        "com.weiglewilczek.slf4s" %% "slf4s" % "1.0.7"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
        coffeescriptOptions := Seq("bare")
    )

}
