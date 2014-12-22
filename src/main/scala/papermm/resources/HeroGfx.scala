package papermm.resources

import com.jme3.asset.AssetManager
import com.jme3.material.Material
import com.jme3.material.RenderState.BlendMode
import com.jme3.math.FastMath._
import com.jme3.renderer.queue.RenderQueue.Bucket
import com.jme3.scene.control.BillboardControl
import com.jme3.scene.shape.Quad
import com.jme3.scene.{Geometry, Node}
import papermm.actors.MmObject
import papermm.engine.{ChaseCamera2, MiniAppState}

class HeroGfx(assetManager: AssetManager, cam: ChaseCamera2, parent: MmObject) extends MiniAppState {
    val mesh = new Quad(.81f, 1.6f)

    val geom = new Node("hero")
    val hero_geom = new Geometry("Sphere", mesh)
    hero_geom.move(-.405f, 0, 0)
    geom attachChild hero_geom
    //geom.move(2f, -.5f, 3f)
    geom.move(1.5f, 4-.5f, 1.5f)
    val heroTextures = new HeroTextures(assetManager)
    val mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md")
    val bControl = new BillboardControl()

    mat.setTexture("DiffuseMap", heroTextures.texture)
    mat.setBoolean("UseAlpha", true)
    mat.setFloat("AlphaDiscardThreshold", .9f)
    mat.getAdditionalRenderState.setAlphaFallOff(0.1f)
    mat.getAdditionalRenderState.setAlphaTest(true)
    mat.getAdditionalRenderState.setBlendMode(BlendMode.Alpha)

    geom setMaterial mat
    geom setQueueBucket Bucket.Transparent
    private var timeSinceLastUpdate = 0f
    bControl setAlignment BillboardControl.Alignment.AxialY
    geom.addControl(bControl)

    override def update(tpf: Float) {
        timeSinceLastUpdate += tpf
        if (timeSinceLastUpdate > .25f) {
            if (parent.moving) {
                heroTextures.update()
                parent.moving = false
            } else {
                heroTextures.update(5)
            }
            timeSinceLastUpdate = 0
        }

        updatePosition()
    }

    def updatePosition() {
        heroTextures.switch(Facing(getCameraDirection.id))
    }

    def getCameraDirection = {
        val r = (parent.wasFacing - cam.getRotation) + (PI / 4f)

        if (cos(r) > 0 && sin(r) > 0)
            Facing.Forward
        else if (cos(r) < 0 && sin(r) > 0)
            Facing.Right
        else if (cos(r) < 0 && sin(r) < 0)
            Facing.Backward
        else
            Facing.Left
    }
}