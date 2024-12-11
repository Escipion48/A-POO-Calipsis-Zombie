package es.urjc.poo.equipoj.entidades;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EntradaGUI extends JFrame {
    private JPanel panelInicio;
    private JButton nuevoJuego;
    private JButton cargarJuego;
    private JButton pruebas;

    public EntradaGUI() {
        initUI();
    }

    private void initUI() {
        panelInicio = new JPanel();
        panelInicio.setLayout(new GridLayout(3, 1));
        panelInicio.setBackground(Color.WHITE);
        panelInicio.setBorder(new EmptyBorder(20, 20, 20, 20)); // Añadir márgenes

        nuevoJuego = new JButton("Nuevo Juego");
        cargarJuego = new JButton("Cargar Juego");
        pruebas = new JButton("Prueba");

        panelInicio.add(nuevoJuego);
        panelInicio.add(cargarJuego);
        panelInicio.add(pruebas);

        setContentPane(panelInicio);

        nuevoJuego.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TableroGUI tableroGUI1 = new TableroGUI(); // Asegúrate de que esta clase sea un JPanel
                ShowPanel(tableroGUI1);
            }
        });

        cargarJuego.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        pruebas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void ShowPanel(JPanel p) {
        getContentPane().removeAll(); // Limpiar todos los componentes anteriores
        getContentPane().setLayout(new BorderLayout()); // Asegurar un layout válido
        getContentPane().add(p, BorderLayout.CENTER); // Agregar el panel nuevo en el centro
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                EntradaGUI frame = new EntradaGUI();
                frame.setTitle("Example");
                frame.setSize(600, 400);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}