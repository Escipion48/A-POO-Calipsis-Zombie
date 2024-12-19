package es.urjc.poo.equipoj.entidades;

public class Arma extends Equipo {
    private int potencia;
    private int alcance;
    private int numeroDados;
    private int valorExito;

    public Arma(String nombre, int potencia, int alcance, int numeroDados, int valorExito) {
        super(nombre);
        this.potencia = potencia;
        this.alcance = alcance;
        this.numeroDados = numeroDados;
        this.valorExito = valorExito;
    }

    public Arma(){
        this("ArmaDefecto", 1, 1, 1, 1);
    }

    public void setPotencia(int potencia){
        this.potencia = potencia;
    }

    public void setAlcance(int alcance){
        this.alcance = alcance;
    }

    public void setNumeroDados(int numeroDados){
        this.numeroDados = numeroDados;
    }

    public void setValorExito(int valorExito){
        this.valorExito = valorExito;
    }

    public int getPotencia(){
        return potencia;
    }

    public int getAlcance(){
        return alcance;
    }

    public int getNumeroDados(){
        return numeroDados;
    }

    public int getValorExito(){
        return valorExito;
    }

    @Override
    public String toString(){
        return ("El arma es: "+this.getNombre()+" [potencia: "+this.potencia+", alcance: "+this.alcance+", numero de dados: "+this.numeroDados+ " y valor de exito: "+this.valorExito+"]");
    }


    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o==null || getClass() != o.getClass()) return false;
        Arma arma = (Arma) o;
        if(this.getNombre() == arma.getNombre()){
            return true;
        }
        return false;
    }
}