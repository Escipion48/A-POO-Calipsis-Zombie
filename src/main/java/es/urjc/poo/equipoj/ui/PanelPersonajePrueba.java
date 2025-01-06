package es.urjc.poo.equipoj.ui;

import es.urjc.poo.equipoj.entidades.*;
import es.urjc.poo.equipoj.io.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PanelPersonajePrueba extends JPanel{
    final Juego juego;
    private JPanel panelSupervivienteEntero;
    private JTextPane panelDeTexto;
    private JButton crearSuperviviente;
    private JButton empezar;
    private Posicion posicionObjetivo;

    public PanelPersonajePrueba(JTextPane panelDeTexto, Juego juego, Posicion posicionObjetivo){
        this.panelDeTexto = panelDeTexto;
        this.juego = juego;
        this.posicionObjetivo = posicionObjetivo;
        initUI();
    }

    /**
     * initUI tiene los botones principales y llama a CrearSupervivientes
     */
    private void initUI(){
        //iniciar panel general
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        // Panel de personajes
        panelSupervivienteEntero = new JPanel(new BorderLayout());
        crearSuperviviente = new JButton("CREAR SUPERVIVIENTES");
        crearSuperviviente.addActionListener(e -> JDialogoCrearPersonaje());
        panelSupervivienteEntero.add(crearSuperviviente, BorderLayout.CENTER);

        // Inicializamos el array de supervivientes
        Superviviente[] superviviente = new Superviviente[4];
        for(int i = 0; i < 4; i++){
            superviviente[i] = new Superviviente();
        }
        juego.setSupervivientes(superviviente);

        JPanel contenedorDeBotones = new JPanel();
        contenedorDeBotones.setBackground(Color.WHITE);
        contenedorDeBotones.setLayout(new GridLayout(4, 1, 10, 10));

        JButton siguienteRonda = new JButton("Siguiente Ronda");
        JButton terminar = new JButton("Terminar");//falta accion
        JButton guardar = new JButton("Guardar");//falta accion

        contenedorDeBotones.add(siguienteRonda);
        contenedorDeBotones.add(terminar);
        contenedorDeBotones.add(guardar);

        // Botón empezar
        empezar = new JButton("Empezar Ronda");
        empezar.addActionListener(e->{
            panelDeTexto.setText(panelDeTexto.getText() + "Empezando la 1º ronda...\n");
            JDialogTurnoSuperviviente();
            TurnoZombie();
            remove(empezar);
            add(contenedorDeBotones, BorderLayout.SOUTH);
            revalidate();
            repaint();

        });


        siguienteRonda.addActionListener(e->{
            panelDeTexto.setText(panelDeTexto.getText() + "Empezando la siguiente ronda...\n");
            JDialogTurnoSuperviviente();
            TurnoZombie();
        });

        terminar.addActionListener(e->Terminar());

        guardar.addActionListener(e->Guardar());


        // Agregar componentes
        add(panelSupervivienteEntero, BorderLayout.CENTER);
    }

    /**
     * JDialogoCrearPersonaje es un JDialog en la que se crea supervivientes al introducir los nombres
     * y llama a actualizarSupervivienteUI para crear los botenes de informacion
     */
    private void JDialogoCrearPersonaje(){
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

            if (nombre1.isEmpty() || nombre2.isEmpty() || nombre3.isEmpty() || nombre4.isEmpty()){
                JOptionPane.showMessageDialog(dialogo, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                // Asignar los nombres a los supervivientes
                juego.getSuperviviente(0).setNombre(nombre1);
                juego.getSuperviviente(1).setNombre(nombre2);
                juego.getSuperviviente(2).setNombre(nombre3);
                juego.getSuperviviente(3).setNombre(nombre4);
                // Actualizar la interfaz con los nuevos nombres


                panelSupervivienteEntero.remove(crearSuperviviente); // Quitar el botón de creación
                panelBotonesDeInformacion(); // Reconstruir la interfaz
                add(empezar, BorderLayout.SOUTH);

                revalidate();
                repaint();

                for(Superviviente s : juego.getSupervivientes()){
                    panelDeTexto.setText(panelDeTexto.getText() + "Se ha creado un superviviente: " + s.getNombre() + "\n");
                }

            }
            dialogo.dispose();
        });

        // Añadir los componentes al dialogo
        dialogo.add(panelContenido, BorderLayout.CENTER);
        dialogo.add(botonConfirmar, BorderLayout.SOUTH);

        // Mostrar el dialogo
        dialogo.setVisible(true);
    }

    /**
     * actualizarUI es una funcion que va actualizando toda la información de los botones
     * está en cada JDialog que modifique la información
     */
    private void panelBotonesDeInformacion(){

        panelSupervivienteEntero.removeAll();
        panelSupervivienteEntero.setLayout(new BorderLayout(10, 10));

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
        panelSuperviviente1.setLayout(new GridLayout(7, 1, 10, 10));
        panelSuperviviente2.setLayout(new GridLayout(7, 1, 10, 10));
        panelSuperviviente3.setLayout(new GridLayout(7, 1, 10, 10));
        panelSuperviviente4.setLayout(new GridLayout(7, 1, 10, 10));

        //Ponerlos de color blanco
        panelSuperviviente1.setBackground(Color.WHITE);
        panelSuperviviente2.setBackground(Color.WHITE);
        panelSuperviviente3.setBackground(Color.WHITE);
        panelSuperviviente4.setBackground(Color.WHITE);

        //Añadir los paneles al panel grande para juntarlos
        panelSuperviviente.add(panelSuperviviente1);
        panelSuperviviente.add(panelSuperviviente2);
        panelSuperviviente.add(panelSuperviviente3);
        panelSuperviviente.add(panelSuperviviente4);

        //Añadir los personajes a sus corespondientes paneles
        panelSuperviviente1.add(new JLabel(juego.getSuperviviente(0).getNombre()));
        panelSuperviviente2.add(new JLabel(juego.getSuperviviente(1).getNombre()));
        panelSuperviviente3.add(new JLabel(juego.getSuperviviente(2).getNombre()));
        panelSuperviviente4.add(new JLabel(juego.getSuperviviente(3).getNombre()));

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
        posicion1.addActionListener(e -> JDialogPosicion(juego.getSuperviviente(0)));
        posicion2.addActionListener(e -> JDialogPosicion(juego.getSuperviviente(1)));
        posicion3.addActionListener(e -> JDialogPosicion(juego.getSuperviviente(2)));
        posicion4.addActionListener(e -> JDialogPosicion(juego.getSuperviviente(3)));

        //Añadir los botones posicion
        panelSuperviviente1.add(posicion1);
        panelSuperviviente2.add(posicion2);
        panelSuperviviente3.add(posicion3);
        panelSuperviviente4.add(posicion4);

        //Crear botones para crear Equipo
        JButton crear1 = new JButton("Crear");
        JButton crear2 = new JButton("Crear");
        JButton crear3 = new JButton("Crear");
        JButton crear4 = new JButton("Crear");

        //
        crear1.addActionListener(e->JDialogCrear(juego.getSuperviviente(0)));
        crear2.addActionListener(e->JDialogCrear(juego.getSuperviviente(1)));
        crear3.addActionListener(e->JDialogCrear(juego.getSuperviviente(2)));
        crear4.addActionListener(e->JDialogCrear(juego.getSuperviviente(3)));

        //
        panelSuperviviente1.add(crear1);
        panelSuperviviente2.add(crear2);
        panelSuperviviente3.add(crear3);
        panelSuperviviente4.add(crear4);


        //Boton de la posicion de los zombies
        JButton posicionZombies = new JButton("Posicion Zombies");
        JButton ataques = new JButton("Ataques");
        JButton crearZombie = new JButton("Crear Zombie");
        posicionZombies.addActionListener(e -> JDialogVerPosicionZombies());
        ataques.addActionListener(e -> JDialogAtaquesHechos());
        crearZombie.addActionListener(e->JDialogCrearZombie());


        JPanel panelArriba = new JPanel();
        panelArriba.setLayout(new GridLayout(1, 3, 10, 10));
        panelArriba.add(posicionZombies);
        panelArriba.add(ataques);
        panelArriba.add(crearZombie);

        panelSupervivienteEntero.add(panelArriba, BorderLayout.NORTH);
        panelSupervivienteEntero.add(panelSuperviviente, BorderLayout.CENTER);

        // Actualizar la interfaz gráfica
        revalidate();
        repaint();
    }

    /**
     * JDialogVerInventario permite ver el equipo que tiene el superviviente en el inventario
     */
    private void JDialogVerInventario(Superviviente superviviente){
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
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
        for(Equipo e : superviviente.getInventario()){
            try{
                contenidoDelInventario.add(new JLabel(e.toString()));
            }catch (Exception exception){
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

    /**
     * JDialogVerArmasActivas permite ver las armas activas que tiene el superviviente
     */
    private void JDialogVerArmasActivas(Superviviente superviviente){
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        String titulo = "Armas activas de " + superviviente.getNombre();
        JDialog dialogo = new JDialog(parentWindow, titulo, Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setSize(600, 300);
        dialogo.setLocationRelativeTo(parentWindow);
        dialogo.setLayout(new BorderLayout(10, 10));

        JPanel contenidoDeArmasActivas = new JPanel();
        contenidoDeArmasActivas.setBorder(new EmptyBorder(10, 10, 10, 10));
        contenidoDeArmasActivas.setLayout(new GridLayout(2, 1, 10, 10));
        contenidoDeArmasActivas.setBackground(Color.WHITE);

        for(Arma a : superviviente.getArmasActivas()){
            try{
                contenidoDeArmasActivas.add(new JLabel(a.toString()));
            }catch (Exception exception){
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

    /**
     * JDialogVerZombiesEliminados es una lista de los zombies eliminados
     */
    private void JDialogVerZombiesEliminados(Superviviente superviviente){
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

        if(superviviente.getZombiesEliminados().isEmpty()){
            panelDeTextoDeZombiesEliminados.setText("No se ha eliminado a ningún zombie\n");
        }
        for(Zombie z : superviviente.getZombiesEliminados()){
            panelDeTextoDeZombiesEliminados.setText(panelDeTextoDeZombiesEliminados.getText() + z.toString() + "\n");
        }
        JButton aceptar = new JButton("Aceptar");
        aceptar.addActionListener(e -> {
            dialogo.dispose();
        });
        dialogo.add(jScrollPane, BorderLayout.CENTER);
        dialogo.add(aceptar, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }

    /**
     * JDialogVerPosicionZombies permite ver la posicion de los zombies
     */
    private void JDialogVerPosicionZombies(){
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
        for(Zombie z : juego.getZombies()){
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

    /**
     * JDialogAtaquesHechos recopila los ataques hechos
     */
    private void JDialogAtaquesHechos(){
        Window parentWindow = SwingUtilities.getWindowAncestor(this);

        JDialog dialogo = new JDialog(parentWindow, "Ataques Realizados", JDialog.ModalityType.APPLICATION_MODAL);
        dialogo.setLayout(new BorderLayout(10, 10));
        dialogo.setBackground(Color.white);
        dialogo.setSize(500, 300);
        dialogo.setLocationRelativeTo(null);

        JPanel panelTextoAtaques = new JPanel();
        panelTextoAtaques.setLayout(new BorderLayout(10, 10));
        panelTextoAtaques.setBackground(Color.white);
        panelTextoAtaques.setSize(400, 300);

        JTextPane panelTexto = new JTextPane();
        panelTexto.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(panelTexto);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        try{
            StringBuilder stringBuilder = new StringBuilder("Se ha realizado: ");
            for(Ataque ataque : juego.getAtaques().getAtaques()){
                stringBuilder.append(ataque).append("\n");
            }
            panelTexto.setText(stringBuilder.toString());
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

    /**
     * JDialogAtaqueZombiesRecibidos muestra que zombies nos a atacado
     */
    private void JDialogAtaqueZombiesRecibidos(Superviviente superviviente){
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
        if(superviviente.getAtaquesRecibidos().isEmpty()){
            panelTextoDeAtaqueZombiesRecibidos.setText("No se ha recibido ningún ataque\n");
        }else{
            StringBuilder texto = new StringBuilder();
            for (Zombie z : superviviente.getAtaquesRecibidos()){
                texto.append(z.toString()).append("\n");
            }
            panelTextoDeAtaqueZombiesRecibidos.setText(texto.toString());
        }

        JButton aceptar = new JButton("Aceptar");
        aceptar.addActionListener(e -> dialogo.dispose());


        dialogo.add(new JScrollPane(panelTextoDeAtaqueZombiesRecibidos), BorderLayout.CENTER);
        dialogo.add(aceptar, BorderLayout.SOUTH);

        dialogo.setVisible(true);
    }

    /**
     * JDialogPosicion permite ver la posicion en la que está el superviviente
     */
    private void JDialogPosicion(Superviviente superviviente){
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        String mensaje = "La posicion de " + superviviente.getNombre() + " es " + superviviente.getPosicion();
        JOptionPane.showMessageDialog(parentWindow, mensaje, "Posicion", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * JDialogTurnosuperviviente es un JDialog que contiene botones que son las acciones que puede realizar los supervivientes
     */
    private void JDialogTurnoSuperviviente(){
        Window parentWindow = SwingUtilities.getWindowAncestor(this);

        for (Superviviente superviviente : juego.getSupervivientes()){
            superviviente.setAcciones(3);
            while (superviviente.getAcciones() > 0){
                String titulo = "Tueno de " + superviviente.getNombre();

                JDialog dialogo = new JDialog(parentWindow, titulo, Dialog.ModalityType.APPLICATION_MODAL);
                dialogo.setLocationRelativeTo(null);
                dialogo.setLayout(new BorderLayout(10, 10));

                dialogo.setSize(500, 300);


                JPanel panelTurnoSuperviviente = new JPanel();
                panelTurnoSuperviviente.setLayout(new GridLayout(6, 1, 10, 10));

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
                eliminarItemDelInventerio.addActionListener(e -> {
                    JDialogEliminarItemDelInventerio(superviviente);
                    dialogo.dispose();
                });
                cambiarArmaActiva.addActionListener(e -> {
                    JDialogCambiarArmaActiva(superviviente);
                    dialogo.dispose();
                });
                moverse.addActionListener(e -> {
                    JDialogMoverse(superviviente);
                    dialogo.dispose();
                });
                buscar.addActionListener(e -> {
                    JDialogResultadoBuscar(superviviente);
                    dialogo.dispose();
                });
                atacar.addActionListener(e -> {
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
                dialogo.add(panelTurnoSuperviviente, BorderLayout.CENTER);

                JLabel mensaje = new JLabel("Acciones restantes: " + superviviente.getAcciones());
                mensaje.setHorizontalAlignment(SwingConstants.CENTER);
                dialogo.add(mensaje, BorderLayout.NORTH);

                Victoria();

                dialogo.setVisible(true);


            }
        }

    }

    /**
     * NoHacerNada es una accion del superviviente que pone todas sus acciones a 0 en esta ronda
     */
    private void NoHacerNada(Superviviente superviviente) {
        superviviente.setAcciones(0);
    }

    /**
     * JDialogEliminarItemDelInventario es una accion del superviviente, se selecciona un Equipo del inventario y se elimina
     * al darle aceptar
     */
    private void JDialogEliminarItemDelInventerio(Superviviente superviviente){
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

        for(Equipo item : superviviente.getInventario()){
            if(item != null){ // Crear botón solo si el ítem no es null
                JRadioButton botonItem = new JRadioButton(item.getNombre());
                itemInventario.add(botonItem);
                contenidoDelInventario.add(botonItem);
                botonItem.addActionListener(e -> Item[0] = item);
            }
        }

        JButton aceptar = new JButton("Aceptar");
        aceptar.addActionListener(e -> {
            if(Item[0] == null){
                JOptionPane.showMessageDialog(dialogo, "Debe seleccionar un ítem para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Eliminar ítem del inventario
            Equipo equipo = Item[0];
            superviviente.eliminarItemInventario(equipo);

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

    /**
     * JDialogCombiarArmaActiva accion del superviviente para poner un arma del inventario como arma activa
     */
    private void JDialogCambiarArmaActiva(Superviviente superviviente){
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

        for(int i = 0; i < superviviente.getArmasActivas().length; i++){
            Arma arma = superviviente.getArmaActiva(i);
            if (arma != null){//solo crea botones si no son nulos
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

        for(int j = 0; j < superviviente.getInventario().length; j++){
            if (superviviente.getInventario(j) instanceof Arma ){
                Arma arma = (Arma) superviviente.getInventario(j);
                if(!superviviente.estaActiva(arma)){
                JRadioButton botonArma = new JRadioButton(arma.getNombre());
                botonArma.addActionListener(e -> armaInventarioSeleccionada[0] = arma);
                grupoInventario.add(botonArma);
                panelInventario.add(botonArma);
            }
        }
        }
        panelPrincipal.add(panelInventario);

        dialogo.add(panelPrincipal, BorderLayout.CENTER);

        // Panel de botones
        JButton aceptar = new JButton("Aceptar");
        aceptar.addActionListener(e -> {
            // Verificar las condiciones de selección según el estado actual
            int ArmasActivas = grupoArmasActivas.getButtonCount();

            if(ArmasActivas == 2){
                if(armaActivaSeleccionada[0] == null){
                    JOptionPane.showMessageDialog(dialogo, "Debe seleccionar un arma activa.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(armaInventarioSeleccionada[0] == null){
                    JOptionPane.showMessageDialog(dialogo, "Debe seleccionar un arma del inventario.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Cambiar el arma activa seleccionada
                superviviente.cambiarArmaActiva(armaActivaSeleccionada[0],armaInventarioSeleccionada[0]);
            } else if (ArmasActivas == 1){
                if (armaActivaSeleccionada[0] == null){
                    for (int i = 0; i < superviviente.getArmasActivas().length; i++){
                            if (armaInventarioSeleccionada[0] == null){
                                JOptionPane.showMessageDialog(dialogo, "Debe seleccionar un arma del inventario.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            superviviente.cambiarArmaActiva(null,armaInventarioSeleccionada[0]);
                            break;

                    }
                }else{
                    superviviente.cambiarArmaActiva(armaActivaSeleccionada[0],armaInventarioSeleccionada[0]);
                }
            }else{
                // No hay armas activas
                if (armaInventarioSeleccionada[0] == null){
                    JOptionPane.showMessageDialog(dialogo, "Debe seleccionar un arma del inventario.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for (int i = 0; i < superviviente.getArmasActivas().length; i++){
                    if (superviviente.getArmaActiva(i) == null){
                        superviviente.cambiarArmaActiva(null,armaInventarioSeleccionada[0]);
                        break;
                    }
                }
            }

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

    private void JDialogMoverse(Superviviente superviviente){
        Window parentWindow = SwingUtilities.getWindowAncestor(this);


        JDialog dialogo = new JDialog(parentWindow, "Moverse", Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setSize(400, 300);
        dialogo.setLayout(new BorderLayout(10, 10));
        dialogo.setLocationRelativeTo(null);

        JPanel panelCoordenadas = new JPanel();
        panelCoordenadas.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelCoordenadas.setLayout(new GridLayout(3, 2, 10, 10));

        //Crear los componentes
        JLabel l1 = new JLabel("Posicion actual: " + superviviente.getPosicion());
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
        aceptar.addActionListener(e-> {
            int x, y;
            try {
                x = Integer.parseInt(jt1.getText());
                y = Integer.parseInt(jt2.getText());
            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(dialogo, "Por favor, introduce números válidos para las coordenadas.", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Posicion posicion = new Posicion(x, y);
            ArrayList<EntidadActivable> entidades = new ArrayList<>(juego.getZombies());
            if(superviviente.getPosicion().comprobarAdyacente(posicion) && (superviviente.getAcciones() - superviviente.calcularNumeroAccinesPorMoverse(entidades) > 0)){
                superviviente.moverse(entidades);
                superviviente.setPosicion(posicion);
                panelDeTexto.setText(panelDeTexto.getText()+"\n"+superviviente.getNombre()+" se ha movido a "+ superviviente.getPosicion());
                dialogo.dispose();
            } else if (!superviviente.getPosicion().comprobarAdyacente(posicion)){
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

    /**
     * JDialogResultadoBuscar muestra el resultado de buscar
     */
    private void JDialogResultadoBuscar(Superviviente superviviente) {
        //Comprueba de que si la casilla ya se a buscado y si hay o no Equipo
        int numeroObjetoInventerio = superviviente.calcularNumeroObjetosInventario();
        if(superviviente.buscar(juego.getTablero().getCasilla(superviviente.getPosicion()))){
            if (numeroObjetoInventerio < superviviente.calcularNumeroObjetosInventario()){
                JDialogBuscarTrue(superviviente);
            } else{
                JDialogBuscarNada();
            }
        } else{
            JDialogBuscarFalse();
        }
    }

    /**
     * JDialgoBuscarFalse se muestra si ya se a buscado en esa casilla o si tiene el inventario lleno
     */
    private void JDialogBuscarFalse(){
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        JOptionPane.showMessageDialog(parentWindow, "Ya se ha bucado en esta casilla y/o tiene el inventario lleno", "ERRPR", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * JDilogbuscarNada se muestra si se a podido buscar pero no se a encontrado noda
     */
    private void JDialogBuscarNada(){
        Window parentWindow = SwingUtilities.getWindowAncestor(this);

        JOptionPane.showMessageDialog(parentWindow, "No se ha encontrado nada", "Buscar", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * JDialogBuscarTrue muestra el equipo encontrado
     */
    private void JDialogBuscarTrue(Superviviente superviviente){
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        JOptionPane.showMessageDialog(parentWindow, "Se ha encontrado :" + superviviente.getInventario(superviviente.calcularNumeroObjetosInventario() - 1), "Objeto encontrado", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * JDialogAtacar es una accion en la que se selecciona un arma activa, y llama a mostrarPanelCoordenadas
     */
    private void JDialogAtacar(Superviviente superviviente){
        Window parentWindow = SwingUtilities.getWindowAncestor(this);

        JDialog dialogo = new JDialog(parentWindow, "Atacar", Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setSize(1000, 300);
        dialogo.setLayout(new BorderLayout(10, 10));
        dialogo.setLocationRelativeTo(null);

        // Panel para seleccionar el arma
        JPanel panelSeleccionArma = new JPanel(new GridLayout(0, 1, 10, 10));
        panelSeleccionArma.setBorder(BorderFactory.createTitledBorder("Armas Activas"));

        ButtonGroup armasActivas = new ButtonGroup();
        final Arma[] armaSeleccionada = new Arma[1];

        for (Arma a : superviviente.getArmasActivas()){
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

        aceptar.addActionListener(e-> {
            if (armaSeleccionada[0] == null){
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

    /**
     * mostrarPanelCoordenadas es un panel en el que se introduce las coordenadas para atacar y se crea los ataques
     */
    private void mostrarPanelCoordenadas(JDialog dialogo, Arma arma, Superviviente superviviente){
        JPanel panelCoordenadas = new JPanel(new GridLayout(4, 2, 10, 10));
        panelCoordenadas.setBorder(BorderFactory.createTitledBorder("Seleccionar Casilla Objetivo"));
        panelCoordenadas.setBorder(new EmptyBorder(10, 10, 10, 10));

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

                if(!posicionObjetivo.comprobarDentroDeDistancia(superviviente.getPosicion(), arma.getAlcance())){
                    JOptionPane.showMessageDialog(dialogo, "El objetivo no está dentro del alcance del arma.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else{

                    int n = arma.getNumeroDados();
                    int[] nd = new int[n];
                    Ataque ataque = new Ataque(nd, "");

                    ArrayList<EntidadActivable> entidades = new ArrayList<>(juego.getZombies());
                    ArrayList<Zombie> zombiesEliminados = superviviente.resolverAtaque(arma, posicionObjetivo, entidades, ataque);
                    //Guardar el ataque resultado
                    StringBuilder stringBuilder = new StringBuilder("Se ha eliminado a \n");
                    for (Zombie zombie1 : zombiesEliminados){
                        stringBuilder.append(zombie1.toString()).append("\n");
                        for(Superviviente superviviente1 : juego.getSupervivientes()){
                            if(zombie1 instanceof Toxico && zombie1.getPosicion().equals(superviviente1.getPosicion())){
                                superviviente1.recibirAtaque(zombie1);
                                panelDeTexto.setText(panelDeTexto.getText()+"\n"+superviviente1.getNombre()+" ha sido salpicado por un zombie toxico");
                            }
                        }
                    }
                    ataque.setResultado(stringBuilder.toString());
                    juego.getAtaques().setAtaque(ataque);
                    panelDeTexto.setText(panelDeTexto.getText()+"\n"+stringBuilder);

                    ArrayList<EntidadActivable> entidadesZombie = new ArrayList<>(zombiesEliminados);
                    superviviente.atacar(entidadesZombie);

                    juego.getZombies().removeAll(zombiesEliminados);
                    JOptionPane.showMessageDialog(dialogo,"Se a eliminado "+zombiesEliminados.size()+" zombies","Ataque resultado",JOptionPane.INFORMATION_MESSAGE);

                }
                dialogo.dispose();
            }catch(NumberFormatException ex){
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

    /**
     * Turno de los Zombies, si en la pposicion en la que están hay supervivientes, atacan, si no hay, se mueven y
     * al terminar se genera un zombie
     */
    private void TurnoZombie(){
        ArrayList<EntidadActivable> entidades = new ArrayList<>(Arrays.asList(juego.getSupervivientes()));
        for (Zombie z : juego.getZombies()){
            z.activarse();
            do {
                ataqueZombie(z);
                if (z.getActivaciones() == 0) {
                    break;
                }
                z.moverse(entidades);
            } while(z.getActivaciones() > 0);
        }
        juego.anadirZombie();
        Derrota();
    }

    /**
     * ataqueZombie añade una herida al superviviente al que ataca
     */
    private void ataqueZombie(Zombie zombie){

        ArrayList<Superviviente> supervivientes = new ArrayList<>(Arrays.asList(juego.getSupervivientes()));
        // Comprobar si hay supervivientes en la misma casilla que el zombie
        if (!zombie.getSupervivienteEnMismaCasilla(supervivientes).isEmpty()) {
            // Determinar el objetivo al que el zombie se dirigirá
            Superviviente objetivo = zombie.getSupervivienteAlQueDirigirse(supervivientes);
            if (objetivo != null) {
                ArrayList<EntidadActivable> objetivoList = new ArrayList<>();
                objetivoList.add(objetivo);
                //El zombie ataca
                zombie.atacar(objetivoList);
                panelDeTexto.setText(panelDeTexto.getText()+"\n"+objetivo.getNombre()+" ha recibido un ataque");
            }
        }
    }

    private void JDialogCrear(Superviviente superviviente){
        Window parentWindow = SwingUtilities.getWindowAncestor(this);

        JDialog dialogo = new JDialog(parentWindow,"Crear",Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setLayout(new BorderLayout(10,10));
        dialogo.setSize(400,300);
        dialogo.setBackground(Color.WHITE);
        dialogo.setLocationRelativeTo(null);

        JPanel panelCrear = new JPanel();
        panelCrear.setBorder(new EmptyBorder(10,10,10,10));
        panelCrear.setLayout(new GridLayout(1,2,10,10));
        panelCrear.setBackground(Color.WHITE);

        JButton crearArma = new JButton("Crear Arma");
        JButton crearProvision = new JButton("Crear Provision");

        crearArma.addActionListener(e->crearArma(superviviente,dialogo));
        crearProvision.addActionListener(e->crearProvision(superviviente,dialogo));

        panelCrear.add(crearArma);
        panelCrear.add(crearProvision);

        dialogo.add(panelCrear, BorderLayout.CENTER);
        dialogo.setVisible(true);

    }

    private void crearArma(Superviviente superviviente,JDialog dialogo){
        int numero = superviviente.calcularNumeroObjetosInventario();
        if(numero==5){
            JOptionPane.showMessageDialog(dialogo,"Inventerio lleno","ERROR",JOptionPane.ERROR_MESSAGE);
            dialogo.dispose();
        }
        JPanel panelCrearArma = new JPanel();
        panelCrearArma.setBorder(new EmptyBorder(10,10,10,10));
        panelCrearArma.setBorder(BorderFactory.createTitledBorder("Crear Arma"));
        panelCrearArma.setLayout(new GridLayout(5,2,10,10));
        panelCrearArma.setBackground(Color.WHITE);

        JLabel nombre = new JLabel("Nombre");
        JLabel alcance = new JLabel("Alcance");
        JLabel potencia = new JLabel("Potencia");
        JLabel valorExito = new JLabel("Valor Exito");
        JLabel numeroDados = new JLabel("Numero Dados");

        JTextField nombreTexto = new JTextField();
        JTextField alcanceTexto = new JTextField();
        JTextField potenciaTexto = new JTextField();
        JTextField valorExitoTexto = new JTextField();
        JTextField numeroDadosTexto = new JTextField();

        panelCrearArma.add(nombre);
        panelCrearArma.add(nombreTexto);
        panelCrearArma.add(alcance);
        panelCrearArma.add(alcanceTexto);
        panelCrearArma.add(potencia);
        panelCrearArma.add(potenciaTexto);
        panelCrearArma.add(valorExito);
        panelCrearArma.add(valorExitoTexto);
        panelCrearArma.add(numeroDados);
        panelCrearArma.add(numeroDadosTexto);

        JButton aceptar = new JButton("Aceptar");
        aceptar.addActionListener(e->{
            try{
                String nombreArma = nombreTexto.getText();
                int alcanceArma = Integer.parseInt(alcanceTexto.getText());
                int potenciaArma = Integer.parseInt(potenciaTexto.getText());
                int valorExitoArma = Integer.parseInt(valorExitoTexto.getText());
                int numeroDadosArma = Integer.parseInt(numeroDadosTexto.getText());
                Arma arma = new Arma (nombreArma,potenciaArma,alcanceArma,numeroDadosArma,valorExitoArma);
                int posicionInventario = superviviente.calcularNumeroObjetosInventario();
                superviviente.setInventario(arma,posicionInventario);
            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(dialogo, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            dialogo.dispose();
        });

        JButton cancelar = new JButton("Cancelar");
        cancelar.addActionListener(i->dialogo.dispose());

        JPanel panelBotones = new JPanel();
        panelBotones.add(aceptar);
        panelBotones.add(cancelar);
        dialogo.getContentPane().removeAll();
        dialogo.add(panelCrearArma, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.revalidate();
        dialogo.repaint();

    }

    private void crearProvision(Superviviente superviviente,JDialog dialogo){
        int numero = superviviente.calcularNumeroObjetosInventario();
        if(numero==5){
            JOptionPane.showMessageDialog(dialogo,"Inventerio lleno","ERROR",JOptionPane.ERROR_MESSAGE);
        dialogo.dispose();
        }
        JPanel panelCrearProvision = new JPanel();
        panelCrearProvision.setBorder(new EmptyBorder(10,10,10,10));
        panelCrearProvision.setBorder(BorderFactory.createTitledBorder("Crear Provision"));
        panelCrearProvision.setLayout(new GridLayout(6,2,10,10));
        panelCrearProvision.setBackground(Color.WHITE);

        JLabel nombre = new JLabel("Nombre");
        JLabel kcal = new JLabel("Kcal");
        JLabel caducidadD = new JLabel("Dia");
        JLabel caducidadM = new JLabel("Mes");
        JLabel caducidadA = new JLabel("Año");
        JLabel tipo = new JLabel("Tipo");

        JTextField nombreTexto = new JTextField();

        SpinnerNumberModel Skcal = new SpinnerNumberModel(1,1,10000,1);
        SpinnerNumberModel SDia = new SpinnerNumberModel(1,1,31,1);
        SpinnerNumberModel SMes = new SpinnerNumberModel(1,1,12,1);
        SpinnerNumberModel SAno = new SpinnerNumberModel(1,1,10000,1);
        SpinnerNumberModel STipo = new SpinnerNumberModel(0,0,1,1);
        JSpinner jkal = new JSpinner(Skcal);
        JSpinner jdia = new JSpinner(SDia);
        JSpinner jmes = new JSpinner(SMes);
        JSpinner jano = new JSpinner(SAno);
        JSpinner jtipo = new JSpinner(STipo);


        panelCrearProvision.add(nombre);
        panelCrearProvision.add(nombreTexto);
        panelCrearProvision.add(kcal);
        panelCrearProvision.add(jkal);
        panelCrearProvision.add(caducidadD);
        panelCrearProvision.add(jdia);
        panelCrearProvision.add(caducidadM);
        panelCrearProvision.add(jmes);
        panelCrearProvision.add(caducidadA);
        panelCrearProvision.add(jano);
        panelCrearProvision.add(tipo);
        panelCrearProvision.add(jtipo);

        JButton aceptar = new JButton("Aceptar");
        aceptar.addActionListener(e->{
            try{
                String nombreProvision = nombreTexto.getText();
                int kcalT = (int)jkal.getValue();
                int dia = (int)jdia.getValue();
                int mes =(int)jmes.getValue();
                int ano = (int)jano.getValue();
                int tipoI = (int)jtipo.getValue();
                int[]fecha = new int[3];
                fecha[0]= dia;
                fecha[1]= mes;
                fecha[2]= ano;
                boolean tipoB= false;
                   if(tipoI == 1){
                      tipoB= true;
                    }
                    Provision provision = new Provision(nombreProvision,kcalT,fecha,tipoB);
                    int posicionInventario = superviviente.calcularNumeroObjetosInventario();
                    superviviente.setInventario(provision,posicionInventario);

            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(dialogo, "Introduzca valores adecuados", "Error", JOptionPane.ERROR_MESSAGE);
            }

            dialogo.dispose();
        });

        JButton cancelar = new JButton("Cancelar");
        cancelar.addActionListener(i->dialogo.dispose());

        JPanel panelBotones = new JPanel();
        panelBotones.add(aceptar);
        panelBotones.add(cancelar);
        dialogo.getContentPane().removeAll();
        dialogo.add(panelCrearProvision, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.revalidate();
        dialogo.repaint();
    }

    private void JDialogCrearZombie() {
        Window parentWindow = SwingUtilities.getWindowAncestor(this);

        // Crear el cuadro de diálogo modal
        JDialog dialogo = new JDialog(parentWindow, "Crear Zombie", Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setSize(400, 300);
        dialogo.setBackground(Color.WHITE);
        dialogo.setLayout(new BorderLayout(10, 10));
        dialogo.setLocationRelativeTo(null);

        // Crear el panel principal con un diseño de cuadrícula
        JPanel panelCrearZombie = new JPanel(new GridLayout(4, 2, 10, 10));
        panelCrearZombie.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelCrearZombie.setBackground(Color.WHITE);

        // Etiquetas y campos de entrada
        JLabel lTipo = new JLabel("Tipo");
        JLabel lVariable = new JLabel("Variante");
        JLabel lPosicionX = new JLabel("X");
        JLabel lPosicionY = new JLabel("Y");

        SpinnerNumberModel modeloX = new SpinnerNumberModel(0, 0, juego.getTablero().getDimensiones().getPosicionX(), 1);
        SpinnerNumberModel modeloY = new SpinnerNumberModel(0, 0, juego.getTablero().getDimensiones().getPosicionY(), 1);
        JSpinner spnX = new JSpinner(modeloX);
        JSpinner spnY = new JSpinner(modeloY);

        final TipoZombie []tipoZombie = new TipoZombie[1];
        final int [] variable = new int[1];

        JComboBox <TipoZombie>jComboBoxTipo = new JComboBox<>();
        jComboBoxTipo.addItem(TipoZombie.CAMINANTE);
        jComboBoxTipo.addItem(TipoZombie.CORREDOR);
        jComboBoxTipo.addItem(TipoZombie.ABOMINACION);
        jComboBoxTipo.addActionListener(e->{
            tipoZombie[0] = (TipoZombie) jComboBoxTipo.getSelectedItem();
        });

        JComboBox <String>jComboBoxVariable = new JComboBox<>();
        jComboBoxVariable.addItem("Normal");
        jComboBoxVariable.addItem("Toxico");
        jComboBoxVariable.addItem("Berserker");

        jComboBoxVariable.addActionListener(e->{
            variable[0] = jComboBoxVariable.getSelectedIndex();
        });

        // Agregar componentes al panel
        panelCrearZombie.add(lTipo);
        panelCrearZombie.add(jComboBoxTipo);
        panelCrearZombie.add(lVariable);
        panelCrearZombie.add(jComboBoxVariable);
        panelCrearZombie.add(lPosicionX);
        panelCrearZombie.add(spnX);
        panelCrearZombie.add(lPosicionY);
        panelCrearZombie.add(spnY);

        // Botones
        JButton aceptar = new JButton("Aceptar");
        aceptar.addActionListener(e -> {
            //Obtener la posicion
            try {
                int posX = (int) spnX.getValue();
                int posY = (int) spnY.getValue();
                Posicion posicion = new Posicion(posX, posY);
                if(tipoZombie[0]==null){
                    tipoZombie[0]=TipoZombie.CAMINANTE;
                }

                if (variable[0] == 0) {
                    juego.getZombies().add(new Normal(tipoZombie[0], posicion));
                } else if (variable[0] == 1) {
                    juego.getZombies().add(new Toxico(tipoZombie[0], posicion));
                } else if (variable[0] == 2) {
                    juego.getZombies().add(new Berserker(tipoZombie[0], posicion));
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            dialogo.dispose();
        });

        JButton cancelar = new JButton("Cancelar");
        cancelar.addActionListener(e -> dialogo.dispose());

        // Panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(aceptar);
        panelBotones.add(cancelar);

        // Agregar los paneles al diálogo
        dialogo.add(panelCrearZombie, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);

        // Mostrar el diálogo
        dialogo.setVisible(true);
    }


    private void Derrota(){
        boolean derrota = false;
        Superviviente s1 = new Superviviente();

        for(Superviviente superviviente : juego.getSupervivientes()){
            if (superviviente.getHeridas() >= 2) {
                s1=superviviente;
                derrota = true;
                break;
            }
        }

        if (derrota){
            removeAll(); // Limpiar el panel
            panelDeTexto.setText("");
            setLayout(new BorderLayout());

            JLabel l1 = new JLabel("DERROTA "+s1.getNombre()+" ha muerto", SwingConstants.CENTER);
            l1.setFont(new Font("Arial", Font.BOLD, 30));
            l1.setForeground(Color.RED);
            add(l1, BorderLayout.CENTER);
            revalidate();
            repaint();
            setReiniciar();

        }}


    private void Victoria(){
        boolean victoria = true;

        for(Superviviente superviviente : juego.getSupervivientes()){
            if(!(superviviente.getPosicion().equals(posicionObjetivo))){
                victoria = false;
                break;
            }
            if(!comprobarCaducidad(superviviente)){
                victoria = false;
                break;
            }
        }

        if(victoria){
            System.out.println("Victoria");
            removeAll();
            panelDeTexto.setText("");
            setLayout(new BorderLayout());

            JLabel l1 = new JLabel("Victoria", SwingConstants.CENTER);
            l1.setFont(new Font("Arial", Font.BOLD, 50));
            l1.setForeground(Color.RED);
            add(l1, BorderLayout.CENTER);
            revalidate();
            repaint();
            setReiniciar();
        }
    }

    private void Terminar(){
        removeAll();
        setLayout(new BorderLayout());
        panelDeTexto.setText("");
        JPanel terminar = new JPanel();
        terminar.setLayout(new GridLayout());
        JLabel lterminar = new JLabel("Terminar",SwingConstants.CENTER);
        lterminar.setFont(new Font("Arial", Font.BOLD, 50));
        terminar.add(lterminar);
        add(terminar, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void Guardar(){
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        JDialog dialogo = new JDialog(parentWindow,"Guardar",Dialog.ModalityType.APPLICATION_MODAL);

        dialogo.setLayout(new BorderLayout(10,10));
        dialogo.setSize(400,300);
        dialogo.setBackground(Color.WHITE);
        dialogo.setLocationRelativeTo(null);

        JPanel panelGuardar = new JPanel();
        panelGuardar.setLayout(new GridLayout(1,2,10,10));
        panelGuardar.setBackground(Color.WHITE);
        panelGuardar.setBorder(new EmptyBorder(10,10,10,10));
        JLabel l1 = new JLabel("Introduzca la ruta");
        JTextField textField = new JTextField();

        panelGuardar.add(l1);
        panelGuardar.add(textField);


        dialogo.add(panelGuardar,BorderLayout.CENTER);

        JButton aceptar = new JButton("Aceptar");
        aceptar.addActionListener(e->{
            try{
                if(textField.getText()!=null){
                    IO io = new IO();
                    io.escribirJSON(juego,textField.getText());
                    JOptionPane.showMessageDialog(this,"Se ha guardado correctamente","Partida Guardada",JOptionPane.INFORMATION_MESSAGE);
                    dialogo.dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,"No se ha guardado correctamente","ERROR",JOptionPane.ERROR_MESSAGE);
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

    private boolean comprobarCaducidad(Superviviente superviviente){
        for(Equipo e : superviviente.getInventario()){
            if(e instanceof Provision){
                if(!((Provision) e).caducado()){
                    return true;
                }
            }
        }
        return false;
    }


    private void setReiniciar(){

        // Botón para reiniciar el juego
        JButton reiniciar = new JButton("Reiniciar Juego");
        reiniciar.addActionListener(e -> {
            juego.reiniciar();
            removeAll();
            initUI(); // Reiniciar el panel
            revalidate();
            repaint();
        });

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BorderLayout());
        panelInferior.add(reiniciar,BorderLayout.CENTER);

        add(panelInferior, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }


}

