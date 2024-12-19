package es.urjc.poo.equipoj.entidades;

import java.util.Arrays;

public class Ataque {
    int [] dados;
    String resultado;


    //NO vamos a permitir crear un constructor vacio de ataque ya que no tendria sentido;
    public Ataque(int [] dados, String resultado) {
        this.dados = dados.clone();
        this.resultado = resultado;
    }

    public int[] getDados() {
        return dados.clone();
    }

    public void setDados(int[] dados) {
        this.dados = dados;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "Ataque{" +
                "dados=" + Arrays.toString(dados) +
                ", resultado='" + resultado + '\'' +
                '}';
    }
}
