package com.carsmotors.model;



public class Proveedor {
    private int id;
    private String nombre;
    private String nit;
    private String direccion;
    private String contacto;
    private String frecuenciaSuministro;
    
    
    public Proveedor() {
    }
    
   
    public Proveedor(int id, String nombre, String nit, String direccion, String contacto, String frecuenciaSuministro) {
        this.id = id;
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.contacto = contacto;
        this.frecuenciaSuministro = frecuenciaSuministro;
    }
    
  
    public Proveedor(String nombre, String nit, String direccion, String contacto, String frecuenciaSuministro) {
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.contacto = contacto;
        this.frecuenciaSuministro = frecuenciaSuministro;
    }
    
    // Getters y setters
    
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

    /**
     *
     * @return
     */
public int getIdProveedor() {
    return id;
}

public void setIdProveedor(int idProveedor) {
    this.id = idProveedor;
}

}
