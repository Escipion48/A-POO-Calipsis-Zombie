package es.urjc.poo.equipoj.ui;

import es.urjc.poo.equipoj.entidades.*;
import es.urjc.poo.equipoj.io.*;

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
    private JTextField ruta;



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
            TableroDefault tableroDefault= new TableroDefault(panelDeTexto, juego);
            ShowPanel(tableroDefault);
            PanelPersonaje panelPersonaje=new PanelPersonaje(panelDeTexto, juego);
            panelPrincipal.add(panelPersonaje);
        });

        //accion del boton cargarJuego
        cargarJuego.addActionListener(e -> {
            JDialogCargar(ruta);
            IO io = new IO();
            juego = io.leerJSON(ruta.getText());
            TableroDefault tableroDefault = new TableroDefault(panelDeTexto, juego);
            ShowPanel(tableroDefault);
            PanelPersonajeCargado panelPersonajeCargado = new PanelPersonajeCargado(panelDeTexto, juego);
            panelPrincipal.add(panelPersonajeCargado);
        });


        // Acción para el boton de pruebas
        pruebas.addActionListener(e ->{
            juego=new Juego();
            TableroPrueba tableroPrueba= new TableroPrueba(panelDeTexto, juego);
            ShowPanel(tableroPrueba);
            Posicion posicionObjetivo = tableroPrueba.getPosicionObjetivo();
            PanelPersonajePrueba panelPersonajePrueba=new PanelPersonajePrueba(panelDeTexto, juego,posicionObjetivo);
            panelPrincipal.add(panelPersonajePrueba);
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

    private void JDialogCargar(JTextField ruta){
    Window parentWindow = SwingUtilities.getWindowAncestor(this);

    JDialog dialogo = new JDialog(parentWindow,"Cargar Partida",Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setLayout(new BorderLayout(10,10));
        dialogo.setSize(400,300);
        dialogo.setBackground(Color.WHITE);
        dialogo.setLocationRelativeTo(null);

        JPanel panelCargar= new JPanel();
        panelCargar.setLayout(new GridLayout(1,2,10,10));
        panelCargar.setBackground(Color.WHITE);
        panelCargar.setBorder(new EmptyBorder(10,10,10,10));
        JLabel l1 = new JLabel("Introduzca la ruta");
        ruta= new JTextField();

        panelCargar.add(l1);
        panelCargar.add(ruta);


        dialogo.add(panelCargar,BorderLayout.CENTER);

        JButton aceptar = new JButton("Aceptar");
        JTextField finalRuta = ruta;
        this.ruta = ruta;
        aceptar.addActionListener(e->{
            try{
                if(finalRuta.getText()!=null){
                    IO io = new IO();
                    io.leerJSON(finalRuta.getText());
                    JOptionPane.showMessageDialog(this,"Se ha cargado correctamente","Partida Cargada",JOptionPane.INFORMATION_MESSAGE);
                    dialogo.dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,"No se ha cargado correctamente","ERROR",JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelar = new JButton("Cancelar");
        cancelar.addActionListener(e->{dialogo.dispose();});
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.WHITE);
        panelBotones.setLayout(new GridLayout(1,2,10,10));
        panelBotones.add(aceptar);
        panelBotones.add(cancelar);

        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }

}
