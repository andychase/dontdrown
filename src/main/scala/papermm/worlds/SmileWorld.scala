package papermm.worlds

import com.jme3.asset.AssetManager
import com.jme3.material.Material
import com.jme3.material.RenderState.BlendMode
import com.jme3.math.{FastMath, Quaternion}
import com.jme3.renderer.queue.RenderQueue.Bucket
import com.jme3.scene.control.BillboardControl
import com.jme3.scene.shape.{Box, Quad}
import com.jme3.scene.{Geometry, Node}

class SmileWorld(assetManager: AssetManager, node: Node) {
  val geom = new Node("SmileWorld")
  node attachChild geom

  build_ground()
  build_block(1, 0, 1)
  build_block(2, 0, 2)
  build_planter()

  def build_block(x:Float, y:Float, z:Float) {
    val block_geom = new Geometry("Box", new Box(.5f, .5f, .5f))
    val mat_brick = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md")
    mat_brick.setTexture("DiffuseMap",
      assetManager.loadTexture("Textures/Untitled.png"))
    block_geom setMaterial mat_brick
    block_geom setLocalTranslation (x, y, z)

    geom attachChild block_geom
  }

  def build_planter() {
    val block_geom = new Geometry("Box", new Box(.5f, .5f, .5f))
    val mat_brick = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md")
    mat_brick.setTexture("DiffuseMap",
      assetManager.loadTexture("Textures/planter.png"))
    block_geom setMaterial mat_brick
    block_geom setLocalTranslation (10, 0, 9)

    geom attachChild block_geom

    val sprout_node = new Node("sprout")
    sprout_node setLocalTranslation (10, .5f, 9f)

    val sprout_mesh = new Quad(1, 1)
    val sprout_geom = new Geometry("Quad", sprout_mesh)
    val sprout_mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md")
    sprout_mat.setTexture("DiffuseMap", assetManager.loadTexture("Textures/planter_sprout.png"))
    sprout_mat.setBoolean("UseAlpha", true)
    sprout_mat.setFloat("AlphaDiscardThreshold", .9f)
    sprout_geom setMaterial sprout_mat
    sprout_geom setLocalTranslation (-.5f, 0f, 0)
    sprout_mat.getAdditionalRenderState.setBlendMode(BlendMode.Alpha)
    sprout_geom setQueueBucket Bucket.Transparent

    sprout_node attachChild sprout_geom

    val bControl = new BillboardControl()
    sprout_node.addControl(bControl)

    geom attachChild sprout_node
  }

  def build_ground() {
    val block_geom = new Geometry("Box", new Box(7.5f, 7.5f, .25f))
    block_geom setLocalRotation new Quaternion( - FastMath.PI / 2 :: 3 * FastMath.PI / 2 :: 0f :: Nil toArray)
    block_geom.move(7.5f, -.75f, 7.5f)
    val mat_brick = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md")
    mat_brick.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Untitled.png"))
    block_geom setMaterial mat_brick
    geom attachChild block_geom
  }
}
