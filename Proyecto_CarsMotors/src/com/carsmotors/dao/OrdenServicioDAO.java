package com.carsmotors.dao;

import com.carsmotors.model.OrdenServicio;
import com.carsmotors.model.DetalleServicio;
import com.carsmotors.model.RepuestoUsado;
import com.carsmotors.model.Servicio;
import com.carsmotors.model.Repuesto;
import com.carsmotors.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdenServicioDAO {
    private Connection connection;
    
    public OrdenServicioDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    /**
     * Inserta una nueva orden de servicio en la base de datos
     */
    public boolean insertar(OrdenServicio orden) {
        String sql = "INSERT INTO Orden_Servicio (id_vehiculo, id_tecnico, fecha_ingreso, fecha_entrega, estado, observaciones) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, orden.getIdVehiculo());
            stmt.setInt(2, orden.getIdTecnico());
            stmt.setDate(3, new java.sql.Date(orden.getFechaIngreso().getTime()));
            
            if (orden.getFechaEntrega() != null) {
                stmt.setDate(4, new java.sql.Date(orden.getFechaEntrega().getTime()));
            } else {
                stmt.setNull(4, java.sql.Types.DATE);
            }
            
            stmt.setString(5, orden.getEstado());
            stmt.setString(6, orden.getObservaciones());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int idOrden = rs.getInt(1);
                    orden.setIdOrdenServicio(idOrden);
                    
                    // Insertar detalles de servicio
                    for (DetalleServicio detalle : orden.getDetallesServicio()) {
                        detalle.setIdOrdenServicio(idOrden);
                        insertarDetalleServicio(detalle);
                    }
                    
                    // Insertar repuestos usados
                    for (RepuestoUsado repuesto : orden.getRepuestosUsados()) {
                        repuesto.setIdOrdenServicio(idOrden);
                        insertarRepuestoUsado(repuesto);
                    }
                    
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error al insertar orden de servicio: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Inserta un detalle de servicio en la base de datos
     */
    private boolean insertarDetalleServicio(DetalleServicio detalle) {
        String sql = "INSERT INTO Detalle_Servicio (id_orden_servicio, id_servicio) VALUES (?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, detalle.getIdOrdenServicio());
            stmt.setInt(2, detalle.getIdServicio());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    detalle.setIdDetalleServicio(rs.getInt(1));
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error al insertar detalle de servicio: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Inserta un repuesto usado en la base de datos
     */
    private boolean insertarRepuestoUsado(RepuestoUsado repuesto) {
        String sql = "INSERT INTO Repuesto_Usado (id_orden_servicio, id_repuesto, cantidad) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, repuesto.getIdOrdenServicio());
            stmt.setInt(2, repuesto.getIdRepuesto());
            stmt.setInt(3, repuesto.getCantidad());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    repuesto.setIdRepuestoUsado(rs.getInt(1));
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error al insertar repuesto usado: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Actualiza una orden de servicio existente en la base de datos
     */
    public boolean actualizar(OrdenServicio orden) {
        String sql = "UPDATE Orden_Servicio SET id_vehiculo = ?, id_tecnico = ?, fecha_ingreso = ?, " +
                    "fecha_entrega = ?, estado = ?, observaciones = ? WHERE id_orden_servicio = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orden.getIdVehiculo());
            stmt.setInt(2, orden.getIdTecnico());
            stmt.setDate(3, new java.sql.Date(orden.getFechaIngreso().getTime()));
            
            if (orden.getFechaEntrega() != null) {
                stmt.setDate(4, new java.sql.Date(orden.getFechaEntrega().getTime()));
            } else {
                stmt.setNull(4, java.sql.Types.DATE);
            }
            
            stmt.setString(5, orden.getEstado());
            stmt.setString(6, orden.getObservaciones());
            stmt.setInt(7, orden.getIdOrdenServicio());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                // Eliminar detalles y repuestos anteriores
                eliminarDetallesServicio(orden.getIdOrdenServicio());
                eliminarRepuestosUsados(orden.getIdOrdenServicio());
                
                // Insertar nuevos detalles y repuestos
                for (DetalleServicio detalle : orden.getDetallesServicio()) {
                    detalle.setIdOrdenServicio(orden.getIdOrdenServicio());
                    insertarDetalleServicio(detalle);
                }
                
                for (RepuestoUsado repuesto : orden.getRepuestosUsados()) {
                    repuesto.setIdOrdenServicio(orden.getIdOrdenServicio());
                    insertarRepuestoUsado(repuesto);
                }
                
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error al actualizar orden de servicio: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Elimina los detalles de servicio de una orden
     */
    private boolean eliminarDetallesServicio(int idOrdenServicio) {
        String sql = "DELETE FROM Detalle_Servicio WHERE id_orden_servicio = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idOrdenServicio);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar detalles de servicio: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Elimina los repuestos usados de una orden
     */
    private boolean eliminarRepuestosUsados(int idOrdenServicio) {
        String sql = "DELETE FROM Repuesto_Usado WHERE id_orden_servicio = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idOrdenServicio);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar repuestos usados: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Elimina una orden de servicio de la base de datos
     */
    public boolean eliminar(int idOrdenServicio) {
        // Primero eliminar los detalles y repuestos asociados
        eliminarDetallesServicio(idOrdenServicio);
        eliminarRepuestosUsados(idOrdenServicio);
        
        // Luego eliminar la orden
        String sql = "DELETE FROM Orden_Servicio WHERE id_orden_servicio = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idOrdenServicio);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar orden de servicio: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Busca una orden de servicio por su ID
     */
    public OrdenServicio buscarPorId(int idOrdenServicio) {
        String sql = "SELECT * FROM Orden_Servicio WHERE id_orden_servicio = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idOrdenServicio);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                OrdenServicio orden = extraerOrdenDeResultSet(rs);
                
                // Cargar detalles de servicio
                orden.setDetallesServicio(buscarDetallesServicio(idOrdenServicio));
                
                // Cargar repuestos usados
                orden.setRepuestosUsados(buscarRepuestosUsados(idOrdenServicio));
                
                return orden;
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar orden de servicio por ID: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Busca los detalles de servicio de una orden
     */
    private List<DetalleServicio> buscarDetallesServicio(int idOrdenServicio) {
        List<DetalleServicio> detalles = new ArrayList<>();
        String sql = "SELECT ds.*, s.* FROM Detalle_Servicio ds " +
                    "JOIN Servicio s ON ds.id_servicio = s.id_servicio " +
                    "WHERE ds.id_orden_servicio = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idOrdenServicio);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                DetalleServicio detalle = new DetalleServicio();
                detalle.setIdDetalleServicio(rs.getInt("id_detalle"));
                detalle.setIdOrdenServicio(rs.getInt("id_orden_servicio"));
                detalle.setIdServicio(rs.getInt("id_servicio"));
                
                // Cargar el servicio asociado
                Servicio servicio = new Servicio();
                servicio.setId(rs.getInt("id_servicio"));
                servicio.setDescripcion(rs.getString("descripcion"));
                servicio.setTipo(rs.getString("tipo"));
                servicio.setCostoManoObra(rs.getDouble("costo_mano_obra"));
                servicio.setDuracionEstimada(rs.getTime("duracion_estimada"));
                
                detalle.setServicio(servicio);
                
                detalles.add(detalle);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar detalles de servicio: " + e.getMessage());
        }
        
        return detalles;
    }
    
    /**
     * Busca los repuestos usados en una orden
     */
    private List<RepuestoUsado> buscarRepuestosUsados(int idOrdenServicio) {
        List<RepuestoUsado> repuestos = new ArrayList<>();
        String sql = "SELECT ru.*, r.* FROM Repuesto_Usado ru " +
                    "JOIN Repuesto r ON ru.id_repuesto = r.id_repuesto " +
                    "WHERE ru.id_orden_servicio = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idOrdenServicio);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                RepuestoUsado repuesto = new RepuestoUsado();
                repuesto.setIdRepuestoUsado(rs.getInt("id_repuesto_usado"));
                repuesto.setIdOrdenServicio(rs.getInt("id_orden_servicio"));
                repuesto.setIdRepuesto(rs.getInt("id_repuesto"));
                repuesto.setCantidad(rs.getInt("cantidad"));
                
                // Cargar el repuesto asociado
                Repuesto rep = new Repuesto();
                rep.setId(rs.getInt("id_repuesto"));
                rep.setNombre(rs.getString("nombre"));
                rep.setTipo(rs.getString("tipo"));
                rep.setPrecioUnitario(rs.getDouble("precio_unitario"));
                rep.setStockActual(rs.getInt("stock_actual"));
                
                repuesto.setRepuesto(rep);
                
                repuestos.add(repuesto);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar repuestos usados: " + e.getMessage());
        }
        
        return repuestos;
    }
    
    /**
     * Lista todas las órdenes de servicio
     */
    public List<OrdenServicio> listarTodas() {
        List<OrdenServicio> ordenes = new ArrayList<>();
        String sql = "SELECT * FROM Orden_Servicio ORDER BY fecha_ingreso DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                OrdenServicio orden = extraerOrdenDeResultSet(rs);
                ordenes.add(orden);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar órdenes de servicio: " + e.getMessage());
        }
        
        return ordenes;
    }
    
    /**
     * Lista las órdenes de servicio por estado
     */
    public List<OrdenServicio> listarPorEstado(String estado) {
        List<OrdenServicio> ordenes = new ArrayList<>();
        String sql = "SELECT * FROM Orden_Servicio WHERE estado = ? ORDER BY fecha_ingreso DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, estado);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                OrdenServicio orden = extraerOrdenDeResultSet(rs);
                ordenes.add(orden);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar órdenes por estado: " + e.getMessage());
        }
        
        return ordenes;
    }
    
    /**
     * Extrae un objeto OrdenServicio de un ResultSet
     */
    private OrdenServicio extraerOrdenDeResultSet(ResultSet rs) throws SQLException {
        OrdenServicio orden = new OrdenServicio();
        orden.setIdOrdenServicio(rs.getInt("id_orden_servicio"));
        orden.setIdVehiculo(rs.getInt("id_vehiculo"));
        orden.setIdTecnico(rs.getInt("id_tecnico"));
        orden.setFechaIngreso(rs.getDate("fecha_ingreso"));
        orden.setFechaEntrega(rs.getDate("fecha_entrega"));
        orden.setEstado(rs.getString("estado"));
        orden.setObservaciones(rs.getString("observaciones"));
        return orden;
    }
}
