package observer

import kotlin.properties.Delegates

class Observer {

    interface Observable {

        fun onTextChanged(oldText: String, newText: String)
    }

    class TextChangedListener : Observable {

        override fun onTextChanged(oldText: String, newText: String) {
            println("Text has been changed: \'$oldText\' -> \'$newText\'")
        }
    }

    class TextObserver {

        var listener: TextChangedListener? = null
        var text: String by Delegates.observable("-empty-") { _, old, new ->
            listener?.onTextChanged(old, new)
        }
    }
}