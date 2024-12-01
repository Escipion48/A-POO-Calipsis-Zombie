package es.urjc.poo.equipoj.entidades;

import java.util.Objects;

public class Zombie implements EntidadActivable {

    private long identificador;
    private int aguante;
    private int activaciones;
    private TipoZombie tipo;

    public Zombie() {
        this(System.currentTimeMillis(),TipoZombie.CAMINANTE);

    }

    public Zombie (long identificador, TipoZombie tipo){
        this.identificador = identificador;
        this.tipo = tipo;
        if(tipo == TipoZombie.CAMINANTE){
            this.aguante = 1;
            this.activaciones = 1;
        }
        if(tipo == TipoZombie.CORREDOR){
            this.aguante = 1;
            this.activaciones = 2;
        }
        if(tipo == TipoZombie.ABOMINACION){
            this.aguante = 3;
            this.activaciones = 1;
        }
    }

    public Zombie(long identificador, int aguante, int activaciones, TipoZombie tipo) {
        this.identificador = identificador;
        this.aguante = aguante;
        this.activaciones = activaciones;
        this.tipo = tipo;
    }

    public long getIdentificador() {
        return this.identificador;
    }

    public void setIdentificador(long identificador) {
        this.identificador = identificador;
    }

    public int getAguante() {
        return this.aguante;
    }

    public void setAguante(int aguante) {
        this.aguante = aguante;
    }

    public int getActivaciones() {
        return this.activaciones;
    }

    public void setActivaciones(int activaciones) {
        this.activaciones = activaciones;
    }

    public TipoZombie getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoZombie tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zombie zombie = (Zombie) o;
        return getIdentificador() == zombie.getIdentificador();
    }

    @Override
    public String toString() {
        return ("Zombie: "+this.getIdentificador()+" Tipo:"+this.getTipo());
    }

    public void reaccionAtaque() {
        //TODO: Implementar
    }

    @Override
    public void activarse() {
    //TODO: Implementar
    }

    @Override
    public void moverse() {
    //TODO: Implementar
    }

    @Override
    public void atacar() {
    //TODO: Implementar
    }

}
