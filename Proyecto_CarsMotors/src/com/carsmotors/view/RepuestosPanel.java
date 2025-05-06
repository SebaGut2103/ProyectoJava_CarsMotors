package com.carsmotors.view;

import com.carsmotors.controller.RepuestoController;
import com.carsmotors.model.Repuesto;
import com.carsmotors.utils.SoundManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Panel para la gestión de repuestos
 */
public class RepuestosPanel extends JPanel {
    private JTable tablaRepuestos;
    private DefaultTableModel modeloTabla;
    private RepuestoController repuestoController;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnRefrescar;
    
   
    public RepuestosPanel() {
        repuestoController = new RepuestoController();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(231, 76, 60)); // Rojo
        JLabel lblTitulo = new JLabel("Gestión de Repuestos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        add(panelTitulo, BorderLayout.NORTH);
        
      
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblBuscar = new JLabel("Buscar repuesto:");
        txtBuscar = new JTextField(20);
        btnBuscar = new JButton("Buscar");
        btnBuscar.setIcon(createSafeIcon("/images/icons/search.png"));
        
        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAgregar = new JButton("Agregar");
        btnAgregar.setIcon(createSafeIcon("/images/icons/add.png"));
        btnEditar = new JButton("Editar");
        btnEditar.setIcon(createSafeIcon("/images/icons/edit.png"));
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setIcon(createSafeIcon("/images/icons/delete.png"));
        btnRefrescar = new JButton("Refrescar");
        btnRefrescar.setIcon(createSafeIcon("/images/icons/refresh.png"));
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnRefrescar);
        
        // Panel superior que contiene búsqueda y botones
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelBusqueda, BorderLayout.WEST);
        panelSuperior.add(panelBotones, BorderLayout.EAST);
        
        add(panelSuperior, BorderLayout.CENTER);
        
        // Tabla de repuestos
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que la tabla no sea editable directamente
            }
        };
        
       
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Marca Compatible");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Stock");
        modeloTabla.addColumn("Estado");
        
        tablaRepuestos = new JTable(modeloTabla);
        tablaRepuestos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaRepuestos.getTableHeader().setReorderingAllowed(false);
        
        
        tablaRepuestos.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaRepuestos.getColumnModel().getColumn(1).setPreferredWidth(150);
        tablaRepuestos.getColumnModel().getColumn(2).setPreferredWidth(100);
        tablaRepuestos.getColumnModel().getColumn(3).setPreferredWidth(150);
        tablaRepuestos.getColumnModel().getColumn(4).setPreferredWidth(100);
        tablaRepuestos.getColumnModel().getColumn(5).setPreferredWidth(80);
        tablaRepuestos.getColumnModel().getColumn(6).setPreferredWidth(100);
        
        JScrollPane scrollPane = new JScrollPane(tablaRepuestos);
        add(scrollPane, BorderLayout.SOUTH);
        
        
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarRepuestos();
            }
        });
        
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarRepuesto();
            }
        });
        
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarRepuesto();
            }
        });
        
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarRepuesto();
            }
        });
        
        btnRefrescar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarRepuestos();
            }
        });
        
       
        cargarRepuestos();
    }
    
   
    private ImageIcon createSafeIcon(String path) {
        try {
            return new ImageIcon(getClass().getResource(path));
        } catch (Exception e) {
            System.err.println("No se pudo cargar el icono: " + path);
            return null;
        }
    }
    
   
    private void cargarRepuestos() {
        try {
            // Limpiar tabla
            modeloTabla.setRowCount(0);
            
            // Obtener repuestos
            List<Repuesto> repuestos = repuestoController.obtenerTodosLosRepuestos();
            
            // Agregar repuestos a la tabla
            for (Repuesto repuesto : repuestos) {
                Object[] fila = {
                    repuesto.getId(),
                    repuesto.getNombre(),
                    repuesto.getTipo(),
                    repuesto.getMarcaCompatible(),
                    repuesto.getPrecioUnitario(),
                    repuesto.getStockActual(),
                    repuesto.getEstado()
                };
                modeloTabla.addRow(fila);
            }
            
            // Si no hay repuestos, mostrar mensaje
            if (repuestos.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "No hay repuestos registrados en el sistema.",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar los repuestos: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
   
    private void buscarRepuestos() {
        String textoBusqueda = txtBuscar.getText().trim();
        
        if (textoBusqueda.isEmpty()) {
            cargarRepuestos();
            return;
        }
        
        try {
            
            modeloTabla.setRowCount(0);
            
          
            List<Repuesto> repuestos = repuestoController.buscarRepuestos(textoBusqueda);
            
           
            for (Repuesto repuesto : repuestos) {
                Object[] fila = {
                    repuesto.getId(),
                    repuesto.getNombre(),
                    repuesto.getTipo(),
                    repuesto.getMarcaCompatible(),
                    repuesto.getPrecioUnitario(),
                    repuesto.getStockActual(),
                    repuesto.getEstado()
                };
                modeloTabla.addRow(fila);
            }
            
            // Si no hay resultados, mostrar mensaje
            if (repuestos.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "No se encontraron repuestos con el criterio de búsqueda: " + textoBusqueda,
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al buscar repuestos: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Abre el diálogo para agregar un nuevo repuesto
     */
    private void agregarRepuesto() {
        try {
            // Reproducir sonido
            SoundManager.getInstance().playSound(SoundManager.SOUND_BUTTON_CLICK);
            
            // Crear y mostrar diálogo
            RepuestoDialog dialog = new RepuestoDialog(SwingUtilities.getWindowAncestor(this), null);
            dialog.setVisible(true);
            
            // Si se guardó un repuesto, refrescar la tabla
            if (dialog.isRepuestoGuardado()) {
                cargarRepuestos();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al abrir el diálogo de repuesto: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Abre el diálogo para editar un repuesto seleccionado
     */
    private void editarRepuesto() {
        int filaSeleccionada = tablaRepuestos.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un repuesto para editar.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Reproducir sonido
            SoundManager.getInstance().playSound(SoundManager.SOUND_BUTTON_CLICK);
            
            // Obtener ID del repuesto seleccionado
            int idRepuesto = (int) tablaRepuestos.getValueAt(filaSeleccionada, 0);
            
            // Obtener repuesto
            Repuesto repuesto = repuestoController.obtenerRepuestoPorId(idRepuesto);
            
            // Crear y mostrar diálogo
            RepuestoDialog dialog = new RepuestoDialog(SwingUtilities.getWindowAncestor(this), repuesto);
            dialog.setVisible(true);
            
            // Si se guardó un repuesto, refrescar la tabla
            if (dialog.isRepuestoGuardado()) {
                cargarRepuestos();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al editar el repuesto: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Elimina un repuesto seleccionado
     */
    private void eliminarRepuesto() {
        int filaSeleccionada = tablaRepuestos.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un repuesto para eliminar.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Reproducir sonido
            SoundManager.getInstance().playSound(SoundManager.SOUND_BUTTON_CLICK);
            
            // Obtener ID del repuesto seleccionado
            int idRepuesto = (int) tablaRepuestos.getValueAt(filaSeleccionada, 0);
            String nombreRepuesto = (String) tablaRepuestos.getValueAt(filaSeleccionada, 1);
            
            // Confirmar eliminación
            int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar el repuesto " + nombreRepuesto + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                // Eliminar repuesto
                boolean eliminado = repuestoController.eliminarRepuesto(idRepuesto);
                
                if (eliminado) {
                    // Reproducir sonido de éxito
                    SoundManager.getInstance().playSound(SoundManager.SOUND_SUCCESS);
                    
                    JOptionPane.showMessageDialog(this,
                        "Repuesto eliminado correctamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Refrescar tabla
                    cargarRepuestos();
                } else {
                    // Reproducir sonido de error
                    SoundManager.getInstance().playSound(SoundManager.SOUND_ERROR);
                    
                    JOptionPane.showMessageDialog(this,
                        "No se pudo eliminar el repuesto. Puede tener registros asociados.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            // Reproducir sonido de error
            try {
                SoundManager.getInstance().playSound(SoundManager.SOUND_ERROR);
            } catch (Exception ex) {
                // Ignorar error de sonido
            }
            
            JOptionPane.showMessageDialog(this,
                "Error al eliminar el repuesto: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
