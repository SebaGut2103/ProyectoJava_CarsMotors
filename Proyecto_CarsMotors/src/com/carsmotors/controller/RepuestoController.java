package com.carsmotors.controller;

import com.carsmotors.dao.RepuestoDAO;
import com.carsmotors.model.Repuesto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controlador para operaciones relacionadas con repuestos
 */
public class RepuestoController {
    private RepuestoDAO repuestoDAO;
    
    public RepuestoController() {
        this.repuestoDAO = new RepuestoDAO();
    }
    
    /**
     * Registra un nuevo repuesto en el sistema
     */
    public boolean registrarRepuesto(String nombre, String tipo, String marcaCompatible, 
                                    String modeloCompatible, String descripcion, double precioUnitario, 
                                    int stockActual, int stockMinimo, Date fechaIngreso, 
                                    int vidaUtilMeses, String estado) {
        
        Repuesto repuesto = new Repuesto(nombre, tipo, marcaCompatible, modeloCompatible, 
                                        descripcion, precioUnitario, stockActual, stockMinimo, 
                                        fechaIngreso, vidaUtilMeses, estado);
        
        return repuestoDAO.insertar(repuesto);
    }
    
    /**
     * Actualiza un repuesto existente
     */
    public boolean actualizarRepuesto(int id, String nombre, String tipo, String marcaCompatible, 
                                     String modeloCompatible, String descripcion, double precioUnitario, 
                                     int stockActual, int stockMinimo, Date fechaIngreso, 
                                     int vidaUtilMeses, String estado) {
        
        Repuesto repuesto = new Repuesto(id, nombre, tipo, marcaCompatible, modeloCompatible, 
                                        descripcion, precioUnitario, stockActual, stockMinimo, 
                                        fechaIngreso, vidaUtilMeses, estado);
        
        return repuestoDAO.actualizar(repuesto);
    }
    
    /**
     * Elimina un repuesto del sistema
     */
    public boolean eliminarRepuesto(int id) {
        return repuestoDAO.eliminar(id);
    }
    
    /**
     * Busca un repuesto por su ID
     */
    public Repuesto buscarRepuestoPorId(int id) {
        return repuestoDAO.buscarPorId(id);
    }
    
    /**
     * Lista todos los repuestos
     */
    public List<Repuesto> listarRepuestos() {
        return repuestoDAO.listarTodos();
    }
    
    /**
     * Lista repuestos que requieren reabastecimiento
     */
    public List<Repuesto> listarRepuestosParaReabastecer() {
        return repuestoDAO.listarParaReabastecer();
    }
    
    /**
     * Verifica la disponibilidad de un repuesto
     */
    public boolean verificarDisponibilidad(int idRepuesto, int cantidadRequerida) {
        Repuesto repuesto = repuestoDAO.buscarPorId(idRepuesto);
        if (repuesto != null) {
            return repuesto.getStockActual() >= cantidadRequerida && 
                   "Disponible".equals(repuesto.getEstado());
        }
        return false;
    }
    
    /**
     * Lista repuestos por tipo
     */
    public List<Repuesto> listarRepuestosPorTipo(String tipo) {
        return repuestoDAO.buscarPorTipo(tipo);
    }
    
    /**
     * Busca repuestos por nombre (búsqueda parcial)
     */
    public List<Repuesto> buscarRepuestos(String criterio) {
        return repuestoDAO.buscarPorNombre(criterio);
    }
    
    /**
     * Actualiza el stock de un repuesto
     */
    public boolean actualizarStock(int idRepuesto, int nuevoStock) {
        Repuesto repuesto = repuestoDAO.buscarPorId(idRepuesto);
        if (repuesto != null) {
            repuesto.setStockActual(nuevoStock);
            return repuestoDAO.actualizar(repuesto);
        }
        return false;
    }
    
    /**
     * Obtiene los tipos de repuestos disponibles
     */
    public List<String> obtenerTiposRepuestos() {
        List<String> tipos = new ArrayList<>();
        tipos.add("Mecánico");
        tipos.add("Eléctrico");
        tipos.add("Carrocería");
        tipos.add("Consumo");
        return tipos;
    }
    
    /**
     * Obtiene los estados posibles para un repuesto
     */
    public List<String> obtenerEstadosRepuestos() {
        List<String> estados = new ArrayList<>();
        estados.add("Disponible");
        estados.add("Reservado");
        estados.add("Fuera de servicio");
        return estados;
    }

    public boolean registrarRepuesto(Repuesto repuesto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean actualizarRepuesto(Repuesto repuesto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Repuesto obtenerRepuestoPorId(int idRepuesto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Repuesto> obtenerTodosLosRepuestos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
