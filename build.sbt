name := "papermm"

resolvers +=
    "JME3 Snapshots" at "http://updates.jmonkeyengine.org/maven/"

libraryDependencies ++= Seq(
    "com.jme3" % "jme3-core" % "3.0.+",
    "com.jme3" % "jme3-effects" % "3.0.+",
    "com.jme3" % "jme3-networking" % "3.0.+",
    "com.jme3" % "jme3-plugins" % "3.0.+",
    "com.jme3" % "jme3-blender" % "3.0.+",
    "com.jme3" % "jme3-desktop" % "3.0.+",
    "com.jme3" % "jme3-lwjgl" % "3.0.+"
)
