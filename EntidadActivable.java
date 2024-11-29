public abstract class EntidadActivable {
    private int acciones;

    public EntidadActivable(int acciones) {
        this.acciones = acciones;
    }

    public EntidadActivable() {
        this(0);
    }
    
    public void setAcciones(int acciones) {
        this.acciones = acciones;
    }
    
    public int getAcciones() {
        return acciones;
    }

}
