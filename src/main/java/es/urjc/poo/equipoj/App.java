package es.urjc.poo.equipoj;
import es.urjc.poo.equipoj.images.LectorImagenes;
import es.urjc.poo.equipoj.ui.EntradaGUI;

import javax.swing.*;
import java.awt.*;


/**
 * Este juego ha sido creado como un trabajo de universidad, pido disculpas por los errores en el código y la falta de coherencia
 * en muchas partes, ha sido desarrollada por varias personas y debido a la falta de tiempo me he visto incapaz de corregir muchas cosas,
 * sin embargo, en un futuro intentaré ir cambiando cosas y arreglando otras, aunque no prometo nada. Si estás leyendo este texto
 * significa que has entrado para ver como está programado o para arreglar o añadir cosas, antes que nada, espero que tengas un buen día, :).
 * En la carpeta entidades se encuentra la lógica del juego, en imágenes y sfx evidentemente está el código encargado de esas partes,
 * en io se encuentra el código encargado de guardar y cargar archivos en json y en ui se encuentra la interfaz gráfica.
 * PD: sé que la ui es una chapuza y otras coas, pero me da mucha pereza rehacerlo, ya he perdido demasiado tiempo arreglando
 * esta atrocidad que parece haber sido escrita por un mono con autismo severo.
 *
 * De parte de Pablo Sainz López.
 *
 */

public class App {
 public static void main(String[] args) {
  SwingUtilities.invokeLater(() -> {
   EntradaGUI frame = new EntradaGUI();
   frame.setTitle("A-POO-Calipsis Zombie");
   // Cargar el ícono desde el classpath
   Image icono = Toolkit.getDefaultToolkit().getImage(EntradaGUI.class.getResource((LectorImagenes.ICONO_JUEGO)));
   frame.setIconImage(icono);
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
