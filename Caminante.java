public class Caminante extends Zombie{
    public Caminante(){
        super(1,1);
    }

    @Override
    public String toString(){
        return "Caminante" +"[id= " + this.getId() + ", aguante= " + this.getAguante() + ", acciones= "+ this.getAcciones()+"]";
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Caminante caminante = (Caminante) o;
        return this.getId() == caminante.getId();
    }
}
