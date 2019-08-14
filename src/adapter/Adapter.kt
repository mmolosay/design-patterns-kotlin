package adapter

interface Duck {
    fun quack()
    fun flyLongDistance()
}

interface Turkey {
    fun gobble()
    fun flyShortDistance()
}

class MallardDuck : Duck {

    override fun quack() = println("\'Quack!\'")

    override fun flyLongDistance() = println("Duck flies long distance.")
}

class WildTurkey : Turkey {

    override fun gobble() = println("\'Gobble-gobble\'")

    override fun flyShortDistance() = println("Turkey flies quite a bit.")
}

class TurkeyAdapter(private val turkey: Turkey) : Duck {

    override fun quack() = turkey.gobble()

    override fun flyLongDistance() {
        for (i in 0 until 5) turkey.flyShortDistance()
    }
}

fun huntDuck(duck: Duck) {
    // hunter hunts for the "duck" if he hears it quacking and sees how it flies
    duck.quack()
    duck.flyLongDistance()

    println("Seems like it duck. \'BANG!\'")
}