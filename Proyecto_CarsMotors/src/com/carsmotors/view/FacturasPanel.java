package com.carsmotors.view;

import com.carsmotors.controller.FacturaController;
import com.carsmotors.model.Factura;
import com.carsmotors.utils.SoundManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class FacturasPanel extends JPanel {
    private JTable tblFacturas;
    private DefaultTableModel modeloTabla;
    private JButton btnNueva;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnVer;
    private JComboBox<String> cboFiltroEstado;
    
    private FacturaController controller;
    private SoundManager soundManager;
    
    public FacturasPanel() {
        this.controller = new FacturaController();
        this.soundManager = SoundManager.getInstance();
        
        initComponents();
        cargarDatos();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Panel de título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(52, 152, 219));
        JLabel lblTitulo = new JLabel("Gestión de Facturas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        add(panelTitulo, BorderLayout.NORTH);
        
        // Panel de filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltros.add(new JLabel("Filtrar por estado:"));
        
        cboFiltroEstado = new JComboBox<>(new String[]{"Todos", "Pagada", "Pendiente", "Anulada"});
        cboFiltroEstado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                cargarDatos();
            }
        });
        panelFiltros.add(cboFiltroEstado);
        
        add(panelFiltros, BorderLayout.SOUTH);
        
        // Tabla de facturas
        String[] columnas = {"ID", "Fecha", "Cliente", "Total", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tblFacturas = new JTable(modeloTabla);
        tblFacturas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblFacturas.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane scrollPane = new JScrollPane(tblFacturas);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        
        btnNueva = new JButton("Nueva Factura");
        btnNueva.setIcon(new ImageIcon(getClass().getResource("/images/add.png")));
        btnNueva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                nuevaFactura();
            }
        });
        
        btnEditar = new JButton("Editar");
        btnEditar.setIcon(new ImageIcon(getClass().getResource("/images/edit.png")));
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                editarFactura();
            }
        });
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setIcon(new ImageIcon(getClass().getResource("/images/delete.png")));
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                eliminarFactura();
            }
        });
        
        btnVer = new JButton("Ver Detalles");
        btnVer.setIcon(new ImageIcon(getClass().getResource("/images/view.png")));
        btnVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                verDetallesFactura();
            }
        });
        
        panelBotones.add(btnNueva);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVer);
        
        add(panelBotones, BorderLayout.EAST);
    }
    
   
    public void cargarDatos() {
        // Limpiar la tabla
        modeloTabla.setRowCount(0);
        
    
        String filtro = (String) cboFiltroEstado.getSelectedItem();
        
       
        List<Factura> facturas;
        if (filtro.equals("Todos")) {
            facturas = controller.listarFacturas();
        } else {
            facturas = controller.listarFacturasPorEstado(filtro);
        }
        
        // Formatear fecha
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
     
        for (Factura factura : facturas) {
          
            String nombreCliente = "Cliente " + factura.getIdCliente();
            
            modeloTabla.addRow(new Object[]{
                factura.getIdFactura(),
                sdf.format(factura.getFecha()),
                nombreCliente,
                String.format("$%.2f", factura.getTotal()),
                factura.getEstado()
            });
        }
    }
    

     
    
    private void nuevaFactura() {
        FacturaDialog dialog = new FacturaDialog(
            (JFrame) SwingUtilities.getWindowAncestor(this),
            "Nueva Factura",
            null
        );
        dialog.setVisible(true);
        
        
        cargarDatos();
    }
 
    
    private void editarFactura() {
        int filaSeleccionada = tblFacturas.getSelectedRow();
        
        if (filaSeleccionada >= 0) {
            int idFactura = (int) tblFacturas.getValueAt(filaSeleccionada, 0);
            Factura factura = controller.buscarFacturaPorId(idFactura);
            
            if (factura != null) {
                FacturaDialog dialog = new FacturaDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    "Editar Factura",
                    factura
                );
                dialog.setVisible(true);
                
                // Recargar datos después de cerrar el diálogo
                cargarDatos();
            }
        } else {
            soundManager.playSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione una factura para editar",
                "Selección requerida",
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
   
    private void eliminarFactura() {
        int filaSeleccionada = tblFacturas.getSelectedRow();
        
        if (filaSeleccionada >= 0) {
            int idFactura = (int) tblFacturas.getValueAt(filaSeleccionada, 0);
            
            int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar esta factura?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean resultado = controller.eliminarFactura(idFactura);
                
                if (resultado) {
                    soundManager.playSound(SoundManager.SOUND_SUCCESS);
                    JOptionPane.showMessageDialog(this,
                        "Factura eliminada correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Recargar datos
                    cargarDatos();
                } else {
                    soundManager.playSound(SoundManager.SOUND_ERROR);
                    JOptionPane.showMessageDialog(this,
                        "Error al eliminar la factura",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            soundManager.playSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione una factura para eliminar",
                "Selección requerida",
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
   
     
    private void verDetallesFactura() {
        int filaSeleccionada = tblFacturas.getSelectedRow();
        
        if (filaSeleccionada >= 0) {
            int idFactura = (int) tblFacturas.getValueAt(filaSeleccionada, 0);
            Factura factura = controller.buscarFacturaPorId(idFactura);
            
            if (factura != null) {
               
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                
                StringBuilder mensaje = new StringBuilder();
                mensaje.append("Factura #").append(factura.getIdFactura()).append("\n");
                mensaje.append("Fecha: ").append(sdf.format(factura.getFecha())).append("\n");
                mensaje.append("Subtotal: $").append(String.format("%.2f", factura.getSubtotal())).append("\n");
                mensaje.append("IVA: $").append(String.format("%.2f", factura.getIva())).append("\n");
                mensaje.append("Total: $").append(String.format("%.2f", factura.getTotal())).append("\n");
                mensaje.append("Estado: ").append(factura.getEstado()).append("\n");
                mensaje.append("Cliente ID: ").append(factura.getIdCliente()).append("\n");
                mensaje.append("Orden de Servicio ID: ").append(factura.getIdOrdenServicio());
                
                JOptionPane.showMessageDialog(this,
                    mensaje.toString(),
                    "Detalles de Factura",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            soundManager.playSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione una factura para ver sus detalles",
                "Selección requerida",
                JOptionPane.WARNING_MESSAGE);
        }
    }
}
