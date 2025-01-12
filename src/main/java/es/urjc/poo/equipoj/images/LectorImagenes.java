package es.urjc.poo.equipoj.images;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LectorImagenes {

    static public  String ICONO_CARGAR = "/images/cargarIcono.gif";
    static public  String ICONO_PRUEBA = "/images/pruebaIcono.gif";

    static public String ICONO_JUEGO = "/images/iconoJuego.png";
    static public String ICONO_ATACAR = "/images/atacarIcono.gif";
    static public String ICONO_NOHACERNADA = "/images/noHacerNadaIcono.gif";
    static public String ICONO_ELIMINAR_EQUIPO = "/images/eliminarEquipoIcono.gif";

    //Prueba con un gif en movimiento, sorprendentemente funciona, cambiar si se quiere habilitar un icono para cambiar en movimiento
    static public String ICONO_CAMBIAR_ARMA_ANIMADA = "/images/cambiarArmaAnimadaIcono.gif";
    static public String ICONO_CAMBIAR_ARMA = "/images/cambiarArmaIcono.gif";
    static public String ICONO_BUSCAR = "/images/buscarIcono.gif";
    static public String ICONO_MOVERSE = "/images/moverseIcono.gif";
    static public String ICONO_VICTORIA = "/images/victoriaIcono.gif";
    static public String ICONO_POSICION = "/images/posicionIcono.gif";
    static public String ICONO_ATAQUES_RECIBIDOS = "/images/ataquesRecibidosIcono.gif";
    static public String ICONO_ZOMBIES_ELIMINADOS = "/images/zombiesEliminadosIcono.gif";
    static public String ICONO_VER_ARMAS_ACTIVAS = "/images/verArmasActivasIcon.gif";
    static public String ICONO_VER_INVENTARIO = "/images/verInventarioIcon.gif";
    static public String ICONO_CREAR = "/images/crearIcono.gif";
    static public String ICONO_ARRIBA_IZQUIERDA = "/images/arribaIzquierdaIcono.gif";
    static public String ICONO_ARRIBA = "/images/arribaIcono.gif";
    static public String ICONO_ARRIBA_DERECHA ="/images/arribaDerechaIcono.gif";
    static public String ICONO_IZQUIERDA = "/images/izquierdaIcono.gif";
    static public String ICONO_DERECHA = "/images/derechaIcono.gif";
    static public String ICONO_ABAJO_IZQUIERDA = "/images/abajoIzquierdaIcono.gif";
    static public String ICONO_ABAJO = "/images/abajoIcono.gif";
    static public String ICONO_ABAJO_DERECHA = "/images/abajoDerechaIcono.gif";
    static public  String ICONO_PERSONA= "/images/personaIcono.gif";

    public Image cargarImagenFondo1(){
        return new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/images/fondoPantalla1.gif")).toString()).getImage();
    }

    public Icon CargarIcono(String icono) {
        return new ImageIcon(Objects.requireNonNull(this.getClass().getResource(icono)));
    }


}
