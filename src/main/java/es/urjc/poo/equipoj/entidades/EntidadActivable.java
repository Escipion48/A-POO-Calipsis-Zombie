package es.urjc.poo.equipoj.entidades;

public interface EntidadActivable {

    Posicion posicion = null;


    void activarse();
    void moverse();
    void atacar();
}
