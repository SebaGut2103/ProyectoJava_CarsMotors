# 🚗 CarMotors Automotive Workshop System

This project implements the **CarMotors Automotive Workshop System**, designed to manage:

* Spare parts inventory  
* Maintenance services  
* Clients and vehicles  
* Electronic invoicing  
* Suppliers  
* Special activities

The system is developed in **Java** using **Maven** and follows the **MVC (Model - View - Controller)** component-based architecture, including complete documentation (ER diagrams, class diagrams, SQL scripts, etc.).

---

## 📑 System Overview

The main goal is to efficiently manage all operations of an automotive workshop. The system provides:

* Client and vehicle registration  
* Service order management  
* Tracking of services and used spare parts  
* Electronic invoice generation  
* Supplier and purchase administration  
* Execution of special activities (campaigns, inspections, etc.)

In addition, the system connects to a MySQL database, ensuring persistence and scalability.

---

## ⚙️ Project Architecture

* **Model (`model/`):**
  * `Cliente.java`
  * `DetalleServicio.java`
  * `Factura.java`
  * `OrdenServicio.java`
  * `Proveedor.java`
  * `Repuesto.java`
  * `RepuestoUsado.java`
  * `Servicio.java`
  * `Vehiculo.java`

* **Database (`database/`):**
  * `DatabaseConnection.java` → Singleton class to manage the MySQL connection.

* **Controller (`controller/`):**
  * `ClienteController.java`
  * `FacturaController.java`
  * `OrdenServicioController.java`
  * `ProveedorController.java`
  * `RepuestoController.java`

* **View (`view/`):**
  * `ClienteDialog.java`
  * `FacturaDialog.java`
  * `FacturasPanel.java`
  * `MainFrame.java`
  * `OrdenServicioDialog.java`
  * `OrdenesServicioPanel.java`
  * `ProveedorDialog.java`
  * `ProveedoresPanel.java`
  * `RepuestoDialog.java`
  * `RepuestosPanel.java`

---

## 🏗️ Current Project Structure

```
src/
└── main/
└── java/
└── com/carsmotors/
├── model/
│ ├── Cliente.java
│ ├── DetalleServicio.java
│ ├── Factura.java
│ ├── OrdenServicio.java
│ ├── Proveedor.java
│ ├── Repuesto.java
│ ├── RepuestoUsado.java
│ ├── Servicio.java
│ └── Vehiculo.java
└── database/
└── DatabaseConnection.java
```

---

## 🔌 Database Connection (`DatabaseConnection.java`)

The system uses the **Singleton** pattern to ensure only one database connection instance.

```java
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/taller_automotriz";
    private static final String USER = "root";
    private static final String PASSWORD = "oscar2429";

    private DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading MySQL driver: " + e.getMessage());
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connection established");
            }
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Error closing the connection: " + e.getMessage());
        }
    }
}
```

## 🗄️ Database Model (MySQL)
* **Includes the following tables:**

  * `cliente`
 
  * `vehiculo`
 
  * `tecnico`
 
  *  `servicio`
 
  * `orden_servicio`
 
  * `detalle_orden_servicio`
 
  * `repuesto`
 
  * `factura`
 
  * `proveedor`
 
  * `lote`
 
  * `orden_compra`
 
  * `campana`
 
  * `cita_campana`
 
  * `inspeccion`

It includes primary keys, foreign keys, constraints, and enums to ensure data integrity.


