package decorator

interface BreakfastComponent {

    fun addMeal()
}

class BreakfastCoffee : BreakfastComponent {

    override fun addMeal() {
        // John ALWAYS starts morning with coffee
        println(" and, of course, coffee")
    }
}

abstract class Decorator(c: BreakfastComponent) : BreakfastComponent {

    private var component: BreakfastComponent = c

    override fun addMeal() {
        component.addMeal()
    }

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
