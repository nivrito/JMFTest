/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmftest;

import java.io.IOException;
import java.util.Scanner;
import javax.media.*;

/**
 *
 * @author Nivrito
 */
public class JMFTest {

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws IOException, NoProcessorException, NoDataSinkException, InterruptedException {
        Scanner in = new Scanner(System.in);
        System.out.println("Directive: ");
        int dir = in.nextInt();
        if (dir>0) {
            System.out.println("Initializing Transmitter");
            AudioSender as = new AudioSender();
        } else {
            System.out.println("Initializing Reciever");
            AReciever ar = new AReciever();

        }
    }

}
