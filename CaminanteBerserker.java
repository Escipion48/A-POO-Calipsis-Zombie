public class CaminanteBerserker extends Caminante {
    private boolean distancia; // 0 o false si esta en la misma casilla y 1 o true si esta a distancia
    public CaminanteBerserker() {
        super();
    }

    @Override
    public String toString(){
        return "Caminante Berserker" +"[id= " + this.getId() + ", aguante= " + this.getAguante() + ", acciones= "+ this.getAcciones()+"]";
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        CaminanteBerserker caminanteBerserker = (CaminanteBerserker) o;
        return this.getId() == caminanteBerserker.getId();
    }

}
