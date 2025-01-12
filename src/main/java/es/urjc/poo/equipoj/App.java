package es.urjc.poo.equipoj;
import es.urjc.poo.equipoj.images.LectorImagenes;
import es.urjc.poo.equipoj.ui.EntradaGUI;

import javax.swing.*;
import java.awt.*;

public class App {
 public static void main(String[] args) {
  SwingUtilities.invokeLater(() -> {
   EntradaGUI frame = new EntradaGUI();
   frame.setTitle("A-POO-Calipsis Zombie");
   frame.setIconImage(((ImageIcon) new LectorImagenes().CargarIcono(LectorImagenes.ICONO_JUEGO)).getImage().getScaledInstance(64,64, Image.SCALE_SMOOTH));
   Dimension dimensionPantalla = Toolkit.getDefaultToolkit().getScreenSize();
   frame.setPreferredSize(new Dimension(dimensionPantalla.width/2, dimensionPantalla.height/2));
   frame.setSize(dimensionPantalla.width/2, dimensionPantalla.height/2);
   frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
   frame.setLocationRelativeTo(null);
   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   frame.setVisible(true);
  });
 }
}
