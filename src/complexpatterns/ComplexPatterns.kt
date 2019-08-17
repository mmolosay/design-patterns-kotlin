package complexpatterns

interface Observer {
    fun update(duck: QuackObservable)
}

interface QuackObservable {
    fun register(observer: Observer): Boolean
    fun notifyObservers()
}

interface Quackable : QuackObservable {
    fun quack()
}

object DuckSimulator { // singleton

    fun simulate(duckFactory: AbstractDuckFactory) {
        val redheadDuck: Quackable = duckFactory.createRedheadDuck()
        val decoyDuck: Quackable = duckFactory.createDecoyDuck()
        val rubberDuck: Quackable = duckFactory.createRubberDuck()
        val gooseDuck: Quackable = GooseAdapter(Goose())

        val ducksFlock = Flock()
        ducksFlock.add(redheadDuck)
        ducksFlock.add(decoyDuck)
        ducksFlock.add(rubberDuck)
        ducksFlock.add(gooseDuck)

        val mallardsFlock = Flock()
        mallardsFlock.addTimes({duckFactory.createMallardDuck()}, 5)

        val quackologist = Quackologist()
        ducksFlock.register(quackologist)
        mallardsFlock.register(quackologist)

        simulate(ducksFlock)
        simulate(mallardsFlock)

        println("The ducks quacked ${QuackCounter.quacks} times.")
    }

    private fun simulate(duck: Quackable) = duck.quack()
}

class QuackCounter(private val duck: Quackable) : Quackable { // decorator

    companion object {
        var quacks: Int = 0
    }

    override fun quack() {
        duck.quack()
        quacks++
    }

    override fun register(observer: Observer): Boolean = duck.register(observer)
    override fun notifyObservers() = duck.notifyObservers()
}

abstract class AbstractDuckFactory { // abstract factory

    abstract fun createMallardDuck(): Quackable
    abstract fun createRedheadDuck(): Quackable
    abstract fun createDecoyDuck(): Quackable
    abstract fun createRubberDuck(): Quackable
}

class CountingDuckFuctory : AbstractDuckFactory() {

    override fun createMallardDuck(): Quackable = QuackCounter(MallardDuck())
    override fun createRedheadDuck(): Quackable = QuackCounter(RedheadDuck())
    override fun createDecoyDuck(): Quackable = QuackCounter(DecoyDuck())
    override fun createRubberDuck(): Quackable = QuackCounter(RubberDuck())
}

class Flock : Quackable { // composite (not represented in this repo)

    private val quackers: ArrayList<Quackable> = arrayListOf()
    private val observable = Observable(this)

    fun add(quacker: Quackable) = quackers.add(quacker)

    fun addTimes(factoryMethod: () -> Quackable, times: Int) {
        for (i in 0 until times) quackers.add(factoryMethod())
    }

    override fun quack() { // iterator
        val iterator = quackers.iterator()
        while (iterator.hasNext()) iterator.next().quack()
    }

    override fun register(observer: Observer): Boolean {
        val iterator = quackers.iterator()
        while (iterator.hasNext()) iterator.next().register(observer)
        return true
    }
    override fun notifyObservers() = observable.notifyObservers()
}

class Observable(private val duck: QuackObservable) : QuackObservable {

    private val observers: ArrayList<Observer> = arrayListOf()

    override fun register(observer: Observer) = observers.add(observer)

    override fun notifyObservers() {
        val iterator = observers.iterator()
        while (iterator.hasNext()) iterator.next().update(duck)
    }
}

class Quackologist : Observer {

    override fun update(duck: QuackObservable) = println("Quackologist: $duck just quacked.")
}

class MallardDuck : Quackable {

    private val observable = Observable(this)

    override fun quack() {
        println("\'Quack!\'")
        notifyObservers()
    }

    override fun register(observer: Observer): Boolean = observable.register(observer)
    override fun notifyObservers() = observable.notifyObservers()
    override fun toString(): String = "Mallard duck"
}

class RedheadDuck : Quackable {

    private val observable = Observable(this)

    override fun quack() {
        println("\'Quack!\'")
        notifyObservers()
    }

    override fun register(observer: Observer): Boolean = observable.register(observer)
    override fun notifyObservers() = observable.notifyObservers()
    override fun toString(): String = "Redhead duck"
}

class DecoyDuck : Quackable {

    private val observable = Observable(this)

    override fun quack() {
        println("\'Kwak!\'")
        notifyObservers()
    }

    override fun register(observer: Observer): Boolean = observable.register(observer)
    override fun notifyObservers() = observable.notifyObservers()
    override fun toString(): String = "Decoy duck"
}

class RubberDuck : Quackable {

    private val observable = Observable(this)

    override fun quack() {
        println("\'Squeak!\'")
        notifyObservers()
    }

    override fun register(observer: Observer): Boolean = observable.register(observer)
    override fun notifyObservers() = observable.notifyObservers()
    override fun toString(): String = "Rubber duck"
}

class Goose {
    fun honk() = println("\'Honk!\'")
}

class GooseAdapter(private val goose: Goose) : Quackable { // adapter

    private val observable = Observable(this)

    override fun quack() {
        goose.honk()
        notifyObservers()
    }

    override fun register(observer: Observer): Boolean = observable.register(observer)
    override fun notifyObservers() = observable.notifyObservers()
    override fun toString(): String = "Goose in duck\'s clothing"
}

