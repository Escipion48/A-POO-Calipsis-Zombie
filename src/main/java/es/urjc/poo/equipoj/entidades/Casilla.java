package es.urjc.poo.equipoj.entidades;

import java.util.Objects;

public class Casilla {
    private Posicion posicion;
    boolean explorada;

    public Casilla(Posicion posicion, boolean explorada) {
        this.posicion = posicion;
        this.explorada = explorada;
    }

    public Casilla() {
        this(new Posicion(), false);
    }

    public Casilla(Posicion posicion) {
        this.posicion = posicion;
        this.explorada = false;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public boolean isExplorada() {
        return explorada;
    }

    public void setExplorada(boolean explorada) {
        this.explorada = explorada;
    }

    @Override
    public String toString() {
        return "Casilla{" +
                "posicion=" + posicion +
                ", explorada=" + explorada +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Casilla casilla = (Casilla) o;
        return this.getPosicion().equals(casilla.getPosicion());
    }

}
