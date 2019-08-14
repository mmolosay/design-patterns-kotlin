package strategy

interface QuackBehavior {

    fun quack(): String
}

class Duck(private var quackBehavior: QuackBehavior) {

    fun performQuack(): String = quackBehavior.quack()

    fun setQuackBehavior(quackBehavior: QuackBehavior) : Duck {
        this.quackBehavior = quackBehavior
        return this // chaining support
    }
}