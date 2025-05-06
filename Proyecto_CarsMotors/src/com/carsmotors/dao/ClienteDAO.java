package com.carsmotors.dao;

import com.carsmotors.database.DatabaseConnection;
import com.carsmotors.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para acceso a datos de clientes
 */
public class ClienteDAO {
    
    /**
     * Obtiene todos los clientes de la base de datos
     * @return Lista de clientes
     */
    public List<Cliente> obtenerTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id_cliente, nombre, identificacion, telefono, email, direccion FROM Cliente ORDER BY nombre";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Cliente cliente = extraerClienteDeResultSet(rs);
                clientes.add(cliente);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los clientes: " + e.getMessage());
            e.printStackTrace();
        }
        
        return clientes;
    }
    
    /**
     * Obtiene un cliente por su ID
     * @param id ID del cliente
     * @return Cliente encontrado o null si no existe
     */
    public Cliente obtenerPorId(int id) {
        String sql = "SELECT id_cliente, nombre, identificacion, telefono, email, direccion FROM Cliente WHERE id_cliente = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extraerClienteDeResultSet(rs);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener cliente por ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Busca clientes por nombre, identificación, teléfono o email
     * @param textoBusqueda Texto a buscar
     * @return Lista de clientes que coinciden con la búsqueda
     */
    public List<Cliente> buscar(String textoBusqueda) {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id_cliente, nombre, identificacion, telefono, email, direccion FROM Cliente " +
                     "WHERE nombre LIKE ? OR identificacion LIKE ? OR telefono LIKE ? OR email LIKE ? " +
                     "ORDER BY nombre";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String parametro = "%" + textoBusqueda + "%";
            stmt.setString(1, parametro);
            stmt.setString(2, parametro);
            stmt.setString(3, parametro);
            stmt.setString(4, parametro);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cliente cliente = extraerClienteDeResultSet(rs);
                    clientes.add(cliente);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar clientes: " + e.getMessage());
            e.printStackTrace();
        }
        
        return clientes;
    }
    
    /**
     * Inserta un nuevo cliente en la base de datos
     * @param cliente Cliente a insertar
     * @return true si se insertó correctamente, false en caso contrario
     */
    public boolean insertar(Cliente cliente) {
        String sql = "INSERT INTO Cliente (nombre, identificacion, telefono, email, direccion) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getIdentificacion());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getDireccion());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        cliente.setId(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Actualiza un cliente existente en la base de datos
     * @param cliente Cliente a actualizar
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE Cliente SET nombre = ?, identificacion = ?, telefono = ?, email = ?, direccion = ? WHERE id_cliente = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getIdentificacion());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getDireccion());
            stmt.setInt(6, cliente.getId());
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Elimina un cliente de la base de datos
     * @param id ID del cliente a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Cliente WHERE id_cliente = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Verifica si un cliente tiene registros asociados
     * @param idCliente ID del cliente
     * @return true si tiene registros asociados, false en caso contrario
     */
    public boolean tieneRegistrosAsociados(int idCliente) {
        String sql = "SELECT COUNT(*) FROM Vehiculo WHERE id_cliente = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idCliente);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al verificar registros asociados: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Verifica si existe un cliente con la identificación dada
     * @param identificacion Identificación a verificar
     * @return Cliente encontrado o null si no existe
     */
    public Cliente buscarPorIdentificacion(String identificacion) {
        String sql = "SELECT id_cliente, nombre, identificacion, telefono, email, direccion FROM Cliente WHERE identificacion = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, identificacion);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extraerClienteDeResultSet(rs);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente por identificación: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Extrae un objeto Cliente de un ResultSet
     * @param rs ResultSet con los datos del cliente
     * @return Objeto Cliente
     * @throws SQLException Si ocurre un error de SQL
     */
    private Cliente extraerClienteDeResultSet(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("id_cliente"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setIdentificacion(rs.getString("identificacion"));
        cliente.setTelefono(rs.getString("telefono"));
        cliente.setEmail(rs.getString("email"));
        cliente.setDireccion(rs.getString("direccion"));
        return cliente;
    }
}
