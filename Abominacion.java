public class Abominacion extends Zombie{
    public Abominacion() {
        super(3,1);
    }
    @Override
    public String toString(){
        return "Abominacion" +"[id= " + this.getId() + ", aguante= " + this.getAguante() + ", acciones= "+ this.getAcciones()+"]";
    }
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Abominacion abominacion = (Abominacion) o;
        return this.getId() == abominacion.getId();
    }
}
