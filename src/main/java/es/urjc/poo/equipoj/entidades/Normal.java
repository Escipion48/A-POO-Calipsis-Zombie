package es.urjc.poo.equipoj.entidades;

public class Normal extends Zombie {

    public Normal() {
        super();
    }

    public Normal(TipoZombie tipo){
        super(System.currentTimeMillis(),tipo, new Posicion());
    }

    public Normal(long identificador, TipoZombie tipo) {
        super(identificador, tipo,new Posicion());
    }


    //Constructor principal
    public Normal(TipoZombie tipo, Posicion posicion) {
        super(tipo, posicion);
    }


    @Override
    public String toString() {
        return super.toString() + " Normal";
    }


}
