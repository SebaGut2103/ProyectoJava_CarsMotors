package com.carsmotors.controller;

import com.carsmotors.dao.OrdenServicioDAO;
import com.carsmotors.model.OrdenServicio;
import com.carsmotors.model.DetalleServicio;
import com.carsmotors.model.RepuestoUsado;
import com.carsmotors.controller.RepuestoController;

import java.util.List;
import java.util.Date;

public class OrdenServicioController {
    private OrdenServicioDAO ordenServicioDAO;
    private RepuestoController repuestoController;
    
    public OrdenServicioController() {
        this.ordenServicioDAO = new OrdenServicioDAO();
        this.repuestoController = new RepuestoController();
    }
    
   
    public boolean registrarOrdenServicio(OrdenServicio orden) {
        // Validar datos de la orden
        if (orden.getIdVehiculo() <= 0) {
            return false;
        }
        
        if (orden.getIdTecnico() <= 0) {
            return false;
        }
        
        if (orden.getFechaIngreso() == null) {
            orden.setFechaIngreso(new Date()); // Fecha actual por defecto
        }
        
        if (orden.getEstado() == null || orden.getEstado().trim().isEmpty()) {
            orden.setEstado("Pendiente"); 
        }
        
        
        for (RepuestoUsado repuesto : orden.getRepuestosUsados()) {
            if (!verificarDisponibilidadRepuesto(repuesto.getIdRepuesto(), repuesto.getCantidad())) {
                return false;
            }
        }
        
      
        boolean resultado = ordenServicioDAO.insertar(orden);
        
        if (resultado) {
            
            for (RepuestoUsado repuesto : orden.getRepuestosUsados()) {
                actualizarStockRepuesto(repuesto.getIdRepuesto(), -repuesto.getCantidad());
            }
        }
        
        return resultado;
    }
    
    
    public boolean actualizarOrdenServicio(OrdenServicio orden) {
        // Validar datos de la orden
        if (orden.getIdOrdenServicio() <= 0) {
            return false;
        }
        
        if (orden.getIdVehiculo() <= 0) {
            return false;
        }
        
        if (orden.getIdTecnico() <= 0) {
            return false;
        }
        
        if (orden.getFechaIngreso() == null) {
            return false;
        }
        
        if (orden.getEstado() == null || orden.getEstado().trim().isEmpty()) {
            return false;
        }
        
        
        OrdenServicio ordenOriginal = ordenServicioDAO.buscarPorId(orden.getIdOrdenServicio());
        if (ordenOriginal == null) {
            return false;
        }
        
        
        for (RepuestoUsado repuestoNuevo : orden.getRepuestosUsados()) {
            
            RepuestoUsado repuestoOriginal = null;
            for (RepuestoUsado r : ordenOriginal.getRepuestosUsados()) {
                if (r.getIdRepuesto() == repuestoNuevo.getIdRepuesto()) {
                    repuestoOriginal = r;
                    break;
                }
            }
            
            if (repuestoOriginal != null) {
               
                int diferencia = repuestoNuevo.getCantidad() - repuestoOriginal.getCantidad();
                if (diferencia > 0) {
                    
                    if (!verificarDisponibilidadRepuesto(repuestoNuevo.getIdRepuesto(), diferencia)) {
                        return false;
                    }
                }
            } else {
                
                if (!verificarDisponibilidadRepuesto(repuestoNuevo.getIdRepuesto(), repuestoNuevo.getCantidad())) {
                    return false;
                }
            }
        }
        
       
        boolean resultado = ordenServicioDAO.actualizar(orden);
        
        if (resultado) {
            
           
            for (RepuestoUsado repuestoOriginal : ordenOriginal.getRepuestosUsados()) {
                actualizarStockRepuesto(repuestoOriginal.getIdRepuesto(), repuestoOriginal.getCantidad());
            }
            
      
            for (RepuestoUsado repuestoNuevo : orden.getRepuestosUsados()) {
                actualizarStockRepuesto(repuestoNuevo.getIdRepuesto(), -repuestoNuevo.getCantidad());
            }
        }
        
        return resultado;
    }
    
   
    public boolean eliminarOrdenServicio(int idOrdenServicio) {
      
        OrdenServicio orden = ordenServicioDAO.buscarPorId(idOrdenServicio);
        if (orden == null) {
            return false;
        }
        
        boolean resultado = ordenServicioDAO.eliminar(idOrdenServicio);
        
        if (resultado) {
           
            for (RepuestoUsado repuesto : orden.getRepuestosUsados()) {
                actualizarStockRepuesto(repuesto.getIdRepuesto(), repuesto.getCantidad());
            }
        }
        
        return resultado;
    }
    
   
    public OrdenServicio buscarOrdenServicioPorId(int idOrdenServicio) {
        return ordenServicioDAO.buscarPorId(idOrdenServicio);
    }
    
   
    public List<OrdenServicio> listarOrdenesServicio() {
        return ordenServicioDAO.listarTodas();
    }
    
   
    public List<OrdenServicio> listarOrdenesServicioPorEstado(String estado) {
        return ordenServicioDAO.listarPorEstado(estado);
    }
    
    
    private boolean verificarDisponibilidadRepuesto(int idRepuesto, int cantidad) {
        return repuestoController.verificarDisponibilidad(idRepuesto, cantidad);
    }
    
  
    private boolean actualizarStockRepuesto(int idRepuesto, int cantidad) {
        return repuestoController.actualizarStock(idRepuesto, cantidad);
    }
}