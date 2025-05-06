package com.carsmotors.controller;

import com.carsmotors.dao.FacturaDAO;
import com.carsmotors.model.Factura;
import com.carsmotors.model.OrdenServicio;
import com.carsmotors.controller.OrdenServicioController;
import java.util.Date;
import java.util.List;

/**
 * Controlador para la gestión de facturas
 */
public class FacturaController {
    private FacturaDAO facturaDAO;
    private OrdenServicioController ordenServicioController;
    
    public FacturaController() {
        this.facturaDAO = new FacturaDAO();
        this.ordenServicioController = new OrdenServicioController();
    }
    
    /**
     * Genera una nueva factura a partir de una orden de servicio
     */
    public boolean generarFactura(int idOrden, int idCliente) {
        // Verificar si ya existe una factura para esta orden
        Factura facturaExistente = facturaDAO.buscarPorOrdenServicio(idOrden);
        if (facturaExistente != null) {
            return false;
        }
        
        // Obtener la orden de servicio
        OrdenServicio orden = ordenServicioController.buscarOrdenServicioPorId(idOrden);
        if (orden == null) {
            return false;
        }
        
        // Calcular el costo total de la orden
        double costoTotal = orden.calcularCostoTotal();
        
        // Crear la factura
        Factura factura = new Factura();
        factura.setFecha(new Date());
        factura.setSubtotal(costoTotal);
        factura.setIva(costoTotal * 0.19); // IVA del 19%
        factura.setTotal(factura.getSubtotal() + factura.getIva());
        factura.setEstado("Emitida");
        factura.setIdCliente(idCliente);
        factura.setIdOrdenServicio(idOrden);
        
        // Generar CUFE y código QR
        factura.generarCUFE();
        factura.generarQR();
        
        return facturaDAO.insertar(factura);
    }
    
    /**
     * Actualiza una factura existente
     */
    public boolean actualizarFactura(Factura factura) {
        // Validar datos de la factura
        if (factura.getIdFactura() <= 0) {
            return false;
        }
        
        if (factura.getFecha() == null) {
            return false;
        }
        
        if (factura.getSubtotal() <= 0) {
            return false;
        }
        
        if (factura.getIva() <= 0) {
            return false;
        }
        
        if (factura.getTotal() <= 0) {
            return false;
        }
        
        if (factura.getEstado() == null || factura.getEstado().trim().isEmpty()) {
            return false;
        }
        
        if (factura.getIdCliente() <= 0) {
            return false;
        }
        
        if (factura.getIdOrdenServicio() <= 0) {
            return false;
        }
        
        return facturaDAO.actualizar(factura);
    }
    
    /**
     * Anula una factura
     */
    public boolean anularFactura(int id) {
        Factura factura = facturaDAO.buscarPorId(id);
        if (factura == null) {
            return false;
        }
        
        factura.setEstado("Anulada");
        return facturaDAO.actualizar(factura);
    }
    
    /**
     * Elimina una factura
     */
    public boolean eliminarFactura(int id) {
        return facturaDAO.eliminar(id);
    }
    
    /**
     * Busca una factura por su ID
     */
    public Factura buscarFacturaPorId(int id) {
        return facturaDAO.buscarPorId(id);
    }
    
    /**
     * Busca facturas por ID de cliente
     */
    public List<Factura> buscarFacturasPorCliente(int idCliente) {
        return facturaDAO.buscarPorCliente(idCliente);
    }
    
    /**
     * Busca una factura por ID de orden de servicio
     */
    public Factura buscarFacturaPorOrdenServicio(int idOrden) {
        return facturaDAO.buscarPorOrdenServicio(idOrden);
    }
    
    /**
     * Lista todas las facturas
     */
    public List<Factura> listarFacturas() {
        return facturaDAO.listarTodas();
    }
    
    /**
     * Lista facturas por estado
     */
    public List<Factura> listarFacturasPorEstado(String estado) {
        return facturaDAO.listarPorEstado(estado);
    }
}
