package dbms;

public class ElectricVehicle extends Vehicle {
    private double batteryCapacity;

    public ElectricVehicle(String model, int year, double fuelEfficiency, double batteryCapacity) {
        super(model, year, fuelEfficiency);  // Calls the constructor of the Vehicle class
        this.batteryCapacity = batteryCapacity;
    }

    @Override
    public double calculateCarbonFootprint() {
        return 0; // Zero direct emissions for electric vehicles
    }

    @Override
    public double getBatteryCapacity() {
        return batteryCapacity; // Return battery capacity for electric vehicle
    }

    public void setBatteryCapacity(double batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }
}
