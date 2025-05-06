package com.carsmotors.view;

import com.carsmotors.controller.ClienteController;
import com.carsmotors.model.Cliente;
import com.carsmotors.utils.SoundManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Panel para la gestión de clientes
 */
public class ClientesPanel extends JPanel {
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnNuevo;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnRefrescar;
    
    private ClienteController controller;
    private SoundManager soundManager;
    
    /**
     * Constructor del panel de clientes
     */
    public ClientesPanel() {
        controller = new ClienteController();
        soundManager = SoundManager.getInstance();
        
        initComponents();
        cargarDatos();
    }
    
    /**
     * Inicializa los componentes del panel
     */
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Panel de título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(52, 152, 219)); // Azul
        JLabel lblTitulo = new JLabel("Gestión de Clientes");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        add(panelTitulo, BorderLayout.NORTH);
        
        // Panel central con tabla y controles
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel superior con buscador y botones
        JPanel panelSuperior = new JPanel(new BorderLayout());
        
        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(new JLabel("Buscar:"));
        txtBuscar = new JTextField(20);
        panelBusqueda.add(txtBuscar);
        btnBuscar = new JButton("Buscar");
        panelBusqueda.add(btnBuscar);
        
        panelSuperior.add(panelBusqueda, BorderLayout.WEST);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnNuevo = new JButton("Nuevo Cliente");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnRefrescar = new JButton("Refrescar");
        
        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnRefrescar);
        
        panelSuperior.add(panelBotones, BorderLayout.EAST);
        
        panelCentral.add(panelSuperior, BorderLayout.NORTH);
        
        // Tabla de clientes
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No permitir edición directa en la tabla
            }
        };
        
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Identificación");
        modeloTabla.addColumn("Teléfono");
        modeloTabla.addColumn("Email");
        modeloTabla.addColumn("Dirección");
        
        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaClientes.getTableHeader().setReorderingAllowed(false);
        
        // Ajustar anchos de columnas
        tablaClientes.getColumnModel().getColumn(0).setPreferredWidth(50); // ID
        tablaClientes.getColumnModel().getColumn(1).setPreferredWidth(150); // Nombre
        tablaClientes.getColumnModel().getColumn(2).setPreferredWidth(100); // Identificación
        tablaClientes.getColumnModel().getColumn(3).setPreferredWidth(100); // Teléfono
        tablaClientes.getColumnModel().getColumn(4).setPreferredWidth(150); // Email
        tablaClientes.getColumnModel().getColumn(5).setPreferredWidth(200); // Dirección
        
        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        panelCentral.add(scrollPane, BorderLayout.CENTER);
        
        add(panelCentral, BorderLayout.CENTER);
        
        // Configurar eventos
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
                buscarClientes();
            }
        });
        
        txtBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarClientes();
            }
        });
        
        btnNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
                nuevoCliente();
            }
        });
        
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
                editarCliente();
            }
        });
        
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
                eliminarCliente();
            }
        });
        
        btnRefrescar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
                cargarDatos();
            }
        });
        
        tablaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editarCliente();
                }
            }
        });
    }
    
    /**
     * Reproduce un sonido de manera segura
     */
    private void playSafeSound(String soundName) {
        try {
            if (soundManager != null) {
                soundManager.playSound(soundName);
            }
        } catch (Exception e) {
            System.err.println("Error al reproducir sonido: " + e.getMessage());
        }
    }
    
    /**
     * Carga los datos de clientes en la tabla
     */
    public void cargarDatos() {
        // Mostrar mensaje de carga
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        // Ejecutar en un hilo separado para no bloquear la interfaz
        SwingWorker<List<Cliente>, Void> worker = new SwingWorker<List<Cliente>, Void>() {
            @Override
            protected List<Cliente> doInBackground() throws Exception {
                return controller.obtenerTodosLosClientes();
            }
            
            @Override
            protected void done() {
                try {
                    // Limpiar la tabla
                    modeloTabla.setRowCount(0);
                    
                    // Obtener los clientes
                    List<Cliente> clientes = get();
                    
                    // Agregar los clientes a la tabla
                    for (Cliente cliente : clientes) {
                        Object[] fila = {
                            cliente.getId(),
                            cliente.getNombre(),
                            cliente.getIdentificacion(),
                            cliente.getTelefono(),
                            cliente.getEmail(),
                            cliente.getDireccion()
                        };
                        modeloTabla.addRow(fila);
                    }
                    
                    // Mostrar mensaje si no hay clientes
                    if (clientes.isEmpty()) {
                        JOptionPane.showMessageDialog(ClientesPanel.this,
                            "No hay clientes registrados en el sistema.",
                            "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                    
                } catch (Exception e) {
                    System.err.println("Error al cargar datos: " + e.getMessage());
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(ClientesPanel.this,
                        "Error al cargar los datos: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                } finally {
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        };
        
        worker.execute();
    }
    
    /**
     * Busca clientes según el texto ingresado
     */
    private void buscarClientes() {
        String textoBusqueda = txtBuscar.getText().trim();
        
        if (textoBusqueda.isEmpty()) {
            cargarDatos();
            return;
        }
        
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        SwingWorker<List<Cliente>, Void> worker = new SwingWorker<List<Cliente>, Void>() {
            @Override
            protected List<Cliente> doInBackground() throws Exception {
                return controller.buscarClientes(textoBusqueda);
            }
            
            @Override
            protected void done() {
                try {
                    // Limpiar la tabla
                    modeloTabla.setRowCount(0);
                    
                    // Obtener los clientes
                    List<Cliente> clientes = get();
                    
                    // Agregar los clientes a la tabla
                    for (Cliente cliente : clientes) {
                        Object[] fila = {
                            cliente.getId(),
                            cliente.getNombre(),
                            cliente.getIdentificacion(),
                            cliente.getTelefono(),
                            cliente.getEmail(),
                            cliente.getDireccion()
                        };
                        modeloTabla.addRow(fila);
                    }
                    
                    // Mostrar mensaje si no hay resultados
                    if (clientes.isEmpty()) {
                        JOptionPane.showMessageDialog(ClientesPanel.this,
                            "No se encontraron clientes con el criterio de búsqueda: " + textoBusqueda,
                            "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception e) {
                    System.err.println("Error al buscar clientes: " + e.getMessage());
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(ClientesPanel.this,
                        "Error al buscar clientes: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                } finally {
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        };
        
        worker.execute();
    }
    
    /**
     * Abre el diálogo para crear un nuevo cliente
     */
    private void nuevoCliente() {
        ClienteDialog dialog = new ClienteDialog(SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);
        
        if (dialog.isClienteGuardado()) {
            cargarDatos();
            playSafeSound(SoundManager.SOUND_SUCCESS);
        }
    }
    
    /**
     * Abre el diálogo para editar un cliente existente
     */
    private void editarCliente() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            playSafeSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this, 
                "Por favor, seleccione un cliente para editar", 
                "Selección requerida", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int idCliente = (int) tablaClientes.getValueAt(filaSeleccionada, 0);
        Cliente cliente = controller.obtenerClientePorId(idCliente);
        
        if (cliente != null) {
            ClienteDialog dialog = new ClienteDialog(SwingUtilities.getWindowAncestor(this), cliente);
            dialog.setVisible(true);
            
            if (dialog.isClienteGuardado()) {
                cargarDatos();
                playSafeSound(SoundManager.SOUND_SUCCESS);
            }
        } else {
            playSafeSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this, 
                "No se pudo cargar los datos del cliente", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Elimina un cliente seleccionado
     */
    private void eliminarCliente() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            playSafeSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this, 
                "Por favor, seleccione un cliente para eliminar", 
                "Selección requerida", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int idCliente = (int) tablaClientes.getValueAt(filaSeleccionada, 0);
        String nombreCliente = (String) tablaClientes.getValueAt(filaSeleccionada, 1);
        
        // Confirmar eliminación
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar al cliente " + nombreCliente + "?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean resultado = controller.eliminarCliente(idCliente);
            
            if (resultado) {
                playSafeSound(SoundManager.SOUND_SUCCESS);
                JOptionPane.showMessageDialog(this, 
                    "Cliente eliminado correctamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                cargarDatos();
            } else {
                playSafeSound(SoundManager.SOUND_ERROR);
                JOptionPane.showMessageDialog(this, 
                    "Error al eliminar el cliente. Puede tener registros asociados.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
