# DBMS_ECOTRANSIT
Eco Transit - Transportation Management System

The **Eco Transit** project is a Transportation Management System (TMS) designed to manage a fleet of vehicles, including both **electric** and **hybrid** types. The system allows users to add vehicles, remove them, and display the entire fleet with relevant details like fuel efficiency, battery capacity, and emissions rate. The project uses a MySQL database to store and manage vehicle data, ensuring efficient tracking and retrieval. The system is designed to support operations such as adding vehicles, calculating fleet carbon footprints, and updating vehicle information based on user input.

The program offers a simple command-line interface for interaction, allowing users to input vehicle details (model, year, fuel efficiency, battery capacity, emissions rate) and add these vehicles to the fleet. It also allows users to view the current fleet, including all vehicle types and their respective details. Furthermore, users can remove vehicles from the database, ensuring that the system stays up to date and accurate. Emissions data for hybrid vehicles is also calculated, making it easy to track the fleet's environmental impact.

To initialize the project, a `init.sql` file sets up the necessary database tables, including `vehicles`, `electric_vehicles`, and `hybrid_vehicles`. The system ensures that data integrity is maintained by using foreign key constraints and allows for seamless addition or removal of vehicles. With clear output messages and a user-friendly interface, this project serves as a complete solution for managing a modern, eco-friendly transportation fleet.