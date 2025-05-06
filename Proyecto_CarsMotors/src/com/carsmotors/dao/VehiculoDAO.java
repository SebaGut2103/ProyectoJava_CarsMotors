package com.carsmotors.dao;

import com.carsmotors.database.DatabaseConnection;
import com.carsmotors.model.Vehiculo;
import com.carsmotors.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para acceso a datos de vehículos
 */
public class VehiculoDAO {
    
    private ClienteDAO clienteDAO;
    
    public VehiculoDAO() {
        this.clienteDAO = new ClienteDAO();
    }
    
    /**
     * Obtiene todos los vehículos de la base de datos
     * @return Lista de vehículos
     */
    public List<Vehiculo> obtenerTodos() {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String sql = "SELECT id_vehiculo, matricula, marca, modelo, tipo, anio, id_cliente FROM Vehiculo ORDER BY marca, modelo";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Vehiculo vehiculo = extraerVehiculoDeResultSet(rs);
                vehiculos.add(vehiculo);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los vehículos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return vehiculos;
    }
    
    /**
     * Obtiene un vehículo por su ID
     * @param id ID del vehículo
     * @return Vehículo encontrado o null si no existe
     */
    public Vehiculo obtenerPorId(int id) {
        String sql = "SELECT id_vehiculo, matricula, marca, modelo, tipo, anio, id_cliente FROM Vehiculo WHERE id_vehiculo = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extraerVehiculoDeResultSet(rs);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener vehículo por ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Obtiene los vehículos de un cliente
     * @param idCliente ID del cliente
     * @return Lista de vehículos del cliente
     */
    public List<Vehiculo> obtenerPorCliente(int idCliente) {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String sql = "SELECT id_vehiculo, matricula, marca, modelo, tipo, anio, id_cliente FROM Vehiculo WHERE id_cliente = ? ORDER BY marca, modelo";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idCliente);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Vehiculo vehiculo = extraerVehiculoDeResultSet(rs);
                    vehiculos.add(vehiculo);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener vehículos por cliente: " + e.getMessage());
            e.printStackTrace();
        }
        
        return vehiculos;
    }
    
    /**
     * Busca vehículos por matrícula, marca o modelo
     * @param textoBusqueda Texto a buscar
     * @return Lista de vehículos que coinciden con la búsqueda
     */
    public List<Vehiculo> buscar(String textoBusqueda) {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String sql = "SELECT id_vehiculo, matricula, marca, modelo, tipo, anio, id_cliente FROM Vehiculo " +
                     "WHERE matricula LIKE ? OR marca LIKE ? OR modelo LIKE ? " +
                     "ORDER BY marca, modelo";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String parametro = "%" + textoBusqueda + "%";
            stmt.setString(1, parametro);
            stmt.setString(2, parametro);
            stmt.setString(3, parametro);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Vehiculo vehiculo = extraerVehiculoDeResultSet(rs);
                    vehiculos.add(vehiculo);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar vehículos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return vehiculos;
    }
    
    /**
     * Inserta un nuevo vehículo en la base de datos
     * @param vehiculo Vehículo a insertar
     * @return true si se insertó correctamente, false en caso contrario
     */
    public boolean insertar(Vehiculo vehiculo) {
        String sql = "INSERT INTO Vehiculo (matricula, marca, modelo, tipo, anio, id_cliente) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, vehiculo.getMatricula());
            stmt.setString(2, vehiculo.getMarca());
            stmt.setString(3, vehiculo.getModelo());
            stmt.setString(4, vehiculo.getTipo());
            stmt.setInt(5, vehiculo.getAnio());
            stmt.setInt(6, vehiculo.getIdCliente());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        vehiculo.setId(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al insertar vehículo: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Actualiza un vehículo existente en la base de datos
     * @param vehiculo Vehículo a actualizar
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizar(Vehiculo vehiculo) {
        String sql = "UPDATE Vehiculo SET matricula = ?, marca = ?, modelo = ?, tipo = ?, anio = ?, id_cliente = ? WHERE id_vehiculo = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, vehiculo.getMatricula());
            stmt.setString(2, vehiculo.getMarca());
            stmt.setString(3, vehiculo.getModelo());
            stmt.setString(4, vehiculo.getTipo());
            stmt.setInt(5, vehiculo.getAnio());
            stmt.setInt(6, vehiculo.getIdCliente());
            stmt.setInt(7, vehiculo.getId());
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar vehículo: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Elimina un vehículo de la base de datos
     * @param id ID del vehículo a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Vehiculo WHERE id_vehiculo = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar vehículo: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Verifica si un vehículo tiene registros asociados
     * @param idVehiculo ID del vehículo
     * @return true si tiene registros asociados, false en caso contrario
     */
    public boolean tieneRegistrosAsociados(int idVehiculo) {
        String sql = "SELECT COUNT(*) FROM Orden_Servicio WHERE id_vehiculo = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idVehiculo);
            
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
     * Extrae un objeto Vehículo de un ResultSet
     * @param rs ResultSet con los datos del vehículo
     * @return Objeto Vehículo
     * @throws SQLException Si ocurre un error de SQL
     */
    private Vehiculo extraerVehiculoDeResultSet(ResultSet rs) throws SQLException {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(rs.getInt("id_vehiculo"));
        vehiculo.setMatricula(rs.getString("matricula"));
        vehiculo.setMarca(rs.getString("marca"));
        vehiculo.setModelo(rs.getString("modelo"));
        vehiculo.setTipo(rs.getString("tipo"));
        vehiculo.setAnio(rs.getInt("anio"));
        vehiculo.setIdCliente(rs.getInt("id_cliente"));
        
        // Cargar el cliente asociado
        Cliente cliente = clienteDAO.obtenerPorId(vehiculo.getIdCliente());
        vehiculo.setCliente(cliente);
        
        return vehiculo;
    }
}

