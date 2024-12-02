package es.urjc.poo.equipoj.entidades;

import java.util.Arrays;
import java.util.Objects;

public class Tablero {
    private Posicion dimensiones;//Los valores máximos del tablero, es decir, un 10x10 tendra como posicion la 9,9;
    private Posicion objetivo; //En esta se incluye la posicion de la casilla final, donde termina el juego, por defecto en un ta blero 10x10 tambien seria la 9,9
    private Casilla [][] tablero;

    public Tablero(Posicion dimensiones, Posicion objetivo, Casilla[][] tablero) {
        this.dimensiones = dimensiones;
        this.objetivo = objetivo;
        this.tablero = tablero;
    }

    public Tablero() {
        this.dimensiones = new Posicion(9, 9);
        this.objetivo = new Posicion(9, 9);
        this.tablero = new Casilla[10][10];
    }

    public Posicion getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(Posicion dimensiones) {
        this.dimensiones = dimensiones;
    }

    public Posicion getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(Posicion objetivo) {
        this.objetivo = objetivo;
    }

    public Casilla[][] getTablero() {
        return tablero;
    }

    public void setTablero(Casilla[][] tablero) {
        this.tablero = tablero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tablero tablero1 = (Tablero) o;
        return Objects.equals(getDimensiones(), tablero1.getDimensiones()) && Objects.equals(getObjetivo(), tablero1.getObjetivo()) && Objects.deepEquals(getTablero(), tablero1.getTablero());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Tablero{ Dimensiones: "+getDimensiones().toString()+", Objetivo: "+getObjetivo().toString()+"}");
        return builder.toString();
    }

}
