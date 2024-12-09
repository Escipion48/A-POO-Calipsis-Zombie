package es.urjc.poo.equipoj.entidades;

import java.util.ArrayList;
import java.util.Random;

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

    public void setInventario(Equipo inventario, int posicion) {
        this.inventario[posicion] = inventario;
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

    public void setAtaquesRecibidos(ArrayList<Zombie> ataquesRecibidos) {
        this.ataquesRecibidos.addAll(ataquesRecibidos);
    }

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
    //TODO: Implementar
    }




    /**
     * Esta funcion es la funcion pública que se encarga de buscar objetos en una casilla por parte del superviviente
     * Valorara si la casilla ha sido buscada o no y si el inventario esta lleno o no antes de continuar, para evitar fallos
     * Además el valor de exito de la busqueda dependera de la cantidad de objetos que tenemos en el inventario, a mayor
     * cantidad de objetos menos probable será una busqueda en la que obtengamos un Equipo.
     * @param casilla
     * @return Si se ha realizado correctamente la busqueda se devolvera true y viceversa (Para controlar fallos)
     */
    public boolean buscar(Casilla casilla){
        if(casilla.isExplorada()==true || this.calcularNumeroObjetosInventario()==5){
            return false;
        }
        else{
            casilla.setExplorada(true);
             Random r = new Random();
             int valorExito = r.nextInt(11);
             if(valorExito-this.calcularNumeroObjetosInventario()>=5){
                 this.setInventario(buscarObjeto(),this.calcularNumeroObjetosInventario());
             }
             else{
                 //No hace nada, ya que el superviviente no ha encontrado nada.
             }
             return true;
        }
    }

    /**Esta funcion se encarga de hacer que devuelva un 50/50 de posibilidades un arma o un equipo, utilizando los
     * metodos programados posteriormente de buscarEquipo y armaAleatoria
     *
     * @return Un equipo aleatorio
     */

    private Equipo buscarObjeto(){
        Random r = new Random();
        int selector = r.nextInt(2);
        if(selector==1){
            return buscarEquipo();
        }
        else{
            return armaAleatoria();
        }

    }


    /**Esta funcion la usaremos para retornar un equipo aleatorio, ya sea un arma o provision, para ahorrar codigo
     * dentro de buscar. Como no se especifica en la practica las probabilidades las hemos creado como veiamos mas conveniente
     * Utilizamos otros metodos privados, este metodo solo se encarga de seleccionar el tipo de equipo que devolvera de forma aleatoria
     * @return Un equipo aleatorio arma o provision
     */
    private Equipo buscarEquipo(){
        Random random = new Random();
        int tipoEquipo = random.nextInt(1); // 0 provision, 1 arma.
        if(tipoEquipo == 0){
            int comidaBebida = random.nextInt(1); //0 comida 1 bebida;
            if(comidaBebida == 0){
                return new Provision(nombreComidaAleatoria(),random.nextInt(500)+50,fechaAleatoria(),false);
            }
            else{
                return new Provision(nombreBebidaAleatoria(),random.nextInt(150)+20,fechaAleatoria(),true);
            }
        }
        else{
            return armaAleatoria();
        }
    }

    /**
     * Esta funcion se encarga de crear una fecha aleatoria que usaremos para crear una provision aleatoria en
     * buscar equipo.
     * @return Una fecha aleatoria desde el año 2000 hasta el 2100
     */
    private int [] fechaAleatoria(){
        int [] fecha = new int[3];
        Random r = new Random();
        fecha[0] = r.nextInt(30);
        fecha[1] = r.nextInt(12);
        fecha[2] = r.nextInt(100);
        if(fecha[0]==0){
            fecha[0]++;
        }
        if(fecha[1]==0){
            fecha[1]++;
        }
        fecha[2]+= 2000;
        return fecha;
    }

    /**
     * Esta funcion tiene un conjunto de nombres de comidas posibles que devolvera de manera aleatoria.
     * @return String con un nombre de una comida
     */
    private String nombreComidaAleatoria(){
        Random r = new Random();
        int selector = r.nextInt(20);
        switch(selector){
            case 0:
                return ("Cocido Madrileño");


            case 1:
                return ("Albondigas");


            case 2:
                return ("Cecina");

            case 3:
                return ("Totilla de Patata");

            case 4:
                return ("Paella");

            case 5:
                return ("Patatas Bravas");
            case 6:
                return ("Croquetas");

            case 7:
                return ("Churros");

            case 8:
                return ("Pulpo a la Gallega");

            case 9:
                return ("Cucarachas");

            case 10:
                return ("Migas");

            case 11:
                return ("Fabada");

            case 12:
                return ("Cachopo");

            case 13:
                return ("torrijas");

            case 14:
                return ("Mejillones");

            case 15:
                return ("Pastel");

            case 16:
                return("Pan");

            case 17:
                return ("Canelones");

            case 18:
                return ("Cola de rata");

            case 19:
                return("Gusanos");
        }
        return ("ComidaDefault");
    }


    /**
     * Esta funcion generara de manera aleatoria el nombre de una bebida
     * @return Devuelve un String con el nombre de una bebida
     */
    private String nombreBebidaAleatoria(){
        Random r = new Random();
        int selector = r.nextInt(20);
        switch(selector){
            case 0:
                return ("Cerveza");

            case 1:
                return ("Lejia");

            case 2:
                return ("Batido de proteinas");

            case 3:
                return ("Toro rojo");

            case 4:
                return ("Whiskey");

            case 5:
                return ("Infusion");

            case 6:
                return ("Caldo extraño");


            case 7:
                return ("Sopa de lagarto");


            case 8:
                return ("Kola");


            case 9:
                return ("Bebida isotonica");


            case 10:
                return("Ron");


            case 11:
                return("Vodka");


            case 12:
                return("Agua mineral");


            case 13:
                return("Salsa de tomate");


            case 14:
                return("Pure de verduras");


            case 15:
                return("Leche");


            case 16:
                return("Horchata");


            case 17:
                return("Tinto de verano");


            case 18:
                return("Sidra");


            case 19:
                return("Zumo");

        }
        return ("BebidaDefault");
    }


    /**
     * Esta funcion se encarga de crear un Arma aleatoria
     * @return Un Arma
     */
    private Arma armaAleatoria(){
        Random r = new Random();
        int selector = r.nextInt(17);
        switch(selector){
            //Ordenados por alcance
            case 0:
                return new Arma("Cuchillo", 1, 0, 1, 1);

            case 1:
                return new Arma("Hacha", 2, 0, 1,2);

            case 2:
                return new Arma("Bate",1,0,2,1);

            case 3:
                return new Arma("Navaja",1,0,2,2);

            case 4:
                return new Arma("Puño americano",1,0,3,3);

            case 5:
                return new Arma("Pistola",1,2,3,4);

            case 6:
                return new Arma("Revolver",1,2,2,4);

            case 7:
                return new Arma("Subfusil",1,2,10,5);

            case 8:
                return new Arma("Granada",3,2,4,2);

            case 9:
                return new Arma("Motolotov",1,2,4,2);

            case 10:
                return new Arma("Rifle",1,4,1,4);

            case 11:
                return new Arma("Fusil de Asalto", 1, 4,3,4);

            case 12:
                return new Arma("Bazooka", 4, 4, 4, 5);

            case 13:
                return new Arma("Fusil antimaterial", 4, 4,1, 5);

            case 14:
                return new Arma ("Sniper", 1, 8,1,4);
            //Armas especiales más adelante;
            case 15:
                return new Arma("Globo",0,0,1,1);

            case 16:
                return new Arma("Trompeta",0,8,1,1);

        }
        return new Arma();
    }

    /**
     * Funcion auxiliar para calcular el numero de objetos que tenemos en el inventario
     * @return Un entero con el numero de Objetos del inventario
     */
    private int calcularNumeroObjetosInventario(){
        int contador = 0;
        for(int i=0;i<5;i++){
            if(this.getInventario(i)!=null){
                contador++;
            }
        }
        return contador;
    }

}
