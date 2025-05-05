package com.carsmotors.main;

import com.carsmotors.view.MainFrame;
import com.carsmotors.database.DatabaseConnection;
import com.carsmotors.utils.SoundManager;

import javax.swing.*;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase principal para iniciar la aplicación
 */
public class Main {
    // Modificar el método main para verificar y crear la base de datos si no existe
    public static void main(String[] args) {
        // Establecer el Look and Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Crear directorios necesarios si no existen
        createRequiredDirectories();
        
        // Verificar la conexión a la base de datos
        try {
            // Primero intentamos conectar a MySQL sin especificar la base de datos
            Connection rootConn = null;
            try {
                // Intentar conectar a MySQL para verificar si está en ejecución
                String baseUrl = "jdbc:mysql://localhost:3306/";
                rootConn = DriverManager.getConnection(baseUrl, "root", "oscar2429");
                
                // Verificar si la base de datos existe
                ResultSet rs = rootConn.getMetaData().getCatalogs();
                boolean dbExists = false;
                
                while (rs.next()) {
                    String dbName = rs.getString(1);
                    if (dbName.equals("taller_automotriz")) {
                        dbExists = true;
                        break;
                    }
                }
                
                // Si la base de datos no existe, crearla
                if (!dbExists) {
                    int option = JOptionPane.showConfirmDialog(null,
                        "La base de datos 'taller_automotriz' no existe. ¿Desea crearla ahora?",
                        "Base de datos no encontrada", JOptionPane.YES_NO_OPTION);
                    
                    if (option == JOptionPane.YES_OPTION) {
                        Statement stmt = rootConn.createStatement();
                        stmt.executeUpdate("CREATE DATABASE taller_automotriz");
                        stmt.close();
                        
                        // Crear las tablas necesarias
                        rootConn.setCatalog("taller_automotriz");
                        createTables(rootConn);
                        
                        JOptionPane.showMessageDialog(null,
                            "Base de datos 'taller_automotriz' creada correctamente.",
                            "Base de datos creada", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                            "La aplicación necesita la base de datos para funcionar correctamente.",
                            "Operación cancelada", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
                
                rs.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,
                    "No se pudo conectar al servidor MySQL. Verifique que esté en ejecución.\n" +
                    "Error: " + e.getMessage(),
                    "Error de conexión", JOptionPane.ERROR_MESSAGE);
                return;
            } finally {
                if (rootConn != null) {
                    try {
                        rootConn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            
            // Ahora conectamos a la base de datos específica
            Connection conn = DatabaseConnection.getInstance().getConnection();
            if (conn == null) {
                JOptionPane.showMessageDialog(null, 
                    "No se pudo establecer conexión con la base de datos.\n" +
                    "Verifique la configuración en DatabaseConnection.java",
                    "Error de Conexión", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al conectar con la base de datos: " + e.getMessage(),
                "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }
        
        // Iniciar la aplicación en el Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
            }
        });
    }

    /**
     * Crea los directorios necesarios si no existen
     */
    private static void createRequiredDirectories() {
        // Directorio de sonidos
        File soundDir = new File("sounds");
        if (!soundDir.exists()) {
            soundDir.mkdir();
            System.out.println("Directorio de sonidos creado: " + soundDir.getAbsolutePath());
            System.out.println("Por favor, coloque los archivos de sonido en este directorio.");
        }
        
        // Directorio de imágenes
        File imagesDir = new File("images");
        if (!imagesDir.exists()) {
            imagesDir.mkdir();
            System.out.println("Directorio de imágenes creado: " + imagesDir.getAbsolutePath());
            System.out.println("Por favor, coloque una imagen llamada 'background.jpg' en este directorio para el fondo.");
            
            // Crear subdirectorio para iconos
            File iconsDir = new File("images/icons");
            if (!iconsDir.exists()) {
                iconsDir.mkdir();
                System.out.println("Directorio de iconos creado: " + iconsDir.getAbsolutePath());
                System.out.println("Por favor, coloque los iconos necesarios en este directorio.");
            }
        }
    }
    
    /**
     * Crea las tablas necesarias en la base de datos
     */
    private static void createTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        
        // Tabla Cliente
        stmt.executeUpdate(
            "CREATE TABLE IF NOT EXISTS Cliente (" +
            "id_cliente INT AUTO_INCREMENT PRIMARY KEY, " +
            "nombre VARCHAR(100) NOT NULL, " +
            "identificacion VARCHAR(20) NOT NULL UNIQUE, " +
            "telefono VARCHAR(20), " +
            "email VARCHAR(100), " +
            "direccion VARCHAR(200)" +
            ")"
        );
        
        // Tabla Repuesto
        stmt.executeUpdate(
            "CREATE TABLE IF NOT EXISTS Repuesto (" +
            "id_repuesto INT AUTO_INCREMENT PRIMARY KEY, " +
            "nombre VARCHAR(100) NOT NULL, " +
            "tipo VARCHAR(50), " +
            "marca_compatible VARCHAR(100), " +
            "modelo_compatible VARCHAR(100), " +
            "descripcion TEXT, " +
            "precio_unitario DECIMAL(10,2) NOT NULL, " +
            "stock_actual INT NOT NULL DEFAULT 0, " +
            "stock_minimo INT DEFAULT 0, " +
            "fecha_ingreso DATE, " +
            "vida_util_meses INT, " +
            "estado VARCHAR(20) DEFAULT 'Disponible', " +
            "ubicacion VARCHAR(100)" +
            ")"
        );
        
        // Tabla Proveedor
        stmt.executeUpdate(
            "CREATE TABLE IF NOT EXISTS Proveedor (" +
            "id_proveedor INT AUTO_INCREMENT PRIMARY KEY, " +
            "nombre VARCHAR(100) NOT NULL, " +
            "nit VARCHAR(20) NOT NULL UNIQUE, " +
            "direccion VARCHAR(200), " +
            "contacto VARCHAR(100), " +
            "frecuencia_suministro VARCHAR(50), " +
            "estado VARCHAR(20) DEFAULT 'Activo'" +
            ")"
        );
        
        // Tabla Servicio
        stmt.executeUpdate(
            "CREATE TABLE IF NOT EXISTS Servicio (" +
            "id_servicio INT AUTO_INCREMENT PRIMARY KEY, " +
            "descripcion VARCHAR(200) NOT NULL, " +
            "tipo VARCHAR(50), " +
            "costo_mano_obra DECIMAL(10,2) NOT NULL, " +
            "duracion_estimada TIME" +
            ")"
        );
        
        // Tabla Vehiculo
        stmt.executeUpdate(
            "CREATE TABLE IF NOT EXISTS Vehiculo (" +
            "id_vehiculo INT AUTO_INCREMENT PRIMARY KEY, " +
            "matricula VARCHAR(20) NOT NULL UNIQUE, " +
            "marca VARCHAR(50) NOT NULL, " +
            "modelo VARCHAR(50) NOT NULL, " +
            "tipo VARCHAR(30), " +
            "anio INT, " +
            "id_cliente INT, " +
            "FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente)" +
            ")"
        );
        
        // Tabla Tecnico
        stmt.executeUpdate(
            "CREATE TABLE IF NOT EXISTS Tecnico (" +
            "id_tecnico INT AUTO_INCREMENT PRIMARY KEY, " +
            "nombre VARCHAR(100) NOT NULL, " +
            "identificacion VARCHAR(20) NOT NULL UNIQUE, " +
            "especialidad VARCHAR(50), " +
            "telefono VARCHAR(20), " +
            "email VARCHAR(100), " +
            "fecha_contratacion DATE, " +
            "estado VARCHAR(20) DEFAULT 'Activo'" +
            ")"
        );
        
        // Tabla OrdenServicio
        stmt.executeUpdate(
            "CREATE TABLE IF NOT EXISTS OrdenServicio (" +
            "id_orden INT AUTO_INCREMENT PRIMARY KEY, " +
            "id_vehiculo INT NOT NULL, " +
            "id_tecnico INT NOT NULL, " +
            "fecha_ingreso DATE NOT NULL, " +
            "fecha_entrega DATE, " +
            "estado VARCHAR(30) NOT NULL DEFAULT 'Pendiente', " +
            "observaciones TEXT, " +
            "FOREIGN KEY (id_vehiculo) REFERENCES Vehiculo(id_vehiculo), " +
            "FOREIGN KEY (id_tecnico) REFERENCES Tecnico(id_tecnico)" +
            ")"
        );
        
        // Tabla DetalleServicio
        stmt.executeUpdate(
            "CREATE TABLE IF NOT EXISTS DetalleServicio (" +
            "id_detalle_servicio INT AUTO_INCREMENT PRIMARY KEY, " +
            "id_orden INT NOT NULL, " +
            "id_servicio INT NOT NULL, " +
            "FOREIGN KEY (id_orden) REFERENCES OrdenServicio(id_orden), " +
            "FOREIGN KEY (id_servicio) REFERENCES Servicio(id_servicio)" +
            ")"
        );
        
        // Tabla RepuestoUsado
        stmt.executeUpdate(
            "CREATE TABLE IF NOT EXISTS RepuestoUsado (" +
            "id_repuesto_usado INT AUTO_INCREMENT PRIMARY KEY, " +
            "id_orden INT NOT NULL, " +
            "id_repuesto INT NOT NULL, " +
            "cantidad INT NOT NULL DEFAULT 1, " +
            "FOREIGN KEY (id_orden) REFERENCES OrdenServicio(id_orden), " +
            "FOREIGN KEY (id_repuesto) REFERENCES Repuesto(id_repuesto)" +
            ")"
        );
        
        // Tabla Factura
        stmt.executeUpdate(
            "CREATE TABLE IF NOT EXISTS Factura (" +
            "id_factura INT AUTO_INCREMENT PRIMARY KEY, " +
            "fecha DATE NOT NULL, " +
            "subtotal DECIMAL(10,2) NOT NULL, " +
            "iva DECIMAL(10,2) NOT NULL, " +
            "total DECIMAL(10,2) NOT NULL, " +
            "estado VARCHAR(20) DEFAULT 'Emitida', " +
            "cufe VARCHAR(100), " +
            "qr_code VARCHAR(200), " +
            "id_cliente INT NOT NULL, " +
            "id_orden INT NOT NULL, " +
            "FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente), " +
            "FOREIGN KEY (id_orden) REFERENCES OrdenServicio(id_orden)" +
            ")"
        );
        
        // Tabla OrdenCompra
        stmt.executeUpdate(
            "CREATE TABLE IF NOT EXISTS OrdenCompra (" +
            "id_orden_compra INT AUTO_INCREMENT PRIMARY KEY, " +
            "fecha_emision DATE NOT NULL, " +
            "fecha_entrega_estimada DATE, " +
            "estado VARCHAR(30) NOT NULL DEFAULT 'Pendiente', " +
            "total DECIMAL(10,2) NOT NULL, " +
            "observaciones TEXT, " +
            "id_proveedor INT NOT NULL, " +
            "FOREIGN KEY (id_proveedor) REFERENCES Proveedor(id_proveedor)" +
            ")"
        );
        
        // Tabla DetalleOrdenCompra
        stmt.executeUpdate(
            "CREATE TABLE IF NOT EXISTS DetalleOrdenCompra (" +
            "id_detalle_compra INT AUTO_INCREMENT PRIMARY KEY, " +
            "id_orden_compra INT NOT NULL, " +
            "id_repuesto INT NOT NULL, " +
            "cantidad INT NOT NULL, " +
            "precio_unitario DECIMAL(10,2) NOT NULL, " +
            "FOREIGN KEY (id_orden_compra) REFERENCES OrdenCompra(id_orden_compra), " +
            "FOREIGN KEY (id_repuesto) REFERENCES Repuesto(id_repuesto)" +
            ")"
        );
        
        // Insertar datos de ejemplo
        insertarDatosEjemplo(stmt);
        
        stmt.close();
    }
    
    /**
     * Inserta datos de ejemplo en las tablas
     */
    private static void insertarDatosEjemplo(Statement stmt) throws SQLException {
        // Formato para fechas
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaActual = sdf.format(new Date());
        
        // Clientes de ejemplo
        stmt.executeUpdate(
            "INSERT INTO Cliente (nombre, identificacion, telefono, email, direccion) VALUES " +
            "('Juan Pérez', '1234567890', '555-1234', 'juan@example.com', 'Calle 123'), " +
            "('María López', '0987654321', '555-5678', 'maria@example.com', 'Avenida 456'), " +
            "('Carlos Rodríguez', '1122334455', '555-9012', 'carlos@example.com', 'Plaza 789')"
        );
        
        // Repuestos de ejemplo
        stmt.executeUpdate(
            "INSERT INTO Repuesto (nombre, tipo, marca_compatible, modelo_compatible, descripcion, precio_unitario, stock_actual, stock_minimo, fecha_ingreso, vida_util_meses, estado, ubicacion) VALUES " +
            "('Filtro de aceite', 'Mecánico', 'Toyota, Honda', 'Corolla, Civic', 'Filtro de aceite de alta calidad', 15.99, 25, 10, '2023-01-15', 12, 'Disponible', 'Estante A-1'), " +
            "('Pastillas de freno', 'Mecánico', 'Ford, Chevrolet', 'Focus, Cruze', 'Pastillas de freno de cerámica', 45.50, 15, 5, '2023-02-20', 24, 'Disponible', 'Estante B-2'), " +
            "('Batería 12V', 'Eléctrico', 'Múltiples', 'Varios', 'Batería de 12V de larga duración', 89.99, 8, 3, '2023-03-10', 36, 'Disponible', 'Estante C-3'), " +
            "('Alternador', 'Eléctrico', 'Nissan, Mazda', 'Sentra, 3', 'Alternador reconstruido', 120.00, 5, 2, '2023-04-05', 48, 'Disponible', 'Estante D-4'), " +
            "('Aceite de motor 5W-30', 'Consumo', 'Múltiples', 'Varios', 'Aceite sintético de alta calidad', 25.99, 30, 15, '2023-05-12', 6, 'Disponible', 'Estante E-5')"
        );
        
        // Proveedores de ejemplo
        stmt.executeUpdate(
            "INSERT INTO Proveedor (nombre, nit, direccion, contacto, frecuencia_suministro, estado) VALUES " +
            "('AutoPartes S.A.', 'P12345678', 'Av. Industrial 123', 'Pedro Gómez (555-2222)', 'Semanal', 'Activo'), " +
            "('ElectroAuto', 'P87654321', 'Calle Electrónica 456', 'Ana Martínez (555-4444)', 'Quincenal', 'Activo'), " +
            "('LubriMax', 'P11223344', 'Av. Lubricantes 789', 'Luis Torres (555-6666)', 'Mensual', 'Activo')"
        );
        
        // Servicios de ejemplo
        stmt.executeUpdate(
            "INSERT INTO Servicio (descripcion, tipo, costo_mano_obra, duracion_estimada) VALUES " +
            "('Cambio de aceite', 'Mantenimiento', 30.00, '00:30:00'), " +
            "('Cambio de frenos', 'Reparación', 80.00, '01:30:00'), " +
            "('Diagnóstico electrónico', 'Diagnóstico', 50.00, '01:00:00'), " +
            "('Alineación y balanceo', 'Mantenimiento', 60.00, '01:00:00'), " +
            "('Reparación de alternador', 'Reparación', 120.00, '02:30:00')"
        );
        
        // Técnicos de ejemplo
        stmt.executeUpdate(
            "INSERT INTO Tecnico (nombre, identificacion, especialidad, telefono, email, fecha_contratacion, estado) VALUES " +
            "('Roberto Méndez', 'T12345', 'Mecánica general', '555-7777', 'roberto@carsmotors.com', '2022-01-10', 'Activo'), " +
            "('Laura Sánchez', 'T67890', 'Electrónica automotriz', '555-8888', 'laura@carsmotors.com', '2022-03-15', 'Activo'), " +
            "('Miguel Ángel Torres', 'T54321', 'Sistemas de frenos', '555-9999', 'miguel@carsmotors.com', '2022-05-20', 'Activo')"
        );
        
        // Vehículos de ejemplo
        stmt.executeUpdate(
            "INSERT INTO Vehiculo (matricula, marca, modelo, tipo, anio, id_cliente) VALUES " +
            "('ABC123', 'Toyota', 'Corolla', 'Sedán', 2020, 1), " +
            "('DEF456', 'Honda', 'Civic', 'Sedán', 2019, 2), " +
            "('GHI789', 'Ford', 'Explorer', 'SUV', 2021, 3), " +
            "('JKL012', 'Chevrolet', 'Cruze', 'Sedán', 2018, 1)"
        );
        
        // Órdenes de servicio de ejemplo
        stmt.executeUpdate(
            "INSERT INTO OrdenServicio (id_vehiculo, id_tecnico, fecha_ingreso, fecha_entrega, estado, observaciones) VALUES " +
            "(1, 1, '" + fechaActual + "', NULL, 'Pendiente', 'Cliente reporta ruido en frenos'), " +
            "(2, 2, '" + fechaActual + "', NULL, 'En proceso', 'Problemas eléctricos intermitentes'), " +
            "(3, 3, '" + fechaActual + "', NULL, 'Pendiente', 'Mantenimiento de rutina')"
        );
        
        // Detalles de servicio de ejemplo
        stmt.executeUpdate(
            "INSERT INTO DetalleServicio (id_orden, id_servicio) VALUES " +
            "(1, 2), " + // Cambio de frenos para la orden 1
            "(2, 3), " + // Diagnóstico electrónico para la orden 2
            "(3, 1), " + // Cambio de aceite para la orden 3
            "(3, 4)"     // Alineación y balanceo para la orden 3
        );
        
        // Repuestos usados de ejemplo
        stmt.executeUpdate(
            "INSERT INTO RepuestoUsado (id_orden, id_repuesto, cantidad) VALUES " +
            "(1, 2, 1), " + // Pastillas de freno para la orden 1
            "(2, 3, 1), " + // Batería para la orden 2
            "(3, 1, 1), " + // Filtro de aceite para la orden 3
            "(3, 5, 5)"     // 5 litros de aceite para la orden 3
        );
        
        // Facturas de ejemplo
        stmt.executeUpdate(
            "INSERT INTO Factura (fecha, subtotal, iva, total, estado, id_cliente, id_orden) VALUES " +
            "('" + fechaActual + "', 125.50, 23.85, 149.35, 'Emitida', 3, 3)"
        );
        
        // Órdenes de compra de ejemplo
        stmt.executeUpdate(
            "INSERT INTO OrdenCompra (fecha_emision, fecha_entrega_estimada, estado, total, observaciones, id_proveedor) VALUES " +
            "('" + fechaActual + "', DATE_ADD('" + fechaActual + "', INTERVAL 7 DAY), 'Pendiente', 500.00, 'Pedido urgente', 1), " +
            "('" + fechaActual + "', DATE_ADD('" + fechaActual + "', INTERVAL 10 DAY), 'Pendiente', 750.00, 'Pedido regular', 2)"
        );
        
        // Detalles de órdenes de compra de ejemplo
        stmt.executeUpdate(
            "INSERT INTO DetalleOrdenCompra (id_orden_compra, id_repuesto, cantidad, precio_unitario) VALUES " +
            "(1, 1, 20, 12.50), " + // 20 filtros de aceite
            "(1, 2, 10, 35.00), " + // 10 pastillas de freno
            "(2, 3, 5, 80.00), " +  // 5 baterías
            "(2, 4, 3, 110.00)"     // 3 alternadores
        );
    }
}