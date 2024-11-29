public class CorredorBerserker extends Corredor {
    private boolean distancia;// 0 o false si esta en la misma casilla y 1 o true si esta a distancia
    public CorredorBerserker() {
        super();
    }
    @Override
    public String toString(){
        return "Corredor Berserker"+"[id= " + this.getId() + ", aguante= " + this.getAguante() + ", acciones= "+ this.getAcciones()+"]";
    }
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        CorredorBerserker corredorBerserker= (CorredorBerserker) o;
        return this.getId() == corredorBerserker.getId();
    }
}
