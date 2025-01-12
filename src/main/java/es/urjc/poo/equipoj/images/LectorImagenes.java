package es.urjc.poo.equipoj.images;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LectorImagenes {

    public static String ICONO_CARGAR = "/images/cargarIcono.gif";
    public static String ICONO_PRUEBA = "/images/pruebaIcono.gif";
    public static String ICONO_JUEGO = "/images/iconoJuego.png";
    public static String ICONO_ATACAR = "/images/atacarIcono.gif";
    public static String ICONO_NOHACERNADA = "/images/noHacerNadaIcono.gif";
    public static String ICONO_ELIMINAR_EQUIPO = "/images/eliminarEquipoIcono.gif";

    //Prueba con un gif en movimiento, sorprendentemente funciona, cambiar si se quiere habilitar un icono para cambiar en movimiento
    public static String ICONO_CAMBIAR_ARMA_ANIMADA = "/images/cambiarArmaAnimadaIcono.gif";
    public static String ICONO_CAMBIAR_ARMA = "/images/cambiarArmaIcono.gif";
    public static String ICONO_BUSCAR = "/images/buscarIcono.gif";
    public static String ICONO_MOVERSE = "/images/moverseIcono.gif";
    public static String ICONO_VICTORIA = "/images/victoriaIcono.gif";
    public static String ICONO_POSICION = "/images/posicionIcono.gif";
    public static String ICONO_ATAQUES_RECIBIDOS = "/images/ataquesRecibidosIcono.gif";
    public static String ICONO_ZOMBIES_ELIMINADOS = "/images/zombiesEliminadosIcono.gif";
    public static String ICONO_VER_ARMAS_ACTIVAS = "/images/verArmasActivasIcon.gif";
    public static String ICONO_VER_INVENTARIO = "/images/verInventarioIcon.gif";
    public static String ICONO_CREAR = "/images/crearIcono.gif";
    public static String ICONO_ARRIBA_IZQUIERDA = "/images/arribaIzquierdaIcono.gif";
    public static String ICONO_ARRIBA = "/images/arribaIcono.gif";
    public static String ICONO_ARRIBA_DERECHA ="/images/arribaDerechaIcono.gif";
    public static String ICONO_IZQUIERDA = "/images/izquierdaIcono.gif";
    public static String ICONO_DERECHA = "/images/derechaIcono.gif";
    public static String ICONO_ABAJO_IZQUIERDA = "/images/abajoIzquierdaIcono.gif";
    public static String ICONO_ABAJO = "/images/abajoIcono.gif";
    public static String ICONO_ABAJO_DERECHA = "/images/abajoDerechaIcono.gif";
    public static String ICONO_PERSONA = "/images/personaIcono.gif";
    public static  String ICONO_SUPERVIVIENTE = "/images/superviviente.png";

    public Image cargarImagenFondo1(){
        return new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/images/fondoPantalla1.gif")).toString()).getImage();
    }

    public Icon CargarIcono(String icono) {
        return new ImageIcon(Objects.requireNonNull(this.getClass().getResource(icono)));
    }
}
