package com.carsmotors.view;

import com.carsmotors.controller.ProveedorController;
import com.carsmotors.model.Proveedor;
import com.carsmotors.utils.SoundManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProveedoresPanel extends JPanel {
    private JTable tblProveedores;
    private DefaultTableModel modeloTabla;
    private JButton btnNuevo;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnVer;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    
    private ProveedorController controller;
    private SoundManager soundManager;
    
    public ProveedoresPanel() {
        this.controller = new ProveedorController();
        this.soundManager = SoundManager.getInstance();
        
        initComponents();
        cargarDatos();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Panel de título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(155, 89, 182));
        JLabel lblTitulo = new JLabel("Gestión de Proveedores");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        add(panelTitulo, BorderLayout.NORTH);
        
        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(new JLabel("Buscar:"));
        
        txtBuscar = new JTextField(20);
        panelBusqueda.add(txtBuscar);
        
        btnBuscar = new JButton("Buscar");
        btnBuscar.setIcon(new ImageIcon(getClass().getResource("/images/search.png")));
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                buscarProveedores();
            }
        });
        panelBusqueda.add(btnBuscar);
        
        add(panelBusqueda, BorderLayout.SOUTH);
        
        // Tabla de proveedores
        String[] columnas = {"ID", "Nombre", "NIT", "Contacto", "Frecuencia"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tblProveedores = new JTable(modeloTabla);
        tblProveedores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblProveedores.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane scrollPane = new JScrollPane(tblProveedores);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        
        btnNuevo = new JButton("Nuevo Proveedor");
        btnNuevo.setIcon(new ImageIcon(getClass().getResource("/images/add.png")));
        btnNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                nuevoProveedor();
            }
        });
        
        btnEditar = new JButton("Editar");
        btnEditar.setIcon(new ImageIcon(getClass().getResource("/images/edit.png")));
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                editarProveedor();
            }
        });
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setIcon(new ImageIcon(getClass().getResource("/images/delete.png")));
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                eliminarProveedor();
            }
        });
        
        btnVer = new JButton("Ver Detalles");
        btnVer.setIcon(new ImageIcon(getClass().getResource("/images/view.png")));
        btnVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                verDetallesProveedor();
            }
        });
        
        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVer);
        
        add(panelBotones, BorderLayout.EAST);
    }
    
   
    public void cargarDatos() {
       
        modeloTabla.setRowCount(0);
        
       
        List<Proveedor> proveedores = controller.listarProveedores();
        
 
        for (Proveedor proveedor : proveedores) {
            modeloTabla.addRow(new Object[]{
                proveedor.getIdProveedor(),
                proveedor.getNombre(),
                proveedor.getNit(),
                proveedor.getContacto(),
                proveedor.getFrecuenciaSuministro()
            });
        }
    }
    
    /**
     * Busca proveedores por nombre o NIT
     */
    private void buscarProveedores() {
        String termino = txtBuscar.getText().trim();
        
        if (termino.isEmpty()) {
            cargarDatos(); 
            return;
        }
        
      
        modeloTabla.setRowCount(0);
        
        
        List<Proveedor> proveedores = controller.buscarProveedores(termino);
        
        
        for (Proveedor proveedor : proveedores) {
            modeloTabla.addRow(new Object[]{
                proveedor.getIdProveedor(),
                proveedor.getNombre(),
                proveedor.getNit(),
                proveedor.getContacto(),
                proveedor.getFrecuenciaSuministro()
            });
        }
    }
    
   
    private void nuevoProveedor() {
        ProveedorDialog dialog = new ProveedorDialog(
            (JFrame) SwingUtilities.getWindowAncestor(this),
            "Nuevo Proveedor",
            null
        );
        dialog.setVisible(true);
        
       
        cargarDatos();
    }
    
   
    private void editarProveedor() {
        int filaSeleccionada = tblProveedores.getSelectedRow();
        
        if (filaSeleccionada >= 0) {
            int idProveedor = (int) tblProveedores.getValueAt(filaSeleccionada, 0);
            Proveedor proveedor = controller.buscarProveedorPorId(idProveedor);
            
            if (proveedor != null) {
                ProveedorDialog dialog = new ProveedorDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    "Editar Proveedor",
                    proveedor
                );
                dialog.setVisible(true);
                
               
                cargarDatos();
            }
        } else {
            soundManager.playSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un proveedor para editar",
                "Selección requerida",
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Elimina un proveedor seleccionado
     */
    private void eliminarProveedor() {
        int filaSeleccionada = tblProveedores.getSelectedRow();
        
        if (filaSeleccionada >= 0) {
            int idProveedor = (int) tblProveedores.getValueAt(filaSeleccionada, 0);
            
            int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar este proveedor?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean resultado = controller.eliminarProveedor(idProveedor);
                
                if (resultado) {
                    soundManager.playSound(SoundManager.SOUND_SUCCESS);
                    JOptionPane.showMessageDialog(this,
                        "Proveedor eliminado correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Recargar los datos obtenifdos
                    cargarDatos();
                } else {
                    soundManager.playSound(SoundManager.SOUND_ERROR);
                    JOptionPane.showMessageDialog(this,
                        "Error al eliminar el proveedor",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            soundManager.playSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un proveedor para eliminar",
                "Selección requerida",
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    
    private void verDetallesProveedor() {
        int filaSeleccionada = tblProveedores.getSelectedRow();
        
        if (filaSeleccionada >= 0) {
            int idProveedor = (int) tblProveedores.getValueAt(filaSeleccionada, 0);
            Proveedor proveedor = controller.buscarProveedorPorId(idProveedor);
            
            if (proveedor != null) {
                StringBuilder mensaje = new StringBuilder();
                mensaje.append("Proveedor #").append(proveedor.getIdProveedor()).append("\n");
                mensaje.append("Nombre: ").append(proveedor.getNombre()).append("\n");
                mensaje.append("NIT: ").append(proveedor.getNit()).append("\n");
                mensaje.append("Dirección: ").append(proveedor.getDireccion()).append("\n");
                mensaje.append("Contacto: ").append(proveedor.getContacto()).append("\n");
                mensaje.append("Frecuencia de Suministro: ").append(proveedor.getFrecuenciaSuministro());
                
                JOptionPane.showMessageDialog(this,
                    mensaje.toString(),
                    "Detalles del Proveedor",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            soundManager.playSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un proveedor para ver sus detalles",
                "Selección requerida",
                JOptionPane.WARNING_MESSAGE);
        }
    }
}
