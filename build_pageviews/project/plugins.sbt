resolvers += Resolver.url("artifactory", url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)
resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
resolvers += "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/"
resolvers += "Spark Package Main Repo" at "https://dl.bintray.com/spark-packages/maven"
resolvers += Resolver.url("ClouderaRepo", url("https://repository.cloudera.com/content/repositories/releases"))
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.3")