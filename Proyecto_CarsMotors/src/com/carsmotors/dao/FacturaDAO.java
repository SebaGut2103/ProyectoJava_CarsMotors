package com.carsmotors.dao;

import com.carsmotors.model.Factura;
import com.carsmotors.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAO {
    private Connection connection;
    
    public FacturaDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
   
    
    public boolean insertar(Factura factura) {
        String sql = "INSERT INTO Factura (fecha, subtotal, iva, total, estado, cufe, qr_code, id_cliente, id_orden_servicio) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, new java.sql.Date(factura.getFecha().getTime()));
            stmt.setDouble(2, factura.getSubtotal());
            stmt.setDouble(3, factura.getIva());
            stmt.setDouble(4, factura.getTotal());
            stmt.setString(5, factura.getEstado());
            stmt.setString(6, factura.getCufe());
            stmt.setString(7, factura.getQrCode());
            stmt.setInt(8, factura.getIdCliente());
            stmt.setInt(9, factura.getIdOrdenServicio());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    factura.setIdFactura(rs.getInt(1));
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error al insertar factura: " + e.getMessage());
            return false;
        }
    }
    
   
    
    
    public boolean actualizar(Factura factura) {
        String sql = "UPDATE Factura SET fecha = ?, subtotal = ?, iva = ?, total = ?, estado = ?, " +
                    "cufe = ?, qr_code = ?, id_cliente = ?, id_orden_servicio = ? WHERE id_factura = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(factura.getFecha().getTime()));
            stmt.setDouble(2, factura.getSubtotal());
            stmt.setDouble(3, factura.getIva());
            stmt.setDouble(4, factura.getTotal());
            stmt.setString(5, factura.getEstado());
            stmt.setString(6, factura.getCufe());
            stmt.setString(7, factura.getQrCode());
            stmt.setInt(8, factura.getIdCliente());
            stmt.setInt(9, factura.getIdOrdenServicio());
            stmt.setInt(10, factura.getIdFactura());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar factura: " + e.getMessage());
            return false;
        }
    }
    
    
    
    
    public boolean eliminar(int idFactura) {
        String sql = "DELETE FROM Factura WHERE id_factura = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idFactura);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar factura: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Busca una factura por su ID
     */
    public Factura buscarPorId(int idFactura) {
        String sql = "SELECT * FROM Factura WHERE id_factura = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idFactura);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extraerFacturaDeResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar factura por ID: " + e.getMessage());
        }
        
        return null;
    }
    
   
    
    
    public List<Factura> buscarPorCliente(int idCliente) {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT * FROM Factura WHERE id_cliente = ? ORDER BY fecha DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                facturas.add(extraerFacturaDeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar facturas por cliente: " + e.getMessage());
        }
        
        return facturas;
    }
    
    
    
    public Factura buscarPorOrdenServicio(int idOrdenServicio) {
        String sql = "SELECT * FROM Factura WHERE id_orden_servicio = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idOrdenServicio);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extraerFacturaDeResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar factura por orden de servicio: " + e.getMessage());
        }
        
        return null;
    }
    
   
    
    
    public List<Factura> listarTodas() {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT * FROM Factura ORDER BY fecha DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                facturas.add(extraerFacturaDeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar facturas: " + e.getMessage());
        }
        
        return facturas;
    }
    
    /**
     * Lista facturas por estado
     */
    public List<Factura> listarPorEstado(String estado) {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT * FROM Factura WHERE estado = ? ORDER BY fecha DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, estado);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                facturas.add(extraerFacturaDeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar facturas por estado: " + e.getMessage());
        }
        
        return facturas;
    }
    
   
    private Factura extraerFacturaDeResultSet(ResultSet rs) throws SQLException {
        Factura factura = new Factura();
        factura.setIdFactura(rs.getInt("id_factura"));
        factura.setFecha(rs.getDate("fecha"));
        factura.setSubtotal(rs.getDouble("subtotal"));
        factura.setIva(rs.getDouble("iva"));
        factura.setTotal(rs.getDouble("total"));
        factura.setEstado(rs.getString("estado"));
        factura.setCufe(rs.getString("cufe"));
        factura.setQrCode(rs.getString("qr_code"));
        factura.setIdCliente(rs.getInt("id_cliente"));
        factura.setIdOrdenServicio(rs.getInt("id_orden_servicio"));
        return factura;
    }
}
