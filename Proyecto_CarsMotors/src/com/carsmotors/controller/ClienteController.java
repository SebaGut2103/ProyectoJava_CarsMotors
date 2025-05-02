package com.carsmotors.controller;

import com.carsmotors.dao.ClienteDAO;
import com.carsmotors.model.Cliente;

import java.util.List;

/**
 * Controlador para la gestión de clientes
 */
public class ClienteController {
    private ClienteDAO clienteDAO;
    
    public ClienteController() {
        this.clienteDAO = new ClienteDAO();
    }
    
    /**
     * Registra un nuevo cliente
     */
    public boolean registrarCliente(Cliente cliente) {
        // Validar datos del cliente
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            return false;
        }
        
        if (cliente.getIdentificacion() == null || cliente.getIdentificacion().trim().isEmpty()) {
            return false;
        }
        
        // Verificar si ya existe un cliente con la misma identificación
        Cliente clienteExistente = clienteDAO.buscarPorIdentificacion(cliente.getIdentificacion());
        if (clienteExistente != null) {
            return false;
        }
        
        return clienteDAO.insertar(cliente);
    }
    
    /**
     * Actualiza los datos de un cliente
     */
    public boolean actualizarCliente(Cliente cliente) {
        // Validar datos del cliente
        if (cliente.getId() <= 0) {
            return false;
        }
        
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            return false;
        }
        
        if (cliente.getIdentificacion() == null || cliente.getIdentificacion().trim().isEmpty()) {
            return false;
        }
        
        // Verificar si existe otro cliente con la misma identificación
        Cliente clienteExistente = clienteDAO.buscarPorIdentificacion(cliente.getIdentificacion());
        if (clienteExistente != null && clienteExistente.getId() != cliente.getId()) {
            return false;
        }
        
        return clienteDAO.actualizar(cliente);
    }
    
    /**
     * Elimina un cliente
     */
    public boolean eliminarCliente(int id) {
        return clienteDAO.eliminar(id);
    }
    
    /**
     * Busca un cliente por su ID
     */
    public Cliente buscarClientePorId(int id) {
        return clienteDAO.buscarPorId(id);
    }
    
    /**
     * Busca un cliente por su identificación
     */
    public Cliente buscarClientePorIdentificacion(String identificacion) {
        return clienteDAO.buscarPorIdentificacion(identificacion);
    }
    
    /**
     * Lista todos los clientes
     */
    public List<Cliente> listarClientes() {
        return clienteDAO.listarTodos();
    }
}
