package es.urjc.poo.equipoj.ui;

import es.urjc.poo.equipoj.entidades.*;

import javax.swing.*;
import java.awt.*;

public class TableroDefaultTemp extends JPanel {
    final Juego juego;
    private JPanel panelTableroDefault;
    private String celdaPulsado = "";
    private JTextPane panelDeTexto; // Referencia al JTextPane
    private JButton ultimoBotonPresionado;// Último botón presionado
    private JButton objetivo;// Posicion a la que se quiere llegar

    public TableroDefaultTemp(JTextPane panelDeTexto, Juego juego) {
        this.juego = juego;
        this.panelDeTexto = panelDeTexto;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        panelTableroDefault = new JPanel();
        panelTableroDefault.setLayout(new GridLayout(10, 10, 2, 2));
        panelTableroDefault.setBackground(Color.WHITE);

        objetivo = new JButton();
        inicializarPanel();

        setBorder(BorderFactory.createTitledBorder("Tablero"));
        add(panelTableroDefault, BorderLayout.CENTER);
    }

    private void inicializarPanel() {
        Posicion pObj = new Posicion(10, 10);
        Casilla [][] casillas = new Casilla[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int finalI = i;
                int finalJ = j;
                Posicion posicion = new Posicion(i, j);
                casillas[i][j] = new Casilla(posicion, false);

                if(i == 9 && j == 9) {
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
                        Posicion posicionObj = new Posicion(finalI, finalJ);
                        celdaPulsado = "Celda seleccionada: Casilla objeetivo[" + finalI + "," + finalJ + "]";
                        escribirEnTexto(celdaPulsado);
                        escribirEntidadesEnCasilla(posicionObj);
                });}else {

                JButton celda = new JButton("    [" + i + "," + j + "]");
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
        panelDeTexto.setText(panelDeTexto.getText() + mensaje);
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

}
