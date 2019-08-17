package state

interface State {
    fun insertCard()
    fun removeCard()
    fun withdrawCash(amount: Int)
}

interface StateMachine : State {
    fun setState(state: State) : StateMachine
}

class NoCardState(private val atm: ATM) : State {

    override fun insertCard() {
        println("SUCCESS: Card inserted.")
        atm.setState(atm.hasCardState)
        atm.hasCard = true
    }
    override fun removeCard() = println("ERROR: No card.")
    override fun withdrawCash(amount: Int) = println("ERROR: No card")
}

class HasCardState(private val atm: ATM) : State {

    override fun insertCard() = println("ERROR: Card is already inserted.")
    override fun removeCard() {
        println("SUCCESS: Card removed.")
        atm.setState(atm.noCardState)
        atm.hasCard = false
    }
    override fun withdrawCash(amount: Int) {
        if (atm.cashAmount >= amount) {
            atm.cashAmount -= amount
            println("SUCCESS: Cash withdrawn.")
        }
        else
            println("ERROR: Insufficient funds.")
    }
}

class ATM(cashAmount: Int) : StateMachine {

    val noCardState: NoCardState = NoCardState(this)
    val hasCardState: HasCardState = HasCardState(this)

    private var state: State = noCardState

    var hasCard: Boolean = false
    var cashAmount: Int = 0

    init { this.cashAmount = cashAmount }

    override fun insertCard() = state.insertCard()
    override fun removeCard() = state.removeCard()
    override fun withdrawCash(amount: Int) = state.withdrawCash(amount)

    override fun setState(state: State) : StateMachine =
        apply { this.state = state }
}