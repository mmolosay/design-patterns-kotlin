package facade

interface SmartPowerBehavior {
    // toggle power distantly
    fun on()
    fun off()
}

interface SmartUseBehavior {
    // use item distantly
    fun use()
}

abstract class SmartTech(private val name: String) : SmartPowerBehavior, SmartUseBehavior {

    override fun on() = println("$name: on")

    override fun off() = println("$name: off")

    abstract override fun use()
}

class SmartHouse {

    val innerLight = object : SmartTech("House light") {
        override fun use() {}
    }
    val gardenLight = object : SmartTech("Garden light") {
        override fun use() {}
    }
    val teapot = object : SmartTech("Teapot") {
        override fun use() = println("Water is boiling.")
    }
    val toster = object : SmartTech("Toster") {
        override fun use() = println("Toasts are ready!")
    }
    val acousticSystem = object : SmartTech("Acoustic system") {
        override fun use() = println("Only good music plays in this house.")
    }
}

class UniqueRemoteController(private val house: SmartHouse) {

    fun atHomeArrival() =
        with (house) {
            println("~~~~~~ You arrive home ~~~~~~")
            gardenLight.on()
            innerLight.on()
            acousticSystem.on()
            acousticSystem.use()
            println()
        }

    fun atHomeDeparture() =
        with (house) {
            println("~~~~~~ You leaving home ~~~~~~")
            gardenLight.off()
            innerLight.off()
            acousticSystem.off()
            println()
        }

    fun atWakeUp() =
        with (house) {
            println("~~~~~~ You wake up ~~~~~~")
            teapot.on()
            teapot.use()
            teapot.off()
            toster.on()
            toster.use()
            toster.off()
            acousticSystem.on()
            acousticSystem.use()
            println()
        }

    fun atSleep() =
        with (house) {
            println("~~~~~~ You go to sleep ~~~~~~")
            acousticSystem.off()
            gardenLight.off()
            innerLight.off()
            println()
        }
}