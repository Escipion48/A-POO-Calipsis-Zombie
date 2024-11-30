public abstract class Equipo {
    private String nombre;

    public Equipo(String nombre) {
        this.nombre = nombre;
    }

    public Equipo() {
        this.nombre = "Default";
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
