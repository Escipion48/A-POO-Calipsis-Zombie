package es.urjc.poo.equipoj.entidades;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class Juego {
    private Tablero tablero;
    private Superviviente [] supervivientes;
    private ArrayList<Zombie> zombies;
    private Ataques ataques;

    //Constructor prueba, no definitivo
    public Juego() {

        this.tablero = new Tablero();
        this.supervivientes = new Superviviente[4];
        this.supervivientes[0]= new Superviviente("Superviviente1");
        this.supervivientes[1]= new Superviviente("Superviviente2");
        this.supervivientes[2]= new Superviviente("Superviviente3");
        this.supervivientes[3]= new Superviviente("Superviviente4");
        this.zombies = new ArrayList<>();
        this.zombies.add(generarZombie());
        this.zombies.add(generarZombie());
        this.zombies.add(generarZombie());
        this.ataques = new Ataques();


    }

    public Juego(Tablero tablero, Superviviente[] supervivientes, ArrayList<Zombie> zombies) {
        this.tablero = tablero;
        this.supervivientes = new Superviviente[supervivientes.length];
        this.supervivientes = supervivientes;
        this.zombies = new ArrayList<Zombie>();
        this.zombies.addAll(zombies);
        this.ataques = new Ataques();
    }

    public Superviviente[] getSupervivientes() {
        return supervivientes;
    }

    public Superviviente getSuperviviente(int pos) {
        return supervivientes[pos];
    }

    public void setSupervivientes(Superviviente[] supervivientes) {
        this.supervivientes = supervivientes;
    }

    public void setSuperviviente(Superviviente superviviente, int pos) {
        this.supervivientes[pos] = superviviente;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public Ataques getAtaques() {
        return ataques;
    }

    public Ataque getAtaque(int pos) {
        return ataques.getAtaque(pos);
    }


    public void setAtaques(Ataques ataques) {
        this.ataques = ataques;
    }

    public void setAtaque(Ataque ataque) {
        this.ataques.setAtaque(ataque);
    }

    public ArrayList<Zombie> getZombies() {
        return zombies;
    }


    public Zombie getZombie(int pos) {
        return zombies.get(pos);
    }

    public void setZombies(ArrayList<Zombie> zombies) {
        this.zombies = zombies;
    }

    public void setZombie(Zombie zombie, int pos) {
        this.zombies.set(pos, zombie);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("EL juego:\n");
        builder.append(tablero.toString());
        for(int i = 0; i<this.supervivientes.length; i++) {
            builder.append(supervivientes[i].toString());
        }
        builder.append("Con los zombies:\n");
        for(int i = 0; i<this.zombies.size(); i++) {
            builder.append(zombies.get(i).toString()+"\n");
        }
        builder.append("Ataques:\n");
        builder.append(ataques.toString());
        return builder.toString();
    }

    /**
     * Este metodo privado se utilizará en el juego para generar Zombies aleatorios en posiciones aleatorias del juego.
     * @return Un zombie cualquiera según las probabilidades indicadas dentro del metodo.
     */
    private Zombie generarZombie(){
        Random random = new Random();
        //Generamos una posicion aleatoria con las dimensiones del tablero como valores máximos posibles.
        Posicion posicionAleatoria = new Posicion(random.nextInt(this.getTablero().getDimensiones().getPosicionX())+1,random.nextInt(this.getTablero().getDimensiones().getPosicionY())+1);

        try{
            Thread.sleep(1);
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        //Elegimos el tipo del zombie con un 60% de probabilidad de que sea caminante, 30% Corredor y 10% Abominacion
        int tipoZombie = random.nextInt(10);
        TipoZombie tipoZombieEnum;
        if(tipoZombie < 6) {
            tipoZombieEnum = TipoZombie.values()[0];
        }
        else if(tipoZombie < 9) {
            tipoZombieEnum = TipoZombie.values()[1];
        }
        else{
            tipoZombieEnum = TipoZombie.values()[2];
        }


        //Elegimos de forma equiprobable si es normal, berserker o tóxico
        int selector = random.nextInt(3);
        switch(selector){
            case 0:
                return new Normal(tipoZombieEnum,posicionAleatoria);
            case 1:
                return new Berserker(tipoZombieEnum,posicionAleatoria);
            case 2:
                return new Toxico(tipoZombieEnum,posicionAleatoria);
            default:
                return null; // No deberia de llegar aqui, ya que el random va de 0 a 2, en cualquier caso retorna un null
        }
    }

    public void anadirZombie(){
        Zombie z = generarZombie();
        this.zombies.add(z);
    }

    public void reiniciar(){
        this.setSupervivientes(new Superviviente[]{});
        this.setAtaques(new Ataques());
        this.setTablero(new Tablero());
        this.zombies.clear();
        this.zombies.add(generarZombie());
        this.zombies.add(generarZombie());
        this.zombies.add(generarZombie());
    }


    /**
     * Este metodo se encarga de comprobar si se cumplen las condiciones de victoria dentro de un juego
     * @return Boolean, true= victoria, false = derrota
     */
    public boolean comprobarVictoria(){
        boolean victoria = true;

        for(Superviviente superviviente : this.getSupervivientes()) {
            if(!(superviviente.getPosicion().equals(this.getTablero().getObjetivo()))){
                victoria = false;
                break;
            }
            if(!superviviente.tieneProvisionValida(LocalDate.now())){
                victoria = false;
                break;
            }
        }
        return victoria;
    }

    public boolean derrota(){
        for(Superviviente superviviente : this.getSupervivientes()) {
            if(superviviente.getEstado()==TipoEstado.ELIMINADO){
                return true;
            }
        }
        return false;
    }


}
