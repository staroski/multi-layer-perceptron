package br.com.staroski.ai.perceptron;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Neuronio {

	private double bias; // Bias value.
	private double erro; // Sum of error.
	private double entrada; // Sum of inputs.
	private double gradiente = 5; // Steepness of sigmoid curve.
	private double taxaAprendizado = 0.01; // Learning rate.
	private double saida = Double.MIN_VALUE; // Preset value of neuron.
	private List<Sinapse> pesos; // Collection of weights to inputs.

	public Neuronio() {}
	
	public Neuronio(Camada entradas, Random rnd) {
		pesos = new ArrayList<Sinapse>();
		for (Neuronio entrada : entradas) {
			Sinapse p = new Sinapse();
			p.entrada = entrada;
			p.valor = rnd.nextDouble() * 2 - 1;
			pesos.add(p);
		}
	}
	
	public void ajustarPesos() {
		for (Sinapse p : pesos) {
			p.valor += erro * funcaoDerivativa() * taxaAprendizado * p.entrada.getValorSaida();
		}
		//
		// for (int i = 0; i < pesos.size(); i++) {
		// pesos.get(i).valor += erro * funcaoDerivativa() * taxaAprendizado * pesos.get(i).entrada.getValorSaida();
		// }
		bias += erro * funcaoDerivativa() * taxaAprendizado;
	}

	public void coletarErro(double delta) {
		if (pesos != null) {
			erro += delta;
			for (Sinapse p : pesos) {
				p.entrada.coletarErro(erro * p.valor);
			}
		}
	}

	public void funcaoAtivacao() {
		erro = 0;
		entrada = 0;
		for (Sinapse peso : pesos) {
			entrada += peso.valor * peso.entrada.getValorSaida();
		}
	}

	public double getBias() {
		return bias;
	}

	public List<Sinapse> getEntradas() {
		return pesos;
	}

	public double getValorSaida() {
		if (saida != Double.MIN_VALUE) {
			return saida;
		}
		return 1 / (1 + Math.exp(-gradiente * (entrada + bias)));
	}

	public void setSaida(double value) {
		saida = value;
	}

	private double funcaoDerivativa() {
		double activation = getValorSaida();
		return activation * (1 - activation);
	}


}