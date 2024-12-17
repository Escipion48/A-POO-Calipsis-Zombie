package es.urjc.poo.equipoj.entidades;

public class Berserker extends Zombie {

    public Berserker() {
        super();
    }

    public Berserker(TipoZombie tipo){
        super(System.currentTimeMillis(),tipo, new Posicion());
    }

    public Berserker(long identificador, TipoZombie tipo) {
        super(identificador, tipo, new Posicion());
    }

    public Berserker(long identificador, TipoZombie tipo, Posicion posicion) {
        super(identificador, tipo, posicion);
    }


    //constructor principal
    public Berserker(TipoZombie tipo, Posicion posicion) {
        super(tipo,posicion);
    }

    @Override
    public String toString() {
        return super.toString() + " Berserker";
    }
}
