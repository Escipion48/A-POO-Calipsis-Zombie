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
        z.setTipo(TipoZombie.CAMINANTE);
        assertEquals(TipoZombie.CAMINANTE, z.getTipo());
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
        String str = "Zombie: 8 Posicion: [5,8] Tipo: CAMINANTE";
        assertEquals(str, z.toString());

    }
    public void testCrearZombie(){
        boolean iguales=false;
        Posicion p1= new Posicion(0,0);
        Posicion p2= new Posicion(1,1);
        Posicion p3= new Posicion(2,2);

        Toxico t1 = new Toxico(TipoZombie.CAMINANTE,p1);
        Toxico t2 = new Toxico(TipoZombie.CORREDOR,p2);
        Normal n4 = new Normal(TipoZombie.CORREDOR,p3);
        Toxico t3 = new Toxico(TipoZombie.ABOMINACION,p3);

        Berserker b1 =new Berserker(TipoZombie.CAMINANTE,p1);
        Berserker b2 = new Berserker(TipoZombie.CORREDOR,p2);
        Berserker b3= new Berserker(TipoZombie.ABOMINACION,p3);
        Normal n1 = new Normal(TipoZombie.CAMINANTE,p1);
        Normal n2 = new Normal(TipoZombie.CORREDOR,p2);
        Berserker b4 = new Berserker(TipoZombie.ABOMINACION,p3);
        Toxico t4 = new Toxico(TipoZombie.ABOMINACION,p3);
        Normal n3 =new Normal(TipoZombie.ABOMINACION,p3);
        long id1=t1.getIdentificador();
        long id2=t2.getIdentificador();
        long id3=t3.getIdentificador();
        long id4=b1.getIdentificador();
        long id5=b2.getIdentificador();
        long id6=b3.getIdentificador();
        long id7=n1.getIdentificador();
        long id8=n2.getIdentificador();
        long id9=n3.getIdentificador();
        long id10=b4.getIdentificador();
        long id11=t4.getIdentificador();
        long id12 = n4.getIdentificador();
        if(id1 == id2 || id1 == id3 || id2 == id3 || id1 == id4 || id4 == id5 || id5 == id6){
            iguales=true;
        }
        System.out.println(id1+"\n"+ id2+"\n"+id3+"\n"+id4+"\n"+id5+"\n"+id6+"\n"+id7+"\n"
                +id8+"\n"+id9+"\n"+id10+"\n"+id11+"\n"+id12);
        assertEquals(false, iguales);

    }
}