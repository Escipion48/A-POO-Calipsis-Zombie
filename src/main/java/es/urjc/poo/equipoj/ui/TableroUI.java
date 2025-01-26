package es.urjc.poo.equipoj.ui;

import es.urjc.poo.equipoj.entidades.*;
import es.urjc.poo.equipoj.sfx.LectorSonido;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class TableroUI extends JPanel {
    private Juego juego;
    private JTextPane texto;
    boolean prueba; //True si es una prueba, en tal caso preguntara por dimensiones, sino, crea el tablero directamente

    public TableroUI(Juego juegoParametro, JTextPane texto, boolean prueba) {
        this.texto = texto;
        this.juego = juegoParametro;

        if(prueba){
            JDialogDimensiones(juegoParametro);
        }
        else{
            this.juego = juegoParametro;
            juegoParametro.setTablero(new Tablero(new Posicion(10,10),new Posicion(9,9)));
        }
        setLayout(new BorderLayout(10,10));
        JPanel panelConCasillas = new JPanel();
        panelConCasillas.setBorder(new EmptyBorder(10,10,10,10));
        panelConCasillas.setLayout(new GridLayout(this.juego.getTablero().getDimensiones().getPosicionY(), this.juego.getTablero().getDimensiones().getPosicionX(), 2, 2));
        panelConCasillas.setBackground(Color.WHITE);

        for(int i=0 ; i<this.juego.getTablero().getDimensiones().getPosicionX() ; i++){

            for(int j=0 ; j<this.juego.getTablero().getDimensiones().getPosicionY() ; j++){
                JButton casilla = new JButton(new celdaDePosicion(nombresUbicaciones(),j,i));

                if(j==this.juego.getTablero().getObjetivo().getPosicionX()&&i==this.juego.getTablero().getObjetivo().getPosicionY()){
                    casilla.setBackground(Color.GREEN);
                }
                panelConCasillas.add(casilla);
            }
        }
        this.add(panelConCasillas, BorderLayout.CENTER);
    }



    private void JDialogDimensiones(Juego juegoParametro){

        JDialog dialogoPrincipal = new JDialog(SwingUtilities.getWindowAncestor(this),"Tamaño",Dialog.ModalityType.APPLICATION_MODAL);
        dialogoPrincipal.setSize(600,200);
        dialogoPrincipal.setBackground(Color.WHITE);
        dialogoPrincipal.setLocationRelativeTo(null);
        dialogoPrincipal.setLayout(new BorderLayout(10,10));

        JPanel panelJDialogDimensiones = new JPanel();
        panelJDialogDimensiones.setBorder(new EmptyBorder(10,10,10,10));
        panelJDialogDimensiones.setLayout(new GridLayout(3, 3, 10, 10));
        panelJDialogDimensiones.setBackground(Color.WHITE);

        JLabel l1 = new JLabel("Ingrese el tamaño del tablero:");
        JLabel l2 = new JLabel("Ingrese el objetivo:");
        panelJDialogDimensiones.add(l2);

        JSpinner dimensionesX = new JSpinner(new SpinnerNumberModel(1,1,null,1));
        JSpinner dimensionesY = new JSpinner(new SpinnerNumberModel(1,1,null,1));
        JSpinner objetivoX = new JSpinner(new SpinnerNumberModel(1,1,null,1));
        JSpinner objetivoY = new JSpinner(new SpinnerNumberModel(1,1,null,1));

        JButton aceptarButton = new JButton("Aceptar");

        panelJDialogDimensiones.add(l1);
        panelJDialogDimensiones.add(dimensionesX);
        panelJDialogDimensiones.add(dimensionesY);
        panelJDialogDimensiones.add(l2);
        panelJDialogDimensiones.add(objetivoX);
        panelJDialogDimensiones.add(objetivoY);
        //Jlabel vacio para ajustar el diseño
        panelJDialogDimensiones.add(new JLabel());
        panelJDialogDimensiones.add(new JLabel());
        panelJDialogDimensiones.add(aceptarButton);


        //Añadimos el panel al panel principal
        dialogoPrincipal.add(panelJDialogDimensiones);

        //Añadimos el listener de aceptar
        aceptarButton.addActionListener(e -> {
            int dimensionX = (int)dimensionesX.getValue();
            int dimensionY = (int)dimensionesY.getValue();
            int objetivox = (int)objetivoX.getValue();
            int objetivoy = (int)objetivoY.getValue();
            if(objetivox>dimensionX || objetivoy>dimensionY){
                JOptionPane.showMessageDialog(this, "El objetivo está fuera del rango del tablero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else{
                Posicion dimensiones = new Posicion(dimensionX,dimensionY);
                Posicion objetivo = new Posicion(objetivox-1, objetivoy-1);
                Tablero tablero = new Tablero(dimensiones,objetivo);

                //Asignamos al tablero el nuevo tablero y regeneramos los zombies
                juegoParametro.setTablero(tablero);
                juegoParametro.getZombies().clear();
                juegoParametro.getZombies().add(juegoParametro.generarZombie());
                juegoParametro.getZombies().add(juegoParametro.generarZombie());
                juegoParametro.getZombies().add(juegoParametro.generarZombie());
                this.juego=juegoParametro;

                dialogoPrincipal.dispose();
            }
        });
    dialogoPrincipal.setVisible(true);
    }


    private class celdaDePosicion extends AbstractAction {

        celdaDePosicion(String nombre, int x, int y) {
            putValue(Action.NAME, nombre);
            putValue(Action.SHORT_DESCRIPTION, "["+x+","+y+"]");
            putValue("x", x);
            putValue("y", y);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            new LectorSonido().reproducirSonido(LectorSonido.SONIDO_CLICK);

            Posicion posicion = new Posicion((int)getValue("x"),(int)getValue("y"));

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
            if(juego.getTablero().getCasilla(posicion).isExplorada()){
                stringBuilder.append("Explorada\n");
            }
            else{
                stringBuilder.append("No explorada\n");
            }
            texto.setText(texto.getText()+stringBuilder);
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
