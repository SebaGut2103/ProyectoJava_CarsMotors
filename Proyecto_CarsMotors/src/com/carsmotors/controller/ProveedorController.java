package com.carsmotors.controller;

import com.carsmotors.dao.ProveedorDAO;
import com.carsmotors.model.Proveedor;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para la gestión de proveedores
 */
public class ProveedorController {
    private ProveedorDAO proveedorDAO;
    
    public ProveedorController() {
        this.proveedorDAO = new ProveedorDAO();
    }
    
    /**
     * Registra un nuevo proveedor
     */
    public boolean registrarProveedor(Proveedor proveedor) {
        // Validar datos del proveedor
        if (proveedor.getNombre() == null || proveedor.getNombre().trim().isEmpty()) {
            return false;
        }
        
        if (proveedor.getNit() == null || proveedor.getNit().trim().isEmpty()) {
            return false;
        }
        
        // Verificar si ya existe un proveedor con el mismo NIT
        Proveedor proveedorExistente = proveedorDAO.buscarPorNit(proveedor.getNit());
        if (proveedorExistente != null) {
            return false;
        }
        
        return proveedorDAO.insertar(proveedor);
    }
    
    /**
     * Actualiza los datos de un proveedor
     */
    public boolean actualizarProveedor(Proveedor proveedor) {
        // Validar datos del proveedor
        if (proveedor.getIdProveedor() <= 0) {
            return false;
        }
        
        if (proveedor.getNombre() == null || proveedor.getNombre().trim().isEmpty()) {
            return false;
        }
        
        if (proveedor.getNit() == null || proveedor.getNit().trim().isEmpty()) {
            return false;
        }
        
        // Verificar si existe otro proveedor con el mismo NIT
        Proveedor proveedorExistente = proveedorDAO.buscarPorNit(proveedor.getNit());
        if (proveedorExistente != null && proveedorExistente.getIdProveedor() != proveedor.getIdProveedor()) {
            return false;
        }
        
        return proveedorDAO.actualizar(proveedor);
    }
    
    /**
     * Elimina un proveedor
     */
    public boolean eliminarProveedor(int id) {
        return proveedorDAO.eliminar(id);
    }
    
    /**
     * Busca un proveedor por su ID
     */
    public Proveedor buscarProveedorPorId(int id) {
        return proveedorDAO.buscarPorId(id);
    }
    
    /**
     * Busca un proveedor por su NIT
     */
    public Proveedor buscarProveedorPorNit(String nit) {
        return proveedorDAO.buscarPorNit(nit);
    }
    
    /**
     * Lista todos los proveedores
     */
    public List<Proveedor> listarProveedores() {
        return proveedorDAO.listarTodos();
    }
    
    /**
     * Busca proveedores por nombre o NIT
     */
    public List<Proveedor> buscarProveedores(String termino) {
        List<Proveedor> resultados = new ArrayList<>();
        
        // Buscar por NIT exacto
        Proveedor proveedorPorNit = proveedorDAO.buscarPorNit(termino);
        if (proveedorPorNit != null) {
            resultados.add(proveedorPorNit);
        }
        
        // Buscar por nombre parcial
        List<Proveedor> proveedoresPorNombre = proveedorDAO.buscarPorNombre(termino);
        for (Proveedor p : proveedoresPorNombre) {
            // Evitar duplicados
            if (!resultados.contains(p)) {
                resultados.add(p);
            }
        }
        
        return resultados;
    }
}