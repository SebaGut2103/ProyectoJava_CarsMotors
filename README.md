# 🚗 Sistema de taller automotriz CarMotors

Este proyecto implementa el **Sistema de Taller Automotriz CarMotors**, diseñado para gestionar:

* Inventario de repuestos  
* Servicios de mantenimiento  
* Clientes y vehículos  
* Facturación electrónica  
* Proveedores  
*Actividades especiales

El sistema está desarrollado en **Java** utilizando **Maven** y sigue la arquitectura basada en componentes **MVC (Modelo - Vista - Controlador)**, incluyendo documentación completa (diagramas ER, diagramas de clases, scripts SQL, etc.).

---

## 📑 Descripción general del sistema

El objetivo principal es gestionar eficientemente todas las operaciones de un taller automotriz. El sistema proporciona:

*Matrícula de clientes y vehículos  
* Gestión de órdenes de servicio  
* Seguimiento de servicios y repuestos usados  
* Generación de factura electrónica  
* Administración de proveedores y compras  
* Ejecución de actividades especiales (campañas, inspecciones, etc.)

Además, el sistema se conecta a una base de datos MySQL, lo que garantiza la persistencia y la escalabilidad.

---

## ⚙️ Arquitectura del proyecto

* **Modelo (`modelo/`):**
  * `Cliente.java`
  * `DetalleServicio.java`
  * `Factura.java`
  * `OrdenServicio.java`
  * `Proveedor.java`
  * `Repuesto.java`
  * `RepuestoUsado.java`
  * `Servicio.java`
  * `Vehiculo.java`

* **Base de datos (`base de datos/`):**
  * `DatabaseConnection.java` → Clase Singleton para administrar la conexión MySQL.

* **Controlador (`controlador/`):**
  * `ClienteController.java`
  * `FacturaController.java`
  * `OrdenServicioController.java`
  * `ProveedorController.java`
  * `RepuestoController.java`

* **Ver (`view/`):**
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

## 🏗️ Estructura actual del proyecto

```
fuente/
└── principal/
└── java/
└── com/carsmotors/
├── modelo/
│ ├── Cliente.java
│ ├── DetalleServicio.java
│ ├── Factura.java
│ ├── OrdenServicio.java
│ ├── Proveedor.java
│ ├── Repuesto.java
│ ├── RepuestoUsado.java
│ ├── Servicio.java
│ └── Vehiculo.java
└── base de datos/
└── Conexión a base de datos.java
```

---

## 🔌 Conexión a la base de datos (`DatabaseConnection.java`)

El sistema utiliza el patrón **Singleton** para garantizar solo una instancia de conexión a la base de datos.

``Java
clase pública DatabaseConnection {
    instancia privada de DatabaseConnection estática;
    Conexión privada conexión;
    privada estática final String URL = "jdbc:mysql://localhost:3306/taller_automotriz";
    cadena privada estática final USUARIO = "root";
    cadena privada estática final CONTRASEÑA = "oscar2429";

    Conexión de base de datos privada() {
        intentar {
            Clase.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el controlador MySQL: " + e.getMessage());
        }
    }

    pública estática sincronizada DatabaseConnection getInstance() {
        si (instancia == null) {
            instancia = nueva ConexiónDeBaseDeDatos();
        }
        devolver instancia;
    }

    Conexión pública getConnection() {
        intentar {
            si (conexión == null || conexión.isClosed()) {
                conexión = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
                System.out.println("Conexión a la base de datos establecida");
            }
        } captura (SQLException e) {
            System.err.println("Error al conectarse a la base de datos: " + e.getMessage());
        }
        conexión de retorno;
    }

    público void cerrarConexión() {
        intentar {
            si (conexión != null && !conexión.isClosed()) {
                conexión.close();
                System.out.println("Conexión a la base de datos cerrada");
            }
        } captura (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
```

## 🗄️ Modelo de base de datos (MySQL)
* **Incluye las siguientes tablas:**

  * `cliente`
 
  * `vehículo`
 
  * `técnico`
 
  * `servicio`
 
  * `orden_servicio`
 
  * `detalle_orden_servicio`
 
  * `repuesto`
 
  *factura
 
  * `proveedor`
 
  * `lote`
 
  * `orden_compra`
 
  * `campana`
 
  * `cita_campana`
 
  * `inspección`

Incluye claves primarias, claves externas, restricciones y enumeraciones para garantizar la integridad de los datos.


