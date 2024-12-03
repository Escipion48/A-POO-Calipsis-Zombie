package es.urjc.poo.equipoj.entidades;

public class Toxico extends Zombie {


    public Toxico() {
        super();
    }

    public Toxico(TipoZombie tipo){
        super(System.currentTimeMillis(),tipo, new Posicion());
    }

    public Toxico(long identificador, TipoZombie tipo) {
        super(identificador, tipo, new Posicion());
    }


    //Constructor principal
    public Toxico(TipoZombie tipo, Posicion posicion) {
        super(tipo,posicion);
    }

    @Override
    public String toString() {
        return super.toString() + " Toxico";
    }
}
