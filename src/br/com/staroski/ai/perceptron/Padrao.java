package br.com.staroski.ai.perceptron;

public class Padrao {

	private static final class Builder implements PadraoBuilder {

		private final int dimensaoEntrada;
		private final int dimensaoSaida;

		private Builder(int dimensaoEntrada, int dimensaoSaida) {
			this.dimensaoEntrada = dimensaoEntrada;
			this.dimensaoSaida = dimensaoSaida;
		}

		@Override
		public Padrao valores(double... valores) {
			return new Padrao(dimensaoEntrada, dimensaoSaida, valores);
		}
	}

	public static PadraoBuilder teste(int dimensaoEntrada) {
		return new Builder(dimensaoEntrada, 0);
	}

	public static PadraoBuilder treinamento(int dimensaoEntrada, int dimensaoSaida) {
		return new Builder(dimensaoEntrada, dimensaoSaida);
	}

	public final double[] entradas;
	public final double[] saidas;

	private Padrao(int dimensaoEntrada, int dimensaoSaida, double... valores) {
		if (dimensaoEntrada + dimensaoSaida != valores.length) {
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
}