package com.carsmotors.test;

import java.lang.reflect.Constructor;

/**
 * Clase para diagnosticar problemas con la carga de clases
 */
public class DiagnosticoClases {
    
    public static void main(String[] args) {
        System.out.println("Iniciando diagnóstico de clases...");
        
        // Lista de clases a verificar
        String[] clasesAVerificar = {
            "com.carsmotors.view.ClientesPanel",
            "com.carsmotors.view.ProveedoresPanel",
            "com.carsmotors.view.FacturasPanel",
            "com.carsmotors.view.OrdenesServicioPanel",
            "com.carsmotors.view.RepuestosPanel",
            "com.carsmotors.controller.ClienteController",
            "com.carsmotors.controller.ProveedorController",
            "com.carsmotors.controller.FacturaController",
            "com.carsmotors.controller.OrdenServicioController",
            "com.carsmotors.controller.RepuestoController",
            "com.carsmotors.dao.ClienteDAO",
            "com.carsmotors.dao.ProveedorDAO",
            "com.carsmotors.dao.FacturaDAO",
            "com.carsmotors.dao.OrdenServicioDAO",
            "com.carsmotors.dao.RepuestoDAO",
            "com.carsmotors.database.DatabaseConnection"
        };
        
        // Verificar cada clase
        for (String nombreClase : clasesAVerificar) {
            verificarClase(nombreClase);
        }
        
        System.out.println("Diagnóstico completado.");
    }
    
    /**
     * Verifica si una clase existe y puede ser instanciada
     */
    private static void verificarClase(String nombreClase) {
        System.out.println("\nVerificando clase: " + nombreClase);
        
        try {
            // Intentar cargar la clase
            System.out.println("  - Intentando cargar la clase...");
            Class<?> clase = Class.forName(nombreClase);
            System.out.println("  - Clase cargada exitosamente.");
            
            // Verificar si tiene constructor sin argumentos
            System.out.println("  - Verificando constructores...");
            Constructor<?>[] constructores = clase.getConstructors();
            boolean tieneConstructorSinArgs = false;
            
            for (Constructor<?> constructor : constructores) {
                if (constructor.getParameterCount() == 0) {
                    tieneConstructorSinArgs = true;
                    break;
                }
            }
            
            if (tieneConstructorSinArgs) {
                System.out.println("  - Tiene constructor sin argumentos.");
                
                // Intentar crear una instancia
                System.out.println("  - Intentando crear una instancia...");
                Object instancia = clase.newInstance();
                System.out.println("  - Instancia creada exitosamente: " + instancia.getClass().getName());
            } else {
                System.out.println("  - ADVERTENCIA: No tiene constructor sin argumentos.");
                System.out.println("  - Constructores disponibles:");
                
                for (Constructor<?> constructor : constructores) {
                    System.out.println("    * " + constructor);
                }
            }
            
        } catch (ClassNotFoundException e) {
            System.out.println("  - ERROR: Clase no encontrada.");
            System.out.println("  - Detalles: " + e.getMessage());
        } catch (InstantiationException e) {
            System.out.println("  - ERROR: No se puede instanciar la clase.");
            System.out.println("  - Detalles: " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println("  - ERROR: Acceso ilegal al constructor.");
            System.out.println("  - Detalles: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("  - ERROR: Excepción al verificar la clase.");
            System.out.println("  - Tipo: " + e.getClass().getName());
            System.out.println("  - Mensaje: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
