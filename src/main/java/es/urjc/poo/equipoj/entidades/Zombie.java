package es.urjc.poo.equipoj.entidades;

import java.util.ArrayList;
import java.util.Objects;

public class Zombie implements EntidadActivable {

    private long identificador;
    private int aguante;
    private int activaciones;
    private TipoZombie tipo;
    private Posicion posicion;

    public Zombie() {
        this(System.currentTimeMillis(),TipoZombie.CAMINANTE, new Posicion());

    }

    public Zombie(TipoZombie tipo, Posicion posicion) {
        this(System.currentTimeMillis(),tipo,posicion);
    }

    //Deseamos usar este constructor dentro de otros constructores, principalmente el de tipo y posicion.
    private Zombie (long identificador, TipoZombie tipo) {
        this.identificador = identificador;
        this.tipo = tipo;
        if(tipo == TipoZombie.CAMINANTE){
            this.aguante = 1;
            this.activaciones = 1;
        }
        if(tipo == TipoZombie.CORREDOR){
            this.aguante = 1;
            this.activaciones = 2;
        }
        if(tipo == TipoZombie.ABOMINACION){
            this.aguante = 3;
            this.activaciones = 1;
        }
    }

    public Zombie (long identificador, TipoZombie tipo, Posicion posicion) {
        this(identificador,tipo);
        this.posicion = posicion;
    }


    //Lo podemos llegar a utilizar para hacer zombies distintos o para pruebas posteriores.
    public Zombie(long identificador, int aguante, int activaciones, TipoZombie tipo, Posicion posicion) {
        this.identificador = identificador;
        this.aguante = aguante;
        this.activaciones = activaciones;
        this.tipo = tipo;
        this.posicion = posicion;
    }

    public long getIdentificador() {
        return this.identificador;
    }

    public void setIdentificador(long identificador) {
        this.identificador = identificador;
    }

    public int getAguante() {
        return this.aguante;
    }

    public void setAguante(int aguante) {
        this.aguante = aguante;
    }

    public int getActivaciones() {
        return this.activaciones;
    }

    public void setActivaciones(int activaciones) {
        this.activaciones = activaciones;
    }

    public TipoZombie getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoZombie tipo) {
        this.tipo = tipo;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zombie zombie = (Zombie) o;
        return getIdentificador() == zombie.getIdentificador();
    }

    @Override
    public String toString() {
        return ("Zombie: "+this.getIdentificador()+" Posicion: "+this.getPosicion().toString()+" Tipo:"+this.getTipo());
    }

    public void reaccionAtaque() {
        //TODO: Implementar
    }


    //De momento solo esta la logica general de activarse, falta moverse y atacar.
    @Override
    public void activarse(Tablero tablero, ArrayList<EntidadActivable> entidades) {

        this.recuperarActivaciones();
        ArrayList<Superviviente> supervivientes = this.pasarEntidadesASupervivientes(entidades);

        do{
            if(this.getSupervivienteEnMismaCasilla(supervivientes)==null){
            this.moverse(entidades);
            }
            else{
                this.atacar();
            }

        }while(activaciones > 0);
    }

    @Override
    public void moverse(ArrayList<EntidadActivable> entidades) {
    //TODO: Implementar

        //Creamos las coordenadas con la posicion actual del zombie con las que operaremos.
        int posicionX = this.getPosicion().posicionX;
        int posicionY = this.getPosicion().posicionY;

        //Obtenemos el superviviente al cual el zombie se dirigirá
        Superviviente supervivienteObjetivo = this.getSupervivienteAlQueDirigirse(this.pasarEntidadesASupervivientes(entidades));


        //Sabiendo el superviviente podemos comparar su posicion respecto a la del zombie y operar en consecuencia.
        if(supervivienteObjetivo.getPosicion().posicionX>posicionX){
            posicionX++;
        }
        if(supervivienteObjetivo.getPosicion().getPosicionX()<posicionX){
            posicionX--;
        }

        if(supervivienteObjetivo.getPosicion().posicionY>posicionY){
            posicionY++;
        }
        if(supervivienteObjetivo.getPosicion().getPosicionY()<posicionY){
            posicionY--;
        }

        //Una vez tener las coordenadas de la posicion a donde irá el zombie creamos una nueva posicion y la ponemos en zombie
        Posicion posicion = new Posicion(posicionX,posicionY);
        this.setPosicion(posicion);

        //Finalmente quitamos al zombie una activacion
        this.setActivaciones(this.getActivaciones() - 1);
    }

    @Override
    public void atacar() {
    //TODO: Implementar
    }


    /**
     * Establece el número de activaciones según el tipo de Zombie.
     */
    private void recuperarActivaciones(){
        if(this.getTipo() == TipoZombie.CORREDOR){
            this.activaciones=2;
        }
        else{
            this.activaciones=1;
        }
    }



    /**
     * Transforma el array de entidadesActivables a Supervivientes, solo cogiendo instancias de tipo Superviviente
     * @param entidades
     * @return ArrayLIst con los supervivientes del juego
     */
    private ArrayList<Superviviente> pasarEntidadesASupervivientes(ArrayList<EntidadActivable> entidades) {
        ArrayList<Superviviente> supervivientes = new ArrayList<>();
        for(EntidadActivable entidad : entidades){
            if(entidad instanceof Superviviente){
                supervivientes.add((Superviviente) entidad);
            }
        }
        return supervivientes;
    }


    /**
     * este metodo se encarga de devolver el superviviente de la misma casilla en la que se encuentra el zombie
     * devolvera un null si no hay supervivientes en su casilla. Además, si en la misma casilla en la que esta
     * un zombie hay varios supervivientes, estos eligiran al superviviente con más mordeduras, o con menos vida
     * @param supervivientes Supervivientes del juego
     * @return
     */
    private Superviviente getSupervivienteEnMismaCasilla(ArrayList<Superviviente> supervivientes){

        Superviviente supervivienteMismaCasilla = null;

        for(Superviviente superviviente : supervivientes){

            if(superviviente.getPosicion().equals(this.getPosicion())){

                if(supervivienteMismaCasilla == null){

                    supervivienteMismaCasilla = superviviente;

                }
                else{
                    supervivienteMismaCasilla = supervivienteMismaCasilla.cualTieneMasMordeduras(superviviente);
                }
            }
        }
        return supervivienteMismaCasilla;
    }


    /**
     * Este metodo devuelve el superviviente hacia el cual el zombie se va ha dirigir.
     * Tenemos en cuenta que ya se ha comprobado previamente que no hay supervivientes
     * dentro de la posicion donde se encuentra el zombie, por lo que empezamos a mirar
     * en las casillas adyacentes en adelante.
     * @param supervivientes
     * @return el superviviente más cercano y con más mordeduras
     */
    private Superviviente getSupervivienteAlQueDirigirse(ArrayList<Superviviente> supervivientes){
        int distancia = 1;
        Superviviente supervivienteObjetivo = null;
        do{
            for(Superviviente superviviente : supervivientes){
                if(this.getPosicion().comprobarDentroDeDistancia(superviviente.getPosicion(),distancia)==true){
                    if(supervivienteObjetivo == null){
                        supervivienteObjetivo = superviviente;
                    }
                    else{
                        supervivienteObjetivo = supervivienteObjetivo.cualTieneMasMordeduras(superviviente);
                    }
                }
                else{
                    //No hace nada, ya que no se encuentra ningún superviviente dentro de la distancia
                }
            }
            distancia++;
        }while(supervivienteObjetivo == null);

        return supervivienteObjetivo;
    }


}
