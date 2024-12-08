package es.urjc.poo.equipoj.entidades;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class Superviviente implements EntidadActivable{

    private int acciones;
    private String nombre;
    private TipoEstado estado;
    private Arma [] armasActivas;
    private Equipo [] inventario;
    private int contadorZombiesEliminados;
    private int heridas;
    private ArrayList<Zombie> zombiesEliminados;
    private ArrayList <Zombie> ataquesRecibidos;
    private Posicion posicion;

    public Superviviente(String nombre, TipoEstado estado, Arma[] armasActivas, Equipo [] inventario, int contadorZombiesEliminados, int heridas, ArrayList<Zombie> zombiesEliminados, ArrayList<Zombie> ataquesRecibidos, int acciones, Posicion posicion) {
        this.acciones = acciones;
        this.nombre = nombre;
        this.estado = estado;
        this.posicion = posicion;
        this.armasActivas = new Arma[2];
        this.armasActivas = armasActivas.clone();
        this.inventario = new Equipo[5];
        this.inventario=inventario.clone();
        this.contadorZombiesEliminados = contadorZombiesEliminados;
        this.heridas = heridas;
        this.zombiesEliminados = new ArrayList<Zombie>();
        this.zombiesEliminados.addAll(zombiesEliminados);
        this.ataquesRecibidos = new ArrayList<Zombie>();
        this.ataquesRecibidos.addAll(ataquesRecibidos);
    }

    public Superviviente(String nombre) {
        this(nombre, TipoEstado.VIVO, new Arma[2], new Equipo[5], 0,0,new ArrayList<Zombie>(), new ArrayList<Zombie>(),3,new Posicion());
    }

    public Superviviente(){
        this("SupervivienteDefault", TipoEstado.VIVO, new Arma[2], new Equipo[5], 0,0,new ArrayList<Zombie>(), new ArrayList<Zombie>(),3,new Posicion());
    }

    public void setAcciones(int acciones) {
        this.acciones = acciones;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEstado(TipoEstado estado) {
        this.estado = estado;
    }

    public void setArmasActivas(Arma[] armasActivas) {
        this.armasActivas = armasActivas.clone();
    }

    public void setInventario(Equipo[] inventario) {
        this.inventario = inventario.clone();
    }

    public void setContadorZombiesEliminados(int contadorZombiesEliminados) {
        this.contadorZombiesEliminados = contadorZombiesEliminados;
    }

    public void setHeridas(int heridas) {
        this.heridas = heridas;
    }

    public void setZombiesEliminados(ArrayList<Zombie> zombiesEliminados) {
        this.zombiesEliminados.addAll(zombiesEliminados);
    }
    public void anadirZombieElimninado(Zombie zombie){ this.zombiesEliminados.add(zombie);}

    public void setAtaquesRecibidos(ArrayList<Zombie> ataquesRecibidos) {
        this.ataquesRecibidos.addAll(ataquesRecibidos);
    }
    public void anadirContadorZombiesEliminados1(){this.contadorZombiesEliminados++;}

    public void anadirHeridas1(){ this.heridas++;}

    public int getAcciones() {
        return this.acciones;
    }

    public TipoEstado getEstado() {
        return this.estado;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Arma[] getArmasActivas() {
        return this.armasActivas.clone();
    }

    public Arma getArmaActiva(int indice) {
        return this.armasActivas[indice];
    }

    public Equipo[] getInventario() {
        return this.inventario.clone();
    }

    public Equipo getInventario(int indice) {
        return this.inventario[indice];
    }

    public int getContadorZombiesEliminados() {
        return this.contadorZombiesEliminados;
    }

    public int getHeridas() {
        return this.heridas;
    }

    public ArrayList <Zombie> getZombiesEliminados() {
        return this.zombiesEliminados;
    }

    public ArrayList <Zombie> getAtaquesRecibidos() {
        return this.ataquesRecibidos;
    }

    public Posicion getPosicion(){ return this.posicion; }

    public void setPosicion(Posicion posicion) { this.posicion = posicion; }


    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Superviviente superviviente = (Superviviente) o;
        if(this.getNombre()==superviviente.getNombre()) return true;
        return false;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder("Superviviente: "+this.getNombre()+"\n");
        str.append(estado+"\n"+"Armas activadas: \n");

        for(int i =0; i<2;i++){

            if(this.getArmaActiva(i)!=null){
                str.append(this.getArmaActiva(i).toString()+"\n");}

            else{
                str.append("Espacio vacio\n");}

        }

        str.append("Inventario: \n");
        for(int i = 0; i<5;i++){
            if(this.getInventario(i)!=null){
                str.append(this.getInventario(i).toString()+"\n");
            }
            else {
                str.append("Espacio vacio\n");
            }
        }

        str.append("Zombies eliminados: "+this.getContadorZombiesEliminados()+"\n");
        str.append("Heridas: "+this.getHeridas()+"\n");

        str.append("Zombies eliminados: \n");
        for(int i =0; i<this.getZombiesEliminados().size();i++){
            str.append(this.getZombiesEliminados().get(i).toString()+"\n");
        }
        str.append("Ataques recibidos: \n");
        for(int i =0; i<this.getAtaquesRecibidos().size();i++){
            str.append(this.getAtaquesRecibidos().get(i).toString()+"\n");
        }

        return str.toString();
    }

    @Override
    public void activarse() {
    //TODO: Implementar
    }

    @Override
    public void moverse() {
    //TODO: Implementar
    }

    @Override
    public void atacar() {
        if(this.acciones>0){
       resolverAtaque(new Tablero(),new Juego());
        this.acciones--;}
        else{System.out.println("No hay suficientes acciones\n");
    }}
    private Arma seleccionarArma() {
        System.out.println("Selecione un arma: \n"+this.getArmaActiva(0)+"\n"+this.getArmaActiva(1));
        Scanner sc = new Scanner(System.in);
        int n;
        do{
            n= sc.nextInt();
            if(n==0){
                System.out.println("Arma seleccionada: "+this.getArmaActiva(0));
            } else if (n==1) {
                System.out.println("Arma seleccionada: "+this.getArmaActiva(1));
            }else{
                System.out.println("Seleccione un arma:\n");}}
        while(n!=0 && n!=1);
        sc.close();
        return this.getArmaActiva(n);
    }
    private Casilla selecionarCasilla(ArrayList<Casilla> casillas,Tablero tablero) {
        System.out.println("Selecione una casilla: \n");
        for(int i=0; i<casillas.size(); i++){
            System.out.print("("+i+") "+casillas.get(i)+"\n");
        }
        Scanner sc = new Scanner(System.in);
        int casilla= sc.nextInt();
        Posicion p =casillas.get(casilla).getPosicion();
        int x= p.getPosicionX();
        int y= p.getPosicionY();
        sc.close();
        return tablero.getCasilla(x, y);

    }

    private int[] lanzarDados(Ataque ataque,Arma arma) {
        int n = arma.getNumeroDados();
        int[] nd= new int[n];
        ataque.setDados(nd);
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            ataque.dados[i]=0;
            ataque.dados[i] = random.nextInt(6) + 1;  }
        return ataque.dados;
    }

    private int evaluarExito(Arma arma,Ataque ataque) {
        int exitos=0;
        int []dados= lanzarDados(ataque,arma);
        for(int i=0 ; i<dados.length ; i++){
            if(dados[i]>=arma.getNumeroDados()){
                exitos++;
            }
        }
        return exitos;
    }

    private ArrayList<Casilla> obtenerCasillaDentroAlcance(Arma arma) {
        ArrayList<Casilla> casillaDentroAlcance= new ArrayList<>();
        Posicion pCentro =this.getPosicion();
        for(int i =pCentro.posicionX-arma.getAlcance(); i<pCentro.posicionX+arma.getAlcance();i++){
            for(int j =pCentro.posicionY-arma.getAlcance(); j<pCentro.posicionY+arma.getAlcance();j++){
                if(i>=0 && j>=0 && i<=9 && j<=9){
                    Posicion p1= new Posicion(i,j);
                    Casilla casilla= new Casilla(p1);
                    casillaDentroAlcance.add(casilla);
                }
            }
        }
        return casillaDentroAlcance;
    }

    private void resolverAtaque( Tablero tablero, Juego juego) {//No terminado
        Arma arma = seleccionarArma();
        ArrayList<Casilla> casillas =obtenerCasillaDentroAlcance(arma);
        int potencia = arma.getPotencia();
        Casilla casilla = selecionarCasilla(casillas,tablero);
        int n =arma.getNumeroDados();
        int[] nd= new int[n];
        Ataque ataque =new Ataque(nd,"");
        lanzarDados(ataque,arma);
        int num_exitos = evaluarExito(arma,ataque);
        ArrayList<Zombie> CasillaZombie= new ArrayList<Zombie>();
        ArrayList<Zombie> Eliminados = new ArrayList<Zombie>();

        for (Zombie zombie : juego.getZombies()) {
            if (casilla.getPosicion().equals(zombie.getPosicion())) {
                CasillaZombie.add(zombie);
            }
        }
        Iterator<Zombie> iterator =CasillaZombie.iterator();
        Zombie zombie = iterator.next();
        if(zombie instanceof Berserker && zombie.getAguante()<=potencia && this.getPosicion().equals(zombie.getPosicion())){
            iterator.remove();
            juego.getZombies().remove(zombie);
            Eliminados.add(zombie);
            this.anadirZombieElimninado(zombie);
            this.anadirContadorZombiesEliminados1();
        }else if(!(zombie instanceof Berserker) && zombie.getAguante()<=potencia) {
            if(zombie instanceof Toxico && this.getPosicion().equals(zombie.getPosicion())){
                this.anadirHeridas1();
            }
            iterator.remove();
            juego.getZombies().remove(zombie);
            Eliminados.add(zombie);
            this.anadirZombieElimninado(zombie);
            this.anadirContadorZombiesEliminados1();
        }

        StringBuilder stringBuilder = new StringBuilder("Se ha eliminado a los Zombies: \n");
        for (Zombie zombie1 : Eliminados) {
            stringBuilder.append(zombie1.toString()).append("\n"); }
        ataque.setResultado(stringBuilder.toString());
        System.out.println(ataque.getResultado());

    }



}
