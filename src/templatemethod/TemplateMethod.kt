package templatemethod

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