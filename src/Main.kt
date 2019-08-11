import abstractfactory.*
import command.*
import decorator.*
import factorymethod.*
import observer.*
import singleton.*
import strategy.*
import java.lang.Exception

fun main(args: Array<String>) {
    while (true) {
        printMenu()
        toggleMenu(getInput())
    }
}

fun printMenu() {
    println("" +
            "1. Strategy\n" +
            "2. Observer\n" +
            "3. Decorator\n" +
            "4. Factory Method\n" +
            "5. Abstract Factory\n" +
            "6. Singleton\n" +
            "7. Command\n" +
            "==============================" +
            "")
}

fun getInput(): Int {
    var input: Int
    while (true) {
        println("Select pattern to demonstrate:")
        try {
            input = readLine()!!.toInt()
        } catch (e: Exception) {
            println("Invalid input. Try again.")
            continue
        }
        return input
    }
}

fun toggleMenu(input: Int) {
    println("==============================")
    when(input) {
        1    -> strategy()
        2    -> observer()
        3    -> decorator()
        4    -> factoryMethod()
        5    -> abstractFactory()
        6    -> singleton()
        7    -> command()

        else -> println("Invalid input. Try again.")
    }
    println("==============================")
}


fun strategy() {
    val commonQuackBehavior = object : Strategy.QuackBehavior {
        override fun quack(): String = "\'Quack!\'"
    }
    val rubberQuackBehavior = object : Strategy.QuackBehavior {
        override fun quack(): String = "\'Squeeeeek\'"
    }
    val silentQuackBehavior = object : Strategy.QuackBehavior {
        override fun quack(): String = "\'...\'"
    }

    val mallardDuck = Strategy.Duck(commonQuackBehavior)
    val rubberDuck = Strategy.Duck(rubberQuackBehavior)

    val toyDuck = Strategy.Duck(silentQuackBehavior)
    val decoyDuck = Strategy.Duck(commonQuackBehavior)

    println("Mallard duck says ${mallardDuck.performQuack()}.")
    println("Rubber duck says ${rubberDuck.performQuack()} when it is squeezed.")
    println("Toy duck says ${toyDuck.performQuack()}. Seems like it is primitive toy.")
    println("Decoy duck says ${decoyDuck.performQuack()}, hunter is here.")

    toyDuck.setQuackBehavior(commonQuackBehavior)
    rubberDuck.setQuackBehavior(silentQuackBehavior)

    println("Toy duck now says ${toyDuck.performQuack()} like a real one!")
    println("Rubber duck says ${rubberDuck.performQuack()}. Looks like it is broken.")
}

fun observer() {
    val textObserver = Observer.TextObserver().apply {
        listener = Observer.TextChangedListener()
    }

    with(textObserver) {
        text = "Observer"
        text = "pattern"
        text = "demonstration"
    }
}

fun decorator() {
    val coffee = BreakfastCoffee()
    val cornflakes = BreakfastMeal(coffee, "cornflakes")
    val sandwich = BreakfastMeal(cornflakes, "sandwich")
    val apple = BreakfastMeal(sandwich, "apple")

    print("John's breakfast: ")
    apple.makeBreakfast()
}

fun factoryMethod() {
    val noCountryFound = "No contry found :("

    val nyCountry = CountryFactory().fromCity(City.NewYork)?.name ?: noCountryFound
    val moscowCountry = CountryFactory().fromCity(City.Moscow)?.name ?: noCountryFound
    val borisovCountry = CountryFactory().fromCity(City.Muhosransk)?.name ?: noCountryFound

    println("New York —> $nyCountry")
    println("Moscow —> $moscowCountry")
    println("Muhosransk —> $borisovCountry")
}

fun abstractFactory() {
    val vehicleFactory = VehicleFactory.createFactory<Car>()
    val vehicle = vehicleFactory.makeVehicle()
    println("Vehicle created: $vehicle")
}

fun singleton() {
    MouseController.setCoords(10, 25)
    MouseController.getCoords()
}

fun command() {
    Vault()
        .enter(1)
        .enter(2)
        .enter(2)
        .enter(4)
        .confirm()
}