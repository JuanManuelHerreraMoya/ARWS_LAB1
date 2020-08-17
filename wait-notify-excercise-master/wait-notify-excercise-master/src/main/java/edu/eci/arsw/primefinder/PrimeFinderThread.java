package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;



public class PrimeFinderThread extends Thread{

	
	int a,b,count,hilo,segundos;
	private Calendar hora,copia;
	private boolean fly;

	private List<Integer> primes;
	
	public PrimeFinderThread(int a, int b, int hilo, int segundos) {
		super();
		this.primes = new LinkedList<>();
		this.a = a;
		this.b = b;
		this.hilo=hilo;
		this.segundos=segundos;
		fly = false;
		copia = Calendar.getInstance();
		copia.add(Calendar.SECOND, segundos/1000);
	}

	@Override
	public void run() {
			try{
				primos();
			}catch (InterruptedException e){
				e.printStackTrace();
			}
	}

	public boolean isFly(){
		return fly;
	}

	public void setFly() {
		try{
			ver();
		}catch (InterruptedException e){
			e.printStackTrace();
		}
		/*
		synchronized (this) {
			fly = false;
			System.out.println("Thread " + hilo + " conto " + getPrimes().size() + " primos");
			wait();
			copia = Calendar.getInstance();
			copia.add(Calendar.SECOND  , segundos/1000);
		}
		 */
	}

	public void ver() throws InterruptedException{
		synchronized (this) {
			if(fly){
				System.out.println("Thread " + hilo + " conto " + getPrimes().size() + " primos");
				fly=false;
				copia = Calendar.getInstance();
				copia.add(Calendar.SECOND  , segundos/1000);
				notifyAll();
			}

		}
	}

	public void primos() throws InterruptedException{
		synchronized (this) {
			for (int i = a; i < b; i++) {
				hora = Calendar.getInstance();
				if (hora.equals(copia)) {
					fly=true;
					System.out.println("Thread " + hilo + " conto " + getPrimes().size() + " primos");
					wait();
				}
				if (isPrime(i)) {
					primes.add(i);
					//System.out.println(i);
				}
			}
		}
	}

	
	boolean isPrime(int n) {
	    boolean ans;
            if (n > 2) {
                ans = n%2 != 0;
                for(int i = 3;ans && i*i <= n; i+=2 ) {
                    ans = n % i != 0;
                }
            } else {
                ans = n == 2;
            }
	    return ans;
	}

	public List<Integer> getPrimes() {
		return primes;
	}
	
}
