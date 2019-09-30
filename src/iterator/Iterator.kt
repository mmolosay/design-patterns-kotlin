package iterator

interface IteratorBehavior {
    fun hasNext() : Boolean
    fun next() : Any
    fun reset()
}

class CollectionItem(
        private var name: String,
        private var description: String
) {
    override fun toString(): String = "$name: $description"
}

class Iterator(private val items: ArrayList<CollectionItem>) : IteratorBehavior {

    private var position = 0

    override fun hasNext(): Boolean = (position < items.size)
    override fun next(): Any = items[position++]
    override fun reset() { position = 0 }
}

class Collection() {

    constructor(_items: ArrayList<CollectionItem>) : this() {
        items = _items
    }

    private var items = arrayListOf<CollectionItem>()

    fun createIterator() : IteratorBehavior = Iterator(items)
}