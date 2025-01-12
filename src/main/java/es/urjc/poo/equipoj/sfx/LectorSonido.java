package es.urjc.poo.equipoj.sfx;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class LectorSonido {

    public static String SONIDO_CLICK = "/sfx/clickEfectoSonido.wav";
    public static String SONIDO_RESPIROALIVIO = "/sfx/respiroAlivioEfectoSonido.wav";
    public static String SONIDO_INVENTARIO = "/sfx/inventarioAbriendoyCerrandoseEfectoSonido.wav";
    public static String SONIDO_PASOS = "/sfx/pasosEfectoSonido.wav";
    public static String SONIDO_MORDISCO = "/sfx/mordiscoEfectoSonido.wav";
    public static String SONIDO_DISPARO ="/sfx/ataqueDisparosEfectoSonido.wav";
    public static String SONIDO_ATAQUECERCANO ="/sfx/ataqueCercanoEfectoSonido.wav";

    public void reproducirSonido(String fichero){
//        try {
//            File file = new File(Objects.requireNonNull(this.getClass().getResource(fichero)).toString());
//            AudioInputStream sonido = null;
//            sonido = AudioSystem.getAudioInputStream(file);
//
//            Clip clip = AudioSystem.getClip();
//            clip.open(sonido);
//            clip.setFramePosition(0);
//            clip.start();
//        } catch (UnsupportedAudioFileException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (LineUnavailableException e) {
//            throw new RuntimeException(e);
//        }
    }
}
