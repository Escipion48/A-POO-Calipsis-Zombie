package es.urjc.poo.equipoj.images;

import javax.swing.*;
import java.awt.*;

public class PanelConFondo extends JPanel {
    Image fondo;
    private int alto, ancho;
    public PanelConFondo(Image fondo, int ancho, int alto) {
        this.fondo = fondo;
        this.alto = alto;
        this.ancho = ancho;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondo, 0, 0,this.ancho,this.alto, this);
    }
}
