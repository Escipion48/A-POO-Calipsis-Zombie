package es.urjc.poo.equipoj.entidades;

public class Toxico extends Zombie {


    public Toxico() {
    }

    public Toxico(TipoZombie tipo){
        super(System.currentTimeMillis(),tipo);
    }

    public Toxico(long identificador, TipoZombie tipo) {
        super(identificador, tipo);
    }

    @Override
    public String toString() {
        return super.toString() + " Toxico";
    }
}
