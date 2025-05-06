package com.carsmotors.test;

import com.carsmotors.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class mainTest {
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SHOW TABLES");
                System.out.println("Tablas en la base de datos:");
                while (rs.next()) {
                    System.out.println("- " + rs.getString(1));
                }
                rs.close();
                stmt.close();
            } catch (Exception e) {
                System.err.println("Error durante la prueba: " + e.getMessage());
            } finally {
                DatabaseConnection.closeConnection(); // Llamar directamente
            }
        } else {
            System.err.println("No se pudo conectar.");
        }
    }
}

