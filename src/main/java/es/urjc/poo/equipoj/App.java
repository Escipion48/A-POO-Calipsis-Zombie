package es.urjc.poo.equipoj;

import es.urjc.poo.equipoj.entidades.*;

import static java.lang.Thread.sleep;

public class App {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello World!");

        // Para que no se repita el identificador
        for (int i = 0; i < 10; i++) {
            Zombie z = new Zombie();
            sleep(1);
            System.out.println("Zombi(" + i + "): " + z.getIdentificador());
        }

       Arma arma = new Arma();
        System.out.println(arma.getNombre());

        Posicion posicion = new Posicion(1,1);
        Posicion posicion2 = new Posicion(2,2);
        Posicion posicion3 = new Posicion(3,1);
        Posicion posicion4 = new Posicion(1,1);
        System.out.println(posicion.equals(posicion2));
        System.out.println(posicion.equals(posicion3));
        System.out.println(posicion.equals(posicion4));
        System.out.println(posicion.toString());

        Normal n1 = new Normal();
        System.out.println(n1.toString());

        Normal n2 = new Normal(TipoZombie.CORREDOR);
        Normal n3 = new Normal(TipoZombie.ABOMINACION);
        System.out.println(n2.equals(n3));
        System.out.println(n2.toString());
        System.out.println(n3.toString());


        Casilla c1 = new Casilla();
        Casilla c2 = new Casilla(posicion);
        Casilla c3 = new Casilla(posicion4);

        System.out.println(c1.toString());
        System.out.println(c2.toString());
        System.out.println(c3.toString());
        System.out.println(c1.equals(c2));
        System.out.println(c2.equals(c3));

        Tablero t1 = new Tablero();

        System.out.println(t1.toString());
    }
}
