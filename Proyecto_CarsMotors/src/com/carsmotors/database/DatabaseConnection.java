package com.carsmotors.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para gestionar la conexión a la base de datos
 */
public class DatabaseConnection {
    // Configuración de la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/taller_automotriz";
    private static final String USER = "root";
    private static final String PASSWORD = "oscar2429";
    
    // Instancia única de la conexión (patrón Singleton)
    private static Connection connection;

    
    private static DatabaseConnection instance;

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
}

    Connection conn = DatabaseConnection.getInstance().getConnection();

    
    
    
    
    /**
     * Constructor privado para evitar instanciación directa
     */
    private DatabaseConnection() {
        // Constructor privado para implementar el patrón Singleton
    }
    
    /**
     * Obtiene una conexión a la base de datos
     * @return Conexión a la base de datos
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Cargar el driver de MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                // Establecer la conexión
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión a la base de datos establecida correctamente");
            }
            return connection;
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: No se pudo cargar el driver de MySQL: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("ERROR: No se pudo conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Cierra la conexión a la base de datos
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
                System.out.println("Conexión a la base de datos cerrada correctamente");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Verifica si la conexión a la base de datos está disponible
     * @return true si la conexión está disponible, false en caso contrario
     */
    public static boolean testConnection() {
        try {
            Connection testConn = getConnection();
            return testConn != null && !testConn.isClosed();
        } catch (SQLException e) {
            System.err.println("Error al probar la conexión: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
