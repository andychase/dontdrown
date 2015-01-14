package papermm.resources

import com.jme3.asset.AssetManager
import com.jme3.asset.plugins.FileLocator

object PaperAssets {
  def AddAssetToClassLoader(assetManager:AssetManager) {
      assetManager.registerLocator("./assets", classOf[FileLocator])
  }
}
