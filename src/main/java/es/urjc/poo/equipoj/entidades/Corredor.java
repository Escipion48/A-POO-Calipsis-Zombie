package es.urjc.poo.equipoj.entidades;

public class Corredor extends Zombie {

    public Corredor() {
        super(1,2);
    }

    public Corredor(TipoZombie tipo){
        super(System.currentTimeMillis(),tipo, new Posicion(),1,2);
    }

    public Corredor(long identificador, TipoZombie tipo) {
        super(identificador, tipo,new Posicion(),1,2);
    }


    //Constructor principal
    public Corredor(TipoZombie tipo, Posicion posicion) {
        super(tipo, posicion,1,2);
    }


    @Override
    public String toString() {
        return super.toString() + " Corredor "+getTipo();
    }


}
