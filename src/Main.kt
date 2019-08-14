import abstractfactory.Car
import abstractfactory.VehicleFactory
import adapter.MallardDuck
import adapter.TurkeyAdapter
import adapter.WildTurkey
import adapter.huntDuck
import command.Vault
import decorator.BreakfastCoffee
import decorator.BreakfastMeal
import factorymethod.City
import factorymethod.CountryFactory
import observer.TextChangedListener
import observer.TextObserver
import singleton.MouseController
import strategy.Duck
import strategy.QuackBehavior

fun main(args: Array<String>) {
    while (true) {
        printMenu()
        toggleMenu(getInput("Select pattern to demonstrate:"))
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
        1    -> strategy()
        2    -> observer()
        3    -> decorator()
        4    -> factoryMethod()
        5    -> abstractFactory()
        6    -> singleton()
        7    -> command()
        8    -> adapter()

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
    println("Toy duck says ${toyDuck.performQuack()}. Seems like it is primitive toy.")
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
    val vault = Vault()

    while (true) {
        vault
                .enter(getInput("Enter code digit:"))
                .enter(getInput("Enter code digit:"))
                .enter(getInput("Enter code digit:"))
                .enter(getInput("Enter code digit:"))

        if (vault.confirm()){
            println("Cracked!")
            break
        }
    }
}

fun adapter() {
    val mallardDuck = MallardDuck()
    val wildTurkey = WildTurkey()
    val turkeyInDucksClothing = TurkeyAdapter(wildTurkey)

    huntDuck(mallardDuck)
  //huntDuck(wildTurkey)
  //hunter hears 'Gobble-gobble' and sees smth flies too short — it's turkey, not a duck!
    huntDuck(turkeyInDucksClothing) // turkey in ducks clothing cheated hunter!
}