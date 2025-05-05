package com.carsmotors.view;

import com.carsmotors.controller.OrdenServicioController;
import com.carsmotors.model.OrdenServicio;
import com.carsmotors.model.DetalleServicio;
import com.carsmotors.model.RepuestoUsado;
import com.carsmotors.model.Servicio;
import com.carsmotors.model.Repuesto;
import com.carsmotors.utils.SoundManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdenServicioDialog extends JDialog {
    private JTextField txtNumero;
    private JTextField txtFechaIngreso;
    private JTextField txtFechaEntrega;
    private JComboBox<String> cboVehiculo;
    private JComboBox<String> cboTecnico;
    private JComboBox<String> cboEstado;
    private JTextArea txtObservaciones;
    
    // Tablas para servicios y repuestos
    private JTable tblServicios;
    private JTable tblRepuestos;
    private DefaultTableModel modeloServicios;
    private DefaultTableModel modeloRepuestos;
    
    private JButton btnAgregarServicio;
    private JButton btnQuitarServicio;
    private JButton btnAgregarRepuesto;
    private JButton btnQuitarRepuesto;
    private JButton btnGuardar;
    private JButton btnCancelar;
    
    private OrdenServicioController controller;
    private OrdenServicio orden;
    private boolean esNuevo;
    private SoundManager soundManager;
    
    public OrdenServicioDialog(JFrame parent, String title, OrdenServicio orden) {
        super(parent, title, true);
        this.controller = new OrdenServicioController();
        this.orden = orden;
        this.esNuevo = (orden == null || orden.getIdOrdenServicio() == 0);
        this.soundManager = SoundManager.getInstance();
        
        initComponents();
        
        if (!esNuevo) {
            cargarDatosOrden();
        }
        
        pack();
        setLocationRelativeTo(parent);
        
        // Reproducir sonido al abrir el diálogo
        soundManager.playSound(SoundManager.SOUND_WINDOW_OPEN);
    }
    
    private void initComponents() {
        setSize(800, 600);
        setLayout(new BorderLayout());
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel de datos generales
        JPanel datosPanel = new JPanel(new GridBagLayout());
        datosPanel.setBorder(BorderFactory.createTitledBorder("Datos Generales"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Número de Orden
        datosPanel.add(new JLabel("Número:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtNumero = new JTextField(10);
        txtNumero.setEditable(false); // El número se genera automáticamente
        datosPanel.add(txtNumero, gbc);
        
        // Fecha de Ingreso
        gbc.gridx = 2;
        gbc.weightx = 0;
        datosPanel.add(new JLabel("Fecha Ingreso:"), gbc);
        gbc.gridx = 3;
        gbc.weightx = 1.0;
        txtFechaIngreso = new JTextField(10);
        txtFechaIngreso.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        datosPanel.add(txtFechaIngreso, gbc);
        
        // Vehículo
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        datosPanel.add(new JLabel("Vehículo:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        
        // Simulamos una lista de vehículos
        cboVehiculo = new JComboBox<>(new String[]{
            "Toyota Corolla (ABC-123)",
            "Honda Civic (DEF-456)",
            "Ford Focus (GHI-789)"
        });
        datosPanel.add(cboVehiculo, gbc);
        
        // Técnico
        gbc.gridx = 2;
        gbc.weightx = 0;
        datosPanel.add(new JLabel("Técnico:"), gbc);
        gbc.gridx = 3;
        gbc.weightx = 1.0;
        
        // Simulamos una lista de técnicos
        cboTecnico = new JComboBox<>(new String[]{
            "Juan Pérez",
            "María López",
            "Carlos Rodríguez"
        });
        datosPanel.add(cboTecnico, gbc);
        
        // Fecha de Entrega
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        datosPanel.add(new JLabel("Fecha Entrega:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtFechaEntrega = new JTextField(10);
        datosPanel.add(txtFechaEntrega, gbc);
        
        // Estado
        gbc.gridx = 2;
        gbc.weightx = 0;
        datosPanel.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 3;
        gbc.weightx = 1.0;
        cboEstado = new JComboBox<>(new String[]{"Pendiente", "En proceso", "Completado", "Entregado"});
        datosPanel.add(cboEstado, gbc);
        
        // Observaciones
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        datosPanel.add(new JLabel("Observaciones:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        txtObservaciones = new JTextArea(3, 20);
        txtObservaciones.setLineWrap(true);
        JScrollPane scrollObservaciones = new JScrollPane(txtObservaciones);
        datosPanel.add(scrollObservaciones, gbc);
        
        
        JPanel serviciosPanel = new JPanel(new BorderLayout());
        serviciosPanel.setBorder(BorderFactory.createTitledBorder("Servicios"));
        
        // Tabla de servicios
        String[] columnasServicios = {"ID", "Descripción", "Tipo", "Costo"};
        modeloServicios = new DefaultTableModel(columnasServicios, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblServicios = new JTable(modeloServicios);
        JScrollPane scrollServicios = new JScrollPane(tblServicios);
        serviciosPanel.add(scrollServicios, BorderLayout.CENTER);
        
        // Botones de servicios
        JPanel botonesServicios = new JPanel();
        btnAgregarServicio = new JButton("Agregar");
        btnQuitarServicio = new JButton("Quitar");
        
        btnAgregarServicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                agregarServicio();
            }
        });
        
        btnQuitarServicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                quitarServicio();
            }
        });
        
        botonesServicios.add(btnAgregarServicio);
        botonesServicios.add(btnQuitarServicio);
        serviciosPanel.add(botonesServicios, BorderLayout.SOUTH);
        
        
        JPanel repuestosPanel = new JPanel(new BorderLayout());
        repuestosPanel.setBorder(BorderFactory.createTitledBorder("Repuestos"));
        
       
        String[] columnasRepuestos = {"ID", "Nombre", "Cantidad", "Precio Unitario", "Subtotal"};
        modeloRepuestos = new DefaultTableModel(columnasRepuestos, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblRepuestos = new JTable(modeloRepuestos);
        JScrollPane scrollRepuestos = new JScrollPane(tblRepuestos);
        repuestosPanel.add(scrollRepuestos, BorderLayout.CENTER);
        
        
        JPanel botonesRepuestos = new JPanel();
        btnAgregarRepuesto = new JButton("Agregar");
        btnQuitarRepuesto = new JButton("Quitar");
        
        btnAgregarRepuesto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                agregarRepuesto();
            }
        });
        
        btnQuitarRepuesto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                quitarRepuesto();
            }
        });
        
        botonesRepuestos.add(btnAgregarRepuesto);
        botonesRepuestos.add(btnQuitarRepuesto);
        repuestosPanel.add(botonesRepuestos, BorderLayout.SOUTH);
        
        // Panel de detalles (servicios y repuestos)
        JPanel detallesPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        detallesPanel.add(serviciosPanel);
        detallesPanel.add(repuestosPanel);
        
        // Panel de botones principales
        JPanel buttonPanel = new JPanel();
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                guardarOrden();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                dispose();
            }
        });
        
        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnCancelar);
        
        // Agregar todo al panel principal
        mainPanel.add(datosPanel, BorderLayout.NORTH);
        mainPanel.add(detallesPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void cargarDatosOrden() {
        if (orden != null) {
            txtNumero.setText(String.valueOf(orden.getIdOrdenServicio()));
            txtFechaIngreso.setText(new SimpleDateFormat("yyyy-MM-dd").format(orden.getFechaIngreso()));
            
            if (orden.getFechaEntrega() != null) {
                txtFechaEntrega.setText(new SimpleDateFormat("yyyy-MM-dd").format(orden.getFechaEntrega()));
            }
            
            // Seleccionar el vehícuilo (simulado)
            cboVehiculo.setSelectedIndex(0);
            
            // Seleccionar el tecnico (simulado)
            cboTecnico.setSelectedIndex(0);
            
            cboEstado.setSelectedItem(orden.getEstado());
            txtObservaciones.setText(orden.getObservaciones());
            
            // Cargar servicios
            modeloServicios.setRowCount(0);
            for (DetalleServicio detalle : orden.getDetallesServicio()) {
                Servicio servicio = detalle.getServicio();
                if (servicio != null) {
                    modeloServicios.addRow(new Object[]{
                        servicio.getId(),
                        servicio.getDescripcion(),
                        servicio.getTipo(),
                        servicio.getCostoManoObra()
                    });
                }
            }
            
            // Cargar repuestos
            modeloRepuestos.setRowCount(0);
            for (RepuestoUsado repuestoUsado : orden.getRepuestosUsados()) {
                Repuesto repuesto = repuestoUsado.getRepuesto();
                if (repuesto != null) {
                    double subtotal = repuesto.getPrecioUnitario() * repuestoUsado.getCantidad();
                    modeloRepuestos.addRow(new Object[]{
                        repuesto.getId(),
                        repuesto.getNombre(),
                        repuestoUsado.getCantidad(),
                        repuesto.getPrecioUnitario(),
                        subtotal
                    });
                }
            }
        }
    }
    
    private void agregarServicio() {
        
        Servicio servicio = new Servicio();
        servicio.setId(1);
        servicio.setDescripcion("Cambio de aceite");
        servicio.setTipo("Mantenimiento");
        servicio.setCostoManoObra(50.0);
        
        modeloServicios.addRow(new Object[]{
            servicio.getId(),
            servicio.getDescripcion(),
            servicio.getTipo(),
            servicio.getCostoManoObra()
        });
    }
    
    private void quitarServicio() {
        int filaSeleccionada = tblServicios.getSelectedRow();
        if (filaSeleccionada >= 0) {
            modeloServicios.removeRow(filaSeleccionada);
        } else {
            soundManager.playSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this, 
                "Por favor, seleccione un servicio para quitar", 
                "Selección requerida", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void agregarRepuesto() {
        // Simulamos agregar un repuesto
        // En una implementación real, se mostraría un diálogo para seleccionar un repuesto y su cantidad
        Repuesto repuesto = new Repuesto();
        repuesto.setId(1);
        repuesto.setNombre("Filtro de aceite");
        repuesto.setPrecioUnitario(15.99);
        
        int cantidad = 1;
        double subtotal = repuesto.getPrecioUnitario() * cantidad;
        
        modeloRepuestos.addRow(new Object[]{
            repuesto.getId(),
            repuesto.getNombre(),
            cantidad,
            repuesto.getPrecioUnitario(),
            subtotal
        });
    }
    
    private void quitarRepuesto() {
        int filaSeleccionada = tblRepuestos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            modeloRepuestos.removeRow(filaSeleccionada);
        } else {
            soundManager.playSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this, 
                "Por favor, seleccione un repuesto para quitar", 
                "Selección requerida", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void guardarOrden() {
        try {
            // Validar campos obligatorios
            if (cboVehiculo.getSelectedIndex() == -1) {
                soundManager.playSound(SoundManager.SOUND_ERROR);
                JOptionPane.showMessageDialog(this, "Debe seleccionar un vehículo", "Error", JOptionPane.ERROR_MESSAGE);
                cboVehiculo.requestFocus();
                return;
            }
            
            if (cboTecnico.getSelectedIndex() == -1) {
                soundManager.playSound(SoundManager.SOUND_ERROR);
                JOptionPane.showMessageDialog(this, "Debe seleccionar un técnico", "Error", JOptionPane.ERROR_MESSAGE);
                cboTecnico.requestFocus();
                return;
            }
            
            
            Date fechaIngreso;
            try {
                fechaIngreso = new SimpleDateFormat("yyyy-MM-dd").parse(txtFechaIngreso.getText().trim());
            } catch (Exception e) {
                soundManager.playSound(SoundManager.SOUND_ERROR);
                JOptionPane.showMessageDialog(this, "La fecha de ingreso debe tener el formato yyyy-MM-dd", "Error", JOptionPane.ERROR_MESSAGE);
                txtFechaIngreso.requestFocus();
                return;
            }
            
            
            Date fechaEntrega = null;
            if (!txtFechaEntrega.getText().trim().isEmpty()) {
                try {
                    fechaEntrega = new SimpleDateFormat("yyyy-MM-dd").parse(txtFechaEntrega.getText().trim());
                } catch (Exception e) {
                    soundManager.playSound(SoundManager.SOUND_ERROR);
                    JOptionPane.showMessageDialog(this, "La fecha de entrega debe tener el formato yyyy-MM-dd", "Error", JOptionPane.ERROR_MESSAGE);
                    txtFechaEntrega.requestFocus();
                    return;
                }
            }
            
          
            if (esNuevo) {
                orden = new OrdenServicio();
            }
            
           
            int idVehiculo = cboVehiculo.getSelectedIndex() + 1;
            int idTecnico = cboTecnico.getSelectedIndex() + 1;
            
            orden.setIdVehiculo(idVehiculo);
            orden.setIdTecnico(idTecnico);
            orden.setFechaIngreso(fechaIngreso);
            orden.setFechaEntrega(fechaEntrega);
            orden.setEstado((String) cboEstado.getSelectedItem());
            orden.setObservaciones(txtObservaciones.getText().trim());
            
            // Recopilar servicios
            List<DetalleServicio> detallesServicio = new ArrayList<>();
            for (int i = 0; i < modeloServicios.getRowCount(); i++) {
                int idServicio = Integer.parseInt(modeloServicios.getValueAt(i, 0).toString());
                
                DetalleServicio detalle = new DetalleServicio();
                detalle.setIdServicio(idServicio);
                
               
                Servicio servicio = new Servicio();
                servicio.setId(idServicio);
                servicio.setDescripcion(modeloServicios.getValueAt(i, 1).toString());
                servicio.setTipo(modeloServicios.getValueAt(i, 2).toString());
                servicio.setCostoManoObra(Double.parseDouble(modeloServicios.getValueAt(i, 3).toString()));
                
                detalle.setServicio(servicio);
                detallesServicio.add(detalle);
            }
            orden.setDetallesServicio(detallesServicio);
            
           
            List<RepuestoUsado> repuestosUsados = new ArrayList<>();
            for (int i = 0; i < modeloRepuestos.getRowCount(); i++) {
                int idRepuesto = Integer.parseInt(modeloRepuestos.getValueAt(i, 0).toString());
                int cantidad = Integer.parseInt(modeloRepuestos.getValueAt(i, 2).toString());
                
                RepuestoUsado repuestoUsado = new RepuestoUsado();
                repuestoUsado.setIdRepuesto(idRepuesto);
                repuestoUsado.setCantidad(cantidad);
                
                // Simulamos el repuesto completo
                Repuesto repuesto = new Repuesto();
                repuesto.setId(idRepuesto);
                repuesto.setNombre(modeloRepuestos.getValueAt(i, 1).toString());
                repuesto.setPrecioUnitario(Double.parseDouble(modeloRepuestos.getValueAt(i, 3).toString()));
                
                repuestoUsado.setRepuesto(repuesto);
                repuestosUsados.add(repuestoUsado);
            }
            orden.setRepuestosUsados(repuestosUsados);
            
            
            boolean resultado;
            if (esNuevo) {
                resultado = controller.registrarOrdenServicio(orden);
            } else {
                resultado = controller.actualizarOrdenServicio(orden);
            }
            
            if (resultado) {
                soundManager.playSound(SoundManager.SOUND_SUCCESS);
                JOptionPane.showMessageDialog(this, "Orden de servicio guardada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                soundManager.playSound(SoundManager.SOUND_ERROR);
                JOptionPane.showMessageDialog(this, "Error al guardar la orden de servicio", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            soundManager.playSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}