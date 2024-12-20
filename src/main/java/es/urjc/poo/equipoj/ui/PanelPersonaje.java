package es.urjc.poo.equipoj.ui;

import es.urjc.poo.equipoj.entidades.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class PanelPersonaje extends JPanel {
    final Juego juego;
    private JPanel panelSupervivienteEntero;
    private JTextPane panelDeTexto;
    private JButton siguienteRonda;
    private JButton crearSuperviviente;
    private JButton guardar;
    private JButton terminar;
    private JButton empezar;
    private JButton posicionZombies;

    public PanelPersonaje(JTextPane panelDeTexto, Juego juego) {
        this.panelDeTexto = panelDeTexto;
        this.juego = juego;
        initUI();
    }

    private void initUI() {
        //iniciar panel general
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        // Panel de personajes
        panelSupervivienteEntero = new JPanel(new BorderLayout());
        crearSuperviviente = new JButton("CREAR SUPERVIVIENTES");
        crearSuperviviente.addActionListener(e -> JDialogoCrearPersonaje());
        panelSupervivienteEntero.add(crearSuperviviente,BorderLayout.CENTER);

        // Inicializamos el array de supervivientes
        Superviviente[] superviviente = new Superviviente[4];
        for (int i = 0; i < 4; i++) {
            superviviente[i] = new Superviviente();
        }
        juego.setSupervivientes(superviviente);

        JPanel contenedorDeBotones = new JPanel();
        contenedorDeBotones.setBackground(Color.WHITE);
        contenedorDeBotones.setLayout(new GridLayout(4,1,10,10));

        siguienteRonda = new JButton("Siguiente Ronda");
        terminar = new JButton("Terminar");//falta accion
        guardar = new JButton("Guardar");//falta accion

        contenedorDeBotones.add(siguienteRonda);
        contenedorDeBotones.add(terminar);
        contenedorDeBotones.add(guardar);

        // Botón empezar
         empezar = new JButton("Empezar Ronda");
        empezar.addActionListener(e -> {
            panelDeTexto.setText(panelDeTexto.getText() + "Empezando la 1º ronda...");
            JDialogTurnoSuperviviente();
            TurnoZombie();
            remove(empezar);
            add(contenedorDeBotones,BorderLayout.SOUTH);
            revalidate();
            repaint();

        });


        siguienteRonda.addActionListener(e->{
            panelDeTexto.setText(panelDeTexto.getText() + "Empezando la siguiente ronda...");

            JDialogTurnoSuperviviente();
            TurnoZombie();
        });



        // Agregar componentes
        add(panelSupervivienteEntero, BorderLayout.CENTER);
    }


    private void actualizarSupervivientesUI() {
        // Borrar todo y guardar una nueva distribucion
        panelSupervivienteEntero.removeAll();
        panelSupervivienteEntero.setLayout(new BorderLayout(10,10));

        JPanel panelSuperviviente = new JPanel();
        panelSuperviviente.setLayout(new GridLayout(1, 4, 10, 10));
        panelSuperviviente.setBorder(BorderFactory.createTitledBorder("Supervivientes"));
        panelSuperviviente.setBackground(Color.WHITE);

        //Crear paneles para cada superviviente
        JPanel panelSuperviviente1 = new JPanel();
        JPanel panelSuperviviente2 = new JPanel();
        JPanel panelSuperviviente3 = new JPanel();
        JPanel panelSuperviviente4 = new JPanel();

        //Poner la distribucion de los paneles
        panelSuperviviente1.setLayout(new GridLayout(6, 1, 10, 10));
        panelSuperviviente2.setLayout(new GridLayout(6, 1, 10, 10));
        panelSuperviviente3.setLayout(new GridLayout(6, 1, 10, 10));
        panelSuperviviente4.setLayout(new GridLayout(6, 1, 10, 10));

        //Ponerlos de color blanco
        panelSuperviviente1.setBackground(Color.WHITE);
        panelSuperviviente2.setBackground(Color.WHITE);
        panelSuperviviente3.setBackground(Color.WHITE);
        panelSuperviviente4.setBackground(Color.WHITE);

        //Añadir los personajes a sus corespondientes paneles
        panelSuperviviente1.add(new JLabel(juego.getSuperviviente(0).getNombre()));
        panelSuperviviente2.add(new JLabel(juego.getSuperviviente(1).getNombre()));
        panelSuperviviente3.add(new JLabel(juego.getSuperviviente(2).getNombre()));
        panelSuperviviente4.add(new JLabel(juego.getSuperviviente(3).getNombre()));

        //Añadir los paneles al panel grande para juntarlos
        panelSuperviviente.add(panelSuperviviente1);
        panelSuperviviente.add(panelSuperviviente2);
        panelSuperviviente.add(panelSuperviviente3);
        panelSuperviviente.add(panelSuperviviente4);

        //Crear los botones de para ver el inventerio de cada uno
        JButton inventario1 = new JButton("Inventario");
        JButton inventario2 = new JButton("Inventario");
        JButton inventario3 = new JButton("Inventario");
        JButton inventario4 = new JButton("Inventario");

        //Accion del boton para ver el inventario de cada superviviente
        inventario1.addActionListener(e -> JDialogVerInventario(juego.getSuperviviente(0)));
        inventario2.addActionListener(e -> JDialogVerInventario(juego.getSuperviviente(1)));
        inventario3.addActionListener(e -> JDialogVerInventario(juego.getSuperviviente(2)));
        inventario4.addActionListener(e -> JDialogVerInventario(juego.getSuperviviente(3)));

        //Añadir los botones a sus respectivos paneles
        panelSuperviviente1.add(inventario1);
        panelSuperviviente2.add(inventario2);
        panelSuperviviente3.add(inventario3);
        panelSuperviviente4.add(inventario4);

        //Crear botones para ver las armas activas
        JButton armaActivas1 = new JButton("Arma Activas");
        JButton armaActivas2 = new JButton("Arma Activas");
        JButton armaActivas3 = new JButton("Arma Activas");
        JButton armaActivas4 = new JButton("Arma Activas");

        //Accion al pulsar el boton armasActivas
        armaActivas1.addActionListener(e -> JDialogVerArmasActivas(juego.getSuperviviente(0)));
        armaActivas2.addActionListener(e -> JDialogVerArmasActivas(juego.getSuperviviente(1)));
        armaActivas3.addActionListener(e -> JDialogVerArmasActivas(juego.getSuperviviente(2)));
        armaActivas4.addActionListener(e -> JDialogVerArmasActivas(juego.getSuperviviente(3)));

        //Añadir los botones armasActivas a sus correspondientes paneles
        panelSuperviviente1.add(armaActivas1);
        panelSuperviviente2.add(armaActivas2);
        panelSuperviviente3.add(armaActivas3);
        panelSuperviviente4.add(armaActivas4);

        //Crear botones para ver los ZombiesEliminados
        JButton zombiesEliminados1 = new JButton("Zombies Eliminados");
        JButton zombiesEliminados2 = new JButton("Zombies Eliminados");
        JButton zombiesEliminados3 = new JButton("Zombies Eliminados");
        JButton zombiesEliminados4 = new JButton("Zombies Eliminados");

        //Accion al pulsar el boton zombiesEliminados
        zombiesEliminados1.addActionListener(e -> JDialogVerZombiesEliminados(juego.getSuperviviente(0)));
        zombiesEliminados2.addActionListener(e -> JDialogVerZombiesEliminados(juego.getSuperviviente(1)));
        zombiesEliminados3.addActionListener(e -> JDialogVerZombiesEliminados(juego.getSuperviviente(2)));
        zombiesEliminados4.addActionListener(e -> JDialogVerZombiesEliminados(juego.getSuperviviente(3)));

        //Añadir los botones para ver los zombies Eliminados
        panelSuperviviente1.add(zombiesEliminados1);
        panelSuperviviente2.add(zombiesEliminados2);
        panelSuperviviente3.add(zombiesEliminados3);
        panelSuperviviente4.add(zombiesEliminados4);

        //Crear botones para ver los zombies que nos han atacado
        JButton ataqueZombiesRecibidos1 = new JButton("Ataques Recibidos");
        JButton ataqueZombiesRecibidos2 = new JButton("Ataques Recibidos");
        JButton ataqueZombiesRecibidos3 = new JButton("Ataques Recibidos");
        JButton ataqueZombiesRecibidos4 = new JButton("Ataques Recibidos");

        //Accion al pulsar en el boton ataqueZombiesRecibido
        ataqueZombiesRecibidos1.addActionListener(e -> JDialogAtaqueZombiesRecibidos(juego.getSuperviviente(0)));
        ataqueZombiesRecibidos2.addActionListener(e -> JDialogAtaqueZombiesRecibidos(juego.getSuperviviente(1)));
        ataqueZombiesRecibidos3.addActionListener(e -> JDialogAtaqueZombiesRecibidos(juego.getSuperviviente(2)));
        ataqueZombiesRecibidos4.addActionListener(e -> JDialogAtaqueZombiesRecibidos(juego.getSuperviviente(3)));

        //Añadir los botones al panelSuperviviente correspondiente
        panelSuperviviente1.add(ataqueZombiesRecibidos1);
        panelSuperviviente2.add(ataqueZombiesRecibidos2);
        panelSuperviviente3.add(ataqueZombiesRecibidos3);
        panelSuperviviente4.add(ataqueZombiesRecibidos4);

        //Crear botones para consultar la posicion del superviviente
        JButton posicion1 = new JButton("Posicion");
        JButton posicion2 = new JButton("Posicion");
        JButton posicion3 = new JButton("Posicion");
        JButton posicion4 = new JButton("Posicion");

        //Accion al pulsar los botones de posicion
        posicion1.addActionListener(e->JDialogPosicion(juego.getSuperviviente(0)));
        posicion2.addActionListener(e->JDialogPosicion(juego.getSuperviviente(1)));
        posicion3.addActionListener(e->JDialogPosicion(juego.getSuperviviente(2)));
        posicion4.addActionListener(e->JDialogPosicion(juego.getSuperviviente(3)));

        //Añadir los botones posicion
        panelSuperviviente1.add(posicion1);
        panelSuperviviente2.add(posicion2);
        panelSuperviviente3.add(posicion3);
        panelSuperviviente4.add(posicion4);

        //Boton de la posicion de los zombies
        posicionZombies = new JButton("Posicion Zombies");
        JButton ataques = new JButton("Ataques");
        ataques.addActionListener(e->JDialogAtaquesHechos());
        posicionZombies.addActionListener(e->JDialogVerPosicionZombies());

        JPanel panelArriba = new JPanel();
        panelArriba.setLayout(new GridLayout(1,2,10,10));
        panelArriba.add(posicionZombies);
        panelArriba.add(ataques);

        panelSupervivienteEntero.add(panelArriba,BorderLayout.NORTH);
        panelSupervivienteEntero.add(panelSuperviviente,BorderLayout.CENTER);


        // Actualizar la interfaz gráfica
        revalidate();
        repaint();
    }


    private void JDialogoCrearPersonaje() {
        // Obtener la ventana padre (JFrame o JDialog)
        Window parentWindow = SwingUtilities.getWindowAncestor(this);

        // Crear el JDialog
        JDialog dialogo = new JDialog(parentWindow, "Crear Personaje", Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setSize(400, 300);
        dialogo.setLocationRelativeTo(parentWindow);
        dialogo.setLayout(new BorderLayout(10, 10));

        // Panel de contenido del diálogo
        JPanel panelContenido = new JPanel(new GridLayout(5, 2, 10, 10));
        panelContenido.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel l1 = new JLabel("Nombre del personaje 1:");
        JTextField c1 = new JTextField();
        JLabel l2 = new JLabel("Nombre del personaje 2:");
        JTextField c2 = new JTextField();
        JLabel l3 = new JLabel("Nombre del personaje 3:");
        JTextField c3 = new JTextField();
        JLabel l4 = new JLabel("Nombre del personaje 4:");
        JTextField c4 = new JTextField();

        panelContenido.add(l1);
        panelContenido.add(c1);
        panelContenido.add(l2);
        panelContenido.add(c2);
        panelContenido.add(l3);
        panelContenido.add(c3);
        panelContenido.add(l4);
        panelContenido.add(c4);

        // Botón de confirmación
        JButton botonConfirmar = new JButton("Confirmar");
        botonConfirmar.addActionListener(e -> {
            String nombre1 = c1.getText();
            String nombre2 = c2.getText();
            String nombre3 = c3.getText();
            String nombre4 = c4.getText();

            if (nombre1.isEmpty() || nombre2.isEmpty() || nombre3.isEmpty() || nombre4.isEmpty()) {
                JOptionPane.showMessageDialog(dialogo, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Asignar los nombres a los supervivientes
                juego.getSuperviviente(0).setNombre(nombre1);
                juego.getSuperviviente(1).setNombre(nombre2);
                juego.getSuperviviente(2).setNombre(nombre3);
                juego.getSuperviviente(3).setNombre(nombre4);
                // Actualizar la interfaz con los nuevos nombres


                panelSupervivienteEntero.remove(crearSuperviviente); // Quitar el botón de creación
                actualizarSupervivientesUI(); // Reconstruir la interfaz
                add(empezar, BorderLayout.SOUTH);

                revalidate();
                repaint();

                for (Superviviente s : juego.getSupervivientes()) {
                    panelDeTexto.setText(panelDeTexto.getText() + "Se ha creado un superviviente: " + s.getNombre() + "\n");
                }

            }
            dialogo.dispose();
        });

        // Añadir los componentes al diálogo
        dialogo.add(panelContenido, BorderLayout.CENTER);
        dialogo.add(botonConfirmar, BorderLayout.SOUTH);

        // Mostrar el diálogo
        dialogo.setVisible(true);
    }

    private void JDialogVerInventario(Superviviente superviviente) {
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        //Poner el titulo del JDialog
        String titulo = "Inventario de " + superviviente.getNombre();

        //Crear el JDialog
        JDialog dialogo = new JDialog(parentWindow, titulo, Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setSize(600, 300);
        dialogo.setLocationRelativeTo(parentWindow);
        dialogo.setLayout(new BorderLayout(10, 10));


        JPanel contenidoDelInventario = new JPanel();
        contenidoDelInventario.setBorder(new EmptyBorder(10, 10, 10, 10));
        contenidoDelInventario.setLayout(new GridLayout(5, 1, 10, 10));
        contenidoDelInventario.setBackground(Color.WHITE);
        for (Equipo e : superviviente.getInventario()) {
            try {
                contenidoDelInventario.add(new JLabel(e.toString()));
            } catch (Exception exception) {
                contenidoDelInventario.add(new JLabel("Espacio vacio"));
            }
        }
        JButton aceptar = new JButton("Aceptar");
        aceptar.addActionListener(e -> {
            dialogo.dispose();
        });
        dialogo.add(contenidoDelInventario, BorderLayout.CENTER);
        dialogo.add(aceptar, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }

    private void JDialogVerArmasActivas(Superviviente superviviente) {
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        String titulo = "Armas activas de " + superviviente.getNombre();
        JDialog dialogo = new JDialog(parentWindow, titulo, Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setSize(400, 300);
        dialogo.setLocationRelativeTo(parentWindow);
        dialogo.setLayout(new BorderLayout(10, 10));

        JPanel contenidoDeArmasActivas = new JPanel();
        contenidoDeArmasActivas.setBorder(new EmptyBorder(10, 10, 10, 10));
        contenidoDeArmasActivas.setLayout(new GridLayout(2, 1, 10, 10));
        contenidoDeArmasActivas.setBackground(Color.WHITE);

        for (Arma a : superviviente.getArmasActivas()) {
            try {
                contenidoDeArmasActivas.add(new JLabel(a.toString()));
            } catch (Exception exception) {
                contenidoDeArmasActivas.add(new JLabel("Espacio vacio"));
            }
        }
        JButton aceptar = new JButton("Aceptar");
        aceptar.addActionListener(e -> {
            dialogo.dispose();
        });
        dialogo.add(contenidoDeArmasActivas, BorderLayout.CENTER);
        dialogo.add(aceptar, BorderLayout.SOUTH);
        dialogo.setVisible(true);

    }

    private void JDialogVerZombiesEliminados(Superviviente superviviente) {
        Window parentWindow = SwingUtilities.getWindowAncestor(this);

        String titulo = "Zombies eliminados por " + superviviente.getNombre();
        JDialog dialogo = new JDialog(parentWindow, titulo, Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setLayout(new BorderLayout(10, 10));
        dialogo.setBackground(Color.white);
        dialogo.setSize(400, 300);
        dialogo.setLocationRelativeTo(this);

        JTextPane panelDeTextoDeZombiesEliminados = new JTextPane();
        panelDeTextoDeZombiesEliminados.setEditable(false);

        JScrollPane jScrollPane = new JScrollPane(panelDeTextoDeZombiesEliminados);
        jScrollPane.setVerticalScrollBarPolicy(jScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setHorizontalScrollBarPolicy(jScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        if (superviviente.getZombiesEliminados().isEmpty()) {
            panelDeTextoDeZombiesEliminados.setText("No se ha eliminado a ningún zombie\n");
        }
        for (Zombie z : superviviente.getZombiesEliminados()) {
            panelDeTextoDeZombiesEliminados.setText(panelDeTextoDeZombiesEliminados.getText()+z.toString() + "\n");
        }
        JButton aceptar = new JButton("Aceptar");
        aceptar.addActionListener(e -> {
            dialogo.dispose();
        });
        dialogo.add(jScrollPane, BorderLayout.CENTER);
        dialogo.add(aceptar, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }

    private void JDialogVerPosicionZombies() {
        Window parentWindow = SwingUtilities.getWindowAncestor(this);

        String titulo = "Posición de Zombies";
        JDialog dialogo = new JDialog(parentWindow, titulo, Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setLayout(new BorderLayout(10, 10));
        dialogo.setBackground(Color.white);
        dialogo.setSize(400, 300);
        dialogo.setLocationRelativeTo(this);

        // Panel de texto para mostrar las posiciones de los zombies
        JTextPane panelDeTextoPosicionZombie = new JTextPane();
        panelDeTextoPosicionZombie.setEditable(false);

        JScrollPane jScrollPane = new JScrollPane(panelDeTextoPosicionZombie);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Usar StringBuilder para construir el texto
        StringBuilder textoZombies = new StringBuilder();
        for (Zombie z : juego.getZombies()) {
            textoZombies.append(z.toString()).append("\n");
        }
        panelDeTextoPosicionZombie.setText(textoZombies.toString());

        // Botón para cerrar el diálogo
        JButton aceptar = new JButton("Aceptar");
        aceptar.addActionListener(e -> dialogo.dispose());

        dialogo.add(jScrollPane, BorderLayout.CENTER);
        dialogo.add(aceptar, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }

    private void JDialogAtaquesHechos(){
        Window parentWindow = SwingUtilities.getWindowAncestor(this);

        JDialog dialogo = new JDialog(parentWindow,"Ataques Realizados",JDialog.ModalityType.APPLICATION_MODAL );
        dialogo.setLayout(new BorderLayout(10, 10));
        dialogo.setBackground(Color.white);
        dialogo.setSize(400, 300);
        dialogo.setLocationRelativeTo(null);

        JPanel panelTextoAtaques = new JPanel();
        panelTextoAtaques.setLayout(new BorderLayout(10, 10));
        panelTextoAtaques.setBackground(Color.white);
        panelTextoAtaques.setSize(400, 300);

        JTextPane panelTexto = new JTextPane();
        panelTexto.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(panelTexto);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        try{
            StringBuilder stringBuilder = new StringBuilder("Se ha realizado: ");
            for(Ataque ataque : juego.getAtaques().getAtaques()){
                stringBuilder.append(ataque).append("\n");
        }panelTexto.setText(stringBuilder.toString());
        }catch(NullPointerException e){
            panelTexto.setText("No se ha realizado ningun ataque\n");
        }

        panelTextoAtaques.add(jScrollPane, BorderLayout.CENTER);
        JButton aceptar = new JButton("Aceptar");
        aceptar.addActionListener(e -> dialogo.dispose());
        dialogo.add(panelTextoAtaques, BorderLayout.CENTER);
        dialogo.add(aceptar, BorderLayout.SOUTH);

        dialogo.setVisible(true);

    }

    private void JDialogAtaqueZombiesRecibidos(Superviviente superviviente) {
        // Crear ventana modal
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        String titulo = "Zombies que han atacado a " + superviviente.getNombre();
        JDialog dialogo = new JDialog(parentWindow, titulo, Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setSize(400, 300);
        dialogo.setLocationRelativeTo(this);
        dialogo.setLayout(new BorderLayout(10, 10));

        // Crear panel de texto
        JTextPane panelTextoDeAtaqueZombiesRecibidos = new JTextPane();
        panelTextoDeAtaqueZombiesRecibidos.setEditable(false);

        // Construir el texto del panel
        if (superviviente.getAtaquesRecibidos().isEmpty()) {
            panelTextoDeAtaqueZombiesRecibidos.setText("No se ha recibido ningún ataque\n");
        } else {
            StringBuilder texto = new StringBuilder();
            for (Zombie z : superviviente.getAtaquesRecibidos()) {
                texto.append(z.toString()).append("\n");
            }
            panelTextoDeAtaqueZombiesRecibidos.setText(texto.toString());
        }

        // Botón de aceptar para cerrar el diálogo
        JButton aceptar = new JButton("Aceptar");
        aceptar.addActionListener(e -> dialogo.dispose());

        // Añadir componentes al diálogo
        dialogo.add(new JScrollPane(panelTextoDeAtaqueZombiesRecibidos), BorderLayout.CENTER);
        dialogo.add(aceptar, BorderLayout.SOUTH);

        // Hacer visible el diálogo
        dialogo.setVisible(true);
    }


    private void JDialogTurnoSuperviviente() {
        Window parentWindow = SwingUtilities.getWindowAncestor(this);

        for (Superviviente superviviente : juego.getSupervivientes()) {
            superviviente.setAcciones(3);
            while(superviviente.getAcciones()>0){
            String titulo = "Tueno de " + superviviente.getNombre();

            JDialog dialogo = new JDialog(parentWindow, titulo, Dialog.ModalityType.APPLICATION_MODAL);
            dialogo.setLocationRelativeTo(null);
            dialogo.setLayout(new BorderLayout(10,10));

            dialogo.setSize(500, 300);


            JPanel panelTurnoSuperviviente = new JPanel();
            panelTurnoSuperviviente.setLayout(new GridLayout(6,1,10,10));

            //Crear los Botones que contienen las acciones de los supervivientes
            JButton noHacerNada = new JButton("No hacer nada");
            JButton atacar = new JButton("Atacar");
            JButton eliminarItemDelInventerio = new JButton("Eliminar item del inventerio");
            JButton cambiarArmaActiva = new JButton("Cambiar arma activa");
            JButton buscar = new JButton("Buscar equipo");
            JButton moverse = new JButton("Moverse");


            //Acciones de los botones
            noHacerNada.addActionListener(e -> {
                NoHacerNada(superviviente);
                dialogo.dispose();
            });
            eliminarItemDelInventerio.addActionListener(e-> {
                JDialogEliminarItemDelInventerio(superviviente);
                dialogo.dispose();
            });
            cambiarArmaActiva.addActionListener(e->{
                JDialogCambiarArmaActiva(superviviente);
                dialogo.dispose();
            });
            moverse.addActionListener(e->{
                JDialogMoverse(superviviente);
                dialogo.dispose();
            });
            buscar.addActionListener(e->{
                JDialogResultadoBuscar(superviviente);
                dialogo.dispose();
            });
            atacar.addActionListener(e->{
                JDialogAtacar(superviviente);
                dialogo.dispose();
            });

            //Añadir los botones
                panelTurnoSuperviviente.add(noHacerNada);
                panelTurnoSuperviviente.add(eliminarItemDelInventerio);
                panelTurnoSuperviviente.add(cambiarArmaActiva);
                panelTurnoSuperviviente.add(moverse);
                panelTurnoSuperviviente.add(buscar);
                panelTurnoSuperviviente.add(atacar);
                dialogo.add(panelTurnoSuperviviente,BorderLayout.CENTER);

                JLabel mensaje = new JLabel("Acciones restantes: " + superviviente.getAcciones());
                mensaje.setHorizontalAlignment(SwingConstants.CENTER);
                dialogo.add(mensaje, BorderLayout.NORTH);

                dialogo.setVisible(true);


        }}

    }

    private void NoHacerNada(Superviviente superviviente) {
         superviviente.setAcciones(0);
    }

    private void JDialogEliminarItemDelInventerio(Superviviente superviviente) {
        final Equipo[] Item = new Equipo[1];
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        String titulo = "Eliminar item del inventario de " + superviviente.getNombre();

        JDialog dialogo = new JDialog(parentWindow, titulo, Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setSize(400, 300);
        dialogo.setLayout(new BorderLayout(10, 10));
        dialogo.setLocationRelativeTo(null);

        JPanel contenidoDelInventario = new JPanel(new GridLayout(5, 1, 10, 10));
        contenidoDelInventario.setBorder(new EmptyBorder(10, 10, 10, 10));
        ButtonGroup itemInventario = new ButtonGroup();

        for (Equipo item : superviviente.getInventario()) {
            if (item != null) { // Crear botón solo si el ítem no es null
                JRadioButton botonItem = new JRadioButton(item.getNombre());
                itemInventario.add(botonItem);
                contenidoDelInventario.add(botonItem);
                botonItem.addActionListener(e -> Item[0] = item);
            }
        }

        JButton aceptar = new JButton("Aceptar");
        aceptar.addActionListener(e -> {
            if (Item[0] == null) {
                JOptionPane.showMessageDialog(dialogo, "Debe seleccionar un ítem para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Eliminar ítem del inventario
            for (int i = 0; i < superviviente.getInventario().length; i++) {
                if (superviviente.getInventario()[i] != null && superviviente.getInventario()[i].equals(Item[0])) {
                    System.out.println("Ítem encontrado en posición: " + i);
                    superviviente.setInventario(null,i);// Eliminar ítem
                    break;
                }
            }

            System.out.println("Ítem eliminado: " + superviviente);

            // Verificar si es un arma activa
            if (Item[0] instanceof Arma) {
                Arma armaItem = (Arma) Item[0];
                for (int i = 0; i < superviviente.getArmasActivas().length; i++) {
                    if (superviviente.getArmaActiva(i) != null && superviviente.getArmaActiva(i).equals(armaItem)) {
                        superviviente.setArmaActiva(null, i);
                        System.out.println("Arma activa eliminada: " + superviviente);
                        break;
                    }
                }
            }

            // Actualizar la UI
            actualizarSupervivientesUI();
            System.out.println("UI actualizada.");

            // Mostrar mensaje de éxito y cerrar diálogo
            JOptionPane.showMessageDialog(dialogo, "Ítem eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dialogo.dispose();
        });

        JButton cancelar = new JButton("Cancelar");
        cancelar.addActionListener(e -> dialogo.dispose());

        dialogo.add(contenidoDelInventario, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.add(aceptar);
        panelBotones.add(cancelar);
        dialogo.add(panelBotones, BorderLayout.SOUTH);

        dialogo.setVisible(true);
    }

    private void TurnoZombie(){
        ArrayList<EntidadActivable> entidades = new ArrayList<>(Arrays.asList(juego.getSupervivientes()));
        for(Zombie z: juego.getZombies()){
            z.activarse();
            do{
                ataqueZombie(z);
                if(z.getActivaciones()==0){
                    break;
                }
                z.moverse(entidades);
            }while(z.getActivaciones() > 0);
        }
        juego.anadirZombie();
    }

    private void JDialogCambiarArmaActiva(Superviviente superviviente) {
        final Arma[] armaActivaSeleccionada = new Arma[1];
        final Arma[] armaInventarioSeleccionada = new Arma[1];

        // Crear diálogo modal
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        String titulo = "Cambiar arma activa de " + superviviente.getNombre();
        JDialog dialogo = new JDialog(parentWindow, titulo, Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setSize(400, 300);
        dialogo.setLayout(new BorderLayout(10, 10));
        dialogo.setLocationRelativeTo(null);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new GridLayout(1, 2, 10, 10));
        panelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Panel de armas activas
        JPanel panelArmasActivas = new JPanel(new GridLayout(0, 1, 5, 5));
        panelArmasActivas.setBorder(BorderFactory.createTitledBorder("Armas Activas"));
        ButtonGroup grupoArmasActivas = new ButtonGroup();

        for (int i = 0; i < superviviente.getArmasActivas().length; i++) {
            Arma arma = superviviente.getArmaActiva(i);
            if (arma != null) {//solo crea botones si no son nulos
                JRadioButton botonArma = new JRadioButton(arma.getNombre());
                botonArma.addActionListener(e -> armaActivaSeleccionada[0] = arma);
                grupoArmasActivas.add(botonArma);
                panelArmasActivas.add(botonArma);
            }
        }
        panelPrincipal.add(panelArmasActivas);

        // Panel del inventario
        JPanel panelInventario = new JPanel(new GridLayout(0, 1, 5, 5));
        panelInventario.setBorder(BorderFactory.createTitledBorder("Inventario"));
        ButtonGroup grupoInventario = new ButtonGroup();

        for (int j = 0; j < superviviente.getInventario().length; j++) {
            if (superviviente.getInventario(j) instanceof Arma) {
                Arma arma = (Arma) superviviente.getInventario(j);
                JRadioButton botonArma = new JRadioButton(arma.getNombre());
                botonArma.addActionListener(e -> armaInventarioSeleccionada[0] = arma);
                grupoInventario.add(botonArma);
                panelInventario.add(botonArma);
            }
        }
        panelPrincipal.add(panelInventario);

        dialogo.add(panelPrincipal, BorderLayout.CENTER);

        // Panel de botones
        JButton aceptar = new JButton("Aceptar");
        aceptar.addActionListener(e -> {
            // Verificar las condiciones de selección según el estado actual
            int ArmasActivas = grupoArmasActivas.getButtonCount();

            if (ArmasActivas == 2) {
                if (armaActivaSeleccionada[0] == null) {
                    JOptionPane.showMessageDialog(dialogo, "Debe seleccionar un arma activa.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (armaInventarioSeleccionada[0] == null) {
                    JOptionPane.showMessageDialog(dialogo, "Debe seleccionar un arma del inventario.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Cambiar el arma activa seleccionada
                for (int i = 0; i < superviviente.getArmasActivas().length; i++) {
                    if (superviviente.getArmaActiva(i) != null && superviviente.getArmaActiva(i).equals(armaActivaSeleccionada[0])) {
                        superviviente.setArmaActiva(armaInventarioSeleccionada[0], i);
                        superviviente.menosUnaAccion();
                        break;
                    }
                }
            } else if (ArmasActivas == 1) {
                if (armaActivaSeleccionada[0] == null) {
                    for (int i = 0; i < superviviente.getArmasActivas().length; i++) {
                        if (superviviente.getArmaActiva(i) == null) {
                            if (armaInventarioSeleccionada[0] == null) {
                                JOptionPane.showMessageDialog(dialogo, "Debe seleccionar un arma del inventario.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            superviviente.setArmaActiva(armaInventarioSeleccionada[0], i);
                            superviviente.menosUnaAccion();
                            break;
                        }
                    }
                } else {
                    for (int i = 0; i < superviviente.getArmasActivas().length; i++) {
                        if (superviviente.getArmaActiva(i) != null && superviviente.getArmaActiva(i).equals(armaActivaSeleccionada[0])) {
                            superviviente.setArmaActiva(armaInventarioSeleccionada[0], i);
                            superviviente.menosUnaAccion();
                            break;
                        }
                    }
                }
            } else {
                // No hay armas activas
                if (armaInventarioSeleccionada[0] == null) {
                    JOptionPane.showMessageDialog(dialogo, "Debe seleccionar un arma del inventario.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for (int i = 0; i < superviviente.getArmasActivas().length; i++) {
                    if (superviviente.getArmaActiva(i) == null) {
                        superviviente.setArmaActiva(armaInventarioSeleccionada[0], i);
                        superviviente.menosUnaAccion();
                        break;
                    }
                }
            }

            actualizarSupervivientesUI();
            dialogo.dispose();
        });

        JButton cancelar = new JButton("Cancelar");
        cancelar.addActionListener(e -> dialogo.dispose());

        JPanel panelBotones = new JPanel();
        panelBotones.add(aceptar);
        panelBotones.add(cancelar);

        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }


    private void JDialogMoverse(Superviviente superviviente) {
        Window parentWindow = SwingUtilities.getWindowAncestor(this);


        JDialog dialogo = new JDialog(parentWindow, "Moverse", Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setSize(400, 300);
        dialogo.setLayout(new BorderLayout(10, 10));
        dialogo.setLocationRelativeTo(null);

        JPanel panelCoordenadas = new JPanel();
        panelCoordenadas.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelCoordenadas.setLayout(new GridLayout(3, 2, 10, 10));

        //Crear los componentes
        JLabel l1 = new JLabel("Posicion actual: "+superviviente.getPosicion());
        JLabel l2 = new JLabel("X: ");
        JLabel l3 = new JLabel("Y: ");
        JTextField jt1 = new JTextField();
        JTextField jt2 = new JTextField();

        //Añadirlos al panel
        panelCoordenadas.add(l1);
        panelCoordenadas.add(new JLabel(""));
        panelCoordenadas.add(l2);
        panelCoordenadas.add(jt1);
        panelCoordenadas.add(l3);
        panelCoordenadas.add(jt2);


        JButton aceptar = new JButton("Aceptar");
        aceptar.addActionListener(e -> {
            int x, y;
            try {
                x = Integer.parseInt(jt1.getText());
                y = Integer.parseInt(jt2.getText());
            }catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialogo, "Por favor, introduce números válidos para las coordenadas.", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Posicion posicion = new Posicion(x, y);
            ArrayList<EntidadActivable> entidades = new ArrayList<>(juego.getZombies());
            if (superviviente.getPosicion().comprobarAdyacente(posicion) && (superviviente.getAcciones() - superviviente.calcularNumeroAccinesPorMoverse(entidades)>0)){
                superviviente.moverse(entidades);
                superviviente.setPosicion(posicion);
                actualizarSupervivientesUI();
                dialogo.dispose();
            } else if (!superviviente.getPosicion().comprobarAdyacente(posicion)) {
                JOptionPane.showMessageDialog(dialogo, "La posición no es adyacente...", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                for(Zombie z : juego.getZombies()){
                    System.out.println(z);
                }
                JOptionPane.showMessageDialog(dialogo, "Hay demasiados Zombies para moverse y/o falta de acciones.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialogo.add(panelCoordenadas, BorderLayout.CENTER);
        dialogo.add(aceptar, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }

    private void JDialogResultadoBuscar(Superviviente superviviente){
        //Comprueba de que si la casilla ya se a buscado y si hay o no Equipo
        int numeroObjetoInventerio = superviviente.calcularNumeroObjetosInventario();
        if(superviviente.buscar(juego.getTablero().getCasilla(superviviente.getPosicion()))){
            if(numeroObjetoInventerio < superviviente.calcularNumeroObjetosInventario()){
            JDialogBuscarTrue(superviviente);}
            else{JDialogBuscarNada();}
        }else {
            JDialogBuscarFalse();
        }
    }

    private void JDialogBuscarFalse(){
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        JOptionPane.showMessageDialog(parentWindow,"Ya se ha bucado en esta casilla y/o tiene el inventario lleno","ERRPR",JOptionPane.INFORMATION_MESSAGE);
    }

    private void JDialogBuscarNada(){
        Window parentWindow = SwingUtilities.getWindowAncestor(this);

        JOptionPane.showMessageDialog(parentWindow,"No se ha encontrado nada","Buscar",JOptionPane.INFORMATION_MESSAGE);
    }

    private void JDialogBuscarTrue(Superviviente superviviente){
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        JOptionPane.showMessageDialog(parentWindow,"Se ha encontrado :"+ superviviente.getInventario(superviviente.calcularNumeroObjetosInventario()-1),"Objeto encontrado",JOptionPane.INFORMATION_MESSAGE);
    }

    private void JDialogPosicion(Superviviente superviviente){
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        String mensaje = "La posicion de "+ superviviente.getNombre() +" es "+ superviviente.getPosicion();
        JOptionPane.showMessageDialog(parentWindow,mensaje,"Posicion",JOptionPane.INFORMATION_MESSAGE);
    }

    private void JDialogAtacar(Superviviente superviviente) {
        Window parentWindow = SwingUtilities.getWindowAncestor(this);

        JDialog dialogo = new JDialog(parentWindow, "Atacar", Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setSize(400, 300);
        dialogo.setLayout(new BorderLayout(10, 10));
        dialogo.setLocationRelativeTo(null);

        // Panel para seleccionar el arma
        JPanel panelSeleccionArma = new JPanel(new GridLayout(0, 1, 10, 10));
        panelSeleccionArma.setBorder(BorderFactory.createTitledBorder("Armas Activas"));

        ButtonGroup armasActivas = new ButtonGroup();
        final Arma[] armaSeleccionada = new Arma[1];

        for (Arma a : superviviente.getArmasActivas()) {
            if (a != null) {
                JRadioButton botonArma = new JRadioButton(a.getNombre());
                armasActivas.add(botonArma);
                panelSeleccionArma.add(botonArma);

                botonArma.addActionListener(e -> armaSeleccionada[0] = a);
            }
        }

        dialogo.add(panelSeleccionArma, BorderLayout.CENTER);

        // Panel de botones
        JButton aceptar = new JButton("Aceptar");
        JButton cancelar = new JButton("Cancelar");

        aceptar.addActionListener(e -> {
            if (armaSeleccionada[0] == null) {
                JOptionPane.showMessageDialog(dialogo, "Debe seleccionar un arma para atacar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Mostrar panel de coordenadas
            mostrarPanelCoordenadas(dialogo, armaSeleccionada[0], superviviente);

        });

        cancelar.addActionListener(e -> dialogo.dispose());

        JPanel panelBotones = new JPanel();
        panelBotones.add(aceptar);
        panelBotones.add(cancelar);

        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }

    private void mostrarPanelCoordenadas(JDialog dialogo, Arma arma, Superviviente superviviente) {
        JPanel panelCoordenadas = new JPanel(new GridLayout(4, 2, 10, 10));
        panelCoordenadas.setBorder(BorderFactory.createTitledBorder("Seleccionar Casilla Objetivo"));
        panelCoordenadas.setBorder(new EmptyBorder(10,10,10,10));

        JLabel l1 = new JLabel("Introduzca coordenadas:");
        JLabel l2 = new JLabel(arma.toString());
        JLabel xLabel = new JLabel("X:");
        JLabel yLabel = new JLabel("Y:");
        JTextField campoX = new JTextField();
        JTextField campoY = new JTextField();

        panelCoordenadas.add(l1);
        panelCoordenadas.add(new JLabel());
        panelCoordenadas.add(l2);
        panelCoordenadas.add(new JLabel());
        panelCoordenadas.add(xLabel);
        panelCoordenadas.add(campoX);
        panelCoordenadas.add(yLabel);
        panelCoordenadas.add(campoY);

        JButton atacar = new JButton("Atacar");
        atacar.addActionListener(e -> {
            String valorX = campoX.getText();
            String valorY = campoY.getText();

            try {
                int x = Integer.parseInt(valorX);
                int y = Integer.parseInt(valorY);

                Posicion posicionObjetivo = new Posicion(x, y);

                if (!posicionObjetivo.comprobarDentroDeDistancia(superviviente.getPosicion(), arma.getAlcance())) {
                    JOptionPane.showMessageDialog(dialogo, "El objetivo no está dentro del alcance del arma.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else {

                    int n = arma.getNumeroDados();
                    int[] nd = new int[n];
                    Ataque ataque = new Ataque(nd, "");

                    ArrayList<EntidadActivable> entidades = new ArrayList<>(juego.getZombies());
                    ArrayList<Zombie> zombiesEliminados =superviviente.resolverAtaque(arma, posicionObjetivo, entidades,ataque);
                    //Guardar el ataque resultado
                    StringBuilder stringBuilder = new StringBuilder("Se ha eliminado a los Zombies: \n");
                    for (Zombie zombie1 : zombiesEliminados) {
                        stringBuilder.append(zombie1.toString()).append("\n");
                    }
                    ataque.setResultado(stringBuilder.toString());
                    juego.getAtaques().setAtaque(ataque);

                    ArrayList<EntidadActivable> entidadesZombie = new ArrayList<>(zombiesEliminados);
                    superviviente.atacar(entidadesZombie);

                    juego.getZombies().removeAll(zombiesEliminados);
                    superviviente.menosUnaAccion();
                    }
                dialogo.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialogo, "Las coordenadas deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelar = new JButton("Cancelar");
        cancelar.addActionListener(e -> dialogo.dispose());

        JPanel panelBotones = new JPanel();
        panelBotones.add(atacar);
        panelBotones.add(cancelar);

        dialogo.getContentPane().removeAll();
        dialogo.add(panelCoordenadas, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.revalidate();
        dialogo.repaint();
    }

    private void ataqueZombie(Zombie zombie) {

        ArrayList<Superviviente> supervivientes = new ArrayList<>(Arrays.asList(juego.getSupervivientes()));
        // Comprobar si hay supervivientes en la misma casilla que el zombie
        if (!zombie.getSupervivienteEnMismaCasilla(supervivientes).isEmpty()) {
            // Determinar el objetivo al que el zombie se dirigirá
            Superviviente objetivo = zombie.getSupervivienteAlQueDirigirse(supervivientes);
            if (objetivo != null) {

                ArrayList<EntidadActivable> objetivoList = new ArrayList<>();
                objetivoList.add(objetivo);

                // El zombie ataca al objetivo
                zombie.atacar(objetivoList);

                // Convertir las entidades activables a supervivientes
                ArrayList<Superviviente> supervivientesHeridos = zombie.pasarEntidadesASupervivientes(objetivoList);

                // Actualizar las heridas del superviviente herido
                for (Superviviente superviviente : juego.getSupervivientes()) {
                    if (superviviente.equals(supervivientesHeridos.get(0))) {
                        superviviente.anadirHeridas1();
                        superviviente.anadirAtaqueRecibido(zombie);
                    }
                }
                // Reducir una acción al zombie
                zombie.menosUnaAccion();
            }
        }
    }

}
