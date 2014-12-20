package papermm.engine

import com.jme3.bounding.BoundingBox
import com.jme3.input.controls.{AnalogListener, KeyTrigger}
import com.jme3.input.{InputManager, KeyInput}
import com.jme3.math.FastMath._
import com.jme3.math.{FastMath, Vector3f}
import com.jme3.scene.Spatial
import papermm.actors.MmObject
import papermm.resources.Facing

class HeroInput(inputManager:InputManager, parent:MmObject, cam:ChaseCamera2, geom:Spatial) extends AnalogListener {
  var keyboardFacing = Facing.Forward

  inputManager.addMapping("moveForward", new KeyTrigger(KeyInput.KEY_UP), new KeyTrigger(KeyInput.KEY_W))
  inputManager.addMapping("moveBackward", new KeyTrigger(KeyInput.KEY_DOWN), new KeyTrigger(KeyInput.KEY_S))
  inputManager.addMapping("moveRight", new KeyTrigger(KeyInput.KEY_RIGHT), new KeyTrigger(KeyInput.KEY_D))
  inputManager.addMapping("moveLeft", new KeyTrigger(KeyInput.KEY_LEFT), new KeyTrigger(KeyInput.KEY_A))
  inputManager.addListener(this, "moveForward", "moveBackward", "moveRight", "moveLeft")

  val stayInsideBox = new BoundingBox(new Vector3f(1f, -1f, 1f), new Vector3f(14f, 1f, 14f))

  def testLoc(x:Float, z:Float) = {
    val loc = geom.getLocalTranslation
    val r = new BoundingBox(new Vector3f(loc.x + x, loc.y, loc.z + z), .5f, .5f, .5f)
    r.intersectsBoundingBox(stayInsideBox)
  }

  def amountForward(tpf:Float, r:Float = 0) = {
    - FastMath.sin(cam.getHorizontalRotation + r) * 5 * tpf
  }

  def amountRight(tpf:Float, r:Float = 0) = {
    FastMath.cos(cam.getHorizontalRotation + r) * 5 * tpf
  }

  def forward() {
    parent.wasFacing = cam.getRotation
    keyboardFacing = Facing.Forward
  }

  def backward() {
    parent.wasFacing = cam.getRotation - PI
    keyboardFacing = Facing.Backward
  }

  def right() {
    parent.wasFacing = cam.getRotation + PI / 2
    keyboardFacing = Facing.Right
  }

  def left() {
    parent.wasFacing = cam.getRotation - PI / 2
    keyboardFacing = Facing.Left
  }

  def onAnalog(name: String, value: Float, tpf: Float) {
    val f = amountForward(tpf)
    val r = amountRight(tpf)

    val f2 = amountForward(tpf, FastMath.HALF_PI)
    val r2 = amountRight(tpf, FastMath.HALF_PI)

    parent.moving = true

    if (name.equals("moveForward") && testLoc(-r, f)) {
      geom.move(-r, 0, f)
      forward()
    }
    if (name.equals("moveBackward") && testLoc(r, -f)) {
      geom.move(r, 0, -f)
      backward()
    }
    if (name.equals("moveRight") && testLoc(-r2, f2)) {
      geom.move(-r2, 0, f2)
      right()
    }
    if (name.equals("moveLeft") && testLoc(r2, -f2)) {
      geom.move(r2, 0, -f2)
      left()
    }
  }
}
