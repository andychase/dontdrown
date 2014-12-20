name := "papermm"

resolvers +=
    "JME3 Snapshots" at "http://updates.jmonkeyengine.org/maven/"

resolvers +=
    "papermm-assets" at "http://papermm-assets.s3-website-us-west-2.amazonaws.com/"

libraryDependencies ++= Seq(
    "com.jme3" % "jme3-core" % "3.0.+",
    "com.jme3" % "jme3-effects" % "3.0.+",
    "com.jme3" % "jme3-networking" % "3.0.+",
    "com.jme3" % "jme3-plugins" % "3.0.+",
    "com.jme3" % "jme3-blender" % "3.0.+",
    "com.jme3" % "jme3-desktop" % "3.0.+",
    "com.jme3" % "jme3-lwjgl" % "3.0.+",
    "default" % "papermm-assets" % "1.0"
)

publishTo := Some(Resolver.file("file",  new File("/Users/a5c/Desktop/mvn")))
