package es.urjc.poo.equipoj.entidades;

import java.util.Objects;

public class Posicion {
    int posicionX;
    int posicionY;

    public Posicion(int posicionX, int posicionY) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    public Posicion() {
        this.posicionX = 0;
        this.posicionY = 0;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }

    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;
    }


    /**
     * Este metodo se encarga de comprobar si una posicion es adyacente a otra, util para moverse,
     * reutilizamos comprobarDentroDeDistancia, ya que una posicion adyacente a otra es aquella
     * que se encuentra a una distancia igual o inferior a uno en ambos ejes.
     * @param posicion Posicion a comprobar
     * @return Devuelve true si es una casilla adyacente
     */
    public boolean comprobarAdyacente(Posicion posicion) {
        return comprobarDentroDeDistancia(posicion, 1);
    }


    /**
     * Este metodo se encarga de comprobar si dos posiciones se encuentran a una distancia determinada.
     * @param posicion Posicion a comprobar.
     * @param distancia Distancia maxima a la que puede estar la posicion
     * @return Verdadero si la posicion se encuentra dentro del rango determinado por distancia
     */
    public boolean comprobarDentroDeDistancia(Posicion posicion, int distancia) {
        int diferenciaX = Math.abs(this.getPosicionX() - posicion.getPosicionX());
        int diferenciaY = Math.abs(this.getPosicionY() - posicion.getPosicionY());
        if(diferenciaX<=distancia && diferenciaY<=distancia){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return (this.posicionX + "/" + this.posicionY);
    }

    @Override
    public boolean equals(Object o) {
        if(this==o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Posicion posicion = (Posicion) o;
        return getPosicionX() == posicion.getPosicionX() && getPosicionY() == posicion.getPosicionY();
    }

}
