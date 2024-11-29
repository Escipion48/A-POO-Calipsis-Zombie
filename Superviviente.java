import java.util.ArrayList;

public class Superviviente extends EntidadActivable{
    private String nombre;
    private boolean estado; // 0 o false vivo y 1 o true eliminado
    private Arma [] armasActivas;
    private Equipo [] inventario;
    private int contadorZombiesEliminados;
    private int heridas;
    private ArrayList <Zombie> zombiesEliminados;
    private ArrayList <Zombie> ataquesRecibidos;

    public Superviviente(String nombre, boolean estado, Arma[] armasActivas, Equipo [] inventario, int contadorZombiesEliminados, int heridas, ArrayList<Zombie> zombiesEliminados, ArrayList<Zombie> ataquesRecibidos, int acciones) {
        super(acciones);
        this.nombre = nombre;
        this.estado = estado;
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
        this(nombre, false, new Arma[2], new Equipo[5], 0,0,new ArrayList<Zombie>(), new ArrayList<Zombie>(),3);
    }

    public Superviviente(){
        this("SupervivienteDefault", false, new Arma[2], new Equipo[5], 0,0,new ArrayList<Zombie>(), new ArrayList<Zombie>(),3);
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEstado(boolean estado) {
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

    public void setAtaquesRecibidos(ArrayList<Zombie> ataquesRecibidos) {
        this.ataquesRecibidos.addAll(ataquesRecibidos);
    }

    public String getNombre() {
        return nombre;
    }

    public boolean getEstado() {
        return this.estado;
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
        String estado;
        if(this.getEstado()==true){
             estado = "Muerto";
        }
        else{
             estado = "Vivo";
        }

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
}
