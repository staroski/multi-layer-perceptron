package br.com.staroski.ai.perceptron;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSV {

	public static List<Padrao> carregar(File csvFile, int dimensaoEntrada, int dimensaoSaida) throws IOException {
		return carregar(new FileInputStream(csvFile), dimensaoEntrada, dimensaoSaida);
	}

	public static List<Padrao> carregar(InputStream csvInput, int dimensaoEntrada, int dimensaoSaida) throws IOException {
		List<Padrao> padroes = new ArrayList<Padrao>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(csvInput));
		String texto = null;
		while ((texto = reader.readLine()) != null) {
			double[] valores = new double[dimensaoEntrada + dimensaoSaida];
			String[] linha = texto.split(",");
			for (int i = 0; i < dimensaoEntrada; i++) {
				valores[i] = Double.parseDouble(linha[i]);
			}
			for (int i = 0; i < dimensaoSaida; i++) {
				valores[dimensaoEntrada + i] = Double.parseDouble(linha[dimensaoEntrada + i]);
			}
			padroes.add(Padrao.treinamento(dimensaoEntrada, dimensaoSaida).valores(valores));
		}
		return padroes;
	}

	public static List<Padrao> carregar(String csvFile, int dimensaoEntrada, int dimensaoSaida) throws IOException {
		return carregar(new File(csvFile), dimensaoEntrada, dimensaoSaida);
	}

	private CSV() {}
}
