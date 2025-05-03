package com.carsmotors.model;

/**
 * Clase que representa el detalle de un servicio en una orden
 */
public class DetalleServicio {
    private int id;
    private int idOrdenServicio;
    private int idServicio;
    private Servicio servicio; // Objeto para mantener detallado el Servicio
    
  
    public DetalleServicio() {
    }
    
    
    public DetalleServicio(int id, int idOrdenServicio, int idServicio) {
        this.id = id;
        this.idOrdenServicio = idOrdenServicio;
        this.idServicio = idServicio;
    }
    
    // Constructor sin ID para nuevos detalles
    public DetalleServicio(int idOrdenServicio, int idServicio) {
        this.idOrdenServicio = idOrdenServicio;
        this.idServicio = idServicio;
    }
    
    
    
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getIdOrdenServicio() {
        return idOrdenServicio;
    }
    
    public void setIdOrdenServicio(int idOrdenServicio) {
        this.idOrdenServicio = idOrdenServicio;
    }
    
    public int getIdServicio() {
        return idServicio;
    }
    
    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }
    
    public Servicio getServicio() {
        return servicio;
    }
    
    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
        if (servicio != null) {
            this.idServicio = servicio.getId();
        }
    }
}