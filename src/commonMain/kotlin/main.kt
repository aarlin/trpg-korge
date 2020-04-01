import com.soywiz.kds.*
import com.soywiz.klock.*
import com.soywiz.korev.*
import com.soywiz.korge.*
import com.soywiz.korge.input.*
import com.soywiz.korge.input.MouseEvents
import com.soywiz.korge.input.mouse
import com.soywiz.korge.render.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.tiled.*
import com.soywiz.korge.time.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korgw.*
import com.soywiz.korim.color.*
import com.soywiz.korim.format.*
import com.soywiz.korinject.*
import com.soywiz.korio.async.*
import com.soywiz.korio.file.std.*
import com.soywiz.korio.lang.*
import com.soywiz.korio.serialization.json.*
import com.soywiz.korma.geom.*
import com.soywiz.korma.geom.vector.*
import com.soywiz.korma.interpolation.*
import kotlin.reflect.*
import com.soywiz.korge.view.tiles.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korio.util.*

// TODO: use sample-tilemap for example on mapping a grid map: https://github.com/korlibs/korge-samples/tree/master/sample-tilemap
// TODO: use sample-ui for buttons on start & stop: https://github.com/korlibs/korge-samples/tree/master/sample-ui
// TODO: use sample-minesweeper
// TODO: use sample-tic-tac-toe-swf
// TODO: use sample-input for button click
suspend fun main() = Korge(width = 540, height = 720) {
	var strReader: StrReader = StrReader(str = "PC013", file = "gfx/PC013.json")
//	while (!strReader.eof) {
//////		println(strReader.)
////	}
	val tiledMap = resourcesVfs["gfx/PC013.tmx"].readTiledMap()
	fixedSizeContainer(540, 720, clip = true) {
		position(0, 0)
		val camera = camera {
			tiledMapView(tiledMap) {
			}
		}
		mouse {
			over { alpha(1.0) }
			out { alpha(0.5) }
			click { println("clicked box!") }
		}
		this.keys.apply {
			down { key ->
				when (key) {
					Key.RIGHT -> camera.moveBy(-16, 0, 0.25.seconds)
					Key.LEFT -> camera.moveBy(+16, 0, 0.25.seconds)
					Key.DOWN -> camera.moveBy(0, -16, 0.25.seconds)
					Key.UP -> camera.moveBy(0, +16, 0.25.seconds)
				}
			}
		}
		var buttonContainer = this
		this += Button("Start") {
			println("Start")
		}.position(8, views.virtualHeight - 48)
		this += Button("End") {
			println("End")
		}.position(200, views.virtualHeight - 48)
	}
}

//suspend fun main(): Unit {
//	//Logger.defaultLevel = Logger.Level.TRACE
//	//Logger("Views").level = Logger.Level.TRACE
//	//Logger("Korge").level = Logger.Level.TRACE
//	//Logger("RenderContext").level = Logger.Level.TRACE
//	//Logger("BatchBuilder2D").level = Logger.Level.TRACE
//	//Logger("DefaultShaders").level = Logger.Level.TRACE
//	//Logger("RenderContext2D").level = Logger.Level.TRACE
//	//Korge(MyModule, debug = true)
//	println("V0")
//	println("KorioNative.ResourcesVfs.absolutePath: " + resourcesVfs.absolutePath)
//
//	com.soywiz.korge.Korge(Korge.Config(object : MyModule() {
//		//override val mainScene: KClass<out Scene> = HelloScene::class
//		override val quality: GameWindow.Quality = GameWindow.Quality.QUALITY
//	}, debug = false))
//}

//open class MyModule : Module() {
//	override val mainScene: KClass<out Scene> = MyScene::class
//	//override val quality: LightQuality = LightQuality.QUALITY
//	//override val quality: LightQuality = LightQuality.PERFORMANCE
//	//override val quality: LightQuality = LightQuality.AUTO
//	override val quality: GameWindow.Quality = GameWindow.Quality.QUALITY
//
//	override suspend fun init(injector: AsyncInjector) {
//		println("init[0]")
//		injector
//				.mapPrototype { HelloScene() }
//				.mapPrototype { MyScene() }
//				.mapPrototype { HelloWorldScene() }
//		println("init[1]")
//	}
//
//	override val size: SizeInt = SizeInt(1280, 720)
//	override val windowSize: SizeInt = SizeInt(1280, 720)
//}
//
////class MyScene : MyBaseScene() {
////	lateinit var buttonContainer: Container
////
////	override suspend fun Container.sceneInit() {
////		//addEventListener<MouseEvent> {
////		//	println("MouseEvent: ${views.nativeWidth},${views.nativeHeight} :: ${views.virtualWidth},${views.virtualHeight} :: $it")
////		//}
////
////		val mySceneContainer = sceneContainer(views) {
////			this.x = views.virtualWidth.toDouble() * 0.5
////			this.y = views.virtualHeight.toDouble() * 0.5
////		}
////		buttonContainer = this
////		this += Button("Start") {
////			println("Start")
////			mySceneContainer.changeToDisablingButtons<HelloWorldScene>()
////		}.position(8, views.virtualHeight - 48)
////		this += Button("End") {
////			println("End")
////		}.position(200, views.virtualHeight - 48)
////		mySceneContainer.changeToDisablingButtons<HelloWorldScene>()
////	}
////
////	suspend inline fun <reified T : Scene> SceneContainer.changeToDisablingButtons() {
////		for (child in buttonContainer.children.filterIsInstance<Button>()) {
////			//println("DISABLE BUTTON: $child")
////			child.enabledButton = false
////		}
////		try {
////			changeTo<T>()
////		} finally {
////			for (child in buttonContainer.children.filterIsInstance<Button>()) {
////				//println("ENABLE BUTTON: $child")
////				child.enabledButton = true
////			}
////		}
////	}
////}
//
class Button(text: String, handler: suspend () -> Unit) : Container() {
	val textField = Text(text, textSize = 32.0).apply { filtering = false }
	private val bounds = textField.textBounds
	val g = Graphics().apply {
		fill(Colors.DARKGREY, 0.7) {
			roundRect(bounds.x, bounds.y, bounds.width + 16, bounds.height + 16, 8.0, 8.0)
		}
	}
	var enabledButton = true
		set(value) {
			field = value
			updateState()
		}
	private var overButton = false
		set(value) {
			field = value
			updateState()
		}

	fun updateState() {
		when {
			!enabledButton -> alpha = 0.3
			overButton -> alpha = 1.0
			else -> alpha = 0.8
		}
	}

	init {
		//this += this.solidRect(bounds.width, bounds.height, Colors.TRANSPARENT_BLACK)
		this += g.apply {
			mouseEnabled = true
		}
		this += textField.position(8, 8)

		mouse {
			over { overButton = true }
			out { overButton = false }
		}
		onClick {
			if (enabledButton) handler()
		}
		updateState()
	}
}
//
//class HelloWorldScene : BaseDbScene() {
//	val SCALE = 1.6
//	override suspend fun Container.sceneInit() {
//		val skeDeferred = asyncImmediately { Json.parse(resources["mecha_1002_101d_show/mecha_1002_101d_show_ske.json"].readString())!! }
//		//val skeDeferred = asyncImmediately { MemBufferWrap(resources["mecha_1002_101d_show/mecha_1002_101d_show_ske.dbbin"].readBytes()) }
//		val texDeferred = asyncImmediately { resources["mecha_1002_101d_show/mecha_1002_101d_show_tex.json"].readString() }
//		val imgDeferred = asyncImmediately { resources["mecha_1002_101d_show/mecha_1002_101d_show_tex.png"].readBitmap().mipmaps() }
//
//	}
//}
//
//// @TODO: Remove in next KorGE version
//val MouseEvents.exit by WeakPropertyThis<MouseEvents, Signal<MouseEvents>> {
//	Signal()
//}
//
//abstract class BaseDbScene : MyBaseScene() {
//	val resources get() = resourcesVfs
//}