package abstractfactory

import java.lang.IllegalArgumentException

interface Vehicle

class Car : Vehicle
class Bicycle : Vehicle

abstract class VehicleFactory {

    abstract fun makeVehicle() : Vehicle

    companion object {
        inline fun <reified T: Vehicle> createFactory() : VehicleFactory {
            return when(T::class) {
                Car::class     -> CarFactory()
                Bicycle::class -> BicycleFactory()
                else           -> throw IllegalArgumentException()
            }
        }
    }
}

class CarFactory : VehicleFactory() {
    override fun makeVehicle(): Vehicle = Car()
}

class BicycleFactory : VehicleFactory() {
    override fun makeVehicle(): Vehicle = Bicycle()
}