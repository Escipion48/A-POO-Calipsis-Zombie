package es.urjc.poo.equipoj.entidades;

import java.util.ArrayList;
import java.util.Objects;

public class Zombie implements EntidadActivable {

    private long identificador;
    private int aguante;
    private int activaciones;
    private TipoZombie tipo;
    private Posicion posicion;

    public Zombie() {
        this(System.currentTimeMillis(),TipoZombie.CAMINANTE, new Posicion());

    }

    public Zombie(TipoZombie tipo, Posicion posicion) {
        this(System.currentTimeMillis(),tipo,posicion);
    }

    //Deseamos usar este constructor dentro de otros constructores, principalmente el de tipo y posicion.
    private Zombie (long identificador, TipoZombie tipo) {
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

    public Zombie (long identificador, TipoZombie tipo, Posicion posicion) {
        this(identificador,tipo);
        this.posicion = posicion;
    }


    //Lo podemos llegar a utilizar para hacer zombies distintos o para pruebas posteriores.
    public Zombie(long identificador, int aguante, int activaciones, TipoZombie tipo, Posicion posicion) {
        this.identificador = identificador;
        this.aguante = aguante;
        this.activaciones = activaciones;
        this.tipo = tipo;
        this.posicion = posicion;
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

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
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
        return ("Zombie: "+this.getIdentificador()+" Posicion: "+this.getPosicion().toString()+" Tipo:"+this.getTipo());
    }

    public void reaccionAtaque() {
        //TODO: Implementar
    }

    @Override
    public void activarse(Tablero tablero, ArrayList<EntidadActivable> entidades) {
    //TODO: Implementar
    }

    @Override
    public void moverse(ArrayList<EntidadActivable> entidades) {
    //TODO: Implementar
    }

    @Override
    public void atacar() {
    //TODO: Implementar
    }

}
