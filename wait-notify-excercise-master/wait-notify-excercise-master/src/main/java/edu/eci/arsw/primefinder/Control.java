/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.primefinder;

import java.util.Scanner;

/**
 *
 */
public class Control extends Thread {
    
    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 30000000;
    private final static int TMILISECONDS = 2000;

    private final int NDATA = MAXVALUE / NTHREADS;

    private PrimeFinderThread pft[];
    
    private Control() {
        super();
        this.pft = new  PrimeFinderThread[NTHREADS];

        int i;
        for(i = 0;i < NTHREADS - 1; i++) {
            PrimeFinderThread elem = new PrimeFinderThread(i*NDATA, (i+1)*NDATA,i,TMILISECONDS);
            pft[i] = elem;
        }
        pft[i] = new PrimeFinderThread(i*NDATA, MAXVALUE + 1,i,TMILISECONDS);
    }
    
    public static Control newControl() {
        return new Control();
    }

    @Override
    public void run() {
        /*
        Thread t = new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Entro aca? no se");
                    for (int i = 0; i < NTHREADS; i++) {
                        if(pft[i].isFly()){
                            System.out.println("Espicha la tecla enter para continuar");
                            Scanner key = new Scanner(System.in);
                            String s = key.nextLine();
                            if(s.equals("")){
                                for (int h = 0; h < NTHREADS; h++) {
                                    pft[h].ver();
                                }
                            }
                            for (int j = 0; j < NTHREADS; j++) {
                                pft[j].setFly();
                                }
                            }
                        }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
         */

        for (int i = 0; i < NTHREADS; i++) {
            pft[i].start();
        }

        for (int i = 0; i < NTHREADS; i++) {
            pft[i].setFly();
        }

    }


}
