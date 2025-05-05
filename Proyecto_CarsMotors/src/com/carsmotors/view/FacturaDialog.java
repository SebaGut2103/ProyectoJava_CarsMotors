package com.carsmotors.view;

import com.carsmotors.controller.FacturaController;
import com.carsmotors.controller.ClienteController;
import com.carsmotors.controller.OrdenServicioController;
import com.carsmotors.model.Factura;
import com.carsmotors.model.Cliente;
import com.carsmotors.model.OrdenServicio;
import com.carsmotors.utils.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Diálogo para crear o editar una factura
 */
public class FacturaDialog extends JDialog {
    private JTextField txtNumero;
    private JTextField txtFecha;
    private JComboBox<Cliente> cboCliente;
    private JComboBox<String> cboOrdenServicio;
    private JTextField txtSubtotal;
    private JTextField txtIva;
    private JTextField txtTotal;
    private JComboBox<String> cboEstado;
    private JButton btnGuardar;
    private JButton btnCancelar;
    
    private FacturaController facturaController;
    private ClienteController clienteController;
    private OrdenServicioController ordenServicioController;
    private Factura factura;
    private boolean esNuevo;
    private SoundManager soundManager;
    
    public FacturaDialog(JFrame parent, String title, Factura factura) {
        super(parent, title, true);
        this.facturaController = new FacturaController();
        this.clienteController = new ClienteController();
        this.ordenServicioController = new OrdenServicioController();
        this.factura = factura;
        // Corregido: Usar getIdFactura() en lugar de getId()
        this.esNuevo = (factura == null || factura.getIdFactura() == 0);
        this.soundManager = SoundManager.getInstance();
        
        initComponents();
        
        if (!esNuevo) {
            cargarDatosFactura();
        }
        
        pack();
        setLocationRelativeTo(parent);
        
        // Reproducir sonido al abrir el diálogo
        soundManager.playSound(SoundManager.SOUND_WINDOW_OPEN);
    }
    
    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Número de Factura
        panel.add(new JLabel("Número:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtNumero = new JTextField(20);
        txtNumero.setEditable(false); // El número se genera automáticamente
        panel.add(txtNumero, gbc);
        
        // Fecha
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Fecha:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtFecha = new JTextField(20);
        txtFecha.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        panel.add(txtFecha, gbc);
        
        // Cliente
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panel.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        
        // Cargar lista de clientes
        List<Cliente> clientes = clienteController.listarClientes();
        cboCliente = new JComboBox<>(clientes.toArray(new Cliente[0]));
        panel.add(cboCliente, gbc);
        
        // Orden de Servicio
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        panel.add(new JLabel("Orden de Servicio:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        
        // Cargar lista de órdenes de servicio
        List<OrdenServicio> ordenes = ordenServicioController.listarOrdenesServicio();
        String[] ordenesStr = new String[ordenes.size()];
        for (int i = 0; i < ordenes.size(); i++) {
            OrdenServicio orden = ordenes.get(i);
            // Corregido: Usar getIdOrdenServicio() en lugar de getId()
            ordenesStr[i] = "Orden #" + orden.getIdOrdenServicio() + " - " + orden.getEstado();
        }
        cboOrdenServicio = new JComboBox<>(ordenesStr);
        panel.add(cboOrdenServicio, gbc);
        
        // Subtotal
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        panel.add(new JLabel("Subtotal:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtSubtotal = new JTextField(20);
        panel.add(txtSubtotal, gbc);
        
        // IVA
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0;
        panel.add(new JLabel("IVA:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtIva = new JTextField(20);
        panel.add(txtIva, gbc);
        
        // Total
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0;
        panel.add(new JLabel("Total:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtTotal = new JTextField(20);
        panel.add(txtTotal, gbc);
        
        // Estado
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 0;
        panel.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cboEstado = new JComboBox<>(new String[]{"Emitida", "Pagada", "Anulada"});
        panel.add(cboEstado, gbc);
        
        // Botones
        JPanel buttonPanel = new JPanel();
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                guardarFactura();
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
        
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);
        
        add(panel);
    }
    
    private void cargarDatosFactura() {
        if (factura != null) {
            // Corregido: Usar getIdFactura() en lugar de getId()
            txtNumero.setText(String.valueOf(factura.getIdFactura()));
            txtFecha.setText(new SimpleDateFormat("yyyy-MM-dd").format(factura.getFecha()));
            
            // Seleccionar el cliente
            for (int i = 0; i < cboCliente.getItemCount(); i++) {
                Cliente cliente = cboCliente.getItemAt(i);
                // Corregido: Asegurarse de que Cliente use getId()
                if (cliente.getId() == factura.getIdCliente()) {
                    cboCliente.setSelectedIndex(i);
                    break;
                }
            }
            
            // Seleccionar la orden de servicio
            for (int i = 0; i < cboOrdenServicio.getItemCount(); i++) {
                String ordenStr = cboOrdenServicio.getItemAt(i);
                int idOrden = Integer.parseInt(ordenStr.split("#")[1].split(" ")[0]);
                if (idOrden == factura.getIdOrdenServicio()) {
                    cboOrdenServicio.setSelectedIndex(i);
                    break;
                }
            }
            
            txtSubtotal.setText(String.valueOf(factura.getSubtotal()));
            txtIva.setText(String.valueOf(factura.getIva()));
            txtTotal.setText(String.valueOf(factura.getTotal()));
            cboEstado.setSelectedItem(factura.getEstado());
        }
    }
    
    private void guardarFactura() {
        try {
            // Validar campos obligatorios
            if (cboCliente.getSelectedIndex() == -1) {
                soundManager.playSound(SoundManager.SOUND_ERROR);
                JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente", "Error", JOptionPane.ERROR_MESSAGE);
                cboCliente.requestFocus();
                return;
            }
            
            if (cboOrdenServicio.getSelectedIndex() == -1) {
                soundManager.playSound(SoundManager.SOUND_ERROR);
                JOptionPane.showMessageDialog(this, "Debe seleccionar una orden de servicio", "Error", JOptionPane.ERROR_MESSAGE);
                cboOrdenServicio.requestFocus();
                return;
            }
            
            // Validar campos numéricos
            double subtotal, iva, total;
            try {
                subtotal = Double.parseDouble(txtSubtotal.getText().trim());
                iva = Double.parseDouble(txtIva.getText().trim());
                total = Double.parseDouble(txtTotal.getText().trim());
                
                if (subtotal < 0 || iva < 0 || total < 0) {
                    throw new NumberFormatException("Los valores no pueden ser negativos");
                }
            } catch (NumberFormatException e) {
                soundManager.playSound(SoundManager.SOUND_ERROR);
                JOptionPane.showMessageDialog(this, "Los valores monetarios deben ser números válidos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validar fecha
            Date fecha;
            try {
                fecha = new SimpleDateFormat("yyyy-MM-dd").parse(txtFecha.getText().trim());
            } catch (Exception e) {
                soundManager.playSound(SoundManager.SOUND_ERROR);
                JOptionPane.showMessageDialog(this, "La fecha debe tener el formato yyyy-MM-dd", "Error", JOptionPane.ERROR_MESSAGE);
                txtFecha.requestFocus();
                return;
            }
            
            // Crear o actualizar el objeto factura
            if (esNuevo) {
                factura = new Factura();
            }
            
            factura.setFecha(fecha);
            factura.setSubtotal(subtotal);
            factura.setIva(iva);
            factura.setTotal(total);
            factura.setEstado((String) cboEstado.getSelectedItem());
            
            // Obtener el cliente seleccionado
            Cliente cliente = (Cliente) cboCliente.getSelectedItem();
            factura.setIdCliente(cliente.getId());
            
            // Obtener la orden de servicio seleccionada
            String ordenStr = (String) cboOrdenServicio.getSelectedItem();
            int idOrden = Integer.parseInt(ordenStr.split("#")[1].split(" ")[0]);
            factura.setIdOrdenServicio(idOrden);
            
            // Verificar si los métodos generarCUFE() y generarQR() existen
            // Si no existen, comentar o eliminar estas líneas
            if (esNuevo) {
                // Comprobar si estos métodos existen en la clase Factura
                try {
                    factura.generarCUFE();
                    factura.generarQR();
                } catch (NoSuchMethodError e) {
                    // Si los métodos no existen, simplemente continuamos
                    System.err.println("Advertencia: Métodos generarCUFE() o generarQR() no implementados");
                }
            }
            
            boolean resultado;
            if (esNuevo) {
                resultado = facturaController.generarFactura(idOrden, cliente.getId());
            } else {
                resultado = facturaController.actualizarFactura(factura);
            }
            
            if (resultado) {
                soundManager.playSound(SoundManager.SOUND_SUCCESS);
                JOptionPane.showMessageDialog(this, "Factura guardada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                soundManager.playSound(SoundManager.SOUND_ERROR);
                JOptionPane.showMessageDialog(this, "Error al guardar la factura", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            soundManager.playSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
