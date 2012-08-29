

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Reed
 */
//Música de Fondo en Vista2 (NO IMPLEMENTADO AUN)
public class Music extends SwingWorker <Void,Void> {
    private boolean buc;
    private Void finish;
    private Clip audio1, audio2, audio3;
    public Music (){
        buc = true;
    }
    private void crearClips() throws Exception{
        audio1 = AudioSystem.getClip();
        audio2 = AudioSystem.getClip();
        audio3 = AudioSystem.getClip();
    }
    @Override
    protected Void doInBackground() throws Exception {
        boolean audio = false;
        crearClips();
        while (buc){
            audio1.open(AudioSystem.getAudioInputStream(new File (System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft\\resources\\music\\calm1.wav")));
            audio1.start();
            audio = audio1.isRunning();
            while (audio){
                Thread.sleep(1000);
                audio = audio1.isRunning();
            }
            audio1.close();
            audio2.open(AudioSystem.getAudioInputStream(new File (System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft\\resources\\music\\calm2.wav")));
            audio2.start();
            audio = audio2.isRunning();
            while (audio){
                Thread.sleep(1000);
                audio = audio2.isRunning();
            }
            audio2.close();
            audio3.open(AudioSystem.getAudioInputStream(new File (System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft\\resources\\music\\calm3.wav")));
            audio3.start();
            audio = audio3.isRunning();
            while (audio){
                Thread.sleep(1000);
                audio = audio3.isRunning();
            }
            audio3.close();
        }
        return finish;
    }
    public void cancelar(){
        buc = false;
        if (audio1.isRunning()){
            audio1.stop();
        } else if (audio2.isRunning()){
            audio2.stop();
        } else if (audio3.isRunning()){
            audio3.stop();
        }
        audio1.close();
        audio2.close();
        audio3.close();
        this.cancel(true);
    }
}
