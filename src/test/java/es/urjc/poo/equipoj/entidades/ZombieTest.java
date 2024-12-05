package es.urjc.poo.equipoj.entidades;

import junit.framework.TestCase;

import static java.lang.Thread.sleep;

public class ZombieTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }


    public void testSetGetIdentificador() {
        Zombie z = new Zombie();
        z.setIdentificador(10);
        assertEquals(10, z.getIdentificador());
    }

    public void testSetGetAguante() {
        Zombie z = new Zombie();
        z.setAguante(10);
        assertEquals(10, z.getAguante());
    }

    public void testSetGetActivaciones() {
        Zombie z = new Zombie();
        z.setActivaciones(5);
        assertEquals(5, z.getActivaciones());
    }

    public void testSetGetTipo() {
        Zombie z = new Zombie();
        z.setTipo(TipoZombie.BERSERKER);
        assertEquals(TipoZombie.BERSERKER, z.getTipo());
    }


    public void testSetGetPosicion() {
        Zombie z = new Zombie();
        Posicion posicion= new Posicion(5,8);
        z.setPosicion(posicion);
        assertEquals(posicion, z.getPosicion());
    }

    public void testTestEquals() {
        Zombie z1 = new Zombie();
        Zombie z2 = new Zombie();
        assertEquals(false, z1.equals(z2));
        assertEquals(true, z1.equals(z1));
    }

    public void testTestToString() {
        Zombie z = new Zombie();
        Posicion posicion= new Posicion(5,8);
        z.setPosicion(posicion);
        z.setIdentificador(8);
        String str = "Zombie: 8 Posicion: [5,8] Tipo: NORMAL";
        assertEquals(str, z.toString());

    }
    public void testCrear(){
        Posicion posicion = new Posicion(5,8);
        Abominacion ab = new Abominacion(TipoZombie.NORMAL,posicion);
        assertEquals(3, ab.getAguante());

        Corredor corredor = new Corredor(TipoZombie.NORMAL,posicion);
        assertEquals(2, corredor.getActivaciones());
        assertEquals(1, corredor.getAguante());
        corredor.setActivaciones(5);
        assertEquals(5, corredor.getActivaciones());

        Abominacion abominacion = new Abominacion(TipoZombie.BERSERKER);
        assertEquals(3, abominacion.getAguante());

        Zombie zombie = new Zombie();
        System.out.println(zombie.toString());
        System.out.println(abominacion.toString());
        System.out.println(corredor);
        assertEquals(1, zombie.getAguante());
    }


    public void testCrearZombie(){
        boolean iguales=false;
        Posicion p1= new Posicion(0,0);
        Posicion p2= new Posicion(1,1);
        Posicion p3= new Posicion(2,2);

        Caminante c1= new Caminante(TipoZombie.NORMAL);
        Caminante c2= new Caminante(TipoZombie.TOXICO);
        Caminante c3= new Caminante(TipoZombie.BERSERKER);

        Corredor co1 = new Corredor(TipoZombie.NORMAL);
        Corredor co2 = new Corredor(TipoZombie.TOXICO);
        Corredor co3 = new Corredor(TipoZombie.BERSERKER);

        Abominacion ab1 = new Abominacion(TipoZombie.NORMAL);
        Abominacion ab2 = new Abominacion(TipoZombie.TOXICO);
        Abominacion ab3 = new Abominacion(TipoZombie.BERSERKER);
        long id1=c1.getIdentificador();
        long id2=c2.getIdentificador();
        long id3=c3.getIdentificador();
        long id4=co1.getIdentificador();
        long id5=co2.getIdentificador();
        long id6=co3.getIdentificador();
        long id7=ab1.getIdentificador();
        long id8=ab2.getIdentificador();
        long id9=ab3.getIdentificador();
        if(id1 == id2 || id1 == id3 || id2 == id3 || id1 == id4 || id4 == id5 || id5 == id6){
            iguales=true;
        }
        System.out.println(id1+"\n"+ id2+"\n"+id3+"\n"+id4+"\n"+id5+"\n"+id6+"\n"+id7+"\n"
                +id8+"\n"+id9+"\n");
        assertEquals(false, iguales);

    }
}