public class Arma extends Equipo{
    private String nombre;
    private int potencia;
    private int alcance;
    private int numeroDados;
    private int valorExito;

    public Arma(String nombre, int potencia, int alcance, int numeroDados, int valorExito) {
        super();
        this.nombre = nombre;
        this.potencia = potencia;
        this.alcance = alcance;
        this.numeroDados = numeroDados;
        this.valorExito = valorExito;
    }
}
