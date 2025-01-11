package es.urjc.poo.equipoj.entidades;

import java.util.Arrays;

public class Ataque {
    private int [] dados;
    private String resultado;


    //NO vamos a permitir crear un constructor vacio de ataque ya que no tendria sentido;
    public Ataque(int [] dados, String resultado) {
        this.dados = dados.clone();
        this.resultado = resultado;
    }

    public int[] getDados() {
        return dados.clone();
    }

    public int getDado(int indice){
        return this.dados[indice];
    }

    public void setDados(int[] dados) {
        this.dados = dados;
    }

    public void setDado(int indice, int valor) {
        this.dados[indice] = valor;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String obtenerDados(){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < this.dados.length; i++) {
            str.append(this.getDado(i) + " ");
        }
        return str.toString();
    }

    @Override
    public String toString() {
        return "Ataque{" +
                "dados=" + Arrays.toString(dados) +
                resultado + '\'' +
                '}';
    }
}
