package com.carsmotors.controller;

import com.carsmotors.dao.ClienteDAO;
import com.carsmotors.model.Cliente;
import java.util.List;

/**
 * Controlador para la gestión de clientes
 */
public class ClienteController {
    private ClienteDAO clienteDAO;
    
    /**
     * Constructor del controlador
     */
    public ClienteController() {
        this.clienteDAO = new ClienteDAO();
    }
    
    /**
     * Obtiene todos los clientes
     * @return Lista de clientes
     */
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteDAO.obtenerTodos();
    }
    
    /**
     * Busca clientes por texto
     * @param texto Texto a buscar
     * @return Lista de clientes que coinciden con la búsqueda
     */
    public List<Cliente> buscarClientes(String texto) {
        return clienteDAO.buscar(texto);
    }
    
    /**
     * Obtiene un cliente por su ID
     * @param id ID del cliente
     * @return Cliente encontrado o null si no existe
     */
    public Cliente obtenerClientePorId(int id) {
        return clienteDAO.obtenerPorId(id);
    }
    
    /**
     * Registra un nuevo cliente
     * @param cliente Cliente a registrar
     * @return true si se registró correctamente, false en caso contrario
     */
    public boolean registrarCliente(Cliente cliente) {
        // Verificar si ya existe un cliente con la misma identificación
        Cliente clienteExistente = clienteDAO.buscarPorIdentificacion(cliente.getIdentificacion());
        if (clienteExistente != null) {
            System.err.println("Ya existe un cliente con la identificación: " + cliente.getIdentificacion());
            return false;
        }
        
        return clienteDAO.insertar(cliente);
    }
    
    /**
     * Actualiza un cliente existente
     * @param cliente Cliente a actualizar
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizarCliente(Cliente cliente) {
        // Verificar si ya existe otro cliente con la misma identificación
        Cliente clienteExistente = clienteDAO.buscarPorIdentificacion(cliente.getIdentificacion());
        if (clienteExistente != null && clienteExistente.getId() != cliente.getId()) {
            System.err.println("Ya existe otro cliente con la identificación: " + cliente.getIdentificacion());
            return false;
        }
        
        return clienteDAO.actualizar(cliente);
    }
    
    /**
     * Elimina un cliente
     * @param id ID del cliente a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminarCliente(int id) {
        // Verificar si el cliente tiene registros asociados
        if (clienteDAO.tieneRegistrosAsociados(id)) {
            System.err.println("No se puede eliminar el cliente porque tiene registros asociados");
            return false;
        }
        
        return clienteDAO.eliminar(id);
    }
}

