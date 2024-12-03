package es.urjc.poo.equipoj.entidades;

import java.util.ArrayList;

public class Ataques {
    private ArrayList<Ataque> ataques;

    public Ataques() {
        ataques = new ArrayList<Ataque>();
    }

    public Ataques(ArrayList<Ataque> ataques) {
        this.ataques = ataques;
    }

    public ArrayList<Ataque> getAtaques() {
        return ataques;
    }

    public Ataque getAtaque(int pos) {
        return ataques.get(pos);
    }

    public void setAtaques(ArrayList<Ataque> ataques) {
        this.ataques.clear();
        this.ataques.addAll(ataques);
    }

    public void setAtaque(int pos, Ataque ataque) {
        ataques.add(pos, ataque);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(Ataque ataque : ataques) {
            str.append(ataque.toString());
        }
        return str.toString();
    }
}
