package singleton

object MouseController {

    private var x: Int = 0
    private var y: Int = 0

    init {
        println("Initializing: $this")
    }

    fun setCoords(x: Int = 0, y: Int = 0) {
        this.x = x
        this.y = y
        println("Changing coordinates: $this")
    }

    fun getCoords() = println("Getting coordingates: x: $x, y: $y, $this")
}