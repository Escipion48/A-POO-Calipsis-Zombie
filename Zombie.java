public class Zombie extends EntidadActivable{
    private static int nextId = 1;
    private int id;
    private int aguante;

    public Zombie(int acciones,int aguante){
        super(acciones);
        this.aguante = aguante;
        this.id = nextId++;
    }
    public Zombie(){
        super(1);
        this.aguante = 1;
        this.id = nextId++;
    }
    public int getId() {
        return id;
    }
    public int getAguante() {
        return aguante;
    }
    public void setAguante(int aguante) {
        this.aguante = aguante;
    }
    @Override
    public String toString() {
        return "Zombie[id= " + this.id + ", aguante= " + this.aguante + ", acciones= "+ this.getAcciones()+"]";
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Zombie zombie = (Zombie) o;
        return this.getId() == zombie.getId();
    }

}
