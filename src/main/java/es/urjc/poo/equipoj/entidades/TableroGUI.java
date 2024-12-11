package es.urjc.poo.equipoj.entidades;

import javax.swing.*;
import java.awt.*;

public class TableroGUI extends JPanel {
    private JPanel panelTablero;
    private JSpinner x;
    private JSpinner y;
    private JButton aceptarButton;

    public TableroGUI() {
        setLayout(new BorderLayout()); // Usamos BorderLayout en el panel principal
        initUI();
    }

    private void initUI() {
        SpinnerNumberModel modelX = new SpinnerNumberModel(1, 1, 100, 1);
        SpinnerNumberModel modelY = new SpinnerNumberModel(1, 1, 100, 1);
        x = new JSpinner(modelX);
        y = new JSpinner(modelY);

        // Cambiar el tamaño de los JSpinner
        Dimension spinnerSize = new Dimension(60, 30); // Ancho y alto deseados
        x.setPreferredSize(spinnerSize);
        y.setPreferredSize(spinnerSize);

        // Crear el panel inicial
        panelTablero = new JPanel();
        panelTablero.setLayout(new GridLayout(2, 3, 10, 10)); // Diseño en 2 filas
        JLabel l1 = new JLabel("Ingrese el tamaño del tablero:");
        aceptarButton = new JButton("Aceptar");

        panelTablero.add(l1);
        panelTablero.add(x);
        panelTablero.add(y);
        panelTablero.add(new JLabel()); // Espaciador
        panelTablero.add(new JLabel()); // Espaciador
        panelTablero.add(aceptarButton);

        // Añadir el panel inicial al centro
        add(panelTablero, BorderLayout.CENTER);

        // Evento para el botón "Aceptar"
        aceptarButton.addActionListener(e -> {
            int valorX = (int) x.getValue();
            int valorY = (int) y.getValue();
            System.out.println("Iniciando tablero: " + valorX + " x " + valorY);
            iniciarTableroReal(valorX, valorY);
        });
    }

    private void iniciarTableroReal(int filas, int columnas) {
        // Limpia el panel actual
        removeAll();

        // Crea el tablero
        JPanel tableroReal = new JPanel();
        tableroReal.setLayout(new GridLayout(filas, columnas, 2, 2)); // Espaciado entre celdas

        // Rellenar el tablero con botones o paneles
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                JButton celda = new JButton();
                celda.setBackground(Color.LIGHT_GRAY);
                celda.setText("["+i + "," + j+"]"); // Muestra las coordenadas
                int finalI = i;
                int finalJ = j;
                celda.addActionListener(e -> {
                    System.out.println("Celda seleccionada: [" + finalI + "," + finalJ+"]");
                });
                tableroReal.add(celda);
            }
        }

        // Añade el tablero al panel principal
        add(tableroReal, BorderLayout.CENTER);

        // Refresca la vista
        revalidate();
        repaint();
    }
}
