package com.carsmotors.model;

public class DetalleServicio {
    private int idDetalleServicio;
    private int idOrdenServicio;
    private int idServicio;
    private Servicio servicio;
    
    
    public DetalleServicio() {
    }
    
  
    public DetalleServicio(int idDetalleServicio, int idOrdenServicio, int idServicio) {
        this.idDetalleServicio = idDetalleServicio;
        this.idOrdenServicio = idOrdenServicio;
        this.idServicio = idServicio;
    }
    
   
    public int getIdDetalleServicio() {
        return idDetalleServicio;
    }
    
    public void setIdDetalleServicio(int idDetalleServicio) {
        this.idDetalleServicio = idDetalleServicio;
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
    }
}