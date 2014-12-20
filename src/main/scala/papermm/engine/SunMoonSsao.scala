package papermm.engine

import com.jme3.app.Application
import com.jme3.app.state.AppStateManager
import com.jme3.light.{AmbientLight, DirectionalLight}
import com.jme3.math.{ColorRGBA, FastMath, Vector3f}
import com.jme3.post.FilterPostProcessor
import com.jme3.post.ssao.SSAOFilter
import com.jme3.renderer.ViewPort
import com.jme3.scene.Node
import com.jme3.shadow.DirectionalLightShadowRenderer

class SunMoonSsao(private var rootNode: Node) extends MiniAppState {
  val SHADOWMAP_SIZE = 2048
  var time: Float = 3.14f
  val sunPosition: Vector3f = new Vector3f(1, 0, 0)

  val sun = new DirectionalLight()
  val sun_current = new ColorRGBA()
  val sun_end = ColorRGBA.White.mult(0.2f)
  val sun_start = ColorRGBA.Orange
  sun setColor sun_current

  val moon = new DirectionalLight()
  val moon_current = new ColorRGBA()
  val moon_end = new ColorRGBA(0, 0, 1f, 1)
  val moon_start = ColorRGBA.Pink
  moon setColor moon_current

  val back_current = new ColorRGBA()
  val back_end = ColorRGBA.White
  val back_start = ColorRGBA.Black

  val ambientLight = new AmbientLight()
  val ambientColor = new ColorRGBA()

  val ssao_filter = new SSAOFilter()

  var viewPort: ViewPort = null

  override def initialize(stateManager: AppStateManager, app: Application) {
    val assetManager = app.getAssetManager
    viewPort = app.getViewPort

    // Sun
    rootNode.addLight(sun)

    val sun_renderer = new DirectionalLightShadowRenderer(assetManager, SHADOWMAP_SIZE, 4)
    sun_renderer.setLight(sun)
    viewPort.addProcessor(sun_renderer)

    // Moon
    rootNode.addLight(moon)

    // SSAO
    val filter_processor = new FilterPostProcessor(assetManager)
    filter_processor.addFilter(ssao_filter)
    viewPort.addProcessor(filter_processor)

    // Ambient Light
    rootNode.addLight(ambientLight)

    update(0)
  }

  override def update(tpf: Float) {
    time += tpf / 8
    if (FastMath.sin(time) > 0)
      time += tpf / 8
    val y = FastMath.sin(time)
    sunPosition.setY(y)
    sunPosition.setX(FastMath.cos(time))
    sun.setDirection(sunPosition)
    if (y > 0)
      sun_current.interpolate(sun_start, sun_end, y)
    else
      sun_current.interpolate(sun_start, sun_end, -y)
    sun.setColor(sun_current)

    sunPosition.negateLocal()
    moon.setDirection(sunPosition)

    if (y > 0)
      moon_current.interpolate(moon_start, moon_end, y)
    else
      moon_current.interpolate(moon_start, moon_end, -y)
    moon.setColor(moon_current)

    if (y > 0)
      ssao_filter.setIntensity(1.2f + (10 * y))

    // Change Background color
    if (y < 0) {
      back_current.interpolate(back_start, back_end, -y * 5)
      viewPort setBackgroundColor back_current

      ambientLight.setColor(ColorRGBA.White.mult((-y * 3f) + 1f))
    }
  }
}
