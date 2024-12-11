package es.urjc.poo.equipoj.entidades;

import java.util.ArrayList;

public interface EntidadActivable {

    Posicion posicion = null;


    void activarse(Tablero tablero, ArrayList<EntidadActivable> entidades);
    void moverse(ArrayList<EntidadActivable> entidades);
    void atacar();
}
