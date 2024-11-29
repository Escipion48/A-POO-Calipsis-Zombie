public class AbominacionToxico extends Abominacion{
    public AbominacionToxico(){
        super();
    }
    @Override
    public String toString(){
        return "Abominacion Toxico" +"[id= " + this.getId() + ", aguante= " + this.getAguante() + ", acciones= "+ this.getAcciones()+"]";
    }
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        AbominacionToxico abominacionToxico = (AbominacionToxico) o;
        return this.getId() == abominacionToxico.getId();
    }
}
