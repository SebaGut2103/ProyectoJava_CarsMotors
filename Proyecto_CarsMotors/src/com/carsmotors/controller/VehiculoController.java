package com.carsmotors.controller;

import com.carsmotors.dao.VehiculoDAO;
import com.carsmotors.model.Vehiculo;
import java.util.List;

/**
 * Controlador para la gestión de vehículos
 */
public class VehiculoController {
    private VehiculoDAO vehiculoDAO;
    
    /**
     * Constructor del controlador
     */
    public VehiculoController() {
        this.vehiculoDAO = new VehiculoDAO();
    }
    
    /**
     * Obtiene todos los vehículos
     * @return Lista de vehículos
     */
    public List<Vehiculo> obtenerTodosLosVehiculos() {
        return vehiculoDAO.obtenerTodos();
    }
    
    /**
     * Obtiene los vehículos de un cliente
     * @param idCliente ID del cliente
     * @return Lista de vehículos del cliente
     */
    public List<Vehiculo> obtenerVehiculosPorCliente(int idCliente) {
        return vehiculoDAO.obtenerPorCliente(idCliente);
    }
    
    /**
     * Busca vehículos por texto
     * @param texto Texto a buscar
     * @return Lista de vehículos que coinciden con la búsqueda
     */
    public List<Vehiculo> buscarVehiculos(String texto) {
        return vehiculoDAO.buscar(texto);
    }
    
    /**
     * Obtiene un vehículo por su ID
     * @param id ID del vehículo
     * @return Vehículo encontrado o null si no existe
     */
    public Vehiculo obtenerVehiculoPorId(int id) {
        return vehiculoDAO.obtenerPorId(id);
    }
    
    /**
     * Registra un nuevo vehículo
     * @param vehiculo Vehículo a registrar
     * @return true si se registró correctamente, false en caso contrario
     */
    public boolean registrarVehiculo(Vehiculo vehiculo) {
        return vehiculoDAO.insertar(vehiculo);
    }
    
    /**
     * Actualiza un vehículo existente
     * @param vehiculo Vehículo a actualizar
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizarVehiculo(Vehiculo vehiculo) {
        return vehiculoDAO.actualizar(vehiculo);
    }
    
    /**
     * Elimina un vehículo
     * @param id ID del vehículo a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminarVehiculo(int id) {
        // Verificar si el vehículo tiene registros asociados
        if (vehiculoDAO.tieneRegistrosAsociados(id)) {
            System.err.println("No se puede eliminar el vehículo porque tiene registros asociados");
            return false;
        }
        
        return vehiculoDAO.eliminar(id);
    }
}

