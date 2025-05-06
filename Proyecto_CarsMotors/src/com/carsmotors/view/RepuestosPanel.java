package com.carsmotors.view;

import com.carsmotors.controller.RepuestoController;
import com.carsmotors.model.Repuesto;
import com.carsmotors.utils.SoundManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Panel para la gestión de repuestos
 */
public class RepuestosPanel extends JPanel {
    private JTable tblRepuestos;
    private DefaultTableModel modeloTabla;
    private JButton btnNuevo;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnVer;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JComboBox<String> cboFiltroTipo;
    
    private RepuestoController controller;
    private SoundManager soundManager;
    
    /**
     * Constructor del panel
     */
    public RepuestosPanel() {
        this.controller = new RepuestoController();
        this.soundManager = SoundManager.getInstance();
        
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
        panelTitulo.setBackground(new Color(231, 76, 60)); // Rojo para repuestos
        JLabel lblTitulo = new JLabel("Gestión de Repuestos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        add(panelTitulo, BorderLayout.NORTH);
        
        // Panel de filtros y búsqueda
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        panelFiltros.add(new JLabel("Tipo:"));
        cboFiltroTipo = new JComboBox<>(new String[]{"Todos", "Mecánico", "Eléctrico", "Carrocería", "Consumo"});
        cboFiltroTipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                cargarDatos();
            }
        });
        panelFiltros.add(cboFiltroTipo);
        
        panelFiltros.add(new JLabel("Buscar:"));
        txtBuscar = new JTextField(15);
        panelFiltros.add(txtBuscar);
        
        btnBuscar = new JButton("Buscar");
        btnBuscar.setIcon(new ImageIcon(getClass().getResource("/images/search.png")));
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                buscarRepuestos();
            }
        });
        panelFiltros.add(btnBuscar);
        
        add(panelFiltros, BorderLayout.SOUTH);
        
        // Tabla de repuestos
        String[] columnas = {"ID", "Nombre", "Tipo", "Precio", "Stock", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tblRepuestos = new JTable(modeloTabla);
        tblRepuestos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblRepuestos.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane scrollPane = new JScrollPane(tblRepuestos);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        
        btnNuevo = new JButton("Nuevo Repuesto");
        btnNuevo.setIcon(new ImageIcon(getClass().getResource("/images/add.png")));
        btnNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                nuevoRepuesto();
            }
        });
        
        btnEditar = new JButton("Editar");
        btnEditar.setIcon(new ImageIcon(getClass().getResource("/images/edit.png")));
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                editarRepuesto();
            }
        });
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setIcon(new ImageIcon(getClass().getResource("/images/delete.png")));
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                eliminarRepuesto();
            }
        });
        
        btnVer = new JButton("Ver Detalles");
        btnVer.setIcon(new ImageIcon(getClass().getResource("/images/view.png")));
        btnVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                verDetallesRepuesto();
            }
        });
        
        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVer);
        
        add(panelBotones, BorderLayout.EAST);
    }
    
    /**
     * Carga los datos de repuestos en la tabla
     */
    public void cargarDatos() {
        // Limpiar la tabla
        modeloTabla.setRowCount(0);
        
        // Obtener el filtro seleccionado
        String filtroTipo = (String) cboFiltroTipo.getSelectedItem();
        
        // Cargar los datos según el filtro
        List<Repuesto> repuestos;
        if (filtroTipo.equals("Todos")) {
            repuestos = controller.listarRepuestos();
        } else {
            repuestos = controller.listarRepuestosPorTipo(filtroTipo);
        }
        
        // Llenar la tabla con los datos
        for (Repuesto repuesto : repuestos) {
            modeloTabla.addRow(new Object[]{
                repuesto.getId(),
                repuesto.getNombre(),
                repuesto.getTipo(),
                String.format("$%.2f", repuesto.getPrecioUnitario()),
                repuesto.getStockActual(),
                repuesto.getEstado()
            });
        }
    }
    
    /**
     * Busca repuestos por nombre o descripción
     */
    private void buscarRepuestos() {
        String termino = txtBuscar.getText().trim();
        
        if (termino.isEmpty()) {
            cargarDatos(); // Si no hay término de búsqueda, mostrar todos
            return;
        }
        
        // Limpiar la tabla
        modeloTabla.setRowCount(0);
        
        // Buscar repuestos que coincidan con el término
        List<Repuesto> repuestos = controller.buscarRepuestos(termino);
        
        // Llenar la tabla con los resultados
        for (Repuesto repuesto : repuestos) {
            modeloTabla.addRow(new Object[]{
                repuesto.getId(),
                repuesto.getNombre(),
                repuesto.getTipo(),
                String.format("$%.2f", repuesto.getPrecioUnitario()),
                repuesto.getStockActual(),
                repuesto.getEstado()
            });
        }
        
        // Si no se encontraron resultados
        if (modeloTabla.getRowCount() == 0) {
            soundManager.playSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this,
                "No se encontraron repuestos con el término: " + termino,
                "Sin resultados",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Abre el diálogo para crear un nuevo repuesto
     */
    private void nuevoRepuesto() {
        RepuestoDialog dialog = new RepuestoDialog(
            (JFrame) SwingUtilities.getWindowAncestor(this),
            "Nuevo Repuesto",
            null
        );
        dialog.setVisible(true);
        
        // Recargar datos después de cerrar el diálogo
        cargarDatos();
    }
    
    /**
     * Abre el diálogo para editar un repuesto existente
     */
    private void editarRepuesto() {
        int filaSeleccionada = tblRepuestos.getSelectedRow();
        
        if (filaSeleccionada >= 0) {
            int idRepuesto = (int) tblRepuestos.getValueAt(filaSeleccionada, 0);
            Repuesto repuesto = controller.buscarRepuestoPorId(idRepuesto);
            
            if (repuesto != null) {
                RepuestoDialog dialog = new RepuestoDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    "Editar Repuesto",
                    repuesto
                );
                dialog.setVisible(true);
                
                // Recargar datos después de cerrar el diálogo
                cargarDatos();
            }
        } else {
            soundManager.playSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un repuesto para editar",
                "Selección requerida",
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Elimina un repuesto seleccionado
     */
    private void eliminarRepuesto() {
        int filaSeleccionada = tblRepuestos.getSelectedRow();
        
        if (filaSeleccionada >= 0) {
            int idRepuesto = (int) tblRepuestos.getValueAt(filaSeleccionada, 0);
            
            int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar este repuesto?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean resultado = controller.eliminarRepuesto(idRepuesto);
                
                if (resultado) {
                    soundManager.playSound(SoundManager.SOUND_SUCCESS);
                    JOptionPane.showMessageDialog(this,
                        "Repuesto eliminado correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Recargar datos
                    cargarDatos();
                } else {
                    soundManager.playSound(SoundManager.SOUND_ERROR);
                    JOptionPane.showMessageDialog(this,
                        "Error al eliminar el repuesto",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            soundManager.playSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un repuesto para eliminar",
                "Selección requerida",
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Muestra los detalles de un repuesto
     */
    private void verDetallesRepuesto() {
        int filaSeleccionada = tblRepuestos.getSelectedRow();
        
        if (filaSeleccionada >= 0) {
            int idRepuesto = (int) tblRepuestos.getValueAt(filaSeleccionada, 0);
            Repuesto repuesto = controller.buscarRepuestoPorId(idRepuesto);
            
            if (repuesto != null) {
                StringBuilder mensaje = new StringBuilder();
                mensaje.append("Repuesto #").append(repuesto.getId()).append("\n");
                mensaje.append("Nombre: ").append(repuesto.getNombre()).append("\n");
                mensaje.append("Tipo: ").append(repuesto.getTipo()).append("\n");
                mensaje.append("Descripción: ").append(repuesto.getDescripcion()).append("\n");
                mensaje.append("Precio Unitario: $").append(String.format("%.2f", repuesto.getPrecioUnitario())).append("\n");
                mensaje.append("Stock Actual: ").append(repuesto.getStockActual()).append("\n");
                mensaje.append("Stock Mínimo: ").append(repuesto.getStockMinimo()).append("\n");
                mensaje.append("Estado: ").append(repuesto.getEstado()).append("\n");
                mensaje.append("Ubicación: ").append(repuesto.getUbicacion());
                
                JOptionPane.showMessageDialog(this,
                    mensaje.toString(),
                    "Detalles del Repuesto",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            soundManager.playSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un repuesto para ver sus detalles",
                "Selección requerida",
                JOptionPane.WARNING_MESSAGE);
        }
    }
}