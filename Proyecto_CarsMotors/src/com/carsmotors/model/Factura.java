package com.carsmotors.model;

import java.util.Date;

/**
 * Clase que representa una factura
 */
public class Factura {
    private int id;
    private Date fecha;
    private double subtotal;
    private double iva;
    private double total;
    private String estado;
    private String cufe;
    private String qrCode;
    private int idCliente;
    private int idOrdenServicio;
    
    // Relaciones
    private Cliente cliente;
    private OrdenServicio ordenServicio;
    
    /**
     * Constructor vacío
     */
    public Factura() {
    }
    
    /**
     * Constructor con todos los campos
     */
    public Factura(int id, Date fecha, double subtotal, double iva, double total, String estado, 
                  String cufe, String qrCode, int idCliente, int idOrdenServicio) {
        this.id = id;
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
    
    /**
     * Constructor sin ID (para nuevas facturas)
     */
    public Factura(Date fecha, double subtotal, double iva, double total, String estado, 
                  String cufe, String qrCode, int idCliente, int idOrdenServicio) {
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
    
    // Getters y setters
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
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
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        if (cliente != null) {
            this.idCliente = cliente.getId();
        }
    }
    
    public OrdenServicio getOrdenServicio() {
        return ordenServicio;
    }
    
    public void setOrdenServicio(OrdenServicio ordenServicio) {
        this.ordenServicio = ordenServicio;
        if (ordenServicio != null) {
            this.idOrdenServicio = ordenServicio.getId();
        }
    }
    
    /**
     * Calcula el total de la factura
     */
    public void calcularTotal() {
        this.total = this.subtotal + this.iva;
    }
    
    @Override
    public String toString() {
        return "Factura #" + id + " - " + estado;
    }

    public void generarCUFE() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void generarQR() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object getIdFactura() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setIdFactura(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
