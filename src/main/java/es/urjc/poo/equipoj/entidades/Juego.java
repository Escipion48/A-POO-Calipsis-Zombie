package es.urjc.poo.equipoj.entidades;

import java.util.ArrayList;

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
        this.zombies.add(0, new Zombie());
        this.zombies.add(1, new Zombie());
        this.zombies.add(2, new Zombie());
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

    public void setAtaque(Ataque ataque, int pos) {
        this.ataques.setAtaque(pos, ataque);
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
            builder.append(zombies.get(i).toString());
        }
        return builder.toString();
    }
}
