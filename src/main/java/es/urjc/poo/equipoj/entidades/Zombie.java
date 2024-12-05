package es.urjc.poo.equipoj.entidades;

import java.util.Objects;

public class Zombie implements EntidadActivable {

    private long identificador;
    private int aguante;
    private int activaciones;
    private TipoZombie tipo;
    private Posicion posicion;

    public Zombie() {
        this(System.currentTimeMillis(),TipoZombie.NORMAL, new Posicion(), 1, 1);
        try { Thread.sleep(1);  } catch (InterruptedException e) { e.printStackTrace(); }
    }
    public Zombie (int aguante, int activaciones){
        this(System.currentTimeMillis(),TipoZombie.NORMAL, new Posicion(), aguante, activaciones);
        this.activaciones = activaciones;
    }

    public Zombie(TipoZombie tipo, Posicion posicion,int aguante, int activaciones) {
        this(System.currentTimeMillis(),tipo,posicion,aguante,activaciones);
        try { Thread.sleep(1);  } catch (InterruptedException e) { e.printStackTrace(); }
    }

    //Deseamos usar este constructor dentro de otros constructores, principalmente el de tipo y posicion.
    private Zombie (long identificador, TipoZombie tipo,int aguante, int activaciones) {
        this.identificador = identificador;
        this.tipo = tipo;
        this.aguante = aguante;
        this.activaciones = activaciones;

        try { Thread.sleep(1);  } catch (InterruptedException e) { e.printStackTrace(); }
    }

    public Zombie (long identificador, TipoZombie tipo, Posicion posicion,int aguante,int activaciones) {
        this(identificador,tipo,aguante,activaciones);
        this.posicion = posicion;
        try { Thread.sleep(1);  } catch (InterruptedException e) { e.printStackTrace(); }
    }


    //Lo podemos llegar a utilizar para hacer zombies distintos o para pruebas posteriores.
    public Zombie(long identificador, int aguante, int activaciones, TipoZombie tipo, Posicion posicion) {
        this.identificador = identificador;
        this.aguante = aguante;
        this.activaciones = activaciones;
        this.tipo = tipo;
        this.posicion = posicion;
        try { Thread.sleep(1);  } catch (InterruptedException e) { e.printStackTrace(); }
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
        return ("Zombie: "+this.getIdentificador()+" Posicion: "+this.getPosicion().toString()+ " Tipo:");
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
    }

}
