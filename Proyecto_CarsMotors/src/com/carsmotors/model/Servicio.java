package com.carsmotors.model;

import java.sql.Time;

/**
 * Clase que reprecenta un tipo de servicio ofrecido por el taller
 */
public class Servicio {
    private int id;
    private String descripcion;
    private String tipo;
    private double costoManoObra;
    private Time duracionEstimada;
    
    // Constructor vacío
    public Servicio() {
    }
    
    // Constructor completo
    public Servicio(int id, String descripcion, String tipo, double costoManoObra, Time duracionEstimada) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.costoManoObra = costoManoObra;
        this.duracionEstimada = duracionEstimada;
    }
    
    // Constructor sin ID para nuevos servicios
    public Servicio(String descripcion, String tipo, double costoManoObra, Time duracionEstimada) {
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.costoManoObra = costoManoObra;
        this.duracionEstimada = duracionEstimada;
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public double getCostoManoObra() {
        return costoManoObra;
    }
    
    public void setCostoManoObra(double costoManoObra) {
        this.costoManoObra = costoManoObra;
    }
    
    public Time getDuracionEstimada() {
        return duracionEstimada;
    }
    
    public void setDuracionEstimada(Time duracionEstimada) {
        this.duracionEstimada = duracionEstimada;
    }
    
    @Override
    public String toString() {
        return descripcion + " - " + tipo;
    }
}
