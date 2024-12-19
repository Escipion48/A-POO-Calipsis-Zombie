package es.urjc.poo.equipoj.ui;

import es.urjc.poo.equipoj.entidades.*;

import javax.swing.*;
import java.awt.*;

public class TableroGUI extends JPanel {
    Tablero tablero = new Tablero();
    private JPanel panelTablero;
    private JSpinner x;
    private JSpinner y;
    private JButton aceptarButton;
    private JSpinner objX;
    private JSpinner objY;
    private JTextPane panelTexto;
    private JPanel tableroReal;

    public TableroGUI() {
        setLayout(new BorderLayout(10,10)); //  BorderLayout en el panel principal
        initUI();
    }

    private void initUI() {
        SpinnerNumberModel modelX = new SpinnerNumberModel(1, 1, 100, 1);
        SpinnerNumberModel modelY = new SpinnerNumberModel(1, 1, 100, 1);
        x = new JSpinner(modelX);
        y = new JSpinner(modelY);
        SpinnerNumberModel modelOX = new SpinnerNumberModel(1, 1, 100, 1);
        SpinnerNumberModel modelOY = new SpinnerNumberModel(1, 1, 100, 1);
        objX = new JSpinner(modelOX);
        objY = new JSpinner(modelOY);

        /**
         * @param x e y  son un spinners que no pueden bajar de 1 asi no hay numeros negativos*/

        // Cambiar el tamaño de los JSpinner que no sirve de mucho en verdad, no cambia
        Dimension spinnerSize = new Dimension(60, 30);//ancho y alto
        x.setPreferredSize(spinnerSize);
        y.setPreferredSize(spinnerSize);
        objX.setPreferredSize(spinnerSize);
        objY.setPreferredSize(spinnerSize);

        // Crear el panel inicial
        panelTablero = new JPanel();
        panelTablero.setBackground(Color.WHITE);
        panelTablero.setLayout(new GridLayout(3, 3, 10, 10)); // Diseño en 2 filas
        JLabel l1 = new JLabel("Ingrese el tamaño del tablero:");
        aceptarButton = new JButton("Aceptar");
        JLabel l2 = new JLabel("Ingrese el objetivo:");

        panelTablero.add(l1);
        panelTablero.add(x);
        panelTablero.add(y);
        panelTablero.add(l2);
        panelTablero.add(objX);
        panelTablero.add(objY);
        panelTablero.add(new JLabel()); // Para acomodar el diseño
        panelTablero.add(new JLabel()); // igual
        panelTablero.add(aceptarButton);

        // Añadir el panel inicial al centro
        add(panelTablero, BorderLayout.CENTER);

        // Al pulsar el boton aceptar, se guarda los valores de x e y para crear la tabla con fors
        aceptarButton.addActionListener(e -> {
            int valorX = (int) x.getValue();
            int valorY = (int) y.getValue();
            int valorOX = (int) objX.getValue();
            int valorOY = (int) objY.getValue();
            if (valorOX > valorX || valorOY > valorY) {
                JOptionPane.showMessageDialog(this, "El objetivo está fuera del rango del tablero.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Posicion dimensiones = new Posicion(valorX, valorY);
            Posicion objetivos = new Posicion(valorOX, valorOY);
            Casilla[][] tableroDim = new Casilla[valorX][valorY];
            this.tablero=new Tablero(dimensiones,objetivos,tableroDim);
            System.out.println("Iniciando tablero: " + valorX + " x " + valorY);
            panelFinal(valorX, valorY);
        });
    }

    private void panelFinal(int filas, int columnas) {
        // Limpia el panel actual
        removeAll();

        tableroReal = new JPanel();
        tableroReal.setLayout(new GridLayout(filas, columnas, 2, 2)); // Espaciado entre celdas
        panelTexto = new JTextPane();
        panelTexto.setEditable(false);

        Dimension tamanoTextPanel = new Dimension(400,200);
        panelTexto.setPreferredSize(tamanoTextPanel);

        JScrollPane jScrollPane = new JScrollPane(panelTexto);
        jScrollPane.setBackground(Color.lightGray);
        jScrollPane.setPreferredSize(tamanoTextPanel);
        // Rellenar el tablero con botones
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                JButton celda = new JButton();
                celda.setBackground(Color.white);
                celda.setText("["+i + "," + j+"]"); // Muestra las coordenadas
                int finalI = i;
                int finalJ = j;
                celda.addActionListener(e -> {
                    panelTexto.setText("Celda seleccionada: [" + finalI + "," + finalJ+"]");
                });
                tableroReal.add(celda);
            }
        }

        // Añade el tablero al panel principal
        add(tableroReal, BorderLayout.CENTER);
        add(panelTexto, BorderLayout.SOUTH);

        // Refresca la vista
        revalidate();
        repaint();
    }
    public Tablero getTablero(){
        return this.tablero;
    }
    public JButton getAceptarButton() {
        return this.aceptarButton;
    }

}
