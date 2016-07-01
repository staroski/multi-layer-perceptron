package br.com.staroski.ai.perceptron.tests;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import br.com.staroski.ai.perceptron.CSV;
import br.com.staroski.ai.perceptron.Network;
import br.com.staroski.ai.perceptron.Neuron;
import br.com.staroski.ai.perceptron.Pattern;
import br.com.staroski.ai.perceptron.Synapsis;

public class TestePerceptronVisual extends JFrame {

	private static final long serialVersionUID = 1;

	public static void main(String... args) {
		try {
			TestePerceptronVisual graph = new TestePerceptronVisual();
			graph.setDefaultCloseOperation(EXIT_ON_CLOSE);
			graph.setSize(400, 400);
			graph.setVisible(true);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private static double[] hyperPlane(Neuron neuron) {
		double[] line = new double[3];
		List<Synapsis> weights = neuron.inputs();
		line[0] = weights.get(0).output;
		line[1] = weights.get(1).output;
		line[2] = neuron.bias();
		return line;
	}

	private static List<double[]> hiperPlanos(Network net) {
		List<double[]> lines = new ArrayList<double[]>();
		for (Neuron n : net.outputs()) {
			lines.add(hyperPlane(n));
		}
		return lines;
	}

	private static List<float[]> points2D(Network net, List<Pattern> patterns) {
		int penultimate = net.size() - 2;
		if (net.get(penultimate).size() != 2) {
			throw new IllegalArgumentException("Penultimate layer must be 2D for graphing.");
		}
		List<float[]> points = new ArrayList<float[]>();
		for (int i = 0; i < patterns.size(); i++) {
			net.activate(patterns.get(i));
			float[] point = new float[3];
			point[0] = (float) net.get(penultimate).get(0).value();
			point[1] = (float) net.get(penultimate).get(1).value();
			if (net.outputs().size() > 1) {
				point[2] = maxOutput(patterns.get(i));
			} else {
				point[2] = (patterns.get(i).outputs[0] >= 0.5) ? 1 : 0;
			}
			points.add(point);
		}
		return points;
	}

	private static int maxOutput(Pattern padrao) {
		int item = -1;
		double max = Double.MIN_VALUE;
		for (int i = 0; i < padrao.outputs.length; i++) {
			if (padrao.outputs[i] > max) {
				max = padrao.outputs[i];
				item = i;
			}
		}
		return item;
	}

	private int iteration;

	private List<Pattern> patterns;

	private Network network;

	private Color[] brushes = new Color[] { Color.RED, Color.GREEN, Color.BLUE };

	public TestePerceptronVisual() throws IOException {
		String csvFile = System.getProperty("user.dir") + "/src/iris_data.csv";
		int inputLayer = 4;
		int intermediateLayer1 = 4;
		int intermediateLayer2 = 2;
		int outputLayer = 3;
		patterns = CSV.load(csvFile, inputLayer, outputLayer);
		network = new Network(inputLayer, intermediateLayer1, intermediateLayer2, outputLayer);
		add(BorderLayout.CENTER, new JPanel() {

			private static final long serialVersionUID = 1;

			@Override
			protected void paintComponent(Graphics g) {
				double error = network.train(patterns);
				UpdatePlotArea((Graphics2D) g, getBounds(), error);
				iteration++;
				invalidate();
				repaint(0);
			}

			@Override
			protected void processKeyEvent(KeyEvent e) {
				super.processKeyEvent(e);
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					network.initialize();
					iteration = 0;
				}
			}
		});
	}

	private void UpdatePlotArea(Graphics2D g, Rectangle bounds, double error) {
		setTitle("Iteration: " + iteration + "  Error: " + error);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, bounds.width, bounds.height);
		final int pointSize = 4;
		for (float[] point : points2D(network, patterns)) {
			g.setColor(brushes[(int) point[2]]);
			g.fillRect((int) point[0] * (bounds.width - pointSize), (int) point[1] * (bounds.height - pointSize), pointSize, pointSize);
		}
		for (double[] line : hiperPlanos(network)) {
			double a = -line[0] / line[1];
			double c = -line[2] / line[1];
			Point left = new Point(0, (int) (c * bounds.height));
			Point right = new Point(bounds.width, (int) ((a + c) * bounds.height));
			g.setColor(Color.GRAY);
			g.drawLine(left.x, left.y, right.x, right.y);
		}
	}
}
