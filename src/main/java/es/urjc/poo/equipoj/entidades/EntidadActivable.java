package es.urjc.poo.equipoj.entidades;

import java.util.ArrayList;

public interface EntidadActivable {

    Posicion posicion = null;


   /**
     * La funcion activarse se encarga de regenerar las activaciones de cada entidad del juego
     */
    void activarse();

    /**
     * Mueve a la entidad por el tablero teniendo en cuenta las entidadesActivables
     * @param entidades
     */
    void moverse(ArrayList<EntidadActivable> entidades);

    /**
     * Realiza el Ataque ****Completar****
     */
    void atacar(ArrayList<EntidadActivable> entidad);

}
