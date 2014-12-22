package papermm.engine

import com.jme3.app.Application
import com.jme3.app.state.AppStateManager
import com.jme3.font.BitmapText
import com.jme3.math.ColorRGBA
import com.jme3.scene.Node
import com.jme3.ui.Picture
import papermm.main._

class LoseEngine(heroNode: Node, sinkingEngine: SinkingEngine, sunMoonSsao: SunMoonSsao) extends MiniAppState {
    val timeLastedText = new BitmapText(guiFont, false)
    timeLastedText.setSize(guiFont.getCharSet.getRenderedSize)
    timeLastedText.setColor(ColorRGBA.White)
    timeLastedText.setText("")
    timeLastedText.setLocalTranslation(300, timeLastedText.getLineHeight, 0)

    val looseCardPicture = new Picture("LoseCard")
    looseCardPicture.setImage(getAssetManager, "Textures/losecard.png", true)
    looseCardPicture.setWidth(getCamera.getWidth)
    looseCardPicture.setHeight(getCamera.getHeight)
    looseCardPicture.setPosition(0, 0)

    var timeLasted:Float = 0

    override def initialize(stateManager: AppStateManager, app: Application) {
        guiNode.attachChild(timeLastedText)
    }

    override def update(tpf: Float): Unit = {
        timeLasted += tpf

        if (heroNode.getLocalTranslation.y < -2.05f) {
            timeLastedText.setText(s"$timeLasted seconds")
            sinkingEngine.enabled = false
            sunMoonSsao.enabled = false
            heroNode setLocalScale (0f, 0f, 0f)
            guiNode.attachChild(looseCardPicture)
            getStateManager.detach(this)
        }
    }

}
