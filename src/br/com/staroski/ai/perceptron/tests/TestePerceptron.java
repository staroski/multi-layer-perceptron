package br.com.staroski.ai.perceptron.tests;

import java.io.IOException;
import java.util.List;

import br.com.staroski.ai.perceptron.CSV;
import br.com.staroski.ai.perceptron.Padrao;
import br.com.staroski.ai.perceptron.Rede;

public class TestePerceptron {

	public static void main(String[] args) {
		try {
			new TestePerceptron().executar();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private void executar() throws IOException {
		int neuronsEntrada = 2;
		int neuronsIntermediarios = 3;
		int neuronsSaida = 1;
		String arquivo = System.getProperty("user.dir") + "/src/patterns.csv";
		List<Padrao> padroes = CSV.carregar(arquivo, neuronsEntrada, neuronsSaida);
		Rede rede = new Rede(neuronsEntrada, neuronsIntermediarios, neuronsSaida);
		System.out.println("treinando...");
		double erro;
		int it = 0;
		do {
			erro = rede.treinar(padroes);
			it++;
		} while (erro > 0.001);
		System.out.println("treinamento concluido em " + it + " iteracoes");
		testar(rede);
	}

	private void testar(Rede rede) {
		int neuronsEntrada = rede.getDimensao(0);
		System.out.println(rede.ativar(new Padrao(neuronsEntrada, new double[] { 0, 0 })).get(0).getValorSaida());
		System.out.println(rede.ativar(new Padrao(neuronsEntrada, new double[] { 0, 1 })).get(0).getValorSaida());
		System.out.println(rede.ativar(new Padrao(neuronsEntrada, new double[] { 1, 0 })).get(0).getValorSaida());
		System.out.println(rede.ativar(new Padrao(neuronsEntrada, new double[] { 1, 1 })).get(0).getValorSaida());
	}
}
