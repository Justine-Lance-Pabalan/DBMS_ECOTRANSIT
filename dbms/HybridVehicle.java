package dbms;

public class HybridVehicle extends Vehicle {
    private double emissionsRate;

    public HybridVehicle(String model, int year, double fuelEfficiency, double emissionsRate) {
        super(model, year, fuelEfficiency);
        this.emissionsRate = emissionsRate;
    }

    @Override
    public double calculateCarbonFootprint() {
        return emissionsRate;
    }

    public double getEmissionsRate() {
        return emissionsRate;
    }

    public void setEmissionsRate(double emissionsRate) {
        this.emissionsRate = emissionsRate;
    }

    @Override
    public double getBatteryCapacity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}