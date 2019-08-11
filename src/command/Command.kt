package command

interface ExecuteCommand {
    fun execute(passDigit: Int)
}

interface InformCommand {
    fun inform()
}

class InputCommand(private val digit: Int) : InformCommand, ExecuteCommand {

    override fun inform() = println("Digit $digit was pressed.")

    override fun execute(passDigit: Int) {
        if (digit == passDigit) println("$digit -> OK")
        else println("$digit -> WRONG")
    }
}

class Vault {

    private val password = arrayOf(1, 2, 3, 4)
    private val input = arrayListOf<InputCommand>()

    fun enter(digit: Int) : Vault =
            apply {
                with (InputCommand(digit)) {
                    input.add(this)
                    inform()
                }
            }

    fun confirm() : Vault =
            apply {
                for (i in 0 until password.size)
                    input[i].execute(password[i])

                input.clear()
            }
}