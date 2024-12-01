package es.urjc.poo.equipoj.entidades;

public class Berserker extends Zombie {

    public Berserker() {
    }

    public Berserker(TipoZombie tipo){
        super(System.currentTimeMillis(),tipo);
    }

    public Berserker(long identificador, TipoZombie tipo) {
        super(identificador, tipo);
    }

    @Override
    public String toString() {
        return super.toString() + " Berserker";
    }
}
