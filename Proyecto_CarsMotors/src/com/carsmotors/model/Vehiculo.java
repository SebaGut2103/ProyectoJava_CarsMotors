package com.carsmotors.model;

/**
 * Clase que representa un vehículo en el sistema
 */
public class Vehiculo {
    private int id;
    private String matricula;
    private String marca;
    private String modelo;
    private String tipo;
    private int anio;
    private int idCliente;
    
    // Constructor vacío
    public Vehiculo() {
    }
    
    // Constructor completo
    public Vehiculo(int id, String matricula, String marca, String modelo, String tipo, int anio, int idCliente) {
        this.id = id;
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.anio = anio;
        this.idCliente = idCliente;
    }
    
    // Constructor sin ID para nuevos vehículos
    public Vehiculo(String matricula, String marca, String modelo, String tipo, int anio, int idCliente) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.anio = anio;
        this.idCliente = idCliente;
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getMatricula() {
        return matricula;
    }
    
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    
    public String getMarca() {
        return marca;
    }
    
    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    public String getModelo() {
        return modelo;
    }
    
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public int getAnio() {
        return anio;
    }
    
    public void setAnio(int anio) {
        this.anio = anio;
    }
    
    public int getIdCliente() {
        return idCliente;
    }
    
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
    @Override
    public String toString() {
        return marca + " " + modelo + " (" + matricula + ")";
    }

    public void setCliente(Cliente cliente) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
