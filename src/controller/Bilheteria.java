package controller;

import java.util.concurrent.Semaphore;

public class Bilheteria extends Thread{
	private int Pessoa;
	private static int totalIngressos = 100;
	private Semaphore semaforo;
	
	public Bilheteria(int Pessoa, Semaphore semaforo) {
		this.Pessoa = Pessoa;
		this.semaforo = semaforo;
	}
	@Override
	public void run() {
		SistemLoguin();
		ProcessCompra();
//-----------------------Inicio da seção crítica-------------------------
		try {
			semaforo.acquire();
			ValidaCompra();
		}catch(InterruptedException e){
			e.printStackTrace();
		}finally {
			semaforo.release();
		}
//-------------------------Fim da seção crítica-----------------------
	}
	
	private void SistemLoguin() {
		int tempo = (int)(Math.random()*1501)+500;
		try {
			sleep(tempo);
			if (tempo > 1000) {
				System.out.println("Pessoa #"+Pessoa+" Tempo de 1s excedido.... TimeOut....");
				Bilheteria.interrupted();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	private void ProcessCompra() {
		int tempo = (int)(Math.random()*3001)+1000;
		try {
			sleep(tempo);
			if (tempo > 2500) {
				System.out.println("Pessoa #"+Pessoa+" Tempo de 2,5s excedido.... TimeOut....");
				Bilheteria.interrupted();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private void ValidaCompra() {
		int Venda = (int)(Math.random()*3)+1;
		if(totalIngressos < Venda) {
			System.out.println("Pessoa #"+Pessoa+" Indisponibilidade de ingressos..... TimeOut");
			Bilheteria.interrupted();
		}
		else {
			totalIngressos -= Venda;
			if(Venda > 1) {
				System.out.println("Pessoa #"+Pessoa+ " comprou " +Venda + " ingressos!");
			}
			else {
				System.out.println("Pessoa #"+Pessoa+ " comprou " +Venda + " ingresso!");
			}
		System.out.println("    " +totalIngressos + " 	Ingressos disponiveis");
		}
	}
}

