package es.urjc.poo.equipoj.ui;

import es.urjc.poo.equipoj.entidades.*;
import es.urjc.poo.equipoj.images.LectorImagenes;
import es.urjc.poo.equipoj.images.PanelConFondo;
import es.urjc.poo.equipoj.io.*;
import es.urjc.poo.equipoj.sfx.LectorSonido;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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

        class verInventario extends AbstractAction {
            public verInventario(String nombre, String descripcion, Icon imagen, int indicePersonaje) {
                putValue(Action.NAME, nombre);
                putValue(Action.SHORT_DESCRIPTION, descripcion);
                putValue(Action.SMALL_ICON, imagen);
                putValue("Indice personaje", indicePersonaje);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                LectorSonido.reproducirInventario();

                Superviviente superviviente = juego.getSuperviviente((int)getValue("Indice personaje"));

                JDialog pantallaInventario = new JDialog();
                pantallaInventario.setTitle("Inventario de " + superviviente.getNombre());
                pantallaInventario.setIconImage(((ImageIcon) getValue(Action.SMALL_ICON)).getImage());
                pantallaInventario.setLayout(new GridLayout(5,1,10,10));
                Dimension dimensionesPantalla = Toolkit.getDefaultToolkit().getScreenSize();
                pantallaInventario.setBounds(dimensionesPantalla.width/2,dimensionesPantalla.height/2-100,550,450);
                for(int i = 0; i<5;i++){
                    if(superviviente.getInventario(i)==null){
                        pantallaInventario.add(new JLabel("Espacio "+(i+1) + " del inventario esta vacio"));
                    }
                    else{
                        pantallaInventario.add(new JLabel("Espacio "+(i+1) + ":" +superviviente.getInventario(i).toString()));
                    }
                }

                pantallaInventario.setVisible(true);
            }
        }
        class verArmasActivas extends AbstractAction {
            public verArmasActivas(String nombre, String descripcion, Icon imagen, int indicePersonaje) {
                putValue(Action.NAME, nombre);
                putValue(Action.SHORT_DESCRIPTION, descripcion);
                putValue(Action.SMALL_ICON, imagen);
                putValue("Indice personaje", indicePersonaje);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                LectorSonido.reproducirClick();

                Superviviente superviviente = juego.getSuperviviente((int)getValue("Indice personaje"));

                JDialog pantallaArmasActivas = new JDialog();
                pantallaArmasActivas.setLayout(new GridLayout(2, 1, 10, 10));

                for(int i =0; i<2;i++){

                    if(superviviente.getArmaActiva(i)==null){
                        pantallaArmasActivas.add(new JLabel("No hay arma activa en el hueco: " + (i+1)));
                    }
                    else{
                        pantallaArmasActivas.add(new JLabel("Arma activa en el hueco: " + (i+1)+" "+ superviviente.getArmaActiva(i).getNombre()));
                    }

                }

                pantallaArmasActivas.setIconImage(((ImageIcon) getValue(Action.SMALL_ICON)).getImage());
                Dimension dimensionesPantalla = Toolkit.getDefaultToolkit().getScreenSize();
                pantallaArmasActivas.setBounds(dimensionesPantalla.width/2,dimensionesPantalla.height/2-100,400,450);
                pantallaArmasActivas.setResizable(false);
                pantallaArmasActivas.setVisible(true);

            }
        }
        class mostrarZombiesEliminados extends AbstractAction{
            public mostrarZombiesEliminados(String nombre, String descripcion, Icon imagen, int indicePersonaje) {
                putValue(Action.NAME, nombre);
                putValue(Action.SHORT_DESCRIPTION, descripcion);
                putValue(Action.SMALL_ICON, imagen);
                putValue("Indice personaje", indicePersonaje);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                LectorSonido.reproducirClick();

                Superviviente superviviente = juego.getSuperviviente((int)getValue("Indice personaje"));
                StringBuilder textoZombiesEliminados = new StringBuilder();

                if(superviviente.getZombiesEliminados().isEmpty()){
                    textoZombiesEliminados.append("Todavia "+superviviente.getNombre()+" no ha matado a ningun zombie");
                }
                else{
                    textoZombiesEliminados.append("Zombies eliminados: \n");
                    for(Zombie zombie : superviviente.getZombiesEliminados()){
                        textoZombiesEliminados.append(zombie.toString()+"\n");
                    }
                }
                //Parte interna de la ventana
                JScrollPane paneConAtaques = new JScrollPane(new JTextArea(textoZombiesEliminados.toString()));
                paneConAtaques.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                paneConAtaques.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

                //Personalizando la ventana emergente
                JDialog ventanaConAtaques = new JDialog();
                ventanaConAtaques.setTitle("Zombies eliminados por: "+superviviente.getNombre());
                ventanaConAtaques.setIconImage(((ImageIcon) getValue(Action.SMALL_ICON)).getImage());
                Dimension dimensionesPantalla = Toolkit.getDefaultToolkit().getScreenSize();
                ventanaConAtaques.setBounds(dimensionesPantalla.width/2,dimensionesPantalla.height/2-100,400,450);
                ventanaConAtaques.add(paneConAtaques);
                ventanaConAtaques.setResizable(false);
                ventanaConAtaques.setVisible(true);

            }
        }
        class mostrarAtaqueZombies extends AbstractAction {
            public mostrarAtaqueZombies(String nombre, String descripcion, Icon imagen, int indicePersonaje) {
                putValue(Action.NAME, nombre);
                putValue(Action.SHORT_DESCRIPTION, descripcion);
                putValue(Action.SMALL_ICON, imagen);
                putValue("Indice personaje", indicePersonaje);
            }

            @Override
            public void actionPerformed(ActionEvent e) {

                LectorSonido.reproducirClick();

                Superviviente superviviente = juego.getSuperviviente((int)getValue("Indice personaje"));
                StringBuilder cadenaConAtaques = new StringBuilder();
                if(superviviente.getAtaquesRecibidos().isEmpty()){
                    cadenaConAtaques.append("No ha recibido ataques");
                }
                else{
                    for(Zombie zombie : superviviente.getAtaquesRecibidos()){
                        cadenaConAtaques.append("Sufrio 1 mordedura de " + zombie.toString()+"\n");

                    }
                }
                JOptionPane.showMessageDialog(null,cadenaConAtaques.toString(),"Ataques que ha sufrido "+superviviente.getNombre(),JOptionPane.INFORMATION_MESSAGE,(Icon) getValue(Action.SMALL_ICON));
                LectorSonido.reproducirRespiroAlivio();
            }
        }
        class mostrarPosicionPersonaje extends AbstractAction {
            public mostrarPosicionPersonaje(String nombre, String descripcion, Icon imagen, int indicePersonaje) {
                putValue(Action.NAME, nombre);
                putValue(Action.SHORT_DESCRIPTION, descripcion);
                putValue(Action.SMALL_ICON, imagen);
                putValue("Indice personaje", indicePersonaje);
            }

            @Override
            public void actionPerformed(ActionEvent e) {

                LectorSonido.reproducirClick();

                Superviviente superviviente = juego.getSuperviviente((int)getValue("Indice personaje"));
                JOptionPane.showMessageDialog(null,superviviente.getPosicion().toString(),"Posicion de "+superviviente.getNombre(),JOptionPane.INFORMATION_MESSAGE,(Icon) getValue(Action.SMALL_ICON));

            }
        }
        class crearEquipo extends AbstractAction{
            crearEquipo(String nombre, String descripcion, Icon imagen, int indicePersonaje){
                putValue(NAME,nombre);
                putValue(SHORT_DESCRIPTION,descripcion);
                putValue(SMALL_ICON,imagen);
                putValue("Indice personaje",indicePersonaje);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                LectorSonido.reproducirClick();

                JDialog panelCrearEquipo = new JDialog();
                panelCrearEquipo.setTitle(getValue(NAME).toString());
                panelCrearEquipo.setIconImage(((ImageIcon)getValue(SMALL_ICON)).getImage());
                Dimension dimensionesPantalla = Toolkit.getDefaultToolkit().getScreenSize();
                panelCrearEquipo.setBounds(dimensionesPantalla.width/2,dimensionesPantalla.height/2-100,400,350);
                panelCrearEquipo.setResizable(false);
                panelCrearEquipo.setLayout(new GridLayout(1,2));

                //Añadimos los dos botones para que se pueda elejir que tipo de equipo crear
                panelCrearEquipo.add(new JButton(new CrearArma("Crear Arma", (int)getValue("Indice personaje"))));
                panelCrearEquipo.add(new JButton(new CrearProvision("Crear Provision", (int)getValue("Indice personaje"))));

                panelCrearEquipo.setVisible(true);
            }

            class CrearArma extends AbstractAction {

                //Tenemos que declarar previamente los elementos que vayamos a manipular cuando ocurran acciones
                JButton aceptar = new JButton("Aceptar");
                JButton cancelar = new JButton("Cancelar");

                JTextField nombreTexto = new JTextField();
                JTextField alcanceTexto = new JTextField();
                JTextField potenciaTexto = new JTextField();
                JTextField valorExitoTexto  = new JTextField();
                JTextField numeroDadosTexto = new JTextField();

                public CrearArma(String nombre, int indicePersonaje) {
                    putValue(NAME, nombre);
                    putValue("Indice personaje", indicePersonaje);
                }


                @Override
                public void actionPerformed(ActionEvent e) {
                    LectorSonido.reproducirClick();

                    Superviviente superviviente = juego.getSuperviviente((int)getValue("Indice personaje"));
                    if(superviviente.calcularNumeroObjetosInventario()==5){
                        JOptionPane.showMessageDialog(null,"Inventario lleno", "Error Inventario lleno", JOptionPane.ERROR_MESSAGE);
                    }
                    else{

                        //Crear panel donde introduciremos los elementos
                        JDialog panelCrearArma = new JDialog();
                        panelCrearArma.setTitle("Crear Arma");
                        Dimension dimensionesPantalla = Toolkit.getDefaultToolkit().getScreenSize();
                        panelCrearArma.setBounds(dimensionesPantalla.width/2,dimensionesPantalla.height/2-100,450,350);
                        panelCrearArma.setLayout(new GridLayout(6,2,10,10));
                        panelCrearArma.setResizable(false);
                        panelCrearArma.setVisible(true);

                        //Creamos los Jlabel para que el usuario sepa que hace cada campo
                        JLabel nombre = new JLabel("Nombre");
                        JLabel alcance = new JLabel("Alcance");
                        JLabel potencia = new JLabel("Potencia");
                        JLabel valorExito = new JLabel("Valor éxito");
                        JLabel numeroDados = new JLabel("Numero Dados");



                        //Como ya hemos declarado los Jtextfield solo queda añadirle los oyentes
                        nombreTexto.getDocument().addDocumentListener(new DocumentListener(){
                            @Override
                            public void insertUpdate(DocumentEvent e) {
                                String texto = nombreTexto.getText().trim();

                                if(texto.length()==0){
                                    nombreTexto.setBackground(Color.RED);
                                }
                                else {
                                    nombreTexto.setBackground(Color.WHITE);
                                }

                            }

                            @Override
                            public void removeUpdate(DocumentEvent e) {
                                String texto = nombreTexto.getText().trim();

                                if(texto.length()==0){
                                    nombreTexto.setBackground(Color.RED);
                                }
                                else {
                                    nombreTexto.setBackground(Color.WHITE);
                                }
                            }

                            @Override
                            public void changedUpdate(DocumentEvent e) {
                                String texto = nombreTexto.getText().trim();

                                if(texto.length()==0){
                                    nombreTexto.setBackground(Color.RED);

                                }
                                else{
                                    nombreTexto.setBackground(Color.WHITE);

                                }
                            }
                        });
                        nombreTexto.setBackground(Color.RED);
                        alcanceTexto.getDocument().addDocumentListener(new DocumentListener(){

                            @Override
                            public void insertUpdate(DocumentEvent e) {
                                String texto = alcanceTexto.getText().trim();
                                if(texto.length()==0){
                                    alcanceTexto.setBackground(Color.RED);
                                }
                                else if(texto.matches("\\d+")){
                                    int valorAlcance= Integer.parseInt(alcanceTexto.getText().trim());
                                    if(valorAlcance<0){
                                        alcanceTexto.setBackground(Color.RED);
                                    }
                                    else{
                                        alcanceTexto.setBackground(Color.WHITE);
                                    }
                                }else{
                                    alcanceTexto.setBackground(Color.RED);
                                }
                            }

                            @Override
                            public void removeUpdate(DocumentEvent e) {
                                String texto = alcanceTexto.getText().trim();
                                if(texto.length()==0){
                                    alcanceTexto.setBackground(Color.RED);
                                }
                                else if(texto.matches("\\d+")){
                                    int valorAlcance= Integer.parseInt(alcanceTexto.getText().trim());
                                    if(valorAlcance<0){
                                        alcanceTexto.setBackground(Color.RED);
                                    }
                                    else{
                                        alcanceTexto.setBackground(Color.WHITE);
                                    }
                                }
                                else{
                                    alcanceTexto.setBackground(Color.RED);
                                }
                            }

                            @Override
                            public void changedUpdate(DocumentEvent e) {
                                String texto = alcanceTexto.getText().trim();
                                if(texto.length()==0){
                                    alcanceTexto.setBackground(Color.RED);
                                }
                                else if(texto.matches("\\d+")){
                                    int valorAlcance= Integer.parseInt(alcanceTexto.getText().trim());
                                    if(valorAlcance<0){
                                        alcanceTexto.setBackground(Color.RED);
                                    }
                                    else{
                                        alcanceTexto.setBackground(Color.WHITE);
                                    }
                                }
                                else{
                                    alcanceTexto.setBackground(Color.RED);
                                }
                            }
                        });
                        alcanceTexto.setBackground(Color.RED);
                        potenciaTexto.getDocument().addDocumentListener(new DocumentListener(){

                            @Override
                            public void insertUpdate(DocumentEvent e) {
                                String texto = potenciaTexto.getText().trim();
                                if(texto.length()==0){
                                    potenciaTexto.setBackground(Color.RED);

                                }
                                else if(texto.matches("\\d+")){
                                    int valorPotencia= Integer.parseInt(potenciaTexto.getText().trim());
                                    if(valorPotencia<0){
                                        potenciaTexto.setBackground(Color.RED);
                                    }
                                    else{
                                        potenciaTexto.setBackground(Color.WHITE);
                                    }
                                }
                                else{
                                    potenciaTexto.setBackground(Color.RED);
                                }
                            }

                            @Override
                            public void removeUpdate(DocumentEvent e) {
                                String texto = potenciaTexto.getText().trim();
                                if(texto.length()==0){
                                    potenciaTexto.setBackground(Color.RED);
                                }
                                else if(texto.matches("\\d+")){
                                    int valorPotencia= Integer.parseInt(potenciaTexto.getText().trim());
                                    if(valorPotencia<0){
                                        potenciaTexto.setBackground(Color.RED);
                                    }
                                    else{
                                        potenciaTexto.setBackground(Color.WHITE);
                                    }
                                }
                                else{
                                    potenciaTexto.setBackground(Color.RED);
                                }
                            }

                            @Override
                            public void changedUpdate(DocumentEvent e) {
                                String texto = potenciaTexto.getText().trim();
                                if(texto.length()==0){
                                    potenciaTexto.setBackground(Color.RED);

                                }
                                else if(texto.matches("\\d+")){
                                    int valorPotencia= Integer.parseInt(potenciaTexto.getText().trim());
                                    if(valorPotencia<0){
                                        potenciaTexto.setBackground(Color.RED);
                                    }
                                    else{
                                        potenciaTexto.setBackground(Color.WHITE);
                                    }
                                }
                                else{
                                    potenciaTexto.setBackground(Color.RED);
                                }
                            }
                        });
                        potenciaTexto.setBackground(Color.RED);
                        valorExitoTexto.getDocument().addDocumentListener(new DocumentListener(){

                            @Override
                            public void insertUpdate(DocumentEvent e) {
                                String texto = valorExitoTexto.getText().trim();
                                if(texto.length()==0){
                                    valorExitoTexto.setBackground(Color.RED);

                                }
                                else if(texto.matches("\\d+")){
                                    int valorExito= Integer.parseInt(valorExitoTexto.getText().trim());
                                    if(valorExito<0){
                                        valorExitoTexto.setBackground(Color.RED);
                                    }
                                    else{
                                        valorExitoTexto.setBackground(Color.WHITE);
                                    }
                                }
                                else {
                                    valorExitoTexto.setBackground(Color.RED);
                                }
                            }

                            @Override
                            public void removeUpdate(DocumentEvent e) {
                                String texto = valorExitoTexto.getText().trim();
                                if(texto.length()==0){
                                    valorExitoTexto.setBackground(Color.RED);

                                }
                                else if(texto.matches("\\d+")){
                                    int valorExito= Integer.parseInt(valorExitoTexto.getText().trim());
                                    if(valorExito<0){
                                        valorExitoTexto.setBackground(Color.RED);
                                    }
                                    else{
                                        valorExitoTexto.setBackground(Color.WHITE);
                                    }
                                }
                                else {
                                    valorExitoTexto.setBackground(Color.RED);
                                }
                            }

                            @Override
                            public void changedUpdate(DocumentEvent e) {
                                String texto = valorExitoTexto.getText().trim();
                                if(texto.length()==0){
                                    valorExitoTexto.setBackground(Color.RED);
                                }
                                else if(texto.matches("\\d+")){
                                    int valorExito= Integer.parseInt(valorExitoTexto.getText().trim());
                                    if(valorExito<0){
                                        valorExitoTexto.setBackground(Color.RED);
                                    }
                                    else{
                                        valorExitoTexto.setBackground(Color.WHITE);
                                    }
                                }
                                else {
                                    valorExitoTexto.setBackground(Color.RED);
                                }
                            }
                        });
                        valorExitoTexto.setBackground(Color.RED);
                        numeroDadosTexto.getDocument().addDocumentListener(new DocumentListener(){

                            @Override
                            public void insertUpdate(DocumentEvent e) {
                                String texto = numeroDadosTexto.getText().trim();
                                if(texto.length()==0){
                                    numeroDadosTexto.setBackground(Color.RED);

                                }
                                else if(texto.matches("\\d+")){
                                    int valorNumeroDados= Integer.parseInt(numeroDadosTexto.getText().trim());
                                    if(valorNumeroDados<0){
                                        numeroDadosTexto.setBackground(Color.RED);
                                    }
                                    else{
                                        numeroDadosTexto.setBackground(Color.WHITE);
                                    }
                                }
                                else{
                                    numeroDadosTexto.setBackground(Color.RED);
                                }
                            }

                            @Override
                            public void removeUpdate(DocumentEvent e) {
                                String texto = numeroDadosTexto.getText().trim();
                                if(texto.length()==0){
                                    numeroDadosTexto.setBackground(Color.RED);

                                }
                                else if(texto.matches("\\d+")){
                                    int valorNumeroDados= Integer.parseInt(numeroDadosTexto.getText().trim());
                                    if(valorNumeroDados<0){
                                        numeroDadosTexto.setBackground(Color.RED);
                                    }
                                    else{
                                        numeroDadosTexto.setBackground(Color.WHITE);
                                    }
                                }
                                else{
                                    numeroDadosTexto.setBackground(Color.RED);
                                }
                            }

                            @Override
                            public void changedUpdate(DocumentEvent e) {
                                String texto = numeroDadosTexto.getText().trim();
                                if(texto.length()==0){
                                    numeroDadosTexto.setBackground(Color.RED);

                                }
                                else if(texto.matches("\\d+")){
                                    int valorNumeroDados= Integer.parseInt(numeroDadosTexto.getText().trim());
                                    if(valorNumeroDados<0){
                                        numeroDadosTexto.setBackground(Color.RED);
                                    }
                                    else{
                                        numeroDadosTexto.setBackground(Color.WHITE);
                                    }
                                }
                                else{
                                    numeroDadosTexto.setBackground(Color.RED);
                                }
                            }
                        });
                        numeroDadosTexto.setBackground(Color.RED);


                        //Finalmente construimos el panel emergente para crear Arma rellenando el Jdialog
                        panelCrearArma.add(nombre); panelCrearArma.add(nombreTexto);
                        panelCrearArma.add(alcance); panelCrearArma.add(alcanceTexto);
                        panelCrearArma.add(potencia); panelCrearArma.add(potenciaTexto);
                        panelCrearArma.add(valorExito); panelCrearArma.add(valorExitoTexto);
                        panelCrearArma.add(numeroDados); panelCrearArma.add(numeroDadosTexto);

                        //Fialmente configuramos los botones de aceptar y cancelar y los metemos en panelCrearArma
                        aceptar.addActionListener(new ActionListener(){

                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(nombreTexto.getBackground() == Color.WHITE && alcanceTexto.getBackground() == Color.WHITE && potenciaTexto.getBackground() == Color.WHITE && valorExitoTexto.getBackground() == Color.WHITE && numeroDadosTexto.getBackground() == Color.WHITE ){
                                    for(int i = 0; i<5;i++){
                                        if(superviviente.getArmaActiva(i)==null){
                                            superviviente.setInventario(new Arma(nombreTexto.getText(),Integer.parseInt(potenciaTexto.getText()),Integer.parseInt(alcanceTexto.getText()),Integer.parseInt(numeroDadosTexto.getText()),Integer.parseInt(valorExitoTexto.getText())),i);
                                            panelCrearArma.dispose();
                                            break;
                                        }
                                    }
                                }
                                else{
                                    JOptionPane.showMessageDialog(null,"Valores no validos en alguno de los campos", "Error valores no validos", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        });
                        cancelar.addActionListener(new ActionListener(){
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                panelCrearArma.dispose();
                            }
                        });

                        panelCrearArma.add(aceptar);
                        panelCrearArma.add(cancelar);
                        panelCrearArma.setVisible(true);
                    }
                }

            }
            class CrearProvision extends AbstractAction{

                //Tenemos que declarar previamente los elementos que vayamos a manipular cuando ocurran acciones
                JButton aceptar = new JButton("Aceptar");
                JButton cancelar = new JButton("Cancelar");

                JTextField nombreTexto = new JTextField();
                JSpinner kcalTexto = new JSpinner();
                JSpinner caducidadTexto = new JSpinner();
                JSpinner tipoTexto = new JSpinner();


                CrearProvision(String nombre, int indicePersonaje){
                    putValue(NAME,nombre);
                    putValue("Indice personaje", indicePersonaje);
                }


                @Override
                public void actionPerformed(ActionEvent e) {
                    LectorSonido.reproducirClick();

                    Superviviente superviviente = juego.getSuperviviente((int)getValue("Indice personaje"));
                    if(superviviente.calcularNumeroObjetosInventario()==5){
                        JOptionPane.showMessageDialog(null,"Inventario lleno", "Error Inventario lleno", JOptionPane.ERROR_MESSAGE);
                    }
                    else{

                        //Crear panel donde introduciremos los elementos
                        JDialog panelCrearProvision = new JDialog();
                        panelCrearProvision.setTitle("Crear Arma");
                        Dimension dimensionesPantalla = Toolkit.getDefaultToolkit().getScreenSize();
                        panelCrearProvision.setBounds(dimensionesPantalla.width/2,dimensionesPantalla.height/2-100,450,350);
                        panelCrearProvision.setLayout(new GridLayout(5,2,10,10));
                        panelCrearProvision.setResizable(false);
                        panelCrearProvision.setVisible(true);

                        //Creamos los Jlabel para que el usuario sepa que hace cada campo
                        JLabel nombre = new JLabel("Nombre");
                        JLabel kcal = new JLabel("Kcal");
                        JLabel caducidad = new JLabel("Caducidad");
                        JLabel tipo = new JLabel("Tipo");

                        //Configuramos las entradas de datos para evitar errores
                        nombreTexto.setBackground(Color.RED);
                        nombreTexto.getDocument().addDocumentListener(new DocumentListener(){
                            @Override
                            public void insertUpdate(DocumentEvent e) {
                                String texto = nombreTexto.getText().trim();

                                if(texto.length()==0){
                                    nombreTexto.setBackground(Color.RED);
                                    aceptar.setEnabled(false);
                                }
                                else{
                                    nombreTexto.setBackground(Color.WHITE);
                                    aceptar.setEnabled(true);
                                }

                            }

                            @Override
                            public void removeUpdate(DocumentEvent e) {
                                String texto = nombreTexto.getText().trim();

                                if(texto.length()==0){
                                    nombreTexto.setBackground(Color.RED);
                                    aceptar.setEnabled(false);
                                }
                                else{
                                    nombreTexto.setBackground(Color.WHITE);
                                    aceptar.setEnabled(true);
                                }
                            }

                            @Override
                            public void changedUpdate(DocumentEvent e) {
                                String texto = nombreTexto.getText().trim();

                                if(texto.length()==0){
                                    nombreTexto.setBackground(Color.RED);
                                    aceptar.setEnabled(false);
                                }
                                else{
                                    nombreTexto.setBackground(Color.WHITE);
                                    aceptar.setEnabled(true);
                                }
                            }
                        });
                        kcalTexto.setModel(new SpinnerNumberModel(1,1,2000,1));
                        caducidadTexto.setModel(new SpinnerDateModel(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                                new GregorianCalendar(2000, Calendar.JANUARY, 1).getTime(),
                                new GregorianCalendar(2100, Calendar.JANUARY, 1).getTime(),
                                Calendar.MONTH));
                        caducidadTexto.setEditor(new JSpinner.DateEditor(caducidadTexto, "dd/MM/yyyy"));
                        tipoTexto.setModel(new SpinnerNumberModel(0,0,1,1));

                        //Finalmente construimos el panel emergente para crear Arma rellenando el Jdialog
                        panelCrearProvision.add(nombre); panelCrearProvision.add(nombreTexto);
                        panelCrearProvision.add(kcal); panelCrearProvision.add(kcalTexto);
                        panelCrearProvision.add(caducidad); panelCrearProvision.add(caducidadTexto);
                        panelCrearProvision.add(tipo); panelCrearProvision.add(tipoTexto);

                        //Añadimos por ultimo los botones de aceptar y cancelar
                        aceptar.setEnabled(false);
                        aceptar.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {
                                for(int i = 0; i<5; i++){
                                    if(superviviente.getInventario(i)==null){
                                        int[] caducidad = {((Date) caducidadTexto.getValue()).getDate(), ((Date) caducidadTexto.getValue()).getMonth() + 1, ((Date) caducidadTexto.getValue()).getYear() + 1900};
                                        superviviente.setInventario(new Provision(nombreTexto.getText().trim(),(int) kcalTexto.getValue(), caducidad,((int)tipoTexto.getValue() == 1 ? true : false)),i);
                                        panelCrearProvision.dispose();
                                        break;
                                    }
                                }
                            }
                        });
                        cancelar.addActionListener(new ActionListener(){
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                panelCrearProvision.dispose();
                            }
                        });

                        panelCrearProvision.add(aceptar); panelCrearProvision.add(cancelar);
                        panelCrearProvision.setVisible(true);
                    }
                }
            }
        }

        panelSupervivienteEntero.removeAll();
        panelSupervivienteEntero.setLayout(new BorderLayout(10, 10));

        JPanel panelSupervivientes = new JPanel();
        panelSupervivientes.setLayout(new GridLayout(1,4,10,10));
        panelSupervivientes.setBorder(BorderFactory.createTitledBorder("Supervivientes"));
        panelSupervivientes.setBackground(Color.white);

        for(int i=0; i<juego.getSupervivientes().length;i++){
            Superviviente superviviente = juego.getSuperviviente(i);
            JPanel panelSuperviviente = new JPanel();
            panelSuperviviente.setLayout(new GridLayout(7,1,10,10));

            Color colorFondo;
            if(superviviente.getHeridas()==2){
                colorFondo = Color.RED;
            }
            else if(superviviente.getHeridas()==1){
                colorFondo = Color.YELLOW;
            }
            else{
                colorFondo = Color.white;
            }

            panelSuperviviente.setBackground(colorFondo);
            panelSupervivientes.repaint();
            panelSuperviviente.add(new JLabel(superviviente.getNombre()));
            panelSuperviviente.add(new JButton(new verInventario("Inventario","Accede al inventario del personaje",LectorImagenes.cargarIconoVerInventario(),i)));
            panelSuperviviente.add(new JButton(new verArmasActivas("Armas Activas", "Mira que armas tiene activas el personaje",LectorImagenes.cargarIconoVerArmasActivas(),i)));
            panelSuperviviente.add(new JButton(new mostrarZombiesEliminados("Zombies eliminados", "Muestra todos los zombies que han sido eliminados por el personaje durante la partida",LectorImagenes.cargarIconoZombiesEliminados(),i)));
            panelSuperviviente.add(new JButton(new mostrarAtaqueZombies("Ataques Recibidos","Muestra los zombies que te han mordido, ten cuidado, las vidas son muy escasas",LectorImagenes.cargarIconoAtaquesRecibidos(),i)));
            panelSuperviviente.add(new JButton(new mostrarPosicionPersonaje("Posicion","Muestra la posición en la que se encuentra el personaje", LectorImagenes.cargarIconoPosicion(),i)));
            panelSuperviviente.add(new JButton(new crearEquipo("Crear","Crea un arma o una provisión que se añadira al inventario del personaje",LectorImagenes.cargarIconoCrear(), i)));
            panelSupervivientes.add(panelSuperviviente);
        }

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
        panelSupervivienteEntero.add(panelSupervivientes, BorderLayout.CENTER);

        // Actualizar la interfaz gráfica
        revalidate();
        repaint();

    }

    /**
     * JDialogAtaquesHechos recopila los ataques hechos
     */
    private void JDialogAtaquesHechos(){
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        LectorSonido.reproducirClick();

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
     * JDialogVerPosicionZombies permite ver la posicion de los zombies
     */
    private void JDialogVerPosicionZombies(){
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        LectorSonido.reproducirClick();

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
     * JDialogTurnosuperviviente es un JDialog que contiene botones que son las acciones que puede realizar los supervivientes
     */
    private void JDialogTurnoSuperviviente(){
        Window pantallaJuego = SwingUtilities.getWindowAncestor(this);

        for (Superviviente superviviente : juego.getSupervivientes()){

            //restauramos las activaciones del superviviente
            superviviente.activarse();

            //Hacemos un bucle con las distintas elecciones que puede hacer cada superviviente hasta que se quede sin acciones
            while (superviviente.getAcciones() > 0){

                JDialog menuTurnoSuperviviente = new JDialog(pantallaJuego, "Turno de " + superviviente.getNombre(), Dialog.ModalityType.APPLICATION_MODAL);
                menuTurnoSuperviviente.setLocationRelativeTo(pantallaJuego);
                menuTurnoSuperviviente.setLayout(new BorderLayout(10, 10));

                menuTurnoSuperviviente.setSize(500, 350);
                menuTurnoSuperviviente.setResizable(false);


                JPanel panelTurnoSuperviviente = new JPanel();
                panelTurnoSuperviviente.setLayout(new GridLayout(6, 1, 10, 10));


                //Clase que hereda de AbstractAction para crear los botones e incluir los oyentes
                class AccionesSuperviviente extends AbstractAction{
                    public AccionesSuperviviente(String nombre, String descripcion, Icon imagen){
                        putValue(Action.NAME, nombre);
                        putValue(Action.SHORT_DESCRIPTION, descripcion);
                        putValue(Action.SMALL_ICON, imagen);

                    }

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        LectorSonido.reproducirClick();

                        String nombre = (String) getValue(Action.NAME);

                        if(nombre.equals("No hacer nada")){
                            superviviente.setAcciones(0);
                        }
                        else if(nombre.equals("Atacar")){
                            JDialogAtacar(superviviente);
                        }
                        else if(nombre.equals("Eliminar equipo del inventario")){
                            JDialogEliminarEquipoDelInventerio(superviviente);
                        }
                        else if(nombre.equals("Cambiar arma Activa")){
                            JDialogCambiarArmaActiva(superviviente);
                        }
                        else if(nombre.equals("Buscar equipo")){
                            JDialogResultadoBuscar(superviviente);
                        }
                        else if(nombre.equals("Moverse")){
                            JDialogMoverse(superviviente);
                        }
                        menuTurnoSuperviviente.dispose();
                    }
                }

                //Crear los Botones que contienen las acciones de los supervivientes
                JButton noHacerNada = new JButton(new AccionesSuperviviente("No hacer nada","El superviviente descansará hasta el proximo turno",LectorImagenes.cargarIconoNoHacerNada()));
                JButton atacar = new JButton(new AccionesSuperviviente("Atacar","El superviviente atacará con una de sus armas activas, necesitará antes tener por lo menos un arma activada",LectorImagenes.cargarIconoAtacar()));
                JButton eliminarEquipoDelInventerio = new JButton(new AccionesSuperviviente("Eliminar equipo del inventario", "Elimina un elemento de tu inventario, ojo, no lo podrás recuperar",LectorImagenes.cargarIconoEliminarEquipo()));
                JButton cambiarArmaActiva = new JButton(new AccionesSuperviviente("Cambiar arma Activa", "Elige un arma para activarte, si tienes ya dos armas activas tendrás que seleccionar una para cambiarla por la nueva", LectorImagenes.cargarIconoCambiarArma()));
                JButton buscar = new JButton(new AccionesSuperviviente("Buscar equipo", "Busca equipo en la casilla en la que te encuentra, si ya esta buscada no podrás buscar y cuanto mas vacio este tu inventario, mas probable será encontrar algo.", LectorImagenes.cargarIconoBuscar()));
                JButton moverse = new JButton(new AccionesSuperviviente("Moverse","Muevete a una casilla adyacente, te costará una acción extra por cada zombie que este en tu misma casilla", LectorImagenes.cargarMoverse()));



                //Atacar y cambiar arma se desactivaran cuando no tenga sentido utilizarlas, para evitar problemas
                atacar.setEnabled(true);
                cambiarArmaActiva.setEnabled(true);

                if(superviviente.getArmaActiva(0)==null && superviviente.getArmaActiva(1)==null){
                    atacar.setEnabled(false);
                }
                if(superviviente.tieneArmasEnElInventario()==false){
                    cambiarArmaActiva.setEnabled(false);
                }


                //Añadir los botones
                panelTurnoSuperviviente.add(noHacerNada);
                panelTurnoSuperviviente.add(eliminarEquipoDelInventerio);
                panelTurnoSuperviviente.add(cambiarArmaActiva);
                panelTurnoSuperviviente.add(moverse);
                panelTurnoSuperviviente.add(buscar);
                panelTurnoSuperviviente.add(atacar);
                menuTurnoSuperviviente.add(panelTurnoSuperviviente, BorderLayout.CENTER);

                //Mensaje con acciones restantes
                JLabel accionesRestantes = new JLabel("Acciones restantes: " + superviviente.getAcciones());
                accionesRestantes.setHorizontalAlignment(SwingConstants.CENTER);
                menuTurnoSuperviviente.add(accionesRestantes, BorderLayout.NORTH);

                if(juego.comprobarVictoria()){
                    removeAll();
                    panelDeTexto.setText("");
                    JPanel terminar = new JPanel();
                    terminar.setLayout(new GridLayout());
                    JLabel mensajeVictoria = new JLabel("Victoria",LectorImagenes.cargarIconoVictoria(),SwingConstants.CENTER);
                    mensajeVictoria.setFont(new Font("Arial", Font.BOLD, 50));
                    terminar.add(mensajeVictoria);
                    add(terminar, BorderLayout.CENTER);
                    revalidate();
                    repaint();
                    break;
                }
                else{
                    menuTurnoSuperviviente.setVisible(true);
                }
            }
        }

    }

    /**
     * JDialogEliminarEquipoDelInventario es una accion del superviviente, se selecciona un Equipo del inventario y se elimina
     * al darle aceptar
     */
    private void JDialogEliminarEquipoDelInventerio(Superviviente superviviente){
        final Equipo[] Item = new Equipo[1];
        Window pantallaJuego = SwingUtilities.getWindowAncestor(this);

        JDialog menuEliminarArma = new JDialog(pantallaJuego, "Eliminar item del inventario de " + superviviente.getNombre(), Dialog.ModalityType.APPLICATION_MODAL);
        menuEliminarArma.setSize(400, 300);
        menuEliminarArma.setLayout(new BorderLayout(10, 10));
        menuEliminarArma.setLocationRelativeTo(pantallaJuego);

        JPanel contenidoDelInventario = new JPanel(new GridLayout(5, 1, 10, 10));
        contenidoDelInventario.setBorder(new EmptyBorder(10, 10, 10, 10));
        ButtonGroup objetosEnElInventario = new ButtonGroup();

        for(Equipo equipo : superviviente.getInventario()){
            if(equipo != null){ // Crear botón solo si el equipo no es null
                JRadioButton botonEquipo = new JRadioButton(equipo.getNombre());
                objetosEnElInventario.add(botonEquipo);
                contenidoDelInventario.add(botonEquipo);
                botonEquipo.addActionListener(e -> Item[0] = equipo);
            }
        }

        JButton aceptar = new JButton("Aceptar");
        aceptar.addActionListener(e -> {
            if(Item[0] == null){
                JOptionPane.showMessageDialog(menuEliminarArma, "Debe seleccionar un ítem para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Eliminar ítem del inventario
            Equipo equipo = Item[0];
            superviviente.eliminarItemInventario(equipo);

            // Mostrar mensaje de éxito y cerrar diálogo
            JOptionPane.showMessageDialog(menuEliminarArma, "Ítem eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            menuEliminarArma.dispose();
        });

        JButton cancelar = new JButton("Cancelar");
        cancelar.addActionListener(e -> menuEliminarArma.dispose());

        menuEliminarArma.add(contenidoDelInventario, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.add(aceptar);
        panelBotones.add(cancelar);
        menuEliminarArma.add(panelBotones, BorderLayout.SOUTH);

        menuEliminarArma.setVisible(true);
    }

    /**
     * JDialogCombiarArmaActiva accion del superviviente para poner un arma del inventario como arma activa
     */
    private void JDialogCambiarArmaActiva(Superviviente superviviente){
        final Arma[] armaActivaSeleccionada = new Arma[1];
        final Arma[] armaInventarioSeleccionada = new Arma[1];

        // Crear diálogo modal
        Window pantallaJuego = SwingUtilities.getWindowAncestor(this);
        JDialog ventanaCambiarArma = new JDialog(pantallaJuego,"Cambiar arma de " + superviviente.getNombre(), Dialog.ModalityType.APPLICATION_MODAL);
        ventanaCambiarArma.setSize(400, 300);
        ventanaCambiarArma.setLayout(new BorderLayout(10, 10));
        ventanaCambiarArma.setLocationRelativeTo(pantallaJuego);

        // Panel principal
        JPanel menuArmas = new JPanel(new GridLayout(1, 2, 10, 10));
        menuArmas.setBorder(new EmptyBorder(10, 10, 10, 10));

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
        menuArmas.add(panelArmasActivas);

        // Panel del inventario
        JPanel panelInventario = new JPanel(new GridLayout(0, 1, 5, 5));
        panelInventario.setBorder(BorderFactory.createTitledBorder("Inventario"));
        ButtonGroup grupoInventario = new ButtonGroup();

        for(int i = 0; i < superviviente.getInventario().length; i++){
            if (superviviente.getInventario(i) instanceof Arma ){
                Arma arma = (Arma) superviviente.getInventario(i);
                if(!superviviente.estaActiva(arma)){
                JRadioButton botonArma = new JRadioButton(arma.getNombre());
                botonArma.addActionListener(e -> armaInventarioSeleccionada[0] = arma);
                grupoInventario.add(botonArma);
                panelInventario.add(botonArma);
            }
        }
        }
        menuArmas.add(panelInventario);

        ventanaCambiarArma.add(menuArmas, BorderLayout.CENTER);

        // Panel de botones
        JButton aceptar = new JButton("Aceptar");
        aceptar.addActionListener(e -> {
            // Verificar las condiciones de selección según el estado actual
            int ArmasActivas = grupoArmasActivas.getButtonCount();

            if(ArmasActivas == 2){
                if(armaActivaSeleccionada[0] == null){
                    JOptionPane.showMessageDialog(ventanaCambiarArma, "Debe seleccionar un arma activa.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(armaInventarioSeleccionada[0] == null){
                    JOptionPane.showMessageDialog(ventanaCambiarArma, "Debe seleccionar un arma del inventario.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Cambiar el arma activa seleccionada
                superviviente.cambiarArmaActiva(armaActivaSeleccionada[0],armaInventarioSeleccionada[0]);

            }
            else if (ArmasActivas == 1){

                if (armaActivaSeleccionada[0] == null){

                    if (armaInventarioSeleccionada[0] == null){

                        JOptionPane.showMessageDialog(ventanaCambiarArma, "Debe seleccionar un arma del inventario.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    superviviente.cambiarArmaActiva(null,armaInventarioSeleccionada[0]);

                }
                else{

                    superviviente.cambiarArmaActiva(armaActivaSeleccionada[0],armaInventarioSeleccionada[0]);}

            }
            else{
                // No hay armas activas
                if (armaInventarioSeleccionada[0] == null){
                    JOptionPane.showMessageDialog(ventanaCambiarArma, "Debe seleccionar un arma del inventario.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for (int i = 0; i < superviviente.getArmasActivas().length; i++){
                    if (superviviente.getArmaActiva(i) == null){
                        superviviente.cambiarArmaActiva(null,armaInventarioSeleccionada[0]);
                        break;
                    }
                }
            }

            ventanaCambiarArma.dispose();
        });

        JButton cancelar = new JButton("Cancelar");
        cancelar.addActionListener(e -> ventanaCambiarArma.dispose());

        JPanel panelBotones = new JPanel();
        panelBotones.add(aceptar);
        panelBotones.add(cancelar);

        ventanaCambiarArma.add(panelBotones, BorderLayout.SOUTH);
        ventanaCambiarArma.setVisible(true);
    }

    private void JDialogMoverse(Superviviente superviviente){
        Window parentWindow = SwingUtilities.getWindowAncestor(this);

        class elegirDireccion extends AbstractAction{
            elegirDireccion(JDialog dialogo,Icon icono, int desplazamientoX, int desplazamientoY, ArrayList<EntidadActivable> entidades){
                putValue("Dialogo", dialogo);
                putValue(AbstractAction.SMALL_ICON, icono);
                putValue("x", desplazamientoX);
                putValue("y", desplazamientoY);
                putValue("entidades", entidades);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                LectorSonido.reproducirClick();
                ArrayList<EntidadActivable> entidades = (ArrayList<EntidadActivable>) getValue("entidades");
                superviviente.moverse(entidades);
                superviviente.setPosicion(new Posicion(superviviente.getPosicion().getPosicionX()+((int)getValue("x")),superviviente.getPosicion().getPosicionY()+((int)getValue("y"))));
                LectorSonido.reproducirPasosSonido();
                ((JDialog)getValue("Dialogo")).dispose();
            }
        }

        ArrayList<EntidadActivable> zombies = new ArrayList<>(juego.getZombies());
        int accionesPorMoverse = superviviente.calcularNumeroAccinesPorMoverse(zombies);
        if(superviviente.getAcciones()<accionesPorMoverse){
            JOptionPane.showMessageDialog(this, "No tienes suficientes acciones para moverte", "Acciones insuficientes", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog dialogo = new JDialog(parentWindow, "Moverse", Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setIconImage(((ImageIcon)LectorImagenes.cargarMoverse()).getImage());
        dialogo.setSize(400, 300);
        dialogo.setLayout(new GridLayout(3, 3,10,10));
        dialogo.setLocationRelativeTo(this);

        //Botones de Direccion
        JButton arribaIzquierda = new JButton(new elegirDireccion(dialogo,LectorImagenes.cargarIconoArribaIzquierda(),-1,-1,zombies));
        JButton arriba = new JButton(new elegirDireccion(dialogo,LectorImagenes.cargarIconoArriba(),0,-1,zombies));
        JButton arribaDerecha = new JButton(new elegirDireccion(dialogo,LectorImagenes.cargarIconoArribaDerecha(),1,-1,zombies));
        JButton izquierda = new JButton(new elegirDireccion(dialogo,LectorImagenes.cargarIconoIzquierda(),-1,0,zombies));
        JButton derecha = new JButton(new elegirDireccion(dialogo,LectorImagenes.cargarIconoDerecha(),1,0,zombies));
        JButton abajoIzquierda = new JButton(new elegirDireccion(dialogo,LectorImagenes.cargarIconoAbajoIzquierda(),-1,1,zombies));
        JButton abajo = new JButton(new elegirDireccion(dialogo,LectorImagenes.cargarIconoAbajo(),0,1,zombies));
        JButton abajoDerecha = new JButton(new elegirDireccion(dialogo,LectorImagenes.cargarIconoAbajoDerecha(),1,1,zombies));

        if(superviviente.getPosicion().getPosicionX()==0){
            arribaIzquierda.setEnabled(false);
            izquierda.setEnabled(false);
            abajoIzquierda.setEnabled(false);
        }
        if(superviviente.getPosicion().getPosicionY()==0){
            arribaDerecha.setEnabled(false);
            arriba.setEnabled(false);
            arribaIzquierda.setEnabled(false);
        }
        if(superviviente.getPosicion().getPosicionX()==juego.getTablero().getDimensiones().getPosicionX()){
            arribaDerecha.setEnabled(false);
            derecha.setEnabled(false);
            abajoDerecha.setEnabled(false);
        }
        if(superviviente.getPosicion().getPosicionY()==juego.getTablero().getDimensiones().getPosicionY()){
            abajoDerecha.setEnabled(false);
            abajo.setEnabled(false);
            abajoIzquierda.setEnabled(false);
        }

        dialogo.add(arribaIzquierda);
        dialogo.add(arriba);
        dialogo.add(arribaDerecha);
        dialogo.add(izquierda);
        dialogo.add(new JLabel("Panel Movimiento"));
        dialogo.add(derecha);
        dialogo.add(abajoIzquierda);
        dialogo.add(abajo);
        dialogo.add(abajoDerecha);

        dialogo.setVisible(true);
    }

    /**
     * JDialogResultadoBuscar muestra el resultado de buscar
     */
    private void JDialogResultadoBuscar(Superviviente superviviente) {
        Casilla casillaABuscar = juego.getTablero().getCasilla(superviviente.getPosicion());
        int numeroObjetoInventerio = superviviente.calcularNumeroObjetosInventario();
        if(superviviente.buscar(casillaABuscar)){
            if(numeroObjetoInventerio<superviviente.calcularNumeroObjetosInventario()){
                LectorSonido.reproducirRespiroAlivio();
                JOptionPane.showMessageDialog(null,"Has encontrado algo, mira en tu inventario","Busqueda", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(null,"No habia nada","Busqueda", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"No puedes buscar aquí", "Error",JOptionPane.ERROR_MESSAGE);
        }
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



    private void JDialogCrearZombie() {
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        LectorSonido.reproducirClick();

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

