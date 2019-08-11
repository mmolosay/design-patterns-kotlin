package command

import java.util.Random

interface ExecuteCommand {
    fun execute(passDigit: Int) : Boolean
}

interface InformCommand {
    fun inform()
}

class InputCommand(val digit: Int) : InformCommand, ExecuteCommand {

    override fun inform() = println("Digit $digit was pressed.")

    override fun execute(passDigit: Int) : Boolean {
        if (digit == passDigit) return true
        return false
    }
}

class Vault {

    private val rnd = Random()

    private val pass = arrayOf(
            rnd.nextInt(10),
            rnd.nextInt(10),
            rnd.nextInt(10),
            rnd.nextInt(10)
    )
    private val input = arrayListOf<InputCommand>()

    fun enter(digit: Int) : Vault =
            apply {
                with ( InputCommand(digit) ) {
                    input.add(this)
                    inform()
                }
            }

    fun confirm(): Boolean {
        var success = true
        for (i in 0 until pass.size)
            with(input[i]) {
                if (this.execute(pass[i]))
                    println("$digit -> OK")
                else {
                    println("$digit -> WRONG")
                    success = false
                }
            }
        input.clear()
        return success
    }
}