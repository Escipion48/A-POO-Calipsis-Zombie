public class AbominacionNormal  extends Abominacion{
    public AbominacionNormal(){
        super();
    }
    @Override
    public String toString(){
        return "Abominacion Normal" +"[id= " + this.getId() + ", aguante= " + this.getAguante() + ", acciones= "+ this.getAcciones()+"]";
    }
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        AbominacionNormal abominacionNormal = (AbominacionNormal) o;
        return this.getId() == abominacionNormal.getId();
    }
}
