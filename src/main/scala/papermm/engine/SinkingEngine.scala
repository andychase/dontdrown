package papermm.engine

import com.jme3.math.Vector2f
import com.jme3.scene.Node
import com.jme3.terrain.geomipmap.TerrainQuad

class SinkingEngine(heroNode:Node, terrain:TerrainQuad) extends MiniAppState {
    val heroPointer = new Vector2f()

    override def update(tpf: Float): Unit = {
        heroPointer.x = heroNode.getLocalTranslation.x
        heroPointer.y = heroNode.getLocalTranslation.z
        val curHeight = terrain.getHeight(heroPointer)
        heroNode.setLocalTranslation(heroPointer.x, curHeight - 2.05f - .53f, heroPointer.y)
        for (x <- -1 to 1; z <- -1 to 1) {
            if (curHeight > 0)
                terrain.adjustHeight(new Vector2f(heroPointer.x + x, heroPointer.y + z), -(tpf * (50f / 3) * (3 - (x + z))))
        }

    }

}
