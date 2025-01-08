package es.urjc.poo.equipoj.images;

import javax.swing.*;

public class LectorImagenes {

    static public Icon cargarIconoJuego(){
        return new ImageIcon("src/main/java/es/urjc/poo/equipoj/images/iconoJuego.gif");
    }

    static public Icon cargarIconoAtacar(){
        return new ImageIcon("src/main/java/es/urjc/poo/equipoj/images/atacarIcono.gif");
    }

    static public Icon cargarIconoNoHacerNada(){
        return new ImageIcon("src/main/java/es/urjc/poo/equipoj/images/noHacerNadaIcono.gif");
    }

    static public Icon cargarIconoEliminarEquipo(){
        return new ImageIcon("src/main/java/es/urjc/poo/equipoj/images/eliminarEquipoIcono.gif");
    }

    //Prueba con un gif en movimiento, sorprendentemente funciona, cambiar si se quiere habilitar un icono para cambiar en movimiento
    static public Icon cargarIconoCambiarArmaAnimado(){
        return new ImageIcon("src/main/java/es/urjc/poo/equipoj/images/cambiarArmaAnimadaIcono.gif");
    }

    static public Icon cargarIconoCambiarArma(){
        return new ImageIcon("src/main/java/es/urjc/poo/equipoj/images/cambiarArmaIcono.gif");
    }

    static public Icon cargarIconoBuscar(){
        return new ImageIcon("src/main/java/es/urjc/poo/equipoj/images/buscarIcono.gif");
    }

    static public Icon cargarMoverse(){
        return new ImageIcon("src/main/java/es/urjc/poo/equipoj/images/moverseIcono.gif");
    }
}
