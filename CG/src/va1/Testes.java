package va1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import control.JanelaParteDois;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Testes extends Application {

	public static void imprimeMatriz(double m[][], int x, int y) {
		for (int i = 0; i < x; i++) {
			System.out.print("[");
			for (int j = 0; j < y; j++) {
				System.out.print(m[i][j] + " ");
			}
			System.out.println("]");
		}
		System.out.println("---------------------");
	}

	public static void Questao1(){
		System.out.println("1A - Mutiplicacao de Matrizes");
		double mA[][] = new double[2][3];
		mA[0][0] = 1.5;
		mA[0][1] = 2.5;
		mA[0][2] = 3.5;
		mA[1][0] = 4.5;
		mA[1][1] = 5.5;
		mA[1][2] = 6.5;

		double mB[][] = new double[3][2];
		mB[0][0] = 7.5;
		mB[0][1] = 8.5;
		mB[1][0] = 9.5;
		mB[1][1] = 10.5;
		mB[2][0] = 11.5;
		mB[2][1] = 12.5;

		double mC[][] = BibOps.matrixMulti(mA, mB, 2, 3, 3, 2);
		imprimeMatriz(mC, 2, 2);

		System.out.println();

		System.out.println("1B - Subtracao de pontos");
		double p[][] = BibOps.subtPontos3D(new Ponto3D(3.5, 1.5, 2.0), new Ponto3D(1.0, 2.0, 1.5));
		imprimeMatriz(p, 2, 1);

		System.out.println();

		System.out.println("1C - Produto Escalar");
		double vA[][] = new double[3][1];
		double vB[][] = new double[3][1];

		vA[0][0] = 3.5;
		vA[1][0] = 1.5;
		vA[2][0] = 2.0;

		vB[0][0] = 1.0;
		vB[1][0] = 2.0;
		vB[2][0] = 1.5;

		double vC = BibOps.produtoEscalar3D(vA, vB);
		System.out.println(vC);

		System.out.println();

		System.out.println("1D - Produto Vetorial");
		double vA2[][] = new double[3][1];
		double vB2[][] = new double[3][1];

		vA2[0][0] = 3.5;
		vA2[1][0] = 1.5;
		vA2[2][0] = 2.0;

		vB2[0][0] = 1.0;
		vB2[1][0] = 2.0;
		vB2[2][0] = 1.5;

		double vC2[][] = BibOps.produtoVetorial3D(vA2, vB2);
		imprimeMatriz(vC2, 3, 1);

		System.out.println();

		System.out.println("1E - Norma do Vetor");
		double vA3[][] = new double[3][1];

		vA3[0][0] = 3.5;
		vA3[1][0] = 1.5;
		vA3[2][0] = 2.0;

		System.out.println("Norma = " + BibOps.normaVetor3D(vA3));
		System.out.println("---------------------");

		System.out.println();

		System.out.println("1F - Vetor Normalizado");
		imprimeMatriz(BibOps.normalizaVetor3D(vA3), 3, 1);

		System.out.println();

		System.out.println("1G - Coordenada Baricentrica");

		Ponto2D ponto = BibOps.coordBaricentricas2D(new Ponto2D(-0.25, 0.75), new Ponto2D(-1, 1), new Ponto2D(0, -1),
				new Ponto2D(1, 1));
		System.out.println("P = (" + ponto.x + ", " + ponto.y + ")");
		System.out.println("---------------------");

		System.out.println();

		System.out.println("1H - Coordenada Cartesiana");
		Ponto2D p2 = BibOps.cartesianaDaBaricentrica(new Ponto2D(-1, 1), new Ponto2D(0, -1), new Ponto2D(1, 1), 0.5,
				0.25, 0.25);
		System.out.println("P = (" + p2.x + ", " + p2.y + ")");
		System.out.println("---------------------");
		
	}
	public static void main(String[] args) throws IOException {
		
		//Questao1();
		
		// ################ Questao 2 ####################
		// Coloque o nome do arquivo de pontos sem extensão
		// getPontosArquivo("vaso");
		// launch(args);

		
		
		camera = new CameraVirtual();
		camera.N = new double[][] { { 0 }, { 1 }, { -1 } };
		camera.V = new double[][] { { 0 }, { -1 }, { -1 } };
		camera.d = 5;
		camera.hx = 1.5;
		camera.hy = 1.5;
		camera.C = new Ponto3D(0, -500, 500);
		camera.ortogonalizarV();

		launch(args);

	}
	
	
	
	public static CameraVirtual camera;
	public static Ponto3D p[];
	public static Triangulo t[];
	public static int xmax = 500, ymax = 500;
	public static String arquivo = "calice2";

	public static void getPontosArquivo(String arquivoSemExtensao) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(arquivoSemExtensao + ".byu"));
		String linha = reader.readLine();
		String qnt[] = linha.split(" ");
		p = new Ponto3D[Integer.parseInt(qnt[0])];
		t = new Triangulo[Integer.parseInt(qnt[1])];
		for (int i = 0; i < p.length; i++) {
			linha = reader.readLine();
			qnt = linha.split(" ");
			p[i] = new Ponto3D();
			p[i].x = Double.parseDouble(qnt[0]);
			p[i].y = Double.parseDouble(qnt[1]);
			p[i].z = Double.parseDouble(qnt[2]);
		}
		for (int i = 0; i < t.length; i++) {
			linha = reader.readLine();
			qnt = linha.split(" ");
			int a, b, c;
			a = Integer.parseInt(qnt[0]);
			b = Integer.parseInt(qnt[1]);
			c = Integer.parseInt(qnt[2]);
			t[i] = new Triangulo(p[a - 1], p[b - 1], p[c - 1]);
		}
		reader.close();
	}

	

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Teste");
		BorderPane pane = FXMLLoader.load(this.getClass().getResource("janelaProjetoP2.fxml"));
		primaryStage.setScene(new Scene(pane));
		primaryStage.setResizable(false);
		primaryStage.show();

	}

}
