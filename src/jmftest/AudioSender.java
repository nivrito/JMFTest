/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmftest;

import java.io.IOException;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.DataSink;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoDataSinkException;
import javax.media.NoDataSourceException;
import javax.media.NoProcessorException;
import javax.media.NotRealizedError;
import javax.media.Processor;
import javax.media.control.FormatControl;
import javax.media.control.StreamWriterControl;
import javax.media.control.TrackControl;
import javax.media.format.AudioFormat;
import javax.media.protocol.DataSource;
import javax.media.protocol.FileTypeDescriptor;
import javax.print.attribute.standard.Media;

/**
 *
 * @author Nivrito
 */
public class AudioSender {

    static DataSource sound = null;
    static Processor proc;
    static DataSink DS;
    static Thread t = Thread.currentThread();

    AudioSender() throws IOException, NoProcessorException, InterruptedException, NoDataSinkException {

        try {
            sound = Manager.createDataSource(new MediaLocator("javasound://44100"));

        } catch (NoDataSourceException ex) {
            System.out.println("NO DS");
        } catch (IOException ex) {
            System.out.println("IO");;
        }

        sound.connect();
        proc = Manager.createProcessor(sound);
        proc.configure();
        t.sleep(5000);
        System.out.println("Configured");
        proc.setContentDescriptor(new FileTypeDescriptor(FileTypeDescriptor.RAW_RTP));
        proc.realize();
        Thread.sleep(5000);
        System.out.println("Realized");
        /*
        DataSink s = Manager.createDataSink(proc.getDataOutput(), new MediaLocator("file://E:/test.wav"));
        System.out.println("DATASINK SET");
        
        StreamWriterControl swc = (StreamWriterControl) proc.getControl("javax.media.control.StreamWriterControl");
        
        if (swc != null) {
            swc.setStreamSizeLimit(5000000);
        }
        System.out.println("SWC SET");
        
         s.open();
        
         System.out.println("DS OPENED");
         s.start();
        
         System.out.println("DS STARTED");
         sound.start();
        
         System.out.println("SOUND STARTED");
         proc.start();
        
         System.out.println("PROC STARTED");
         System.out.println("Record Start");
         Thread.sleep(5000);
         proc.stop();
         proc.close();
         s.stop();
         s.close();
         System.out.println("Record End");
        //DatagramSocket socket = new DatagramSocket(996);*/
        sendAudio();
    }

    public static void sendAudio() throws InterruptedException {
        TrackControl track[] = proc.getTrackControls();
        boolean encodingOk = false;
        System.out.println("Track found: "+track.length);
        for (int i = 0; i < track.length; i++) {
            if (!encodingOk && track[i] instanceof FormatControl) {
                System.out.println("TrackCOntrols");
                if (((FormatControl) track[i]).
                        setFormat(new AudioFormat(AudioFormat.GSM_RTP,
                                        8000,
                                        8,
                                        1)) == null) {
                    track[i].setEnabled(false);
                    System.out.println("Track "+i+" disabled");
                } else {
                    encodingOk = true;
                    System.out.println("Encoding OK");
                }
            } else {
                
                track[i].setEnabled(false);
            }
        }
        
        if (encodingOk) {
            proc.realize();
            t.sleep(5000);
            
            DataSource ds = null;
            try {
                ds = proc.getDataOutput();
            } catch (NotRealizedError e) {
                System.out.println("Not Realized");
            }
            System.out.println("Realized");
            try {
                String url = "rtp://239.0.0.1:3000/audio/1";
                MediaLocator m = new MediaLocator(url);
                DataSink d = Manager.createDataSink(ds, m);
                d.open();
                d.start();
            } catch (Exception e) {
                System.out.println("Error: " +e);
                System.out.println("Exception end");
            }
            System.out.println("Transmitting . . .");
            System.out.println(" Press the Enter key to exit");
            try {
                System.in.read();
            } catch (IOException ex) {
                System.out.println("IOEX");
            }
            System.out.println("-> Exiting");
        }
    }

}
