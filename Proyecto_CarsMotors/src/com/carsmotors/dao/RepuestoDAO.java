package com.carsmotors.dao;

import com.carsmotors.model.Repuesto;
import com.carsmotors.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para operaciones CRUD de repuestos
 */
public class RepuestoDAO {
    private Connection connection;
    
    public RepuestoDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    /**
     * Inserta un nuevo repuesto en la base de datos
     */
    public boolean insertar(Repuesto repuesto) {
        String sql = "INSERT INTO Repuesto (nombre, tipo, marca_compatible, modelo_compatible, " +
                    "descripcion, precio_unitario, stock_actual, stock_minimo, fecha_ingreso, " +
                    "vida_util_meses, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, repuesto.getNombre());
            stmt.setString(2, repuesto.getTipo());
            stmt.setString(3, repuesto.getMarcaCompatible());
            stmt.setString(4, repuesto.getModeloCompatible());
            stmt.setString(5, repuesto.getDescripcion());
            stmt.setDouble(6, repuesto.getPrecioUnitario());
            stmt.setInt(7, repuesto.getStockActual());
            stmt.setInt(8, repuesto.getStockMinimo());
            stmt.setDate(9, new java.sql.Date(repuesto.getFechaIngreso().getTime()));
            stmt.setInt(10, repuesto.getVidaUtilMeses());
            stmt.setString(11, repuesto.getEstado());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    repuesto.setId(rs.getInt(1));
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error al insertar repuesto: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Actualiza un repuesto existente en la base de datos
     */
    public boolean actualizar(Repuesto repuesto) {
        String sql = "UPDATE Repuesto SET nombre = ?, tipo = ?, marca_compatible = ?, " +
                    "modelo_compatible = ?, descripcion = ?, precio_unitario = ?, " +
                    "stock_actual = ?, stock_minimo = ?, fecha_ingreso = ?, " +
                    "vida_util_meses = ?, estado = ? WHERE id_repuesto = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, repuesto.getNombre());
            stmt.setString(2, repuesto.getTipo());
            stmt.setString(3, repuesto.getMarcaCompatible());
            stmt.setString(4, repuesto.getModeloCompatible());
            stmt.setString(5, repuesto.getDescripcion());
            stmt.setDouble(6, repuesto.getPrecioUnitario());
            stmt.setInt(7, repuesto.getStockActual());
            stmt.setInt(8, repuesto.getStockMinimo());
            stmt.setDate(9, new java.sql.Date(repuesto.getFechaIngreso().getTime()));
            stmt.setInt(10, repuesto.getVidaUtilMeses());
            stmt.setString(11, repuesto.getEstado());
            stmt.setInt(12, repuesto.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar repuesto: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Elimina un repuesto de la base de datos
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Repuesto WHERE id_repuesto = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar repuesto: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Busca un repuesto por su ID
     */
    public Repuesto buscarPorId(int id) {
        String sql = "SELECT * FROM Repuesto WHERE id_repuesto = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extraerRepuestoDeResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar repuesto por ID: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Lista todos los repuestos
     */
    public List<Repuesto> listarTodos() {
        List<Repuesto> repuestos = new ArrayList<>();
        String sql = "SELECT * FROM Repuesto ORDER BY nombre";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                repuestos.add(extraerRepuestoDeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar repuestos: " + e.getMessage());
        }
        
        return repuestos;
    }
    
    /**
     * Lista repuestos que requieren reabastecimiento (stock actual <= stock mínimo)
     */
    public List<Repuesto> listarParaReabastecer() {
        List<Repuesto> repuestos = new ArrayList<>();
        String sql = "SELECT * FROM Repuesto WHERE stock_actual <= stock_minimo ORDER BY nombre";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                repuestos.add(extraerRepuestoDeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar repuestos para reabastecer: " + e.getMessage());
        }
        
        return repuestos;
    }
    
    /**
     * Busca repuestos por tipo
     */
    public List<Repuesto> buscarPorTipo(String tipo) {
        List<Repuesto> repuestos = new ArrayList<>();
        String sql = "SELECT * FROM Repuesto WHERE tipo = ? ORDER BY nombre";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tipo);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                repuestos.add(extraerRepuestoDeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar repuestos por tipo: " + e.getMessage());
        }
        
        return repuestos;
    }
    
    /**
     * Busca repuestos por nombre (búsqueda parcial)
     */
    public List<Repuesto> buscarPorNombre(String nombre) {
        List<Repuesto> repuestos = new ArrayList<>();
        String sql = "SELECT * FROM Repuesto WHERE nombre LIKE ? ORDER BY nombre";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + nombre + "%");
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                repuestos.add(extraerRepuestoDeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar repuestos por nombre: " + e.getMessage());
        }
        
        return repuestos;
    }
    
    /**
     * Extrae un objeto Repuesto de un ResultSet
     */
    private Repuesto extraerRepuestoDeResultSet(ResultSet rs) throws SQLException {
        Repuesto repuesto = new Repuesto();
        repuesto.setId(rs.getInt("id_repuesto"));
        repuesto.setNombre(rs.getString("nombre"));
        repuesto.setTipo(rs.getString("tipo"));
        repuesto.setMarcaCompatible(rs.getString("marca_compatible"));
        repuesto.setModeloCompatible(rs.getString("modelo_compatible"));
        repuesto.setDescripcion(rs.getString("descripcion"));
        repuesto.setPrecioUnitario(rs.getDouble("precio_unitario"));
        repuesto.setStockActual(rs.getInt("stock_actual"));
        repuesto.setStockMinimo(rs.getInt("stock_minimo"));
        repuesto.setFechaIngreso(rs.getDate("fecha_ingreso"));
        repuesto.setVidaUtilMeses(rs.getInt("vida_util_meses"));
        repuesto.setEstado(rs.getString("estado"));
        
        return repuesto;
    }
}
