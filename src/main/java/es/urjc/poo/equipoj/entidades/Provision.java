package es.urjc.poo.equipoj.entidades;

import java.time.LocalDate;

public class Provision extends Equipo{
    private int kcal;
    private int[] caducidad;
    private boolean tipo;  // 0 o false para alimento y 1 o true para bebida

    public Provision(String nombre, int kcal, int[] caducidad, boolean tipo){
        super(nombre);
        this.kcal = kcal;
        this.caducidad = new int[3];
        this.caducidad=caducidad.clone();
        this.tipo = tipo;
    }

    public Provision(){
        this("Provision por defecto",1,new int[]{1,1,2025},false);
    }

    public void setKcal(int kcal){
        this.kcal = kcal;
    }

    public void setCaducidad(int[] caducidad){
        caducidad = caducidad.clone();
    }

    public void setTipo(boolean tipo){
        this.tipo = tipo;
    }

    public int getKcal(){
        return this.kcal;
    }

    public int[] getCaducidad(){
        return this.caducidad.clone();
    }

    public int getCaducidad(int posicion){
        return this.caducidad[posicion];
    }

    public boolean getTipo(){
        return this.tipo;
    }

    /**
     * Calcula si la comida está caducada con la fecha que se pasa
     * Le pasaremos la fecha actual del ordenador en tiempo de ejecución
     * @return Booleano con un cierto o falso si esta caducado o no
     */
    public boolean noCaducado(LocalDate fechaActual){

        if(this.getCaducidad(2)>fechaActual.getYear()){
            return true;
        }

        else if (this.getCaducidad(1)>fechaActual.getDayOfMonth()) {
            return true;
        }

        else if (this.getCaducidad(0)>=fechaActual.getDayOfMonth()) {
            return true;
        }

        return false;
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
        return("Provision: "+this.getNombre()+" [Tipo: "+nombreTipo+", " +this.getKcal()+"Kcal, caduca: "+this.getCaducidad(0)+"/"+this.getCaducidad(1)+"/"+this.getCaducidad(2)+"]");
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