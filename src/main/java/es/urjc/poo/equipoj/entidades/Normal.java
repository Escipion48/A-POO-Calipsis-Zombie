package es.urjc.poo.equipoj.entidades;

public class Normal extends Zombie {

    public Normal() {
        super();
    }

    public Normal(TipoZombie tipo){
        super(System.currentTimeMillis(),tipo);
    }

    public Normal(long identificador, TipoZombie tipo) {
        super(identificador, tipo);
    }

    public Normal(TipoZombie tipo, Posicion posicion) {
        super(tipo, posicion);
    }


    @Override
    public String toString() {
        return super.toString() + " Normal";
    }


}
