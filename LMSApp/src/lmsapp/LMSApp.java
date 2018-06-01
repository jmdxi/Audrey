/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lmsapp;

import javax.swing.JFrame;

/**
 *
 * @author JMD
 */
public class LMSApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frameWelcome = new Welcome();
        frameWelcome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameWelcome.pack();
        frameWelcome.setVisible(true);
    }
}
