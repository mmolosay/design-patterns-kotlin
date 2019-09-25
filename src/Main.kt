import abstractfactory.Car
import abstractfactory.VehicleFactory
import adapter.MallardDuck
import adapter.TurkeyAdapter
import adapter.WildTurkey
import adapter.huntDuck
import builder.result
import command.Vault
import complexpatterns.CountingDuckFuctory
import complexpatterns.DuckSimulator
import decorator.BreakfastCoffee
import decorator.BreakfastMeal
import facade.PC
import factorymethod.City
import factorymethod.CountryFactory
import iterator.Collection
import iterator.CollectionItem
import observer.TextChangedListener
import observer.TextObserver
import singleton.MouseController
import state.ATM
import strategy.Duck
import strategy.QuackBehavior
import templatemethod.Coffee
import templatemethod.Tea
import java.io.File

fun main(args: Array<String>) {
    while (true) {
        printMenu()
        toggleMenu(getInput("Select pattern to demonstrate: "))
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
            "8. Adapter\n" +
            "9. Facade\n" +
            "10. Template Method\n" +
            "11. Iterator\n" +
            "12. State\n" +
            "13. Builder\n" +
            "14. Complex Patterns\n" +
            "==============================" +
            "")
}

fun getInput(msg: String? = null): Int {
    var input: Int
    while (true) {
        if (msg != null) println(msg)
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
        1  -> strategy()
        2  -> observer()
        3  -> decorator()
        4  -> factoryMethod()
        5  -> abstractFactory()
        6  -> singleton()
        7  -> command()
        8  -> adapter()
        9  -> facade()
        10 -> templateMethod()
        11 -> iterator()
        12 -> state()
        13 -> builder()
        14 -> complexPatterns()

        else -> println("Invalid input. Try again.")
    }
    println("==============================")
}


fun strategy() {
    val commonQuackBehavior = object : QuackBehavior {
        override fun quack(): String = "\'Quack!\'"
    }
    val rubberQuackBehavior = object : QuackBehavior {
        override fun quack(): String = "\'Squeeeeek\'"
    }
    val silentQuackBehavior = object : QuackBehavior {
        override fun quack(): String = "\'...\'"
    }

    val mallardDuck = Duck(commonQuackBehavior)
    val rubberDuck = Duck(rubberQuackBehavior)

    val toyDuck = Duck(silentQuackBehavior)
    val decoyDuck = Duck(commonQuackBehavior)

    println("Mallard duck says ${mallardDuck.performQuack()}.")
    println("Rubber duck says ${rubberDuck.performQuack()} when it is squeezed.")
    println("Toy duck says ${toyDuck.performQuack()}. Seems like it is a primitive toy.")
    println("Decoy duck says ${decoyDuck.performQuack()}, hunter is here.")

    toyDuck.setQuackBehavior(commonQuackBehavior)
    rubberDuck.setQuackBehavior(silentQuackBehavior)

    println("Toy duck now says ${toyDuck.performQuack()} like a real one!")
    println("Rubber duck says ${rubberDuck.performQuack()}. Looks like it is broken.")
}

fun observer() {
    val textObserver = TextObserver().apply {
        listener = TextChangedListener()
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
    val noCountryFound = "No country found :("

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
    val coords = MouseController.coords
    MouseController.coords = Pair(5, 10)
}

fun command() {
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
}

fun adapter() {
    val mallardDuck = MallardDuck()
    val wildTurkey = WildTurkey()
    val turkeyInDucksClothing = TurkeyAdapter(wildTurkey)

    huntDuck(mallardDuck)
  //huntDuck(wildTurkey)
  //hunter hears 'Gobble-gobble' and sees smth flies not far enough — it's turkey, not a duck!
    huntDuck(turkeyInDucksClothing) // turkey in ducks clothing cheated hunter!
}

fun facade() = PC().start()

fun templateMethod() {
    val coffee = Coffee()
    val tea = Tea()

    println("Making coffee:")
    coffee.make()
    println()

    println("Making tea:")
    tea.make()
    println()
}

fun iterator() {
    val collection = Collection(arrayListOf(
            CollectionItem("apple", "red"),
            CollectionItem("lemon", "yellow"),
            CollectionItem("onion", "white")
    ))
    val iterator = collection.createIterator()

    while (iterator.hasNext()) println(iterator.next())
}

fun state() {
    val atm = ATM(5000)

    atm.removeCard()
    atm.withdrawCash(1000)
    atm.insertCard()
    atm.removeCard()
    atm.insertCard()
    atm.withdrawCash(1000)
    atm.withdrawCash(5000)
    atm.removeCard()
}

fun builder() {
    val result = result {
        title {
            text = "Success!"
            color = "#83e83e"
        }
        info {
            text = "You completed the quest"
            color = "#f0f8ff"
        }
        icon {
            File.createTempFile("icon", "png")
        }
    }

    result.show()
}

fun complexPatterns() {
    val duckSimulator = DuckSimulator

    duckSimulator.simulate(CountingDuckFuctory())
}