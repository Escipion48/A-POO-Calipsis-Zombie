package es.urjc.poo.equipoj.entidades;

import junit.framework.TestCase;

import java.util.ArrayList;

public class SupervivienteTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testSetGetAcciones() {
        Superviviente superviviente = new Superviviente();
        superviviente.setAcciones(3);
        assertEquals(3, superviviente.getAcciones());

    }

    public void testSetGetNombre() {
        Superviviente superviviente = new Superviviente();
        superviviente.setNombre("Pepe");
        assertEquals("Pepe", superviviente.getNombre());
    }

    public void testSetGetEstado() {
        Superviviente superviviente = new Superviviente();
        superviviente.setEstado(TipoEstado.ELIMINADO);
        assertEquals(TipoEstado.ELIMINADO, superviviente.getEstado());
    }

    public void testSetGetArmasActivas() {
        Arma arma = new Arma();
        Arma [] listaArmas = new Arma[2];
        listaArmas[0] = arma;
        Superviviente superviviente = new Superviviente();
        superviviente.setArmasActivas(listaArmas);
        Arma []ArmaObtenida= superviviente.getArmasActivas();
        for (int i = 0; i < ArmaObtenida.length; i++) {
            assertEquals(listaArmas[i],ArmaObtenida[i]);
        }

    }

    public void testSetGetInventario() {
        Equipo [] inventario = new Equipo[5];
        Provision provision = new Provision();
        inventario[0] = provision;
        Arma arma = new Arma();
        inventario[1] = arma;
        Superviviente superviviente = new Superviviente();
        superviviente.setInventario(inventario);
        Equipo[] inventarioObtenido = superviviente.getInventario();
        for (int i = 0; i < inventario.length; i++) {
            assertEquals(inventario[i], inventarioObtenido[i]); }
    }

    public void testSetGetContadorZombiesEliminados() {
        int contadorZombiesEliminados = 9999;
        Superviviente superviviente = new Superviviente();
        superviviente.setContadorZombiesEliminados(contadorZombiesEliminados);
        assertEquals(9999, superviviente.getContadorZombiesEliminados());
    }

    public void testSetGetHeridas() {
        int heridas=1;
        Superviviente superviviente = new Superviviente();
        superviviente.setHeridas(heridas);
        assertEquals(1, superviviente.getHeridas());

    }

    public void testSetGetZombiesEliminados() {
        ArrayList<Zombie> ZombiesEliminados =new ArrayList<>();
        Zombie zombie = new Zombie();
        Zombie zombie2 = new Zombie();
        ZombiesEliminados.add(zombie);
        ZombiesEliminados.add(zombie2);
        Superviviente superviviente = new Superviviente();
        superviviente.setZombiesEliminados(ZombiesEliminados);
        ArrayList<Zombie> listaZombie= superviviente.getZombiesEliminados();
        for (int i = 0; i < listaZombie.size(); i++) {
            assertEquals(ZombiesEliminados.get(i), listaZombie.get(i));
        }
    }

    public void testSetGetAtaquesRecibidos() {
        ArrayList<Zombie> ataquesRecibidos =new ArrayList<>();
        Zombie zombie = new Zombie();
        Zombie zombie2 = new Zombie();
        ataquesRecibidos.add(zombie);
        ataquesRecibidos.add(zombie2);
        Superviviente superviviente = new Superviviente();
        superviviente.setZombiesEliminados(ataquesRecibidos);
        ArrayList<Zombie> listaZombie= superviviente.getZombiesEliminados();
        for (int i = 0; i < listaZombie.size(); i++) {
            assertEquals(ataquesRecibidos.get(i), listaZombie.get(i));
        }
    }

    public void testTestEquals() {
        Superviviente s1 = new Superviviente();
        Superviviente s2 = new Superviviente();
        assertEquals(true, s1.equals(s2));
        Superviviente s3 = new Superviviente("Pepe");
        assertEquals(false, s1.equals(s3));
    }

    public void testTestToString() {
        Superviviente s1 = new Superviviente("Pepe");
        String str ="Superviviente: Pepe\n" +
                "VIVO\n" +
                "Armas activadas: \n" +
                "Espacio vacio\n" +
                "Espacio vacio\n" +
                "Inventario: \n" +
                "Espacio vacio\n" +
                "Espacio vacio\n" +
                "Espacio vacio\n" +
                "Espacio vacio\n" +
                "Espacio vacio\n" +
                "Zombies eliminados: 0\n" +
                "Heridas: 0\n"+
                "Zombies eliminados: \n" +
                "Ataques recibidos: \n";
        assertEquals(str, s1.toString());
    }
}