package es.urjc.poo.equipoj.entidades;

public class Toxico extends Zombie {


    public Toxico() {
        super();
    }

    public Toxico(TipoZombie tipo){
        super(System.currentTimeMillis(),tipo);
    }

    public Toxico(long identificador, TipoZombie tipo) {
        super(identificador, tipo);
    }

    public Toxico(TipoZombie tipo, Posicion posicion) {
        super(tipo,posicion);
    }

    @Override
    public String toString() {
        return super.toString() + " Toxico";
    }
}
