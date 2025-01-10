package es.urjc.poo.equipoj.sfx;

import javax.sound.sampled.*;
import javax.sound.sampled.spi.AudioFileReader;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;

public class LectorSonido {

    public static void reproducirClick(){
        File ficheroClick = new File("src/main/java/es/urjc/poo/equipoj/sfx/clickEfectoSonido.wav");

        AudioInputStream sonido = null;
        try {
            sonido = AudioSystem.getAudioInputStream(ficheroClick);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }


        try {
            clip.open(sonido);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        clip.setFramePosition(0);
        clip.start();
    }

    public static void reproducirRespiroAlivio(){
        File ficheroClick = new File("src/main/java/es/urjc/poo/equipoj/sfx/respiroAlivioEfectoSonido.wav");

        AudioInputStream sonido = null;
        try {
            sonido = AudioSystem.getAudioInputStream(ficheroClick);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }


        try {
            clip.open(sonido);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        clip.setFramePosition(0);
        clip.start();
    }

    public static void reproducirInventario(){
        File ficheroClick = new File("src/main/java/es/urjc/poo/equipoj/sfx/inventarioAbriendoyCerrandoseEfectoSonido.wav");

        AudioInputStream sonido = null;
        try {
            sonido = AudioSystem.getAudioInputStream(ficheroClick);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }


        try {
            clip.open(sonido);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        clip.setFramePosition(0);
        clip.start();

    }

    public static void reproducirPasosSonido(){
        File ficheroClick = new File("src/main/java/es/urjc/poo/equipoj/sfx/pasosEfectoSonido.wav");

        AudioInputStream sonido = null;
        try {
            sonido = AudioSystem.getAudioInputStream(ficheroClick);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }


        try {
            clip.open(sonido);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        clip.setFramePosition(0);
        clip.start();

    }

    public static void reproducirMordiscoSonido(){
        File ficheroClick = new File("src/main/java/es/urjc/poo/equipoj/sfx/mordiscoEfectoSonido.wav");

        AudioInputStream sonido = null;
        try {
            sonido = AudioSystem.getAudioInputStream(ficheroClick);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }


        try {
            clip.open(sonido);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        clip.setFramePosition(0);
        clip.start();
    }
}
