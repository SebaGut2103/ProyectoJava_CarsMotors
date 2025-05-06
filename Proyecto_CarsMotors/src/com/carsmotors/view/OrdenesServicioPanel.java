package com.carsmotors.view;

import com.carsmotors.controller.OrdenServicioController;
import com.carsmotors.model.OrdenServicio;
import com.carsmotors.utils.SoundManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class OrdenesServicioPanel extends JPanel {
    private JTable tblOrdenes;
    private DefaultTableModel modeloTabla;
    private JButton btnNueva;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnVer;
    private JComboBox<String> cboFiltroEstado;
    
    private OrdenServicioController controller;
    private SoundManager soundManager;
    
    public OrdenesServicioPanel() {
        this.controller = new OrdenServicioController();
        this.soundManager = SoundManager.getInstance();
        
        initComponents();
        cargarDatos();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Panel de título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(46, 204, 113));
        JLabel lblTitulo = new JLabel("Gestión de Órdenes de Servicio");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        add(panelTitulo, BorderLayout.NORTH);
        
        // Panel de filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltros.add(new JLabel("Filtrar por estado:"));
        
        cboFiltroEstado = new JComboBox<>(new String[]{"Todos", "Pendiente", "En proceso", "Completado", "Entregado"});
        cboFiltroEstado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                cargarDatos();
            }
        });
        panelFiltros.add(cboFiltroEstado);
        
        add(panelFiltros, BorderLayout.SOUTH);
        
        // Tabla de órdenes
        String[] columnas = {"ID", "Fecha Ingreso", "Vehículo", "Técnico", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tblOrdenes = new JTable(modeloTabla);
        tblOrdenes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblOrdenes.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane scrollPane = new JScrollPane(tblOrdenes);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        
        btnNueva = new JButton("Nueva Orden");
        btnNueva.setIcon(new ImageIcon(getClass().getResource("/images/add.png")));
        btnNueva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                nuevaOrden();
            }
        });
        
        btnEditar = new JButton("Editar");
        btnEditar.setIcon(new ImageIcon(getClass().getResource("/images/edit.png")));
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                editarOrden();
            }
        });
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setIcon(new ImageIcon(getClass().getResource("/images/delete.png")));
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                eliminarOrden();
            }
        });
        
        btnVer = new JButton("Ver Detalles");
        btnVer.setIcon(new ImageIcon(getClass().getResource("/images/view.png")));
        btnVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                verDetallesOrden();
            }
        });
        
        panelBotones.add(btnNueva);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVer);
        
        add(panelBotones, BorderLayout.EAST);
    }
    
    /**
     * Carga los datos de órdenes en la tabla
     */
    public void cargarDatos() {
        // Limpiar la tabla
        modeloTabla.setRowCount(0);
        
        // Obtener el filtro seleccionado
        String filtro = (String) cboFiltroEstado.getSelectedItem();
        
        // Cargar los datos según el filtro
        List<OrdenServicio> ordenes;
        if (filtro.equals("Todos")) {
            ordenes = controller.listarOrdenesServicio();
        } else {
            ordenes = controller.listarOrdenesServicioPorEstado(filtro);
        }
        
        // Formatear fecha
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        // Llenar la tabla con los datos
        for (OrdenServicio orden : ordenes) {
            // Obtener información del vehículo y técnico (simulado)
            String vehiculo = "Vehículo " + orden.getIdVehiculo();
            String tecnico = "Técnico " + orden.getIdTecnico();
            
            modeloTabla.addRow(new Object[]{
                orden.getIdOrdenServicio(),
                sdf.format(orden.getFechaIngreso()),
                vehiculo,
                tecnico,
                orden.getEstado()
            });
        }
    }
    
    /**
     * Abre el diálogo para crear una nueva orden
     */
    private void nuevaOrden() {
        OrdenServicioDialog dialog = new OrdenServicioDialog(
            (JFrame) SwingUtilities.getWindowAncestor(this),
            "Nueva Orden de Servicio",
            null
        );
        dialog.setVisible(true);
        
        // Recargar datos después de cerrar el diálogo
        cargarDatos();
    }
    
    /**
     * Abre el diálogo para editar una orden existente
     */
    private void editarOrden() {
        int filaSeleccionada = tblOrdenes.getSelectedRow();
        
        if (filaSeleccionada >= 0) {
            int idOrden = (int) tblOrdenes.getValueAt(filaSeleccionada, 0);
            OrdenServicio orden = controller.buscarOrdenServicioPorId(idOrden);
            
            if (orden != null) {
                OrdenServicioDialog dialog = new OrdenServicioDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    "Editar Orden de Servicio",
                    orden
                );
                dialog.setVisible(true);
                
                // Recargar datos después de cerrar el diálogo
                cargarDatos();
            }
        } else {
            soundManager.playSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione una orden para editar",
                "Selección requerida",
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Elimina una orden seleccionada
     */
    private void eliminarOrden() {
        int filaSeleccionada = tblOrdenes.getSelectedRow();
        
        if (filaSeleccionada >= 0) {
            int idOrden = (int) tblOrdenes.getValueAt(filaSeleccionada, 0);
            
            int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar esta orden de servicio?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean resultado = controller.eliminarOrdenServicio(idOrden);
                
                if (resultado) {
                    soundManager.playSound(SoundManager.SOUND_SUCCESS);
                    JOptionPane.showMessageDialog(this,
                        "Orden de servicio eliminada correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Recargar datos
                    cargarDatos();
                } else {
                    soundManager.playSound(SoundManager.SOUND_ERROR);
                    JOptionPane.showMessageDialog(this,
                        "Error al eliminar la orden de servicio",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            soundManager.playSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione una orden para eliminar",
                "Selección requerida",
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Muestra los detalles de una orden
     */
    private void verDetallesOrden() {
        int filaSeleccionada = tblOrdenes.getSelectedRow();
        
        if (filaSeleccionada >= 0) {
            int idOrden = (int) tblOrdenes.getValueAt(filaSeleccionada, 0);
            OrdenServicio orden = controller.buscarOrdenServicioPorId(idOrden);
            
            if (orden != null) {
                // Aquí se podría abrir un diálogo para mostrar los detalles completos
                // Por ahora, mostramos un mensaje con información básica
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                
                StringBuilder mensaje = new StringBuilder();
                mensaje.append("Orden de Servicio #").append(orden.getIdOrdenServicio()).append("\n");
                mensaje.append("Fecha de Ingreso: ").append(sdf.format(orden.getFechaIngreso())).append("\n");
                
                if (orden.getFechaEntrega() != null) {
                    mensaje.append("Fecha de Entrega: ").append(sdf.format(orden.getFechaEntrega())).append("\n");
                } else {
                    mensaje.append("Fecha de Entrega: No definida\n");
                }
                
                mensaje.append("Estado: ").append(orden.getEstado()).append("\n");
                mensaje.append("Vehículo ID: ").append(orden.getIdVehiculo()).append("\n");
                mensaje.append("Técnico ID: ").append(orden.getIdTecnico()).append("\n");
                mensaje.append("Observaciones: ").append(orden.getObservaciones()).append("\n\n");
                
                mensaje.append("Servicios: ").append(orden.getDetallesServicio().size()).append("\n");
                mensaje.append("Repuestos: ").append(orden.getRepuestosUsados().size()).append("\n");
                mensaje.append("Costo Total: $").append(String.format("%.2f", orden.calcularCostoTotal()));
                
                JOptionPane.showMessageDialog(this,
                    mensaje.toString(),
                    "Detalles de Orden de Servicio",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            soundManager.playSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione una orden para ver sus detalles",
                "Selección requerida",
                JOptionPane.WARNING_MESSAGE);
        }
    }
}
