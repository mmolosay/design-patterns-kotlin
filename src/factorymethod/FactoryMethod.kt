package factorymethod

interface Country {
    val name: String
}

class USA(override val name: String = "United States of America") : Country
class Canada(override val name: String = "Canada") : Country
class Russia(override val name: String = "Russia") : Country

enum class City {
    NewYork, Chicago, Toronto, Moscow, SaintPetersburg, Muhosransk
}

class CountryFactory {
    fun fromCity(city: City) : Country? {
        return when(city) {
            City.NewYork, City.Chicago        -> USA()
            City.Toronto                      -> Canada()
            City.Moscow, City.SaintPetersburg -> Russia()
            else                              -> null
        }
    }
}