package br.com.staroski.ai.perceptron;

public class Padrao {

	public final double[] entradas;
	public final double[] saidas;

	public Padrao(int dimensaoEntrada, double... valores) {
		this(dimensaoEntrada, 0, valores);
	}

	public Padrao(int dimensaoEntrada, int dimensaoSaida, double... valores) {
		if (valores.length != dimensaoEntrada + dimensaoSaida) {
			throw new IllegalArgumentException("Input does not match network configuration");
		}
		entradas = new double[dimensaoEntrada];
		for (int i = 0; i < dimensaoEntrada; i++) {
			entradas[i] = valores[i];
		}
		saidas = new double[dimensaoSaida];
		for (int i = 0; i < dimensaoSaida; i++) {
			saidas[i] = valores[dimensaoEntrada + i];
		}
	}

	public int saidaMaxima() {
		int item = -1;
		double max = Double.MIN_VALUE;
		for (int i = 0; i < saidas.length; i++) {
			if (saidas[i] > max) {
				max = saidas[i];
				item = i;
			}
		}
		return item;
	}
}