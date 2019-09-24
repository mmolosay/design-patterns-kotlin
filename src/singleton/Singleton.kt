package singleton

object MouseController {

    var coords = Pair(0, 0)
        get() {
            println("Getting coordinates of $this -> (${field.first}, ${field.second})")
            return field
        }
        set(value) {
            println("Setting coordinates of $this -> (${value.first}, ${value.second})")
            field = value
        }

    init { println("Initializing $this") }
}