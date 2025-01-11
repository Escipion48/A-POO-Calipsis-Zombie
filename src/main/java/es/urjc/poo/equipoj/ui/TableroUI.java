package es.urjc.poo.equipoj.ui;

import es.urjc.poo.equipoj.entidades.Juego;
import es.urjc.poo.equipoj.entidades.Posicion;
import es.urjc.poo.equipoj.entidades.Superviviente;
import es.urjc.poo.equipoj.entidades.Zombie;
import es.urjc.poo.equipoj.sfx.LectorSonido;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TableroUI extends JPanel {
    Juego juego;
    JTextPane texto;

    public TableroUI(Juego juego, JTextPane texto) {
        this.juego = juego;
        this.texto = texto;

        JPanel panelConCasillas = new JPanel();
        panelConCasillas.setLayout(new GridLayout(10, 10, 2, 2));
        panelConCasillas.setBackground(Color.WHITE);

        for(int i=0 ; i<juego.getTablero().getDimensiones().getPosicionX() ; i++){

            for(int j=0 ; j<juego.getTablero().getDimensiones().getPosicionY() ; j++){
                JButton casilla = new JButton(new celdaDePosicion(nombresUbicaciones(),i,j));

                if(i==juego.getTablero().getObjetivo().getPosicionX()&&j==juego.getTablero().getObjetivo().getPosicionY()){
                    casilla.setBackground(Color.GREEN);
                }

                panelConCasillas.add(casilla);
            }
        }
        add(panelConCasillas);
    }




    private class celdaDePosicion extends AbstractAction {

        celdaDePosicion(String texto, int x, int y) {
            putValue(Action.NAME, texto);
            putValue(Action.SHORT_DESCRIPTION, "["+x+","+y+"]");
            putValue("x", x);
            putValue("y", y);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            LectorSonido.reproducirClick();

            StringBuilder informacionEnPosicion = new StringBuilder();

            for(Superviviente superviviente : juego.getSupervivientes()) {
                if(superviviente.getPosicion().equals(new Posicion((int)getValue("x"),(int)getValue("y"))));
                informacionEnPosicion.append(superviviente.toString()+"\n");
            }

            for(Zombie zombie : juego.getZombies()) {
                if(zombie.getPosicion().equals(new Posicion((int)getValue("x"),(int)getValue("y")))); {
                    informacionEnPosicion.append(zombie.toString()+"\n");
                }
            }

            texto.setText(texto.getText()+informacionEnPosicion.toString());
        }
    }

    private String nombresUbicaciones(){
        Random r = new Random();
        int selector = r.nextInt(20);
        switch(selector){
            case 0:
                return ("Calle");

            case 1:
                return ("Pantano");

            case 2:
                return ("Hospital");

            case 3:
                return ("Escuela");

            case 4:
                return ("Centro Comercial");

            case 5:
                return ("Cementerio");

            case 6:
                return ("Universidad");


            case 7:
                return ("Universidad");


            case 8:
                return ("Laboratorios");


            case 9:
                return ("Calle");


            case 10:
                return("Calle");


            case 11:
                return("Calle");


            case 12:
                return("Calle");


            case 13:
                return("Mercadona");


            case 14:
                return("Edificio");


            case 15:
                return("Edificio");


            case 16:
                return("Edificio");


            case 17:
                return("Casa");


            case 18:
                return("Casa");


            case 19:
                return("Casa");

        }
        return ("Universidad");
    }
}
