import org.scoverage.coveralls.Imports.CoverallsKeys._

name := """light-play-rest-api"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  ws,
  specs2 % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

coverallsToken := Some("COVERALLS_TOKEN_LIGHT_PLAY_REST_API")

ScoverageSbtPlugin.ScoverageKeys.coverageExcludedPackages := ".*index.*;.*main.*"
