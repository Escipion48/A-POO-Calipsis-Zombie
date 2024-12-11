package es.urjc.poo.equipoj.entidades;

import java.util.ArrayList;

public interface EntidadActivable {

    Posicion posicion = null;


    /**
     * La funcion activarse será utilizada por juego para activar cada entidad, se encargará de que cada entidad
     * activable haga todas sus accines posibles hasta que se quede sin acciones. También se encarga de "regenerar"
     * su respectiva cantidad de acciones a la clase.
     * @param tablero EL tablero del juego, util para los metodos de las clases.
     * @param entidades ArrayList con entidades activables, en supervivientes serán zombies y en zombies supervivientes,
     * Aunque dentro de cada clase hay metodos incluidos para asegurarse que se opera solo con las clases deseadas
     */
    void activarse(Tablero tablero, ArrayList<EntidadActivable> entidades);

    /**
     * Mueve a la entidad por el tablero teniendo en cuenta las entidadesActivables
     * @param entidades
     */
    void moverse(ArrayList<EntidadActivable> entidades);

    /**
     * Realiza el Ataque ****Completar****
     */
    void atacar();
}
