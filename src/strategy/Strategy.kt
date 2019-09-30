package strategy

interface QuackBehavior {

    fun quack(): String

    // not necessary, just simulates SAM conversion
    companion object {
        inline operator fun invoke(crossinline block: () -> String) =
            object : QuackBehavior { override fun quack(): String = block() }
    }
}

class Duck(private var quackBehavior: QuackBehavior) {

    fun performQuack(): String = quackBehavior.quack()

    fun setQuackBehavior(quackBehavior: QuackBehavior) : Duck = apply {
        this.quackBehavior = quackBehavior
    }
}