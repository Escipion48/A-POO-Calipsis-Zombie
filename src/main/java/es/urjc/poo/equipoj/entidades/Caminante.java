package es.urjc.poo.equipoj.entidades;

public class Caminante extends Zombie {

    public Caminante() {
        super();
    }

    public Caminante(TipoZombie tipo){
        super(System.currentTimeMillis(),tipo, new Posicion(),1,1);
    }

    public Caminante(long identificador, TipoZombie tipo) {
        super(identificador, tipo, new Posicion(),1,1);
    }


    //constructor principal
    public Caminante(TipoZombie tipo, Posicion posicion) {
        super(tipo,posicion,1,1);
    }

    @Override
    public String toString() {
        return super.toString() + " Caminante";
    }
}
