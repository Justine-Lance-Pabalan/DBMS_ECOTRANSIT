package dbms;

public abstract class Vehicle implements EcoFriendly {
    private String model;
    private int year;
    private double fuelEfficiency;

    public Vehicle(String model, int year, double fuelEfficiency) {
        this.model = model;
        this.year = year;
        this.fuelEfficiency = fuelEfficiency;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getFuelEfficiency() {
        return fuelEfficiency;
    }

    public void setFuelEfficiency(double fuelEfficiency) {
        this.fuelEfficiency = fuelEfficiency;
    }

    @Override
    public abstract double calculateCarbonFootprint();

    // Abstract method to be implemented by subclasses if needed
    public abstract double getBatteryCapacity();
}
