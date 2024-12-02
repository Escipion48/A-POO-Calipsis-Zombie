package es.urjc.poo.equipoj.entidades;

public class Berserker extends Zombie {

    public Berserker() {
        super();
    }

    public Berserker(TipoZombie tipo){
        super(System.currentTimeMillis(),tipo);
    }

    public Berserker(long identificador, TipoZombie tipo) {
        super(identificador, tipo);
    }

    public Berserker(TipoZombie tipo, Posicion posicion) {
        super(tipo,posicion);
    }

    @Override
    public String toString() {
        return super.toString() + " Berserker";
    }
}
