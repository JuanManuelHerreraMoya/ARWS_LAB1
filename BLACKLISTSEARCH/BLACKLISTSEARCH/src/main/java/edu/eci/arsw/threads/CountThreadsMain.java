/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 *
 * @author hcadavid
 */
public class CountThreadsMain {
    private CountThread uno ;
    private CountThread dos ;
    private CountThread tres;
    public static void  main(String a[]){
        CountThread uno = new CountThread(0,99);
        CountThread dos = new CountThread(100,199);
        CountThread tres = new CountThread(200,299);
        uno.start();
        dos.start();
        tres.start();
    }
}
