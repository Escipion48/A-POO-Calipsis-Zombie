public class Provision extends Equipo{
    private String nombre;
    private int kcal;
    private int[] caducidad;
    private boolean tipo;  // 0 o false para alimento y 1 o true para bebida

    public Provision(String nombre, int kcal, int[] caducidad, boolean tipo){
        super();
        this.nombre = nombre;
        this.kcal = kcal;
        this.caducidad[0] = caducidad[0]; //Dia
        this.caducidad[1]= caducidad[1]; //Mes
        this.caducidad[2]= caducidad[2]; //Año
        this.tipo = tipo;
    }

    public Provision(){
        this("Provision por defecto",1,new int[]{1,1,2025},false);
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public void setKcal(int kcal){
        this.kcal = kcal;
    }
    
    public void setCaducidad(int[] caducidad){
        this.caducidad[0] = caducidad[0];
        this.caducidad[1]= caducidad[1];
        this.caducidad[2]= caducidad[2];
    }
    
    public void setTipo(boolean tipo){
        this.tipo = tipo;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public int getKcal(){
        return this.kcal;
    }
    
    public int getCaducidad(int posicion){
        return this.caducidad[posicion];
    }
    
    public boolean getTipo(){
        return this.tipo;
    }
    
    @Override
    public String toString(){
        String nombreTipo;
        if(this.getTipo()==false){
            nombreTipo = "Comida";
        }
        else{
            nombreTipo = "Bebida";
        }
        return("La provision: "+this.getNombre()+" tiene: "+this.getKcal()+"Kcal caduca: "+this.getCaducidad(0)+"/"+this.getCaducidad(1)+"/"+this.getCaducidad(2)+" Tipo: "+nombreTipo);
    }
    
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Provision provision = (Provision) o;
        if(this.getNombre()==provision.getNombre()) return true;
        return false;
    }
}
