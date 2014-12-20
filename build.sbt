name := "papermm"

resolvers ++= Seq(
    "JME3 Snapshots" at "http://updates.jmonkeyengine.org/maven/",
    "papermm-assets" at "http://papermm-assets.s3-website-us-west-2.amazonaws.com/"
)

// You can put assets here so you can try out changes without re-building a jar file
unmanagedResourceDirectories in Compile += baseDirectory.value / "assets"

libraryDependencies ++= Seq(
    "com.jme3" % "jme3-core" % "3.0.+",
    "com.jme3" % "jme3-effects" % "3.0.+",
    "com.jme3" % "jme3-networking" % "3.0.+",
    "com.jme3" % "jme3-plugins" % "3.0.+",
    "com.jme3" % "jme3-blender" % "3.0.+",
    "com.jme3" % "jme3-terrain" % "3.0.+",
    "com.jme3" % "jme3-desktop" % "3.0.+",
    "com.jme3" % "jme3-lwjgl" % "3.0.+",
    "default" % "papermm-assets" % "1.0"
)
