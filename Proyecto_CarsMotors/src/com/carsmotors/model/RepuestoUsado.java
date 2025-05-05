package com.carsmotors.model;

public class RepuestoUsado {
    private int idRepuestoUsado;
    private int idOrdenServicio;
    private int idRepuesto;
    private int cantidad;
    private Repuesto repuesto;
    
    
    public RepuestoUsado() {
    }
    
    
    public RepuestoUsado(int idRepuestoUsado, int idOrdenServicio, int idRepuesto, int cantidad) {
        this.idRepuestoUsado = idRepuestoUsado;
        this.idOrdenServicio = idOrdenServicio;
        this.idRepuesto = idRepuesto;
        this.cantidad = cantidad;
    }
    
    
    public int getIdRepuestoUsado() {
        return idRepuestoUsado;
    }
    
    public void setIdRepuestoUsado(int idRepuestoUsado) {
        this.idRepuestoUsado = idRepuestoUsado;
    }
    
    public int getIdOrdenServicio() {
        return idOrdenServicio;
    }
    
    public void setIdOrdenServicio(int idOrdenServicio) {
        this.idOrdenServicio = idOrdenServicio;
    }
    
    public int getIdRepuesto() {
        return idRepuesto;
    }
    
    public void setIdRepuesto(int idRepuesto) {
        this.idRepuesto = idRepuesto;
    }
    
    public int getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public Repuesto getRepuesto() {
        return repuesto;
    }
    
    public void setRepuesto(Repuesto repuesto) {
        this.repuesto = repuesto;
    }
}