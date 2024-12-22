package es.urjc.poo.equipoj;

import es.urjc.poo.equipoj.entidades.*;
import es.urjc.poo.equipoj.io.IO;

import java.util.ArrayList;

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


        int [] dados = new int[3];
        dados[0] = 1;
        dados[1] = 2;
        dados[2] = 3;
        Ataque a1 = new Ataque(dados, "S1 ataco a Z2");
        Ataque a2 = new Ataque(dados, "S2 ataco a Z3");
        Ataque a3 = new Ataque(dados, "S3 ataco a Z4");
        ArrayList<Ataque> ataques = new ArrayList<Ataque>();
        ataques.add(a1);
        ataques.add(a2);
        ataques.add(a3);
        Ataques ats = new Ataques(ataques);
        System.out.println(a1.toString());
        System.out.println(ats.toString());



        Superviviente s1 = new Superviviente();
        Casilla busqueda1 = new Casilla();
        Casilla busqueda2 = new Casilla(posicion);
        Casilla busqueda3 = new Casilla(posicion4);
        Casilla busqueda4 = new Casilla(posicion2);
        Casilla busqueda5 = new Casilla(posicion3);
        s1.buscar(busqueda1);
        s1.buscar(busqueda2);
        s1.buscar(busqueda3);
        s1.buscar(busqueda4);
        s1.buscar(busqueda5);
        System.out.println(s1.toString());

        Juego juego = new Juego();
        IO io = new IO();
        io.escribir(juego.toString(), "Prueba");

        Posicion pruebaPosicionLectura1 = io.posicionFromString("posicion=0/0");
        System.out.println(pruebaPosicionLectura1.toString());

        Casilla casillaPruebaLectura1 = io.casillaFromString("Casilla{posicion=5/1, explorada=false}");
        System.out.println(casillaPruebaLectura1.toString());

        Zombie z1 = io.zombieFromString("Zombie: 1734429076027 posicion=2/2 Tipo:CORREDOR Berserker");
        System.out.println(z1.toString());



        System.out.println("\n\n\n\n\n");
        Juego j1 = new Juego();
        j1.getSuperviviente(0).setInventario(arma,0);
        j1.getSuperviviente(0).setInventario(new Provision(),1);
        System.out.println(j1.toString());
        io.escribirJSON(j1,"Prueba");


        System.out.println(j1.getSuperviviente(0).getInventario(0).getClass().getSimpleName());
        System.out.println(j1.getSuperviviente(0).getInventario(0).getNombre());
        System.out.println("\n\n\nPrueba de carga:\n");
        Juego juego2 = io.leerJSON("Prueba");
        System.out.println(juego2.toString());
    }
}
