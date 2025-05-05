package com.carsmotors.model;

import java.util.Date;

public class Factura {
    private int idFactura;
    private Date fecha;
    private double subtotal;
    private double iva;
    private double total;
    private String estado;
    private String cufe;
    private String qrCode;
    private int idCliente;
    private int idOrdenServicio;
    
    // Constructor vacío
    public Factura() {
    }
    
    // Constructor completo
    public Factura(int idFactura, Date fecha, double subtotal, double iva, double total, 
                  String estado, String cufe, String qrCode, int idCliente, int idOrdenServicio) {
        this.idFactura = idFactura;
        this.fecha = fecha;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.estado = estado;
        this.cufe = cufe;
        this.qrCode = qrCode;
        this.idCliente = idCliente;
        this.idOrdenServicio = idOrdenServicio;
    }
    
    // Constructor sin ID para nuevas facturas
    public Factura(Date fecha, double subtotal, double iva, double total, 
                  String estado, int idCliente, int idOrdenServicio) {
        this.fecha = fecha;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.estado = estado;
        this.idCliente = idCliente;
        this.idOrdenServicio = idOrdenServicio;
    }
    
    // Getters y Setters
    public int getIdFactura() {
        return idFactura;
    }
    
    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }
    
    public Date getFecha() {
        return fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public double getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    
    public double getIva() {
        return iva;
    }
    
    public void setIva(double iva) {
        this.iva = iva;
    }
    
    public double getTotal() {
        return total;
    }
    
    public void setTotal(double total) {
        this.total = total;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getCufe() {
        return cufe;
    }
    
    public void setCufe(String cufe) {
        this.cufe = cufe;
    }
    
    public String getQrCode() {
        return qrCode;
    }
    
    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
    
    public int getIdCliente() {
        return idCliente;
    }
    
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
    public int getIdOrdenServicio() {
        return idOrdenServicio;
    }
    
    public void setIdOrdenServicio(int idOrdenServicio) {
        this.idOrdenServicio = idOrdenServicio;
    }
    
    // Método para generar el CUFE (Código Único de Facturación Electrónica)
    public void generarCUFE() {
        // Implementación simplificada para generar un CUFE
        this.cufe = "CUFE-" + System.currentTimeMillis() + "-" + idFactura;
    }
    
    // Método para generar el código QR
    public void generarQR() {
        // Implementación simplificada para generar un código QR
        this.qrCode = "https://taller-carmotors.com/facturas/" + idFactura;
    }

    public boolean getId() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
