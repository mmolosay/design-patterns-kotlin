package composite

/**
 * Created by ordogod on 25.09.2019.
 **/

open class ShopItem(name: String, price: Int) {
    open var totalPrice: Int = price
}

open class Composite(name: String) : ShopItem(name, 0) {

    private val items = ArrayList<ShopItem>()

    override var totalPrice: Int
        get() = items.sumBy { it.totalPrice }
        set(value) {}

    fun add(vararg item: ShopItem): Composite = apply {
        items.addAll(item)
    }
}

class ToothBrush : ShopItem("Tooth brush", 100)
class Rice : ShopItem("Rice", 50)
class Cookie : ShopItem("Cookie", 40)
class Check : Composite("Check")

