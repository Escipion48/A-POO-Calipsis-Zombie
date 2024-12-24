package es.urjc.poo.equipoj.ui;

import es.urjc.poo.equipoj.entidades.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class TableroPrueba extends JPanel {
    final Juego juego;
    private JPanel panelTableroDefault;
    private String celdaPulsado = "";
    private JTextPane panelDeTexto; // Referencia al JTextPane
    private JButton ultimoBotonPresionado;// Último botón presionado
    private JButton objetivo;// Posicion a la que se quiere llegar
    private JSpinner x;
    private JSpinner y;
    private JSpinner objX;
    private JSpinner objY;


    public TableroPrueba(JTextPane panelDeTexto, Juego juego) {
        this.juego = juego;
        this.panelDeTexto = panelDeTexto;
        initUI();
    }

    private void initUI() {
        JDialogTamano();
        setLayout(new BorderLayout(10, 10));
        panelTableroDefault = new JPanel();
        panelTableroDefault.setLayout(new GridLayout((int) x.getValue(), (int) y.getValue(), 2, 2));
        panelTableroDefault.setBackground(Color.WHITE);

        objetivo = new JButton();
        inicializarPanel();

        setBorder(BorderFactory.createTitledBorder("Tablero"));
        add(panelTableroDefault, BorderLayout.CENTER);
    }

    private void inicializarPanel() {
        int px = (int) x.getValue();
        int py = (int) y.getValue();
        int pObjX = (int) objX.getValue();
        int pObjY = (int) objY.getValue();
        Posicion pObj = new Posicion(px, py);

        Casilla [][] casillas = new Casilla[px][py];
        for (int i = 0; i < px; i++) {
            for (int j = 0; j < py; j++) {
                int finalI = i;
                int finalJ = j;
                Posicion posicion = new Posicion(i, j);
                casillas[i][j] = new Casilla(posicion, false);

                if(i == pObjX && j == pObjY) {
                    objetivo = new JButton("[" + i + "," + j + "]");
                    objetivo.setBackground(Color.green);
                    panelTableroDefault.add(objetivo);

                    objetivo.addActionListener(e -> {
                        // Restaurar el color del último botón presionado
                        if (ultimoBotonPresionado != null) {
                            ultimoBotonPresionado.setBackground(Color.WHITE);
                        }

                        // Actualizar el último botón presionado
                        ultimoBotonPresionado = objetivo;

                        // Cambiar el color del botón actual y actualizar el texto
                        objetivo.setBackground(Color.red);
                        celdaPulsado = "Celda seleccionada: Casilla objetivo[" + finalI + "," + finalJ + "]";
                        escribirEnTexto(celdaPulsado);
                        escribirEntidadesEnCasilla(this.getPosicionObjetivo());
                    });}else {

                    JButton celda = new JButton("[" + i + "," + j + "]");
                    celda.setBackground(Color.WHITE);


                    celda.addActionListener(e -> {
                        // Restaurar el color del último botón presionado
                        if (ultimoBotonPresionado != null) {
                            if(ultimoBotonPresionado ==objetivo){
                                ultimoBotonPresionado.setBackground(Color.green);
                            }else{
                                ultimoBotonPresionado.setBackground(Color.WHITE);
                            }}

                        // Actualizar el último botón presionado
                        ultimoBotonPresionado = celda;

                        // Cambiar el color del botón actual y actualizar el texto
                        celda.setBackground(Color.GRAY);
                        celdaPulsado = "Celda seleccionada: [" + finalI + "," + finalJ + "]\n";
                        Posicion posicion1 = new Posicion(finalI, finalJ);
                        escribirEnTexto(celdaPulsado);
                        escribirEntidadesEnCasilla(posicion1);

                    });

                    panelTableroDefault.add(celda);
                }}
        }Tablero tablero = new Tablero(pObj,pObj,casillas);
        juego.setTablero(tablero);

    }

    private void escribirEnTexto(String mensaje) {
        // Añade el mensaje al JTextPane
        panelDeTexto.setText(panelDeTexto.getText() + mensaje+"\n");
    }

    private void escribirEntidadesEnCasilla(Posicion posicion){
        StringBuilder stringBuilder = new StringBuilder();
        for(Superviviente superviviente : juego.getSupervivientes()){
            if(posicion.equals(superviviente.getPosicion())){
                stringBuilder.append(superviviente.getNombre()).append("\n");
            }
        }
        for(Zombie z : juego.getZombies()){
            if(posicion.equals(z.getPosicion())){
                stringBuilder.append(z).append("\n");
            }
        }
        panelDeTexto.setText(panelDeTexto.getText()+stringBuilder);
    }


    private void JDialogTamano(){
        Window parentWindow = SwingUtilities.getWindowAncestor(this);

        JDialog dialogo = new JDialog(parentWindow,"Tamaño",Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setSize(600,200);
        dialogo.setBackground(Color.WHITE);
        dialogo.setLocationRelativeTo(null);
        dialogo.setLayout(new BorderLayout(10,10));

        JPanel panelJDialogTamano = new JPanel();
        panelJDialogTamano.setBorder(new EmptyBorder(10,10,10,10));
        panelJDialogTamano.setLayout(new GridLayout(3, 3, 10, 10));
        panelJDialogTamano.setBackground(Color.WHITE);

        JLabel l1 = new JLabel("Ingrese el tamaño del tablero:");
        JButton aceptarButton = new JButton("Aceptar");
        JLabel l2 = new JLabel("Ingrese el objetivo:");


        SpinnerNumberModel Ox = new SpinnerNumberModel(1, 1, 100, 1);
        SpinnerNumberModel Oy = new SpinnerNumberModel(1, 1, 100, 1);
        SpinnerNumberModel ObjX = new SpinnerNumberModel(1, 1, 100, 1);
        SpinnerNumberModel ObjY = new SpinnerNumberModel(1, 1, 100, 1);

        x = new JSpinner(Ox);
        y = new JSpinner(Oy);
        objX = new JSpinner(ObjX);
        objY = new JSpinner(ObjY);


        panelJDialogTamano.add(l1);
        panelJDialogTamano.add(x);
        panelJDialogTamano.add(y);
        panelJDialogTamano.add(l2);
        panelJDialogTamano.add(objX);
        panelJDialogTamano.add(objY);
        panelJDialogTamano.add(new JLabel()); // Para acomodar el diseño
        panelJDialogTamano.add(new JLabel()); // igual
        panelJDialogTamano.add(aceptarButton);

    // Añadir el panel inicial al centro
    dialogo.add(panelJDialogTamano, BorderLayout.CENTER);
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
            dialogo.dispose();
        });
        dialogo.setVisible(true);
}
public Posicion getPosicionObjetivo(){
        return new Posicion((int) objX.getValue(),(int) objY.getValue());
}

}
