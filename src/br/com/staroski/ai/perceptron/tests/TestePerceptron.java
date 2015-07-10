package br.com.staroski.ai.perceptron.tests;

import java.util.List;

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

	private void executar() {
		int dimensaoEntrada = 2;
		int dimensaoOculta = 3;
		int dimensaoSaida = 1;
		String arquivo = System.getProperty("user.dir") + "/src/patterns.csv";
		List<Padrao> padroes = Padrao.carregar(arquivo, dimensaoEntrada, dimensaoSaida);
		Rede rede = new Rede(dimensaoEntrada, dimensaoOculta, dimensaoSaida);
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
		int dimensaoEntrada = rede.getDimensao(0);
		System.out.println(rede.ativar(new Padrao("0,0", dimensaoEntrada)).get(0).getValorSaida());
		System.out.println(rede.ativar(new Padrao("0,1", dimensaoEntrada)).get(0).getValorSaida());
		System.out.println(rede.ativar(new Padrao("1,0", dimensaoEntrada)).get(0).getValorSaida());
		System.out.println(rede.ativar(new Padrao("1,1", dimensaoEntrada)).get(0).getValorSaida());
	}
}
