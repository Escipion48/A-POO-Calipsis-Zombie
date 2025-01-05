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
        z.setTipo(TipoZombie.ABOMINACION);
        assertEquals(TipoZombie.ABOMINACION, z.getTipo());
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
        String str = "Zombie: 8 Posicion: [5,8] Tipo:CAMINANTE";
        assertEquals(str, z.toString());

    }
    public void testCrear(){
        Posicion posicion = new Posicion(5,8);
        Toxico t1 = new Toxico(TipoZombie.CAMINANTE,posicion);
        assertEquals(1, t1.getAguante());

        Berserker berserker = new Berserker(TipoZombie.CORREDOR,posicion);
        assertEquals(2, berserker.getActivaciones());
        assertEquals(1, berserker.getAguante());
        berserker.setActivaciones(5);
        assertEquals(5, berserker.getActivaciones());

        Toxico t2 = new Toxico(TipoZombie.ABOMINACION);
        assertEquals(3, t2.getAguante());

        Zombie zombie = new Zombie();
        System.out.println(zombie);
        System.out.println(t1);
        /*System.out.println(t2);*/
        assertEquals(1, zombie.getAguante());
    }


    public void testCrearZombie(){
        boolean iguales=false;
        Posicion p1= new Posicion(0,0);
        Posicion p2= new Posicion(1,1);
        Posicion p3= new Posicion(2,2);

        Normal c1= new Normal(TipoZombie.CAMINANTE);
        Normal c2= new Normal(TipoZombie.CORREDOR);
        Normal c3= new Normal(TipoZombie.ABOMINACION);

        Toxico co1 = new Toxico(TipoZombie.CAMINANTE);
        Toxico co2 = new Toxico(TipoZombie.CORREDOR);
        Toxico co3 = new Toxico(TipoZombie.ABOMINACION);

        Berserker ab1 = new Berserker(TipoZombie.CAMINANTE);
        Berserker ab2 = new Berserker(TipoZombie.CORREDOR);
        Berserker ab3 = new Berserker(TipoZombie.ABOMINACION);
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