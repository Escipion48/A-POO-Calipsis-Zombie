package es.urjc.poo.equipoj;
import es.urjc.poo.equipoj.ui.EntradaGUI;

import javax.swing.*;

public class App {
 public static void main(String[] args) {
  SwingUtilities.invokeLater(() -> {
   EntradaGUI frame = new EntradaGUI();
   frame.setTitle("Partida");
   frame.setSize(1000, 800);
   frame.setLocationRelativeTo(null);
   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   frame.setVisible(true);
  });
 }
}
