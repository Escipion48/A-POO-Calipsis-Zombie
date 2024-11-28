public class Provision extends Equipo{
    private int kcal;
    private int[] caducidad;
    private boolean tipo;

    public Provision(int kcal, int[] caducidad, boolean tipo){
        super();
        this.kcal = kcal;
        this.caducidad[0] = caducidad[0];
        this.caducidad[1]= caducidad[1];
        this.caducidad[2]= caducidad[2];
        this.tipo = tipo;
    }
}