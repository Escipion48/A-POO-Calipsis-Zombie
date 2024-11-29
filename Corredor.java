public class Corredor extends Zombie{
    public Corredor() {
        super(2,1);
    }
    @Override
    public String toString(){
        return "Corredor"+"[id= " + this.getId() + ", aguante=" + this.getAguante() + ", acciones"+ this.getAcciones()+"]";
    }
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Corredor corredor= (Corredor) o;
        return this.getId() == corredor.getId();
    }
}
