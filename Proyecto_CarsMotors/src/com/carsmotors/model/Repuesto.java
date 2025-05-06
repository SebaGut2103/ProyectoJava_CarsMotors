package com.carsmotors.model;

import java.util.Date;

/**
 * Clase que representa un repuesto en el inventario
 */
public class Repuesto {
    private int id;
    private String nombre;
    private String tipo;
    private String marcaCompatible;
    private String modeloCompatible;
    private String descripcion;
    private double precioUnitario;
    private int stockActual;
    private int stockMinimo;
    private Date fechaIngreso;
    private int vidaUtilMeses;
    private String estado;
    
    // Constructor vacío
    public Repuesto() {
    }
    
    // Constructor completo
    public Repuesto(int id, String nombre, String tipo, String marcaCompatible, String modeloCompatible, 
                   String descripcion, double precioUnitario, int stockActual, int stockMinimo, 
                   Date fechaIngreso, int vidaUtilMeses, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.marcaCompatible = marcaCompatible;
        this.modeloCompatible = modeloCompatible;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.fechaIngreso = fechaIngreso;
        this.vidaUtilMeses = vidaUtilMeses;
        this.estado = estado;
    }
    
    // Constructor sin ID para nuevos repuestos
    public Repuesto(String nombre, String tipo, String marcaCompatible, String modeloCompatible, 
                   String descripcion, double precioUnitario, int stockActual, int stockMinimo, 
                   Date fechaIngreso, int vidaUtilMeses, String estado) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.marcaCompatible = marcaCompatible;
        this.modeloCompatible = modeloCompatible;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.fechaIngreso = fechaIngreso;
        this.vidaUtilMeses = vidaUtilMeses;
        this.estado = estado;
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getMarcaCompatible() {
        return marcaCompatible;
    }
    
    public void setMarcaCompatible(String marcaCompatible) {
        this.marcaCompatible = marcaCompatible;
    }
    
    public String getModeloCompatible() {
        return modeloCompatible;
    }
    
    public void setModeloCompatible(String modeloCompatible) {
        this.modeloCompatible = modeloCompatible;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public double getPrecioUnitario() {
        return precioUnitario;
    }
    
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    
    public int getStockActual() {
        return stockActual;
    }
    
    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }
    
    public int getStockMinimo() {
        return stockMinimo;
    }
    
    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }
    
    public Date getFechaIngreso() {
        return fechaIngreso;
    }
    
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    
    public int getVidaUtilMeses() {
        return vidaUtilMeses;
    }
    
    public void setVidaUtilMeses(int vidaUtilMeses) {
        this.vidaUtilMeses = vidaUtilMeses;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
        return nombre + " - " + tipo + " (" + stockActual + " unidades)";
    }
    
    // Método para verificar si el stock está por debajo del mínimo
    public boolean requiereReabastecimiento() {
        return stockActual <= stockMinimo;
    }

    public void setUbicacion(String trim) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getUbicacion() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
