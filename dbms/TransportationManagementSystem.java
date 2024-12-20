package dbms;
import java.sql.*;
import java.util.Scanner;

public class TransportationManagementSystem {
    private Connection connection;

    // Constructor: Initializes the connection to the database
    public TransportationManagementSystem() {
        try {
            String url = "jdbc:mysql://localhost:3306/transportation";
            String username = "sqluser";
            String password = "password";
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }

    // Add a vehicle to the fleet (stores it in the database)
    public void addVehicle(Vehicle vehicle) {
        String vehicleQuery = "INSERT INTO vehicles (model, year, efficiency, battery_capacity, emissions_rate) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(vehicleQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, vehicle.getModel());
            statement.setInt(2, vehicle.getYear());
            statement.setDouble(3, vehicle.getFuelEfficiency());
    
            // Check if the vehicle is Electric or Hybrid and insert accordingly
            if (vehicle instanceof ElectricVehicle ev) {
                statement.setDouble(4, ev.getBatteryCapacity());
                statement.setNull(5, Types.DOUBLE);  // Hybrid vehicles don't need emissions_rate
            } else if (vehicle instanceof HybridVehicle hv) {
                statement.setNull(4, Types.DOUBLE);  // Electric vehicles don't need battery_capacity
                statement.setDouble(5, hv.getEmissionsRate());
            }
    
            // Execute the update for the vehicle table
            statement.executeUpdate();
    
            // Retrieve the generated vehicle ID (for use in child tables)
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int vehicleId = generatedKeys.getInt(1);
    
                // Now, insert into the appropriate child table
                if (vehicle instanceof ElectricVehicle ev) {
                    String electricVehicleQuery = "INSERT INTO electric_vehicles (vehicle_id, battery_capacity) VALUES (?, ?)";
                    try (PreparedStatement electricStatement = connection.prepareStatement(electricVehicleQuery)) {
                        electricStatement.setInt(1, vehicleId);
                        electricStatement.setDouble(2, ev.getBatteryCapacity());
                        electricStatement.executeUpdate();
                    }
                } else if (vehicle instanceof HybridVehicle hv) {
                    String hybridVehicleQuery = "INSERT INTO hybrid_vehicles (vehicle_id, emissions_rate) VALUES (?, ?)";
                    try (PreparedStatement hybridStatement = connection.prepareStatement(hybridVehicleQuery)) {
                        hybridStatement.setInt(1, vehicleId);
                        hybridStatement.setDouble(2, hv.getEmissionsRate());
                        hybridStatement.executeUpdate();
                    }
                }
    
                System.out.println("Vehicle added successfully to the database.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding vehicle to DB: " + e.getMessage());
        }
    }
    
    // Display all vehicles in the fleet
    public void displayFleet() {
        String query = "SELECT * FROM vehicles";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                System.out.println("No vehicles in the fleet.");
                return;
            }

            do {
                String model = resultSet.getString("model");
                int year = resultSet.getInt("year");
                double efficiency = resultSet.getDouble("efficiency");
                double batteryCapacity = resultSet.getDouble("battery_capacity");
                double emissionsRate = resultSet.getDouble("emissions_rate");

                System.out.println("Model: " + model + ", Year: " + year + ", Fuel Efficiency: " + efficiency + " km/l");
                if (batteryCapacity > 0) {
                    System.out.println("Battery Capacity: " + batteryCapacity + " kWh");
                }
                if (emissionsRate > 0) {
                    System.out.println("Emissions Rate: " + emissionsRate + " g/km");
                }
                System.out.println("---");
            } while (resultSet.next());
        } catch (SQLException e) {
            System.out.println("Error displaying fleet: " + e.getMessage());
        }
    }

    // Display the total emissions of the fleet
    public void displayFleetEmissions() {
        String query = "SELECT SUM(emissions_rate) AS total_emissions FROM vehicles WHERE emissions_rate IS NOT NULL";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                double totalEmissions = resultSet.getDouble("total_emissions");
                System.out.println("Total Fleet Carbon Footprint: " + totalEmissions + " g/km");
            }
        } catch (SQLException e) {
            System.out.println("Error calculating fleet emissions: " + e.getMessage());
        }
    }

    // Remove a vehicle from the fleet
    public void removeVehicle(Scanner scanner) {
        if (fleetIsEmpty()) return;

        System.out.println("\n--- Current Fleet ---");
        displayFleet();
        System.out.print("\nEnter the model of the vehicle you want to remove: ");
        String model = scanner.nextLine();

        // Delete dependent records in electric_vehicles table
        String deleteElectricQuery = "DELETE FROM electric_vehicles WHERE vehicle_id IN (SELECT id FROM vehicles WHERE model = ?)";
        try (PreparedStatement statement = connection.prepareStatement(deleteElectricQuery)) {
            statement.setString(1, model);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting electric vehicle record: " + e.getMessage());
        }

        // Delete dependent records in hybrid_vehicles table
        String deleteHybridQuery = "DELETE FROM hybrid_vehicles WHERE vehicle_id IN (SELECT id FROM vehicles WHERE model = ?)";
        try (PreparedStatement statement = connection.prepareStatement(deleteHybridQuery)) {
            statement.setString(1, model);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting hybrid vehicle record: " + e.getMessage());
        }

        // Delete the vehicle from the vehicles table
        String query = "DELETE FROM vehicles WHERE model = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, model);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Vehicle with model '" + model + "' removed successfully.");
            } else {
                System.out.println("No vehicle with model '" + model + "' found in the fleet.");
            }
        } catch (SQLException e) {
            System.out.println("Error removing vehicle from DB: " + e.getMessage());
        }
    }

    // Check if the fleet is empty
    public boolean fleetIsEmpty() {
        String query = "SELECT COUNT(*) FROM vehicles";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next() && resultSet.getInt(1) == 0) {
                System.out.println("\nThe fleet is empty. No vehicles to display.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error checking if fleet is empty: " + e.getMessage());
        }
        return false;
    }

    // Close the database connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing database connection: " + e.getMessage());
        }
    }
}
