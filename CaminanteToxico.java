public class CaminanteToxico extends Caminante{
    public CaminanteToxico(){
        super();
    }


    @Override
    public String toString(){
        return "Caminante Toxico" +"[id= " + this.getId() + ", aguante= " + this.getAguante() + ", acciones= "+ this.getAcciones()+"]";
    }
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        CaminanteToxico caminanteToxico = (CaminanteToxico) o;
        return this.getId() == caminanteToxico.getId();
    }

}
