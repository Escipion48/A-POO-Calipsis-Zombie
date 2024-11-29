public class AbominacionBerserker extends Abominacion {
    private boolean distancia; // 0 o false si esta en la misma casilla y 1 o true si esta a distancia
    public AbominacionBerserker() {
        super();
    }
    @Override
    public String toString(){
        return "Abominacion Berserker" +"[id= " + this.getId() + ", aguante= " + this.getAguante() + ", acciones= "+ this.getAcciones()+"]";
    }
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        AbominacionBerserker abominacionBerserker = (AbominacionBerserker) o;
        return this.getId() == abominacionBerserker.getId();
    }
}
