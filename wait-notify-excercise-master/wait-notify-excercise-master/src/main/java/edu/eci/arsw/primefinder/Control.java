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

    private final PrimeFinderThread pF = new PrimeFinderThread(TMILISECONDS);

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

        Thread t = new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    int k=0;
                    for (int i = 0; k < NTHREADS*3; i++) {
                        k++;
                        if(i==NTHREADS){
                            i=0;
                        }
                        if(pft[i].isFly()){
                            System.out.println("Espiche la tecla ENTER para continuar");
                            Scanner myObj = new Scanner(System.in);
                            String key = myObj.nextLine();
                            if(key.equals("")){
                                pft[i].ver();
                                for (int j = 0; j < NTHREADS; j++) {
                                    pft[j].print();
                                }
                            }
                        }
                        pft[i].setFly(true);
                        pft[i].ver();
                        for (int j = 0; j < NTHREADS; j++) {
                            pft[j].print();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        for (int i = 0; i < NTHREADS; i++) {
            pft[i].start();
        }

        t.start();
    }


}
