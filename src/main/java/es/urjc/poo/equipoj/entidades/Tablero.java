package es.urjc.poo.equipoj.entidades;

public class Tablero {
    Posicion dimensiones;//Los valores máximos del tablero, es decir, un 10x10 tendra como posicion la 9,9;
    Posicion objetivo; //En esta se incluye la posicion de la casilla final, donde termina el juego, por defecto en un ta blero 10x10 tambien seria la 9,9
    Casilla [][] tablero;

    public Tablero(Posicion dimensiones, Posicion objetivo, Casilla[][] tablero) {
        this.dimensiones = dimensiones;
        this.objetivo = objetivo;
        this.tablero = tablero;
    }
}
