lazy val commonSettings = Seq(
  scalaVersion := "2.13.6"
)

lazy val testDeps = Seq(
  "org.scalatest"     %% "scalatest"  % "3.1.0"   % Test,
  "junit"              % "junit"      % "4.10"    % Test,
  "org.scalatestplus" %% "junit-4-13" % "3.2.9.0" % Test,
  "org.scalameta"     %% "munit"      % "0.4.3"   % Test
)

// Lab 1
lazy val lab1 = (project in file("labs/lab1"))
  .settings(commonSettings)
  .settings(libraryDependencies ++= testDeps)

addCommandAlias("lab1", s";project lab1;run")
addCommandAlias(s"lab1-test", s";project lab1;test")

// Lab 2
lazy val lab2 = (project in file("labs/lab2"))
  .settings(commonSettings)
  .settings(libraryDependencies ++= testDeps)

addCommandAlias("lab2", s";project lab2;run")
addCommandAlias(s"lab2-test", s";project lab2;test")
