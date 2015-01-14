package papermm

import com.jme3.renderer.queue.RenderQueue.ShadowMode
import com.jme3.scene.Node
import papermm.actors.MmObject
import papermm.engine._
import papermm.resources.{PaperAssets, HeroGfx}
import papermm.worlds.SmileWorld

object main extends SimpleApplication2 with App {
    val worldNode: Node = new Node("WorldNode")
    worldNode.setShadowMode(ShadowMode.CastAndReceive)

    def simpleInitApp() {
        rootNode attachChild worldNode
        setDisplayFps(false)
        PaperAssets.AddAssetToClassLoader(assetManager)

        // Lights
        val sunMoonSsao = new SunMoonSsao(worldNode)
        sunMoonSsao.enabled = false
        stateManager attach sunMoonSsao

        // Camera
        chaseCam = new ChaseCamera2(cam, inputManager)

        // Action
        val heroGeom = createHero()
        val smileWorld = new SmileWorld(assetManager, worldNode, heroGeom)
        val sinkingEngine = new SinkingEngine(heroGeom, smileWorld.terrain)
        sinkingEngine.enabled = false
        stateManager attach sinkingEngine

        // Title
        val titleEngine = new TitleEngine(heroGeom, sinkingEngine, sunMoonSsao)
        stateManager attach titleEngine
        // Lose
        val loseEngine = new LoseEngine(heroGeom, sinkingEngine, sunMoonSsao)
        stateManager attach loseEngine
    }

    def createHero(): Node = {
        val hero = new MmObject()
        // Hero Graphics
        val heroGfx = new HeroGfx(assetManager, chaseCam, hero)
        heroGfx.geom addControl chaseCam
        worldNode attachChild heroGfx.geom
        stateManager attach heroGfx
        // Hero Input
        new HeroInput(inputManager, hero, chaseCam, heroGfx.geom)
        heroGfx.geom
    }
}
