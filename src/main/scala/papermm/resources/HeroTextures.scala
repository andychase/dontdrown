package papermm.resources

import com.jme3.asset.{AssetManager, BlenderKey}
import com.jme3.scene.plugins.blender.BlenderContext
import com.jme3.scene.plugins.blender.textures.TextureHelper
import com.jme3.texture.Texture.{MagFilter, MinFilter}
import papermm.resources.Facing._

import scala.util.Try

class HeroTextures(assetManager: AssetManager) {
    private val (baseTexture, forwardSprites, backSprites, leftSprites, rightSprites) = setup()
    val texture = baseTexture
    var currentState = Forward
    private var i = 0

    texture.setMinFilter(MinFilter.NearestNearestMipMap)
    texture.setMagFilter(MagFilter.Nearest)
    update()

    def switch(newState: Facing.HeroTextureState) {
        currentState = newState
        update(progress = 0)
    }

    def update(progress: Int = 1) {
        i += progress
        val max = currentState match {
            case Facing.Backward => 4
            case _ => 2
        }
        if (i >= max)
            i = 0
        switchSprite(i)
    }

    def switchSprite(i: Int) = currentState match {
        case Forward =>
            texture.setImage(forwardSprites(i))
        case Backward =>
            texture.setImage(backSprites(i))
        case Left =>
            texture.setImage(leftSprites(i))
        case Right =>
            texture.setImage(rightSprites(i))
    }

    private def setup() = {
        val texture = Try(assetManager.loadTexture("hero.png")) getOrElse { assetManager.loadTexture("Textures/hero.png") }
        val img = texture.getImage

        val b = new BlenderContext()
        b.setBlenderKey(new BlenderKey())
        val textureHelper = new TextureHelper("1", b)

        val backward =
            for (i <- 0 to 3)
            yield textureHelper.getSubimage(img, 18 * i, 22, 18 * (i + 1), 44)

        val forward =
            for (i <- 0 to 1)
            yield textureHelper.getSubimage(img, 18 * i, 0, 18 * (i + 1), 22)

        val right =
            for (i <- 2 to 3)
            yield textureHelper.getSubimage(img, 18 * i, 0, 18 * (i + 1), 22)

        val left =
            for (i <- 4 to 5)
            yield textureHelper.getSubimage(img, 18 * i, 0, 18 * (i + 1), 22)

        (texture, forward.toArray, backward.toArray, left.toArray, right.toArray)
    }
}
