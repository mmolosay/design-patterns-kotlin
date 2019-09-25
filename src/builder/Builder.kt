package builder

import java.io.File

/**
 * Created by ordogod on 25.09.2019.
 **/

class Result {

    fun setTitle(text: String) = println("Setting title -> $text.")
    fun setTitleColor(color: String) = println("Setting title color -> $color.")
    fun setInfo(text: String) = println("Setting info -> $text.")
    fun setInfoColor(color: String) = println("Setting info color -> $color.")
    fun setIcon(iconBytes: ByteArray) = println("Setting image -> ${iconBytes.size} bytes.")
    fun show() = println("Showing result.")
}

class ColoredText {
    var text: String = ""
    var color: String = "#000000"
}

class ResultBuilder() {

    constructor(init: ResultBuilder.() -> Unit) : this() {
        init()
    }

    private var titleView = ColoredText()
    private var infoView = ColoredText()
    private var icon = File("")

    fun title(setter: ColoredText.() -> Unit) {
        titleView = ColoredText().apply(setter)
    }

    fun info(setter: ColoredText.() -> Unit) {
        infoView = ColoredText().apply(setter)
    }

    fun icon(block: () -> File) {
        icon = block()
    }

    fun build(): Result {
        val result = Result()

        titleView.apply {
            result.setTitle(text)
            result.setTitleColor(color)
        }

        infoView.apply {
            result.setInfo(text)
            result.setInfoColor(color)
        }

        icon.apply {
            result.setIcon(readBytes())
        }

        return result
    }
}

fun result(init: ResultBuilder.() -> Unit): Result = ResultBuilder(init).build()

