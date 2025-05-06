package com.carsmotors.dao;

import com.carsmotors.model.Proveedor;
import com.carsmotors.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para operaciones CRUD de proveedores
 */
public class ProveedorDAO {
    private Connection connection;
    
    public ProveedorDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    /**
     * Inserta un nuevo proveedor en la base de datos
     */
    public boolean insertar(Proveedor proveedor) {
        String sql = "INSERT INTO Proveedor (nombre, nit, direccion, contacto, frecuencia_suministro) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getNit());
            stmt.setString(3, proveedor.getDireccion());
            stmt.setString(4, proveedor.getContacto());
            stmt.setString(5, proveedor.getFrecuenciaSuministro());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    proveedor.setIdProveedor(rs.getInt(1));
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error al insertar proveedor: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Actualiza un proveedor existente en la base de datos
     */
    public boolean actualizar(Proveedor proveedor) {
        String sql = "UPDATE Proveedor SET nombre = ?, nit = ?, direccion = ?, " +
                    "contacto = ?, frecuencia_suministro = ? WHERE id_proveedor = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getNit());
            stmt.setString(3, proveedor.getDireccion());
            stmt.setString(4, proveedor.getContacto());
            stmt.setString(5, proveedor.getFrecuenciaSuministro());
            stmt.setInt(6, proveedor.getIdProveedor());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar proveedor: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Elimina un proveedor de la base de datos
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Proveedor WHERE id_proveedor = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar proveedor: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Busca un proveedor por su ID
     */
    public Proveedor buscarPorId(int id) {
        String sql = "SELECT * FROM Proveedor WHERE id_proveedor = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extraerProveedorDeResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar proveedor por ID: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Busca un proveedor por su NIT
     */
    public Proveedor buscarPorNit(String nit) {
        String sql = "SELECT * FROM Proveedor WHERE nit = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nit);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extraerProveedorDeResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar proveedor por NIT: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Lista todos los proveedores
     */
    public List<Proveedor> listarTodos() {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM Proveedor ORDER BY nombre";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                proveedores.add(extraerProveedorDeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar proveedores: " + e.getMessage());
        }
        
        return proveedores;
    }
    
    /**
     * Busca proveedores por nombre (búsqueda parcial)
     */
    public List<Proveedor> buscarPorNombre(String nombre) {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM Proveedor WHERE nombre LIKE ? ORDER BY nombre";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + nombre + "%");
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                proveedores.add(extraerProveedorDeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar proveedores por nombre: " + e.getMessage());
        }
        
        return proveedores;
    }
    
    /**
     * Extrae un objeto Proveedor de un ResultSet
     */
    private Proveedor extraerProveedorDeResultSet(ResultSet rs) throws SQLException {
        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(rs.getInt("id_proveedor"));
        proveedor.setNombre(rs.getString("nombre"));
        proveedor.setNit(rs.getString("nit"));
        proveedor.setDireccion(rs.getString("direccion"));
        proveedor.setContacto(rs.getString("contacto"));
        proveedor.setFrecuenciaSuministro(rs.getString("frecuencia_suministro"));
        return proveedor;
    }
}