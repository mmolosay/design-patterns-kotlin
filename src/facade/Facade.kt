package facade

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