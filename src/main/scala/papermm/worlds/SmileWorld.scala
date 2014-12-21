package papermm.worlds

import com.jme3.asset.AssetManager
import com.jme3.material.Material
import com.jme3.material.RenderState.BlendMode
import com.jme3.math.{ColorRGBA, FastMath, Quaternion, Vector2f}
import com.jme3.renderer.queue.RenderQueue.Bucket
import com.jme3.scene.control.BillboardControl
import com.jme3.scene.shape.{Box, Quad}
import com.jme3.scene.{Geometry, Node}
import com.jme3.terrain.geomipmap.TerrainQuad
import com.jme3.terrain.heightmap.ImageBasedHeightMap
import papermm.engine.MiniAppState

class SmileWorld(assetManager: AssetManager, node: Node, val heroNode: Node) extends MiniAppState {
    val geom = new Node("SmileWorld")
    node attachChild geom

    val terrain = new TerrainQuad("terrain", 65, 65, {
        val heightMap = new ImageBasedHeightMap(assetManager.loadTexture("Textures/Terrain.png").getImage, 1f)
        heightMap.load()
        heightMap.getHeightMap
    })

    build_ground()
    build_block(1, 0, 1)
    build_block(2, 0, 2)
    build_planter()
    build_water()

    val heroPointer = new Vector2f()

    def build_block(x: Float, y: Float, z: Float) {
        val block_geom = new Geometry("Box", new Box(.5f, .5f, .5f))
        val mat_brick = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md")
        mat_brick.setTexture("DiffuseMap",
            assetManager.loadTexture("Textures/Untitled.png"))
        block_geom setMaterial mat_brick
        block_geom setLocalTranslation(x, y, z)

        geom attachChild block_geom
    }

    def build_planter() {
        val block_geom = new Geometry("Box", new Box(.5f, .5f, .5f))
        val mat_brick = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md")
        mat_brick.setTexture("DiffuseMap",
            assetManager.loadTexture("Textures/planter.png"))
        block_geom setMaterial mat_brick
        block_geom setLocalTranslation(10, 0, 9)

        geom attachChild block_geom

        val sprout_node = new Node("sprout")
        sprout_node setLocalTranslation(10, .5f, 9f)

        val sprout_mesh = new Quad(1, 1)
        val sprout_geom = new Geometry("Quad", sprout_mesh)
        val sprout_mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md")
        sprout_mat.setTexture("DiffuseMap", assetManager.loadTexture("Textures/planter_sprout.png"))
        sprout_mat.setBoolean("UseAlpha", true)
        sprout_mat.setFloat("AlphaDiscardThreshold", .9f)
        sprout_geom setMaterial sprout_mat
        sprout_geom setLocalTranslation(-.5f, 0f, 0)
        sprout_mat.getAdditionalRenderState.setBlendMode(BlendMode.Alpha)
        sprout_geom setQueueBucket Bucket.Transparent

        sprout_node attachChild sprout_geom

        val bControl = new BillboardControl()
        sprout_node.addControl(bControl)

        geom attachChild sprout_node
    }

    def build_water(): Unit = {
        val waterGeom = new Geometry("Quad", new Quad(750, 750))
        waterGeom setLocalRotation new Quaternion((-FastMath.PI / 2 :: 3 * FastMath.PI / 2 :: 0f :: Nil).toArray)
        waterGeom.move(-750 / 2, -.7f, -750 / 2)
        val groundMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
        groundMaterial.setColor("Color", new ColorRGBA(0f, 0f, 1f, 0.5f))
        waterGeom setMaterial groundMaterial
        groundMaterial.getAdditionalRenderState.setBlendMode(BlendMode.Alpha)
        waterGeom setQueueBucket Bucket.Transparent
        geom attachChild waterGeom
    }

    def build_ground() {
        terrain move(0, -2.05f - .5f, 0)
        terrain scale(1, .011764f, 1)
        val mat_brick = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md")
        mat_brick.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Terrain.png"))
        terrain setMaterial mat_brick
        geom attachChild terrain
    }

    override def update(tpf: Float): Unit = {
        heroPointer.x = heroNode.getLocalTranslation.x
        heroPointer.y = heroNode.getLocalTranslation.z
        val curHeight = terrain.getHeight(heroPointer)
        heroNode.setLocalTranslation(heroPointer.x, curHeight - 2.05f - .5f, heroPointer.y)
        if (curHeight > 0)
            terrain.adjustHeight(heroPointer, -(tpf * 50))
    }
}
