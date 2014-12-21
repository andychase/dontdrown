package papermm.engine

import com.jme3.app.Application
import com.jme3.app.state.{AppState, AppStateManager}
import com.jme3.renderer.RenderManager

class MiniAppState extends AppState {
    var enabled = true

    override def initialize(stateManager: AppStateManager, app: Application) {}

    override def update(tpf: Float) {}

    override def stateAttached(stateManager: AppStateManager) {}

    override def cleanup() {}

    override def isEnabled = enabled

    override def setEnabled(active: Boolean) {
        enabled = active
    }

    override def isInitialized = true

    override def postRender() {}

    override def render(rm: RenderManager) {}

    override def stateDetached(stateManager: AppStateManager) {}
}
