package es.urjc.poo.equipoj.ui;

import es.urjc.poo.equipoj.entidades.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EntradaGUI extends JFrame {//kk
    Juego juego;
    private JPanel panelPrincipal;
    private JPanel panelInicio;
    private JButton nuevoJuego;
    private JButton cargarJuego;
    private JButton pruebas;
    private JScrollPane jScrollPane;
    private TableroGUI tableroGUI;
    private TableroDefault tableroDefault;
    private PanelPersonaje panelPersonaje;
    private PanelPersonajePrueba panelPersonajePrueba;// Referencia a TableroGUI


    public EntradaGUI() {
        initUI();
    }

    private void initUI() {
        //iniciar el panel pricipal que estara atras del de la entrada
        panelPrincipal=new JPanel();
        panelPrincipal.setLayout(new GridLayout(1,2));
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setBorder(new EmptyBorder(20,20,20,20));

        //iniciar el panel de entrada
        panelInicio = new JPanel();
        panelInicio.setLayout(new GridLayout(3, 1,20,20));
        panelInicio.setBackground(Color.WHITE);

        //iniciar los botones del panel de entrada
        nuevoJuego = new JButton("Nuevo Juego");
        cargarJuego = new JButton("Cargar Juego");
        pruebas = new JButton("Prueba");

        //iniciar un panel de texto y poner el scrollbar
        JTextPane panelDeTexto = new JTextPane();
        panelDeTexto.setBackground(Color.WHITE);
        panelDeTexto.setEditable(false);
        jScrollPane = new JScrollPane(panelDeTexto);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //añadir los componentes y ponerlo en el panel inicial
        panelInicio.add(nuevoJuego);
        panelInicio.add(cargarJuego);
        panelInicio.add(pruebas);
        panelPrincipal.add(panelInicio,BorderLayout.CENTER);


        setContentPane(panelPrincipal);

        //accion del boton nuevoJuego
        nuevoJuego.addActionListener(e -> {
            juego=new Juego();
            tableroDefault= new TableroDefault(panelDeTexto, juego);
            ShowPanel(tableroDefault);
            PanelPersonaje panelPersonaje=new PanelPersonaje(panelDeTexto, juego);
            panelPrincipal.add(panelPersonaje);
        });

        //accion del boton cargarJuego
        cargarJuego.addActionListener(e -> System.out.println("Cargar juego no implementado."));

        // Acción para el boton de pruebas
        pruebas.addActionListener(e ->{
            tableroGUI = new TableroGUI(); // CrearTableroGUI
            ShowPanel(tableroGUI); // Cambiar al panel de TableroGUI

            // Aquí podrías comprobar el tablero después de que el usuario lo configure
            tableroGUI.getAceptarButton().addActionListener(ev -> {
                Tablero tablero = tableroGUI.getTablero(); // Obtener el tablero configurado
                if (tablero != null) {
                   panelDeTexto.setText("Tablero configurado correctamente.");
                    Juego juego = new Juego();
                    juego.setTablero(tablero);
                    panelPersonajePrueba = new PanelPersonajePrueba(panelDeTexto);
                    panelPrincipal.add(panelPersonajePrueba);
                    juego.setSupervivientes(panelPersonajePrueba.getSupervivientes());
                } else {
                    panelDeTexto.setText("El tablero no está configurado.");
                }
            });
        });


    }

    private void ShowPanel(JPanel p) {
        getContentPane().removeAll(); // Limpiar todos los componentes anteriores
        getContentPane().setLayout(new GridLayout(1,2));// Usar BorderLayout
        JPanel panelDivisor= new JPanel();
        panelDivisor.setLayout(new GridLayout(2,1));
        panelDivisor.setBackground(Color.WHITE);
        panelDivisor.setBorder(new EmptyBorder(10,10,10,10));
        getContentPane().add(panelDivisor);
        panelDivisor.add(p);
        panelDivisor.add(jScrollPane);// Agregar el nuevo panel en el centro
        getContentPane().revalidate();
        getContentPane().repaint();
    }



}
