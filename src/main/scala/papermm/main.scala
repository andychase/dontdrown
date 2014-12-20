package papermm

import com.jme3.renderer.queue.RenderQueue.ShadowMode
import com.jme3.scene.Node
import papermm.actors.MmObject
import papermm.engine.{ChaseCamera2, HeroInput, SimpleApplication2, SunMoonSsao}
import papermm.resources.HeroGfx
import papermm.worlds.SmileWorld

object main extends SimpleApplication2 with App {
  val worldNode: Node = new Node("WorldNode")
  worldNode.setShadowMode(ShadowMode.CastAndReceive)

  def simpleInitApp() {
    rootNode attachChild worldNode

    // Lights
    val sunMoonSsao = new SunMoonSsao(worldNode)
    stateManager attach sunMoonSsao

    // Camera
    chaseCam = new ChaseCamera2(cam, inputManager)

    // Action
    new SmileWorld(assetManager, worldNode)
    createHero()
  }

  def createHero() {
    val hero = new MmObject()
    // Hero Graphics
    val heroGfx = new HeroGfx(assetManager, chaseCam, hero)
    heroGfx.geom addControl chaseCam
    worldNode attachChild heroGfx.geom
    stateManager attach heroGfx
    // Hero Input
    new HeroInput(inputManager, hero, chaseCam, heroGfx.geom)
  }
}
