public class CorredorToxico extends Corredor{
    public CorredorToxico() {
        super();
    }
    @Override
    public String toString(){
        return "Corredor Toxico"+"[id= " + this.getId() + ", aguante= " + this.getAguante() + ", acciones= "+ this.getAcciones()+"]";
    }
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        CorredorToxico corredorToxico= (CorredorToxico) o;
        return this.getId() == corredorToxico.getId();
    }
}
