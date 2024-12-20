package dbms;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TransportationManagementSystem tms = new TransportationManagementSystem();

        while (true) {
            try {
                System.out.println("\nChoose an option:");
                System.out.println("1. Add Electric Vehicle");
                System.out.println("2. Add Hybrid Vehicle");
                System.out.println("3. Display Fleet");
                System.out.println("4. Display Total Emissions");
                System.out.println("5. Remove a Vehicle");
                System.out.println("6. Exit\n");
                System.out.print("Your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume the newline left over

                switch (choice) {
                    case 1:
                        System.out.println("\n--- Electric Vehicle ---");
                        System.out.print("Enter model: ");
                        String model = scanner.nextLine();
                        System.out.print("Enter year: ");
                        int year = scanner.nextInt();
                        System.out.print("Enter fuel efficiency (km/l): ");
                        double efficiency = scanner.nextDouble();
                        System.out.print("Enter battery capacity (kWh): ");
                        double batteryCapacity = scanner.nextDouble();

                        // Creating an ElectricVehicle instance and adding it to the system
                        tms.addVehicle(new ElectricVehicle(model, year, efficiency, batteryCapacity));
                        System.out.println("\nElectric vehicle added successfully.");
                        break;

                    case 2:
                        System.out.print("\nEnter model: ");
                        model = scanner.nextLine();
                        System.out.print("Enter year: ");
                        year = scanner.nextInt();
                        System.out.print("Enter fuel efficiency (km/l): ");
                        efficiency = scanner.nextDouble();
                        System.out.print("Enter emissions rate (g/km): ");
                        double emissionsRate = scanner.nextDouble();

                        // Creating a HybridVehicle instance and adding it to the system
                        tms.addVehicle(new HybridVehicle(model, year, efficiency, emissionsRate));
                        System.out.println("\nHybrid vehicle added successfully.");
                        break;

                    case 3:
                        tms.displayFleet();
                        break;

                    case 4:
                        tms.displayFleetEmissions();
                        break;

                    case 5:
                        tms.removeVehicle(scanner);
                        break;

                    case 6:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("\nError: Invalid input. Please try again.");
                scanner.nextLine();  // Clear the buffer
            }
        }
    }
}

