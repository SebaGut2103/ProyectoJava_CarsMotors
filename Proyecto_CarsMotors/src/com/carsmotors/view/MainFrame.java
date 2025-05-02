package com.carsmotors.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana principal del sistema
 */
public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private JMenuBar menuBar;
    
    public MainFrame() {
        setTitle("Sistema Taller Automotriz CarMotors");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Inicializar componentes
        initComponents();
        
        // Mostrar panel de inicio por defecto
        showPanel(new HomePanel());
    }
    
    private void initComponents() {
        // Panel principal
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);
        
        // Barra de menú
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        // Menú Inventario
        JMenu menuInventario = new JMenu("Gestión de Inventarios");
        menuBar.add(menuInventario);
        
        JMenuItem itemRepuestos = new JMenuItem("Repuestos");
        itemRepuestos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(new RepuestosPanel());
            }
        });
        menuInventario.add(itemRepuestos);
        
        JMenuItem itemReabastecer = new JMenuItem("Reabastecimiento");
        itemReabastecer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(new ReabastecimientoPanel());
            }
        });
        menuInventario.add(itemReabastecer);
        
        // Menú Mantenimiento
        JMenu menuMantenimiento = new JMenu("Mantenimiento y Reparaciones");
        menuBar.add(menuMantenimiento);
        
        JMenuItem itemOrdenesServicio = new JMenuItem("Órdenes de Servicio");
        itemOrdenesServicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(new OrdenesServicioPanel());
            }
        });
        menuMantenimiento.add(itemOrdenesServicio);
        
        JMenuItem itemServicios = new JMenuItem("Servicios");
        itemServicios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(new ServiciosPanel());
            }
        });
        menuMantenimiento.add(itemServicios);
        
        // Menú Clientes
        JMenu menuClientes = new JMenu("Clientes y Facturación");
        menuBar.add(menuClientes);
        
        JMenuItem itemClientes = new JMenuItem("Clientes");
        itemClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(new ClientesPanel());
            }
        });
        menuClientes.add(itemClientes);
        
        JMenuItem itemFacturas = new JMenuItem("Facturas");
        itemFacturas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(new FacturasPanel());
            }
        });
        menuClientes.add(itemFacturas);
        
        // Menú Proveedores
        JMenu menuProveedores = new JMenu("Proveedores y Compras");
        menuBar.add(menuProveedores);
        
        JMenuItem itemProveedores = new JMenuItem("Proveedores");
        itemProveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(new ProveedoresPanel());
            }
        });
        menuProveedores.add(itemProveedores);
        
        JMenuItem itemCompras = new JMenuItem("Órdenes de Compra");
        itemCompras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(new OrdenesCompraPanel());
            }
        });
        menuProveedores.add(itemCompras);
        
        // Menú Reportes
        JMenu menuReportes = new JMenu("Reportes y Estadísticas");
        menuBar.add(menuReportes);
        
        JMenuItem itemReporteInventario = new JMenuItem("Reporte de Inventario");
        itemReporteInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(new ReporteInventarioPanel());
            }
        });
        menuReportes.add(itemReporteInventario);
        
        JMenuItem itemReporteServicios = new JMenuItem("Reporte de Servicios");
        itemReporteServicios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(new ReporteServiciosPanel());
            }
        });
        menuReportes.add(itemReporteServicios);
    }
    
    /**
     * Muestra un panel en el área principal
     */
    private void showPanel(JPanel panel) {
        mainPanel.removeAll();
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    /**
     * Clase interna para el panel de inicio
     */
    private class HomePanel extends JPanel {
        public HomePanel() {
            setLayout(new BorderLayout());
            
            JLabel titleLabel = new JLabel("Bienvenido al Sistema de Taller Automotriz CarMotors", JLabel.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            add(titleLabel, BorderLayout.NORTH);
            
            JPanel buttonsPanel = new JPanel(new GridLayout(2, 3, 20, 20));
            buttonsPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
            
            // Botones de acceso rápido
            addQuickAccessButton(buttonsPanel, "Clientes", new ClientesPanel());
            addQuickAccessButton(buttonsPanel, "Repuestos", new RepuestosPanel());
            addQuickAccessButton(buttonsPanel, "Órdenes de Servicio", new OrdenesServicioPanel());
            addQuickAccessButton(buttonsPanel, "Facturas", new FacturasPanel());
            addQuickAccessButton(buttonsPanel, "Proveedores", new ProveedoresPanel());
            addQuickAccessButton(buttonsPanel, "Reportes", new ReporteInventarioPanel());
            
            add(buttonsPanel, BorderLayout.CENTER);
        }
        
        private void addQuickAccessButton(JPanel panel, String text, JPanel targetPanel) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.PLAIN, 16));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showPanel(targetPanel);
                }
            });
            panel.add(button);
        }
    }
    
    /**
     * Clase base para los paneles de contenido
     */
    private abstract class ContentPanel extends JPanel {
        public ContentPanel(String title) {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JLabel titleLabel = new JLabel(title, JLabel.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
            add(titleLabel, BorderLayout.NORTH);
        }
    }
    
    /**
     * Panel para la gestión de clientes
     */
    private class ClientesPanel extends ContentPanel {
        public ClientesPanel() {
            super("Gestión de Clientes");
            
            // Aquí se implementaría la interfaz para gestionar clientes
            JPanel panel = new JPanel(new BorderLayout());
            
            // Tabla de clientes
            String[] columnas = {"ID", "Nombre", "Identificación", "Teléfono", "Email", "Dirección"};
            Object[][] datos = {}; // Aquí se cargarían los datos reales
            
            JTable table = new JTable(datos, columnas);
            JScrollPane scrollPane = new JScrollPane(table);
            panel.add(scrollPane, BorderLayout.CENTER);
            
            // Panel de botones
            JPanel buttonPanel = new JPanel();
            JButton btnNuevo = new JButton("Nuevo Cliente");
            JButton btnEditar = new JButton("Editar");
            JButton btnEliminar = new JButton("Eliminar");
            
            buttonPanel.add(btnNuevo);
            buttonPanel.add(btnEditar);
            buttonPanel.add(btnEliminar);
            
            panel.add(buttonPanel, BorderLayout.SOUTH);
            
            add(panel, BorderLayout.CENTER);
        }
    }
    
    /**
     * Panel para la gestión de repuestos
     */
    private class RepuestosPanel extends ContentPanel {
        public RepuestosPanel() {
            super("Gestión de Repuestos");
            
            // Aquí se implementaría la interfaz para gestionar repuestos
            JPanel panel = new JPanel(new BorderLayout());
            
            // Tabla de repuestos
            String[] columnas = {"ID", "Nombre", "Tipo", "Marca Compatible", "Stock Actual", "Precio Unitario", "Estado"};
            Object[][] datos = {}; // Aquí se cargarían los datos reales
            
            JTable table = new JTable(datos, columnas);
            JScrollPane scrollPane = new JScrollPane(table);
            panel.add(scrollPane, BorderLayout.CENTER);
            
            // Panel de botones
            JPanel buttonPanel = new JPanel();
            JButton btnNuevo = new JButton("Nuevo Repuesto");
            JButton btnEditar = new JButton("Editar");
            JButton btnEliminar = new JButton("Eliminar");
            
            buttonPanel.add(btnNuevo);
            buttonPanel.add(btnEditar);
            buttonPanel.add(btnEliminar);
            
            panel.add(buttonPanel, BorderLayout.SOUTH);
            
            add(panel, BorderLayout.CENTER);
        }
    }
    
    // Implementación de los demás paneles (se omiten por brevedad)
    private class ReabastecimientoPanel extends ContentPanel {
        public ReabastecimientoPanel() {
            super("Reabastecimiento de Inventario");
            // Implementación del panel
        }
    }
    
    private class OrdenesServicioPanel extends ContentPanel {
        public OrdenesServicioPanel() {
            super("Órdenes de Servicio");
            // Implementación del panel
        }
    }
    
    private class ServiciosPanel extends ContentPanel {
        public ServiciosPanel() {
            super("Servicios");
            // Implementación del panel
        }
    }
    
    private class FacturasPanel extends ContentPanel {
        public FacturasPanel() {
            super("Facturas");
            // Implementación del panel
        }
    }
    
    private class ProveedoresPanel extends ContentPanel {
        public ProveedoresPanel() {
            super("Proveedores");
            // Implementación del panel
        }
    }
    
    private class OrdenesCompraPanel extends ContentPanel {
        public OrdenesCompraPanel() {
            super("Órdenes de Compra");
            // Implementación del panel
        }
    }
    
    private class ReporteInventarioPanel extends ContentPanel {
        public ReporteInventarioPanel() {
            super("Reporte de Inventario");
            // Implementación del panel
        }
    }
    
    private class ReporteServiciosPanel extends ContentPanel {
        public ReporteServiciosPanel() {
            super("Reporte de Servicios");
            // Implementación del panel
        }
    }
}
