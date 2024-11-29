public class Zombie extends EntidadActivable{
    private int id;
    private int aguante;

    public Zombie( int aguante){
        super(1);
        this.aguante = aguante;
        this.id = id++;
    }
    public Zombie(){
        super(1);
        this.aguante = 1;
        this.id = id++;
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
        return "Zombie [id=" + this.id + ", aguante=" + this.aguante + "]";
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Zombie zombie = (Zombie) o;
        return this.getId() == zombie.getId();
    }

}
