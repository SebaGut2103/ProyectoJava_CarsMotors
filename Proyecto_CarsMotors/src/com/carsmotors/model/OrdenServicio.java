package com.carsmotors.model;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una orden de servicio
 */
public class OrdenServicio {
    private int id;
    private int idVehiculo;
    private int idTecnico;
    private Date fechaIngreso;
    private Date fechaEntrega;
    private String estado;
    private String observaciones;
    private List<DetalleServicio> detallesServicio;
    private List<RepuestoUsado> repuestosUsados;
    
    // Constructor vacío
    public OrdenServicio() {
        this.detallesServicio = new ArrayList<>();
        this.repuestosUsados = new ArrayList<>();
    }
    
    // Constructor completo
    public OrdenServicio(int id, int idVehiculo, int idTecnico, Date fechaIngreso, 
                        Date fechaEntrega, String estado, String observaciones) {
        this.id = id;
        this.idVehiculo = idVehiculo;
        this.idTecnico = idTecnico;
        this.fechaIngreso = fechaIngreso;
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
        this.observaciones = observaciones;
        this.detallesServicio = new ArrayList<>();
        this.repuestosUsados = new ArrayList<>();
    }
    
    // Constructor sin ID para nuevas órdenes
    public OrdenServicio(int idVehiculo, int idTecnico, Date fechaIngreso, 
                        String estado, String observaciones) {
        this.idVehiculo = idVehiculo;
        this.idTecnico = idTecnico;
        this.fechaIngreso = fechaIngreso;
        this.estado = estado;
        this.observaciones = observaciones;
        this.detallesServicio = new ArrayList<>();
        this.repuestosUsados = new ArrayList<>();
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getIdVehiculo() {
        return idVehiculo;
    }
    
    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }
    
    public int getIdTecnico() {
        return idTecnico;
    }
    
    public void setIdTecnico(int idTecnico) {
        this.idTecnico = idTecnico;
    }
    
    public Date getFechaIngreso() {
        return fechaIngreso;
    }
    
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    
    public Date getFechaEntrega() {
        return fechaEntrega;
    }
    
    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public List<DetalleServicio> getDetallesServicio() {
        return detallesServicio;
    }
    
    public void setDetallesServicio(List<DetalleServicio> detallesServicio) {
        this.detallesServicio = detallesServicio;
    }
    
    public void addDetalleServicio(DetalleServicio detalle) {
        this.detallesServicio.add(detalle);
    }
    
    public List<RepuestoUsado> getRepuestosUsados() {
        return repuestosUsados;
    }
    
    public void setRepuestosUsados(List<RepuestoUsado> repuestosUsados) {
        this.repuestosUsados = repuestosUsados;
    }
    
    public void addRepuestoUsado(RepuestoUsado repuesto) {
        this.repuestosUsados.add(repuesto);
    }
    
    // Método para calcular el costo total de la orden
    public double calcularCostoTotal() {
        double costoServicios = 0;
        double costoRepuestos = 0;
        
        // Calcular costo de servicios
        for (DetalleServicio detalle : detallesServicio) {
            costoServicios += detalle.getServicio().getCostoManoObra();
        }
        
        // Calcular costo de repuestos
        for (RepuestoUsado repuesto : repuestosUsados) {
            costoRepuestos += repuesto.getRepuesto().getPrecioUnitario() * repuesto.getCantidad();
        }
        
        return costoServicios + costoRepuestos;
    }
}