public class Posicion {
    int posicionX = 0;
    int posicionY = 0;

    public Posicion(int posicionX, int posicionY) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    public Posicion() {
        this.posicionX = 0;
        this.posicionY = 0;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }

    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;
    }

    @Override
    public String toString() {
        return (this.posicionX + "/" + this.posicionY);
    }

    @Override
    public boolean equals(Object o) {
        if(this==o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Posicion posicion = (Posicion) o;
        return getPosicionX() == posicion.getPosicionX() && getPosicionY() == posicion.getPosicionY();
    }

}
