package com.carsmotors.model;

/**
 * Clase que representa un repuesto usado en una orden de servicio
 */
public class RepuestoUsado {
    private int id;
    private int idOrdenServicio;
    private int idRepuesto;
    private int cantidad;
    private Repuesto repuesto; // Objeto para mantener los detalles 
    
    
    public RepuestoUsado() {
    }
    
    
    public RepuestoUsado(int id, int idOrdenServicio, int idRepuesto, int cantidad) {
        this.id = id;
        this.idOrdenServicio = idOrdenServicio;
        this.idRepuesto = idRepuesto;
        this.cantidad = cantidad;
    }
    
   
    public RepuestoUsado(int idOrdenServicio, int idRepuesto, int cantidad) {
        this.idOrdenServicio = idOrdenServicio;
        this.idRepuesto = idRepuesto;
        this.cantidad = cantidad;
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
        if (repuesto != null) {
            this.idRepuesto = repuesto.getId();
        }
    }
    
  
    public double calcularSubtotal() {
        if (repuesto != null) {
            return repuesto.getPrecioUnitario() * cantidad;
        }
        return 0;
    }
}
