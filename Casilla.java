public class Casilla {
    private int[] posicion;
    private boolean buscado; //0 o false si no se ha realizado una busqueda en esta casilla, 1 o true si se ha realizado una busqueda
    private Equipo tesoro;

    public Casilla (int[]posicion, boolean buscado, Equipo tesoro) {
        this.posicion= new int[2];
        this.posicion = posicion.clone();
        this.buscado = buscado;
        this.tesoro = tesoro;
    }
    public Casilla(){
        this(new int [2],false,null);
    }
    public int[] getPosicion() {
        return posicion;
    }
    public void setPosicion(int[] posicion) {
        this.posicion = posicion.clone();
    }
    public boolean getBuscado() {
        return buscado;
    }
    public void setBuscado(boolean buscado) {
        this.buscado = buscado;
    }
    public Equipo getTesoro() {
        return tesoro;
    }
    public void setTesoro(Equipo tesoro) {
        this.tesoro = tesoro;
    }

    @Override
    public String toString(){
        String Str_buscado;
        if(this.getBuscado()==false) {
            Str_buscado = "No se ha realizado una busqueda en la casilla: ";
            return (Str_buscado + "(" + this.posicion[0] + "," + this.posicion[1] + ")");
        }else{
            Str_buscado="Se ha realizado una busqueda en la casilla: ";
            return (Str_buscado + "(" + this.posicion[0] + "," + this.posicion[1] + ") y se ha encontrado el Equipo: "+ this.tesoro);
        }
    }

    @Override
    public boolean equals(Object o) {
        if(this==o){
            return true;
        }
        if(o==null){
            return false;
        }
        if(this.getClass() != o.getClass()){
            return false;
        }
        Casilla c = (Casilla) o;
        return this.posicion[0] == c.posicion[0] && this.posicion[1] == c.posicion[1];
    }
}
