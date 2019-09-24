# Design Patterns In Kotlin
Inspired by [Design-Patterns-In-Kotlin](https://github.com/dbacinski/Design-Patterns-In-Kotlin#behavioral "Design-Patterns-In-Kotlin") by [dbacinski](https://github.com/dbacinski "dbacinski"), be sure to check him out!

*You can move to Kotlin file by clicking on a pattern title.*

## Table of Contents

* [Behavioral Patterns](#behavioral)
    * [Observer / Listener](#observer--listener)
    * [Strategy](#strategy)
    * [Command](#command)
    * [State](#state)
    * [Iterator](#iterator)
    * [Template Method](#template-method)
* [Creational Patterns](#creational)
    * [Factory Method](#factory-method)
    * [Singleton](#singleton)
    * [Abstract Factory](#abstract-factory)
* [Structural Patterns](#structural)
    * [Adapter](#adapter)
    * [Decorator](#decorator)
    * [Facade](#facade)

Behavioral
========
> In software engineering, behavioral design patterns are design patterns that identify common communication patterns between objects and realize these patterns. By doing so, these patterns increase flexibility in carrying out this communication.
>
> **Source:** [wikipedia.org](https://en.wikipedia.org/wiki/Behavioral_pattern "wikipedia.org")

[Observer / Listener](/src/observer/Observer.kt)
--------
> The observer pattern has an object (called the subject), which maintains a list of its dependents, called observers, and notifies them automatically of any state changes, usually by calling one of their methods.
> In Kotlin, this pattern could be implemented using **delegates**.
>
> **Source:** [wikipeadia.org](https://en.wikipedia.org/wiki/Observer_pattern "wikipeadia.org")
>
>  **More about delegation:** [kotlinlang.org](https://kotlinlang.org/docs/reference/delegation.html "Delegation")

#### Example

```kotlin
interface Observable {

    fun onTextChanged(oldText: String, newText: String)
}

class TextChangedListener : Observable {

    override fun onTextChanged(oldText: String, newText: String) {
        println("Text has been changed: \'$oldText\' -> \'$newText\'")
    }
}

class TextObserver {

    var listener: TextChangedListener? = null
    var text: String by Delegates.observable("-empty-") { _, old, new ->
        listener?.onTextChanged(old, new)
    }
}
```

#### Usage

```kotlin
    val textObserver = TextObserver().apply {
        listener = TextChangedListener()
    }

    with(textObserver) {
        text = "Observer"
        text = "pattern"
        text = "demonstration"
    }
```

#### Output

```
Text is changed <empty> -> Observer
Text is changed Observer -> pattern
Text is changed pattern    -> demonstration
```

[Strategy](/src/strategy/Strategy.kt)
-----------

> The strategy pattern enables selecting an algorithm at runtime. Instead of implementing a single algorithm directly, code receives run-time instructions as to which in a family of algorithms to use.
>
> **Source:** [wikipedia.org](https://en.wikipedia.org/wiki/Strategy_pattern "wikipedia.org")

#### Example
```kotlin
interface QuackBehavior {

    fun quack(): String
}

class Duck(private var quackBehavior: QuackBehavior) {

    fun performQuack(): String = quackBehavior.quack()

    fun setQuackBehavior(quackBehavior: QuackBehavior) : Duck = apply {
        this.quackBehavior = quackBehavior
    }
}
```

#### Usage

```kotlin
    val commonQuackBehavior = object : QuackBehavior { override fun quack(): String = "\'Quack!\'" }
    val rubberQuackBehavior = object : QuackBehavior { override fun quack(): String = "\'Squeeeeek\'" }
    val silentQuackBehavior = object : QuackBehavior { override fun quack(): String = "\'...\'" }

    val mallardDuck = Duck(commonQuackBehavior)
    val rubberDuck = Duck(rubberQuackBehavior)
    val toyDuck = Duck(silentQuackBehavior)
    val decoyDuck = Duck(commonQuackBehavior)
	
	// printing quack behaviors

    toyDuck.setQuackBehavior(commonQuackBehavior)
    rubberDuck.setQuackBehavior(silentQuackBehavior)
	
	// printing changed quack behaviors
```

#### Output

```
Mallard duck says 'Quack!'.
Rubber duck says 'Squeeeeek' when it is squeezed.
Toy duck says '...'. Seems like it is a primitive toy.
Decoy duck says 'Quack!', hunter is here.

Toy duck now says 'Quack!' like a real one!
Rubber duck says '...'. Looks like it is broken.
```

[Command](/src/command/Command.kt)
-----------

> The command pattern is one in which an object is used to encapsulate all information needed to perform an action or trigger an event at a later time.
>
> **Source:** [wikipedia.org](https://en.wikipedia.org/wiki/Command_pattern "wikipedia.org")

#### Example

```kotlin
interface ExecuteCommand {
    fun execute(passDigit: Int) : Boolean
}

interface InformCommand {
    fun inform()
}

class InputCommand(private val digit: Int) : InformCommand, ExecuteCommand {

    override fun inform() = println("Digit $digit was pressed.")

    override fun execute(passDigit: Int) : Boolean =
        if (digit == passDigit) {
            println("$digit -> OK")
            true
        }
        else {
            println("$digit -> WRONG")
            false
        }
}

class Vault {

    private val rnd = Random()

    private val pass = arrayOf( rnd.nextInt(10), rnd.nextInt(10), rnd.nextInt(10), rnd.nextInt(10) )
    private val input = arrayListOf<InputCommand>()

    fun enter(digit: Int): Vault = apply {
        if (digit / 10 != 0)
            throw IllegalArgumentException("Please, enter a digit, not a number.")
        with(InputCommand(digit)) {
            input.add(this)
            inform()
        }
    }

    fun confirm(): Boolean {
        var success = true

        for (i in pass.indices)
            if (!input[i].execute(pass[i]))
                success = false

        input.clear()
        return success
    }
}
```

#### Usage

```kotlin
    val vault = Vault()

    while (true) {
        vault
                .enter(getInput("Enter code digit:"))
                .enter(getInput("Enter code digit:"))
                .enter(getInput("Enter code digit:"))
                .enter(getInput("Enter code digit:"))

        if (vault.confirm()) break
    }

    println("Cracked!")
```

#### Output

```
Output is long enough because the pattern is implemented as a game, pull the repository and try it yourself!
```

[State](/src/state/State.kt)
-----------

> The state pattern allows an object to alter its behavior when its internal state changes. This pattern is close to the concept of finite-state machines.
>
> **Source:** [wikipedia.org](https://en.wikipedia.org/wiki/State_pattern "wikipedia.org")

#### Example

```kotlin
interface State {
    fun insertCard()
    fun removeCard()
    fun withdrawCash(amount: Int)
}

interface StateMachine : State {
    fun setState(state: State) : StateMachine
}

class NoCardState(private val atm: ATM) : State {

    override fun insertCard() {
        println("SUCCESS: Card inserted.")
        atm.setState(atm.hasCardState)
        atm.hasCard = true
    }
    override fun removeCard() = println("ERROR: No card.")
    override fun withdrawCash(amount: Int) = println("ERROR: No card")
}

class HasCardState(private val atm: ATM) : State {

    override fun insertCard() = println("ERROR: Card is already inserted.")
    override fun removeCard() {
        println("SUCCESS: Card removed.")
        atm.setState(atm.noCardState)
        atm.hasCard = false
    }
    override fun withdrawCash(amount: Int) {
        if (atm.cashAmount >= amount) {
            atm.cashAmount -= amount
            println("SUCCESS: Cash withdrawn.")
        }
        else
            println("ERROR: Insufficient funds.")
    }
}

class ATM(cashAmount: Int) : StateMachine {

    val noCardState: NoCardState = NoCardState(this)
    val hasCardState: HasCardState = HasCardState(this)

    private var state: State = noCardState

    var hasCard: Boolean = false
    var cashAmount: Int = 0

    init { this.cashAmount = cashAmount }

    override fun insertCard() = state.insertCard()
    override fun removeCard() = state.removeCard()
    override fun withdrawCash(amount: Int) = state.withdrawCash(amount)

    override fun setState(state: State) : StateMachine = apply { this.state = state }
}
```

#### Usage

```kotlin
    val atm = ATM(5000)

    atm.removeCard()
    atm.withdrawCash(1000)
    atm.insertCard()
    atm.removeCard()
    atm.insertCard()
    atm.withdrawCash(1000)
    atm.withdrawCash(5000)
    atm.removeCard()
```

#### Output

```
ERROR: No card.
ERROR: No card
SUCCESS: Card inserted.
SUCCESS: Card removed.
SUCCESS: Card inserted.
SUCCESS: Cash withdrawn.
ERROR: Insufficient funds.
SUCCESS: Card removed.
```

[Iterator](/src/iterator/Iterator.kt)
-----------

> The iterator pattern is used to traverse a container and access the container's elements.
>
> **Source:** [wikipedia.org](https://en.wikipedia.org/wiki/Iterator_pattern "wikipedia.org")

#### Example

```kotlin
interface IteratorBehavior {
    fun hasNext() : Boolean
    fun next() : Any
    fun reset()
}

class CollectionItem(
        var name: String,
        var description: String
) {
    override fun toString(): String = "$name: $description"
}

class Iterator(private val items: ArrayList<CollectionItem>) : IteratorBehavior {

    private var position = 0

    override fun hasNext(): Boolean = (position < items.size)
    override fun next(): Any = items[position++]
    override fun reset() { position = 0 }
}

class Collection() {

    constructor(_items: ArrayList<CollectionItem>) : this() {
        items = _items
    }

    private var items = arrayListOf<CollectionItem>()

    fun createIterator() : IteratorBehavior = Iterator(items)
}
```

#### Usage

```kotlin
    val collection = Collection(arrayListOf(
            CollectionItem("apple", "red"),
            CollectionItem("lemon", "yellow"),
            CollectionItem("onion", "white")
    ))
    val iterator = collection.createIterator()

    while (iterator.hasNext()) println(iterator.next())
```

#### Output

```
apple: red
lemon: yellow
onion: white
```

[Template Method](/src/templatemethod/TemplateMethod.kt)
-----------

>The template method prepresents a method in a superclass, usually an abstract superclass, that defines the skeleton of an operation in terms of a number of high-level steps. These steps are themselves implemented by additional helper methods in the same class as the template method.
>
> **Source:** [wikipedia.org](https://en.wikipedia.org/wiki/Template_method_pattern "wikipedia.org")

#### Example

```kotlin
abstract class CaffeineBeverage {

    fun make() {
        boilWater()
        putIngredient()
        brew()
        addCondiments()
    }

    open fun boilWater() = println("Water has just boiled.")

    abstract fun putIngredient()

    open fun brew() = println("Water was poured into a mug and brewed a beverage.")

    abstract fun addCondiments()
}

class Coffee : CaffeineBeverage() {

    override fun putIngredient() = println("Coffee was put into a mug.")
    override fun addCondiments() = println("A bit of sugar will make this coffee better.")
}

class Tea : CaffeineBeverage() {

    override fun putIngredient() = println("Black or green..? Let it be black.")
    override fun addCondiments() = println("There is no good black tea without a slice of lemon.")
}
```

#### Usage

```kotlin
    val coffee = Coffee()
    val tea = Tea()

    println("Making coffee:")
    coffee.make()

    println("Making tea:")
    tea.make()
```

#### Output

```
Making coffee:
Water has just boiled.
Coffee was put into a mug.
Water was poured into a mug and brewed a beverage.
A bit of sugar will make this coffee better.

Making tea:
Water has just boiled.
Black or green..? Let it be black.
Water was poured into a mug and brewed a beverage.
There is no good black tea without a slice of lemon.
```

Creational
==========

> In software engineering, creational design patterns are design patterns that deal with object creation mechanisms, trying to create objects in a manner suitable to the situation. The basic form of object creation could result in design problems or added complexity to the design. Creational design patterns solve this problem by somehow controlling this object creation.
>
>**Source:** [wikipedia.org](http://en.wikipedia.org/wiki/Creational_pattern)

[Factory Method](/src/factorymethod/FactoryMethod.kt)
-----------

> The factory method pattern uses factory methods to deal with the problem of creating objects without having to specify the exact class of the object that will be created. This is done by creating objects by calling a factory method – either specified in an interface and implemented by child classes, or implemented in a base class and optionally overridden by derived classes – rather than by calling a constructor.
>
> **Source:** [wikipedia.org](https://en.wikipedia.org/wiki/Factory_method_pattern "wikipedia.org")

#### Example

```kotlin
interface Country {
    val name: String
}

class USA(override val name: String = "United States of America") : Country
class Canada(override val name: String = "Canada") : Country
class Russia(override val name: String = "Russia") : Country

enum class City {
    NewYork, Chicago, Toronto, Moscow, SaintPetersburg, Muhosransk
}

class CountryFactory {
    fun fromCity(city: City) : Country? {
        return when(city) {
            City.NewYork, City.Chicago        -> USA()
            City.Toronto                      -> Canada()
            City.Moscow, City.SaintPetersburg -> Russia()
            else                              -> null
        }
    }
}
```

#### Usage

```kotlin
    val noCountryFound = "No contry found :("

    val nyCountry = CountryFactory().fromCity(City.NewYork)?.name ?: noCountryFound
    val moscowCountry = CountryFactory().fromCity(City.Moscow)?.name ?: noCountryFound
    val borisovCountry = CountryFactory().fromCity(City.Muhosransk)?.name ?: noCountryFound

    println("New York —> $nyCountry")
    println("Moscow —> $moscowCountry")
    println("Muhosransk —> $borisovCountry")
```

#### Output

```
New York     –> United States of America
Moscow       –> Russia
Muhosransk –> No contry found :(
```

[Singleton](/src/singleton/Singleton.kt)
-----------

> The singleton pattern restricts the instantiation of a class to one "single" instance. This is useful when exactly one object is needed to coordinate actions across the system.
> Like delegates, this pattern is embedded in Kotlin as an `object` expression.
>
> **Source:** [wikipedia.org](https://en.wikipedia.org/wiki/Factory_method_pattern "wikipedia.org")
>
> **More about objects:** [kotlinlang.org](https://kotlinlang.org/docs/reference/object-declarations.html "kotlinlang.org")

#### Example

```kotlin
object MouseController {

    var coords = Pair(0, 0)
        get() {
            println("Getting coordingates of $this -> (${field.first}, ${field.second})")
            return field
        }
        set(value) {
            println("Setting coordinates of $this -> (${value.first}, ${value.second})")
            field = value
        }

    init { println("Initializing $this") }
}
```

#### Usage

```kotlin
    val coords = MouseController.coords
    MouseController.coords = Pair(5, 10)
```

#### Output

```
Initializing singleton.MouseController@4c70fda8
Getting coordingates of singleton.MouseController@4c70fda8 -> (0, 0)
Setting coordinates of singleton.MouseController@4c70fda8 -> (5, 10)
```

[Abstract Factory](/src/abstractfactory/AbstractFactory.kt)
-----------

> The abstract factory pattern provides a way to encapsulate a group of individual factories that have a common theme without specifying their concrete classes.
>
> **Source:** [wikipedia.org](https://en.wikipedia.org/wiki/Abstract_factory_pattern "wikipedia.org")

#### Example

```kotlin
interface Vehicle

class Car : Vehicle
class Bicycle : Vehicle

abstract class VehicleFactory {

    abstract fun makeVehicle() : Vehicle

    companion object {
        inline fun <reified T: Vehicle> createFactory() : VehicleFactory {
            return when(T::class) {
                Car::class     -> CarFactory()
                Bicycle::class -> BicycleFactory()
                else           -> throw IllegalArgumentException()
            }
        }
    }
}

class CarFactory : VehicleFactory() {
    override fun makeVehicle(): Vehicle = Car()
}

class BicycleFactory : VehicleFactory() {
    override fun makeVehicle(): Vehicle = Bicycle()
}
```

#### Usage

```kotlin
    val vehicleFactory = VehicleFactory.createFactory<Car>()
    val vehicle = vehicleFactory.makeVehicle()
    println("Vehicle created: $vehicle")
```

#### Output

```
Vehicle created: abstractfactory.Car@6093dd95
```

Structural
==========

>In software engineering, structural design patterns are design patterns that ease the design by identifying a simple way to realize relationships between entities.
>
>**Source:** [wikipedia.org](http://en.wikipedia.org/wiki/Structural_pattern "wikipedia.org")

[Adapter](/src/adapter/Adapter.kt)
-----------

> The adapter pattern allows the interface of an existing class to be used as another interface. It is often used to make existing classes work with others without modifying their source code.
>
> **Source:** [wikipedia.org](https://en.wikipedia.org/wiki/Adapter_pattern "wikipedia.org")

#### Example

```kotlin
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
    // hunter will only shoot if the "duck" quacks and flies far
    duck.quack()
    duck.flyLongDistance()

    println("Seems like it duck. \'BANG!\'")
}
```

#### Usage

```kotlin
    val mallardDuck = MallardDuck()
    val wildTurkey = WildTurkey()
    val turkeyInDucksClothing = TurkeyAdapter(wildTurkey)

    huntDuck(mallardDuck)
  //huntDuck(wildTurkey)
  //hunter hears 'Gobble-gobble' and sees smth flies not far enough — it's turkey, not a duck!
    huntDuck(turkeyInDucksClothing) // turkey in ducks clothing cheated hunter!
```

#### Output

```
'Quack!'
Duck flies long distance.

Seems like it duck. 'BANG!'

'Gobble-gobble'
Turkey flies quite a bit.
Turkey flies quite a bit.
Turkey flies quite a bit.
Turkey flies quite a bit.
Turkey flies quite a bit.

Seems like it duck. 'BANG!'
```

[Decorator](/src/decorator/Decorator.kt)
-----------

> The decorator pattern allows behavior to be added to an individual object, dynamically, without affecting the behavior of other objects from the same class.
>
> **Source:** [wikipedia.org](https://en.wikipedia.org/wiki/Decorator_pattern "wikipedia.org")

#### Example

```kotlin
interface BreakfastComponent {
    fun addMeal()
}

class BreakfastCoffee : BreakfastComponent {

    // John ALWAYS starts morning with coffee
    override fun addMeal() = println(" and, of course, coffee")
}

abstract class Decorator(c: BreakfastComponent) : BreakfastComponent {

    private var component: BreakfastComponent = c

    override fun addMeal() = component.addMeal()

    fun makeBreakfast() = addMeal()
}

class BreakfastMeal(private val c: BreakfastComponent, private val mealName: String) : Decorator(c) {

    override fun addMeal() {
        if (c is BreakfastCoffee)
            print(mealName)
        else
            print("$mealName, ")

        super.addMeal()
    }
}
```

#### Usage

```kotlin
    val coffee = BreakfastCoffee()
    val cornflakes = BreakfastMeal(coffee, "cornflakes")
    val sandwich  = BreakfastMeal(cornflakes, "sandwich")
    val apple        = BreakfastMeal(sandwich, "apple")

    print("John's breakfast: ")
    apple.makeBreakfast()
```

#### Output

```
John's breakfast: apple, sandwich, cornflakes and, of course, coffee.
```

[Facade](/src/facade/Facade.kt)
-----------

> The facade pattern represents an object that serves as a front-facing interface masking more complex underlying or structural code.
>
> **Source:** [wikipedia.org](https://en.wikipedia.org/wiki/Facade_pattern "wikipedia.org")

#### Example

```kotlin
class PC  {
    fun start() = this
        .makeLoudNoise()
        .turnLightsOn()
        .greetUser()
        .prepareDesktop()
        .displayDesktop()

    private fun makeLoudNoise() : PC = apply {
        println("Starting to noise loudly...")
    }
    private fun turnLightsOn() : PC = apply {
        println("Turning the lights on...")
    }
    private fun greetUser() : PC = apply {
        println("Greeting the user...")
    }
    private fun prepareDesktop() : PC = apply {
        println("Preparing the desktop...")
    }
    private fun displayDesktop() : PC = apply {
        println("PC is ready!")
    }
}
```

#### Usage

```kotlin
PC().start()
```

#### Output

```
Starting to noise loudly...
Turning the lights on...
Greeting the user...
Preparing the desktop...
PC is ready!
```
