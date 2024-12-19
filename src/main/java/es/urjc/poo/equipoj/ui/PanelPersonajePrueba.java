package es.urjc.poo.equipoj.ui;

import es.urjc.poo.equipoj.entidades.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PanelPersonajePrueba extends JPanel {
    Superviviente[] supervivientes;
    private JPanel panelSuperviviente;
    private JPanel panelInventario;
    private JButton siguienteRonda;
    private JButton crearSuperviviente;
    private JTextPane panelDeTexto;

    public PanelPersonajePrueba(JTextPane panelDeTexto) {
        this.panelDeTexto = panelDeTexto;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // Panel de personajes
        panelSuperviviente = new JPanel(new GridLayout(5, 1));
        crearSuperviviente = new JButton("CREAR SUPERVIVIENTES");
        crearSuperviviente.addActionListener(e -> abrirDialogoCrearPersonaje());
        panelSuperviviente.add(crearSuperviviente);

        // Inicializamos el array de supervivientes
        supervivientes = new Superviviente[4];
        for (int i = 0; i < supervivientes.length; i++) {
            supervivientes[i] = new Superviviente(); // Asegúrate de que Superviviente tenga un constructor sin parámetros
        }

        // Mostramos los nombres de los supervivientes (vacíos por defecto)
        actualizarSupervivientesUI();

        // Panel de inventario
        panelInventario = new JPanel();
        panelInventario.setBorder(BorderFactory.createTitledBorder("Inventario"));
        panelInventario.setLayout(new BoxLayout(panelInventario, BoxLayout.Y_AXIS));

        // Botón siguiente ronda
        siguienteRonda = new JButton("Siguiente Ronda");
        siguienteRonda.addActionListener(e -> {
            panelDeTexto.setText(panelDeTexto.getText()+"Pasando a la siguiente ronda...");
        });

        // Agregar componentes
        add(panelSuperviviente, BorderLayout.NORTH);
        add(panelInventario, BorderLayout.SOUTH);
        add(siguienteRonda, BorderLayout.EAST);
    }

    private void abrirDialogoCrearPersonaje() {
        // Obtener la ventana padre (JFrame o JDialog)
        Window parentWindow = SwingUtilities.getWindowAncestor(this);

        // Crear el JDialog
        JDialog dialogo = new JDialog(parentWindow, "Crear Personaje", Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setSize(400, 300);
        dialogo.setLocationRelativeTo(parentWindow);
        dialogo.setLayout(new BorderLayout(10, 10));

        // Panel de contenido del diálogo
        JPanel panelContenido = new JPanel(new GridLayout(5, 2, 10, 10));
        panelContenido.setBorder(new EmptyBorder(10,10,10,10));

        JLabel l1 = new JLabel("Nombre del personaje 1:");
        JTextField c1 = new JTextField();
        JLabel l2 = new JLabel("Nombre del personaje 2:");
        JTextField c2 = new JTextField();
        JLabel l3 = new JLabel("Nombre del personaje 3:");
        JTextField c3 = new JTextField();
        JLabel l4 = new JLabel("Nombre del personaje 4:");
        JTextField c4 = new JTextField();

        panelContenido.add(l1);
        panelContenido.add(c1);
        panelContenido.add(l2);
        panelContenido.add(c2);
        panelContenido.add(l3);
        panelContenido.add(c3);
        panelContenido.add(l4);
        panelContenido.add(c4);

        // Botón de confirmación
        JButton botonConfirmar = new JButton("Confirmar");
        botonConfirmar.addActionListener(e -> {
            String nombre1 = c1.getText();
            String nombre2 = c2.getText();
            String nombre3 = c3.getText();
            String nombre4 = c4.getText();

            if (nombre1.isEmpty() || nombre2.isEmpty() || nombre3.isEmpty() || nombre4.isEmpty()) {
                JOptionPane.showMessageDialog(dialogo, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Asignar los nombres a los supervivientes
                supervivientes[0].setNombre(nombre1);
                supervivientes[1].setNombre(nombre2);
                supervivientes[2].setNombre(nombre3);
                supervivientes[3].setNombre(nombre4);

                // Actualizar la interfaz con los nuevos nombres
                actualizarSupervivientesUI();

            }
            dialogo.dispose();
        });

        // Añadir los componentes al diálogo
        dialogo.add(panelContenido, BorderLayout.CENTER);
        dialogo.add(botonConfirmar, BorderLayout.SOUTH);

        // Mostrar el diálogo
        dialogo.setVisible(true);
    }

    private void actualizarSupervivientesUI() {
        // Limpiar el panel antes de agregar las nuevas etiquetas
        panelSuperviviente.removeAll();

        // Añadir el botón para crear personajes
        panelSuperviviente.add(crearSuperviviente);

        // Crear las etiquetas con los nombres de los supervivientes
        for (Superviviente s : supervivientes) {
            panelSuperviviente.add(new JLabel(s.getNombre() == null ? "Sin nombre" : s.getNombre()));
            panelDeTexto.setText("Se ha creado un superviviente: "+s.getNombre()+"\n");
        }

        // Actualizar la interfaz gráfica
        revalidate();
        repaint();
    }

    public Superviviente[] getSupervivientes() {
        return supervivientes;
    }
}
