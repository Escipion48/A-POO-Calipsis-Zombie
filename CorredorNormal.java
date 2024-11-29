public class CorredorNormal extends Corredor{
    public CorredorNormal(){
        super();
    }
    @Override
    public String toString(){
        return "Corredor Normal"+"[id= " + this.getId() + ", aguante= " + this.getAguante() + ", acciones= "+ this.getAcciones()+"]";
    }
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        CorredorNormal corredorNormal= (CorredorNormal) o;
        return this.getId() == corredorNormal.getId();
    }
}
