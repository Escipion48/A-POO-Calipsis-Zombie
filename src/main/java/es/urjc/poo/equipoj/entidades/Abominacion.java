package es.urjc.poo.equipoj.entidades;

public class Abominacion extends Zombie {


    public Abominacion() {
        super();
    }

    public Abominacion(TipoZombie tipo){
        super(System.currentTimeMillis(),tipo, new Posicion(),3,1);
    }

    public Abominacion(long identificador, TipoZombie tipo) {
        super(identificador, tipo, new Posicion(),3,1);
    }


    //Constructor principal
    public Abominacion(TipoZombie tipo, Posicion posicion) {
        super(tipo,posicion,3,1);
    }

    @Override
    public String toString() {
        return super.toString() + " Abominacion";
    }
}
