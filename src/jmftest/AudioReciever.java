/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmftest;

import java.io.IOException;
import java.net.MalformedURLException;
import javax.media.ControllerListener;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import static sun.audio.AudioPlayer.player;

/**
 *
 * @author Nivrito
 */
public class AudioReciever {

    static Player player;

    AudioReciever() {
        try {
            MediaLocator loc = new MediaLocator("rtp://239.0.0.1:3000/audio");
            APlayer player = new APlayer(loc);
            System.out.println(" Press the Enter key to exit");
            player.play();
            System.in.read();
            System.out.println("-> Exiting");
            player.stop();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.exit(0);
    }


    /*public boolean createPlayer()   {
     String url= "rtp://239.0.0.1:3000";
 
     MediaLocator mrl= new MediaLocator(url);
         
     if (mrl == null) {
     System.err.println("NO MRL");
     return false;
     }
         
     // Player for this rtp session
     try {
     player = Manager.createPlayer(mrl);
     } catch (NoPlayerException e) {
     System.err.println("Error:" + e);
     return false;
     } catch (MalformedURLException e) {
     System.err.println("Error:" + e);
     return false;
     } catch (IOException e) {
     System.err.println("Error:" + e);
     return false;
     }
         
     if (player != null) {
     if (this.player == null) {

     this.player = player;
     player.addControllerListener((ControllerListener) this);
     player.realize();
     }
     }
     return true;
     }
     */
}
