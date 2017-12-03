lazy val root = (project in file("."))
  .settings(
    name := "MAL_Project",
    organization := "edu.trinity",
    scalaVersion := "2.11.8",
    version 	 := "0.1.0-SNAPSHOT",
    libraryDependencies += "org.nd4j" % "nd4j-native-platform" % "0.9.1" % "provided",
    libraryDependencies += "org.deeplearning4j" % "deeplearning4j-core" % "0.9.1" % "provided",
    libraryDependencies += "org.datavec" % "datavec-api" % "0.9.1" % "provided",
    libraryDependencies += "org.scalafx" % "scalafx_2.11" % "8.0.102-R11" % "provided",
    libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.2.0" % "provided",
    libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "2.2.0" % "provided",
    libraryDependencies += "org.apache.spark" % "spark-mllib_2.11" % "2.2.0" % "provided",
    libraryDependencies += "org.apache.spark" % "spark-graphx_2.11" % "2.2.0" % "provided"
    )
