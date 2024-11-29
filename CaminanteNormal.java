public class CaminanteNormal extends Caminante{
   public CaminanteNormal(){
       super();

   }

    @Override
    public String toString(){
        return "Caminante Normal" +"[id= " + this.getId() + ", aguante= " + this.getAguante() + ", acciones= "+ this.getAcciones()+"]";
    }
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        CaminanteNormal caminanteNormal = (CaminanteNormal) o;
        return this.getId() == caminanteNormal.getId();
    }

}
