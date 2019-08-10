package command

interface Command {
    fun execute()
}

class RemoteController {

    private val buttons = ArrayList<Command>()

    fun addButton(onPressedAction: Command) : RemoteController =
            apply {
                buttons.add(onPressedAction)
            }

    fun pressButton(button: Int) : RemoteController =
            apply {
                buttons[button - 1].execute()
            }
}