package papermm.engine

import com.jme3.app.Application
import com.jme3.app.state.AppStateManager
import com.jme3.math.FastMath
import com.jme3.scene.Node
import com.jme3.ui.Picture
import papermm.main._

class TitleEngine(heroNode: Node, sinkingEngine: SinkingEngine, sunMoonSsao: SunMoonSsao) extends MiniAppState {
    var falling = false
    var fallingAmount = 0f

    override def initialize(stateManager: AppStateManager, app: Application) {
        val pic = new Picture("TitleCard")
        pic.setImage(getAssetManager, "Textures/titlecard.png", true)
        pic.setWidth(getCamera.getWidth)
        pic.setHeight(getCamera.getHeight)
        pic.setPosition(0, 0)
        guiNode.attachChild(pic)
    }


    override def update(tpf: Float): Unit = {
        if (FastMath.floor(heroNode.getLocalTranslation.x) != 1 || FastMath.floor(heroNode.getLocalTranslation.z) != 1) {
            falling = true
        }
        if (falling) {
            fallingAmount += tpf
            heroNode.move(0, -(fallingAmount * fallingAmount) / 3, 0)
        }
        if (falling && heroNode.getLocalTranslation.y < .5f) {
            sinkingEngine.enabled = true
            sunMoonSsao.enabled = true

            guiNode.detachChildNamed("TitleCard")
            getStateManager.detach(this)
        }
    }
}
