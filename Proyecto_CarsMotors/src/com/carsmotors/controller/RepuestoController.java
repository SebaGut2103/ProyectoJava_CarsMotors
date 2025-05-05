package com.carsmotors.controller;

import com.carsmotors.dao.RepuestoDAO;
import com.carsmotors.model.Repuesto;

import java.util.List;

/**
 * Controlador para la gestión de repuestos
 */
public class RepuestoController {
    private RepuestoDAO repuestoDAO;
    
    public RepuestoController() {
        this.repuestoDAO = new RepuestoDAO();
    }
    
    /**
     * Registra un nuevo repuesto
     */
    public boolean registrarRepuesto(Repuesto repuesto) {
        // Validar datos del repuesto
        if (repuesto.getNombre() == null || repuesto.getNombre().trim().isEmpty()) {
            return false;
        }
        
        if (repuesto.getTipo() == null || repuesto.getTipo().trim().isEmpty()) {
            return false;
        }
        
        if (repuesto.getPrecioUnitario() <= 0) {
            return false;
        }
        
        if (repuesto.getStockActual() < 0) {
            return false;
        }
        
        if (repuesto.getStockMinimo() < 0) {
            return false;
        }
        
        return repuestoDAO.insertar(repuesto);
    }
    
    /**
     * Actualiza los datos de un repuesto
     */
    public boolean actualizarRepuesto(Repuesto repuesto) {
        // Validar datos del repuesto
        if (repuesto.getId() <= 0) {
            return false;
        }
        
        if (repuesto.getNombre() == null || repuesto.getNombre().trim().isEmpty()) {
            return false;
        }
        
        if (repuesto.getTipo() == null || repuesto.getTipo().trim().isEmpty()) {
            return false;
        }
        
        if (repuesto.getPrecioUnitario() <= 0) {
            return false;
        }
        
        if (repuesto.getStockActual() < 0) {
            return false;
        }
        
        if (repuesto.getStockMinimo() < 0) {
            return false;
        }
        
        return repuestoDAO.actualizar(repuesto);
    }
    
    /**
     * Elimina un repuesto
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
     * Actualiza el stock de un repuesto
     */
    public boolean actualizarStock(int idRepuesto, int cantidad) {
        Repuesto repuesto = repuestoDAO.buscarPorId(idRepuesto);
        if (repuesto == null) {
            return false;
        }
        
        // No permitir stock negativo
        if (repuesto.getStockActual() + cantidad < 0) {
            return false;
        }
        
        repuesto.setStockActual(repuesto.getStockActual() + cantidad);
        return repuestoDAO.actualizar(repuesto);
    }

    boolean verificarDisponibilidad(int idRepuesto, int cantidad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Repuesto> listarRepuestosPorTipo(String filtroTipo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Repuesto> buscarRepuestos(String termino) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
