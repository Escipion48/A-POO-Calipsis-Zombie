package es.urjc.poo.equipoj.ui;

import es.urjc.poo.equipoj.entidades.*;
import es.urjc.poo.equipoj.images.LectorImagenes;
import es.urjc.poo.equipoj.io.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class EntradaGUI extends JFrame {
    Juego juego;
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
        //iniciar el panel de entrada
        panelInicio = new JPanel();
        panelInicio.setLayout(new GridLayout(3, 1,20,20));
        panelInicio.setBackground(Color.WHITE);
        panelInicio.setBorder(new EmptyBorder(20,20,20,20));

        //iniciar los botones del panel de entrada
        nuevoJuego = new JButton("Nuevo Juego");
        nuevoJuego.setIcon(new LectorImagenes().CargarIcono(LectorImagenes.ICONO_JUEGO));
        cargarJuego = new JButton("Cargar Juego");
        cargarJuego.setIcon(new LectorImagenes().CargarIcono(LectorImagenes.ICONO_CARGAR));
        pruebas = new JButton("Prueba");
        pruebas.setIcon(new LectorImagenes().CargarIcono(LectorImagenes.ICONO_PRUEBA));


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

        setContentPane(panelInicio);

        //accion del boton nuevoJuego
        nuevoJuego.addActionListener(e -> {
            Jugar(new Juego(), panelDeTexto, false, false);
        });

        //accion del boton cargarJuego
        cargarJuego.addActionListener(e -> {
            JDialogCargar();
            if(!ruta.getText().isEmpty()){
                juego = IO.leerJSON(ruta.getText());
                Jugar(juego, panelDeTexto, false, true);
            }
        });

        // Acción para el boton de pruebas
        pruebas.addActionListener(e ->{
            Jugar(new Juego(), panelDeTexto, true, false);
        });
    }

    private void Jugar(Juego juego, JTextPane panelDeTexto, boolean esPrueba, boolean esCargar) {
        TableroUI tablero = new TableroUI(juego, panelDeTexto,esPrueba);
        ShowPanel(tablero);
        PanelPersonajeJuego panelPersonaje = new PanelPersonajeJuego(panelDeTexto, juego, esPrueba, esCargar);
        panelInicio.add(panelPersonaje);
    }

    private void ShowPanel(JPanel p) {
        getContentPane().removeAll(); // Limpiar todos los componentes anteriores
        getContentPane().setLayout(new GridLayout(1,2));
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

    private void JDialogCargar(){
    Window parentWindow = SwingUtilities.getWindowAncestor(this);

    JDialog dialogo = new JDialog(parentWindow,"Cargar Partida",Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setLayout(new BorderLayout(10,10));
        dialogo.setSize(400,150);
        dialogo.setBackground(Color.WHITE);
        dialogo.setLocationRelativeTo(null);

        JPanel panelCargar = new JPanel();
        panelCargar.setLayout(new GridLayout(1,2,10,10));
        panelCargar.setBackground(Color.WHITE);
        panelCargar.setBorder(new EmptyBorder(10,10,10,10));
        JTextField textField = new JTextField();

        // Crear un botón para seleccionar un archivo
        JButton btnAbrir = new JButton("Seleccionar Archivo");
        btnAbrir.setBounds(50, 50, 50, 10);
        btnAbrir.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int resultado = fileChooser.showOpenDialog(panelCargar);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                File archivoSeleccionado = fileChooser.getSelectedFile();
                textField.setText(archivoSeleccionado.getAbsolutePath());
            }
        });

        // Añadir los botones al Panel
        panelCargar.add(btnAbrir);
        dialogo.add(panelCargar, BorderLayout.CENTER);

        JButton aceptar = new JButton("Aceptar");
        aceptar.addActionListener(e-> {
            try{
                if(textField.getText().length()!=0) {
                    this.ruta = textField;
                    JOptionPane.showMessageDialog(this,"Se ha cargado correctamente","Partida Cargada",JOptionPane.INFORMATION_MESSAGE);
                    dialogo.dispose();
                } else {
                    JOptionPane.showMessageDialog(this,"No se ha seleccionado ningun archivo","ERROR",JOptionPane.ERROR_MESSAGE);
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
