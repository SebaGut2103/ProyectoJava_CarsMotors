package com.carsmotors.model;

public class Proveedor {
    private int idProveedor;
    private String nombre;
    private String nit;
    private String direccion;
    private String contacto;
    private String frecuenciaSuministro;
    
    // Constructor vacío
    public Proveedor() {
    }
    
    // Constructor completo
    public Proveedor(int idProveedor, String nombre, String nit, String direccion, 
                    String contacto, String frecuenciaSuministro) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.contacto = contacto;
        this.frecuenciaSuministro = frecuenciaSuministro;
    }
    
    // Constructor sin ID para nuevos proveedores
    public Proveedor(String nombre, String nit, String direccion, 
                    String contacto, String frecuenciaSuministro) {
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.contacto = contacto;
        this.frecuenciaSuministro = frecuenciaSuministro;
    }
    
    // Getters y Setters
    public int getIdProveedor() {
        return idProveedor;
    }
    
    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getNit() {
        return nit;
    }
    
    public void setNit(String nit) {
        this.nit = nit;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String getContacto() {
        return contacto;
    }
    
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
    
    public String getFrecuenciaSuministro() {
        return frecuenciaSuministro;
    }
    
    public void setFrecuenciaSuministro(String frecuenciaSuministro) {
        this.frecuenciaSuministro = frecuenciaSuministro;
    }
    
    @Override
    public String toString() {
        return nombre + " (" + nit + ")";
    }
}