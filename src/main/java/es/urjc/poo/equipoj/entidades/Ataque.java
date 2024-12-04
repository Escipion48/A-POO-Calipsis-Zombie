package es.urjc.poo.equipoj.entidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Ataque {
    int [] dados;
    String resultado;


    //NO vamos a permitir crear un constructor vacio de ataque ya que no tendria sentido;
    public Ataque(int [] dados, String resultado) {
        this.dados = dados.clone();
        this.resultado = resultado;
    }

    private Arma seleccionarArma(Superviviente superviviente) {
        System.out.println("Selecione un arma: \n"+superviviente.getArmaActiva(0)+"\n"+superviviente.getArmaActiva(1));
        Scanner sc = new Scanner(System.in);
        int n;
        do{
        n= sc.nextInt();
        if(n==0){
            System.out.println("Arma seleccionada: "+superviviente.getArmaActiva(0));
        } else if (n==1) {
            System.out.println("Arma seleccionada: "+superviviente.getArmaActiva(1));
        }else{
            System.out.println("Seleccione un arma:\n");}}
        while(n!=0 && n!=1);
        sc.close();
        return superviviente.getArmaActiva(n);
    }
    private Casilla selecionarCasilla(ArrayList<Casilla> casillas,Tablero tablero) {
        System.out.println(casillas);
        Scanner sc = new Scanner(System.in);
        System.out.println("Selecione una casilla: \n");
        int casilla= sc.nextInt();
        sc.close();
        Posicion p =casillas.get(casilla).getPosicion();
        int x= p.getPosicionX();
        int y= p.getPosicionY();
        return tablero.getCasilla(x, y);
    }

    private int[] lanzarDados(Arma arma) {
        int n = arma.getNumeroDados();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            this.dados[i] =0;
            this.dados[i] = random.nextInt(6) + 1;  }
        return dados;
    }

    private int evaluarExito(Arma arma) {
        int exitos=0;
        for(int i=0 ; i<this.dados.length ; i++){
            if(this.dados[i]>=arma.getNumeroDados()){
                exitos++;
            }
        }
        return exitos;
    }

    private ArrayList<Casilla> obtenerCasillaDentroAlcance(Superviviente superviviente,Arma arma) {
        ArrayList<Casilla> casillaDentroAlcance= new ArrayList<>();
        Posicion pCentro =superviviente.getPosicion();
        for(int i =pCentro.posicionX-arma.getAlcance(); i<pCentro.posicionX+arma.getAlcance();i++){
            for(int j =pCentro.posicionY-arma.getAlcance(); j<pCentro.posicionY+arma.getAlcance();j++){
                if(i>=0 && j>=0 && i<=9 && j<=9){
                    Posicion p1= new Posicion(i,j);
                    Casilla casilla= new Casilla(p1);
                    casillaDentroAlcance.add(casilla);
                }
            }
        }
        return casillaDentroAlcance;
    }

    private void resolverAtaque(int exitos,Superviviente superviviente, Tablero tablero, Juego juego) {//No terminado
        Arma arma = seleccionarArma(superviviente);
        ArrayList<Casilla> casillas =obtenerCasillaDentroAlcance(superviviente,seleccionarArma(superviviente));
        int potencia = seleccionarArma(superviviente).getPotencia();
        Casilla casilla = selecionarCasilla(casillas,tablero);
        lanzarDados(arma);
        int num_exitos = evaluarExito(arma);
        ArrayList<Zombie> CasillaZombie= new ArrayList<Zombie>();

        for (Zombie zombie : juego.getZombies()) {
            if (casilla.getPosicion().equals(zombie.getPosicion())) {
                CasillaZombie.add(zombie);
            }
        }
        for (Zombie zombie : CasillaZombie) {
            if(zombie.getTipo() != TipoZombie.BERSERKER && zombie.getAguante()<=potencia){
                CasillaZombie.remove(zombie);
                juego.getZombies().remove(zombie);
                superviviente.anadirZombieElimninado(zombie);

            }else {

            }
        }

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
