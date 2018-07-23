package va1;

import java.util.ArrayList;
import java.util.Iterator;

import control.JanelaParteDois;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

public class BibOps {
	/*
	 * Funcao chamada pelo no inicio da execucao do programa por
	 * JanelaParteDois.Initialize()
	 */
	public static void executarTarefaInicial(GraphicsContext desenho) {
		Testes.getPontosArquivo(Testes.arquivo);
		BibOps.iniciarMatriz_zBuffer();
		BibOps.setReferenciasOriginaisDosTriangulos();
		BibOps.atualizarCoordVista(Testes.arquivo);
		BibOps.normaisDosTriangulos(Testes.t);
		BibOps.normaisDosVertices(Testes.p, Testes.t);
		BibOps.setBaricentroTriangulos(Testes.t);
		Testes.triangulosOrdenadosPara_zBuffer = BibOps.ordenarTriangulos_zBuffer(Testes.t);
		BibOps.malhaTriangulos(desenho, Testes.xmax, Testes.ymax);
		BibOps.pintar_zBuffer(desenho);
	}

	/*
	 * Funcao chamada sempre que o botao calcular e ativado
	 */
	public static void executarTarefaBotao(GraphicsContext desenho) {
		desenho.fillRect(0, 0, Testes.xmax, Testes.ymax);
		Testes.getPontosArquivo(Testes.arquivo);
		BibOps.iniciarMatriz_zBuffer();
		BibOps.setReferenciasOriginaisDosTriangulos();
		BibOps.atualizarCoordVista(Testes.arquivo);
		BibOps.normaisDosTriangulos(Testes.t);
		BibOps.normaisDosVertices(Testes.p, Testes.t);
		BibOps.setBaricentroTriangulos(Testes.t);
		Testes.triangulosOrdenadosPara_zBuffer = BibOps.ordenarTriangulos_zBuffer(Testes.t);
		BibOps.malhaTriangulos(desenho, Testes.xmax, Testes.ymax);
		BibOps.pintar_zBuffer(desenho);
	}
	/*
	 * funcao chamata apos o calculo das cores dos pontos e de preencher o zbuffer
	 */

	private static void pintar_zBuffer(GraphicsContext desenho) {
		for (int i = 0; i < Testes.xmax; i++) {
			for (int j = 0; j < Testes.ymax; j++) {
				desenho.setFill(Testes.matrix_zBuffer[i][j].corDoPixel);
				desenho.fillRect(i, j, 1, 1);
			}
		}
	}

	private static void iniciarMatriz_zBuffer() {
		Testes.matrix_zBuffer = new Objetto_zBuffer[(int) Testes.xmax][(int) Testes.ymax];
		for (int i = 0; i < Testes.matrix_zBuffer.length; i++) {
			for (int j = 0; j < Testes.matrix_zBuffer.length; j++) {
				Testes.matrix_zBuffer[i][j] = new Objetto_zBuffer();
				Testes.matrix_zBuffer[i][j].corDoPixel = Color.BLACK;
				Testes.matrix_zBuffer[i][j].profundidade = Double.NEGATIVE_INFINITY;
			}
		}
	}

	/*
	 * Verifica se o zBuffer deve ser atualizado, e atualiza se necessario
	 */
	private static void atualizar_zBuffer(int i, int j, double profundidadeAtual, Color cor) {
		if (i < Testes.xmax && i >= 0 && j < Testes.ymax && j >= 0
				&& Testes.matrix_zBuffer[i][j].profundidade < profundidadeAtual) {
			Testes.matrix_zBuffer[i][j].profundidade = profundidadeAtual;
			Testes.matrix_zBuffer[i][j].corDoPixel = cor;
		}
	}

	public static double[][] matrixMulti(double[][] A, double[][] B, int x1, int y1, int x2, int y2) {
		double[] C[] = new double[x1][y2];

		for (int i = 0; i < x1; i++) {
			for (int j = 0; j < y2; j++) {
				C[i][j] = 0;
			}
		}
		if (y1 == x2) {
			for (int i = 0; i < x1; i++) {
				for (int j = 0; j < y2; j++) {
					for (int j2 = 0; j2 < x2; j2++) {
						C[i][j] += A[i][j2] * B[j2][j];
					}
				}
			}
		} else {
			System.err.println("Matrizes incompatíveis para operação.");
		}
		return C;
	}

	public static double[][] subtPontos3D(Ponto3D q, Ponto3D p) {
		double vetor[][] = new double[3][1];

		vetor[0][0] = q.x - p.x;
		vetor[1][0] = q.y - p.y;
		vetor[2][0] = q.z - p.z;

		return vetor;
	}

	public static double[][] subtPontos2D(Ponto2D q, Ponto2D p) {
		double vetor[][] = new double[2][1];

		vetor[0][0] = q.x - p.x;
		vetor[1][0] = q.y - p.y;

		return vetor;
	}

	public static double produtoEscalar3D(double[][] A, double[][] B) {
		double v = 0;
		for (int i = 0; i < 3; i++) {
			v += A[i][0] * B[i][0];
		}
		return v;
	}

	private static Ponto3D produtoComponentePonto3D(Ponto3D a, Ponto3D b) {
		return new Ponto3D(a.x * b.x, a.y * b.y, a.z * b.z);
	}

	private static Ponto3D somarPontos(Ponto3D a, Ponto3D b) {
		return new Ponto3D(a.x + b.x, a.y + b.y, a.z + b.z);
	}

	public static double[][] produtoComponenteVetor(double[][] a, double[][] b) {
		double[][] c = new double[3][1];
		c[0][0] = a[0][0] * c[0][0];
		c[1][0] = a[1][0] * c[1][0];
		c[2][0] = a[2][0] * c[2][0];
		return c;
	}

	private static Ponto3D produtoPonto3DPorEscalar(Ponto3D a, double escalar) {
		return new Ponto3D(a.x * escalar, a.y * escalar, a.z * escalar);
	}

	public static double[][] produtoVetorial3D(double[][] A, double[][] B) {
		double[] vetor[] = new double[3][1];
		double[] matrizAux[] = new double[3][3];

		for (int i = 0; i < 3; i++) {
			matrizAux[0][i] = 1;
			matrizAux[1][i] = A[i][0];
			matrizAux[2][i] = B[i][0];
		}

		vetor[0][0] = matrizAux[0][0] * matrizAux[1][1] * matrizAux[2][2]
				- (matrizAux[0][0] * matrizAux[1][2] * matrizAux[2][1]);
		vetor[1][0] = matrizAux[0][1] * matrizAux[1][2] * matrizAux[2][0]
				- (matrizAux[0][1] * matrizAux[1][0] * matrizAux[2][2]);
		vetor[2][0] = matrizAux[0][2] * matrizAux[1][0] * matrizAux[2][1]
				- (matrizAux[0][2] * matrizAux[1][1] * matrizAux[2][0]);

		return vetor;
	}

	public static double[][] produtoVetorial3DMaoEsq(double[][] A, double[][] B) {
		double[] vetor[] = new double[3][1];
		double[] matrizAux[] = new double[3][3];

		for (int i = 0; i < 3; i++) {
			matrizAux[0][i] = 1;
			matrizAux[1][i] = A[i][0];
			matrizAux[2][i] = B[i][0];
		}

		vetor[0][0] = matrizAux[0][0] * matrizAux[1][1] * matrizAux[2][2]
				- (matrizAux[0][0] * matrizAux[1][2] * matrizAux[2][1]);
		vetor[1][0] = matrizAux[0][1] * matrizAux[1][2] * matrizAux[2][0]
				- (matrizAux[0][1] * matrizAux[1][0] * matrizAux[2][2]);
		vetor[2][0] = matrizAux[0][2] * matrizAux[1][0] * matrizAux[2][1]
				- (matrizAux[0][2] * matrizAux[1][1] * matrizAux[2][0]);

		vetor[0][0] -= 2 * vetor[0][0];
		vetor[1][0] -= 2 * vetor[1][0];
		vetor[2][0] -= 2 * vetor[2][0];

		return vetor;
	}

	public static double normaVetor3D(double[][] A) {
		return Math.sqrt(Math.pow(A[0][0], 2) + Math.pow(A[1][0], 2) + Math.pow(A[2][0], 2));
	}

	public static double[][] normalizaVetor3D(double[][] A) {
		double[] vet[] = new double[3][1];
		double norma = normaVetor3D(A);
		for (int i = 0; i < 3; i++) {
			vet[i][0] = A[i][0] / norma;
		}

		return vet;
	}

	public static Ponto3D coordBaricentricas2D(Ponto2D alvo, Ponto2D a, Ponto2D b, Ponto2D c) {
		double[] matrizT[] = new double[2][2];
		double[] resposta[];

		matrizT[0][0] = a.x - c.x;
		matrizT[0][1] = b.x - c.x;
		matrizT[1][0] = a.y - c.y;
		matrizT[1][1] = b.y - c.y;

		double[] vet[] = new double[2][1];
		vet[0][0] = alvo.x - c.x;
		vet[1][0] = alvo.y - c.y;

		double det = matrizT[0][0] * matrizT[1][1] - matrizT[0][1] * matrizT[1][0];

		double M_Inversa[][] = inversa(matrizT, det);
		resposta = matrixMulti(M_Inversa, vet, 2, 2, 2, 1);

		return new Ponto3D(resposta[0][0], resposta[1][0], 1 - resposta[0][0] - resposta[1][0]);
	}

	public static Ponto3D cartesianaDaBaricentrica(Ponto3D p1, Ponto3D p2, Ponto3D p3, double alfa, double beta,
			double gama) {

		double x = alfa * p1.x + beta * p2.x + gama * p3.x;
		double y = alfa * p1.y + beta * p2.y + gama * p3.y;
		double z = alfa * p1.z + beta * p2.z + gama * p3.z;

		return new Ponto3D(x, y, z);
	}

	private static double[][] inversa(double m[][], double determinante) {
		double inversa[][] = new double[m.length][m[0].length];

		double a = m[0][0];
		double b = m[0][1];
		double c = m[1][0];
		double d = m[1][1];

		inversa[0][0] = d / determinante;
		inversa[0][1] = (-b) / determinante;
		inversa[1][0] = (-c) / determinante;
		inversa[1][1] = a / determinante;

		return inversa;
	}

	private static Ponto3D coordenadasDeVista(CameraVirtual camera, Ponto3D ponto) {
		double p_c[][] = subtPontos3D(ponto, camera.C);
		return pontoDeVetor3x1(matrixMulti(camera.retorneBaseOrtonormal(), p_c, 3, 3, 3, 1));
	}

	public static Ponto3D pontoDeVetor3x1(double v[][]) {
		return new Ponto3D(v[0][0], v[1][0], v[2][0]);
	}

	private static void atualizaTrianguloParaCoordVista(Triangulo t, CameraVirtual camera) {
		t.a = BibOps.coordenadasDeVista(camera, t.a);
		t.b = BibOps.coordenadasDeVista(camera, t.b);
		t.c = BibOps.coordenadasDeVista(camera, t.c);
	}

	private static void scanLine(GraphicsContext gc, Triangulo t) {
		double ymin = Math.min(t.telaA.y, t.telaB.y);
		ymin = Math.min(ymin, t.telaC.y);
		Ponto2D inicio = null, meio = null, fim = null;
		if (ymin == t.telaA.y) {
			inicio = t.telaA;
			if (t.telaB.y <= t.telaC.y) {
				meio = t.telaB;
				fim = t.telaC;
			} else {
				meio = t.telaC;
				fim = t.telaB;
			}
		} else if (ymin == t.telaB.y) {
			inicio = t.telaB;
			if (t.telaA.y <= t.telaC.y) {
				meio = t.telaA;
				fim = t.telaC;
			} else {
				meio = t.telaC;
				fim = t.telaA;
			}
		} else if (ymin == t.telaC.y) {
			inicio = t.telaC;
			if (t.telaB.y <= t.telaA.y) {
				meio = t.telaB;
				fim = t.telaA;
			} else {
				meio = t.telaA;
				fim = t.telaB;
			}
		} else {
			System.err.println("ymin não corresponde a nada");
		}

//		analisarOrdem(inicio, meio, fim);

//        if (Double.valueOf(meio.y).equals(fim.y)) System.out.println("Superior");
//        else if (Double.valueOf(inicio.y).equals(meio.y)) System.out.println("Inferior");
//        else System.out.println("Normal");

		preencherTrianguloSuperior(gc, inicio, meio, fim, t);
		preencherTrianguloInferior(gc, inicio, meio, fim, t);

	}

	private static void preencherTrianguloSuperior(GraphicsContext gc, Ponto2D inicio, Ponto2D meio, Ponto2D fim,
			Triangulo t) {
		double coefRetaMin, coefRetaMax;
		if (meio.x < fim.x) {
			coefRetaMin = (meio.x - inicio.x) / (meio.y - inicio.y);
			coefRetaMax = (fim.x - inicio.x) / (fim.y - inicio.y);
		} else {
			coefRetaMin = (fim.x - inicio.x) / (fim.y - inicio.y);
			coefRetaMax = (meio.x - inicio.x) / (meio.y - inicio.y);
		}

		double xmax = inicio.x;
		double xmin = inicio.x;

		int scanlineY;
		for (scanlineY = (int) Math.round(inicio.y); scanlineY <= (int) Math.round(meio.y); scanlineY++) {
			double aux = Math.max(inicio.x, meio.x);
			aux = Math.max(aux, fim.x);
			for (int x = (int) Math.round(xmin); x <= (int) Math.round(xmax) && x < aux; x++) {
				calcularCor(x, scanlineY, t);
			}
			xmin += coefRetaMin;
			xmax += coefRetaMax;
		}

		xmax = inicio.x;
		xmin = inicio.x;

		double aux2 = coefRetaMax;
		coefRetaMax = coefRetaMin;
		coefRetaMin = aux2;

		for (scanlineY = (int) Math.round(inicio.y); scanlineY <= (int) Math.round(meio.y); scanlineY++) {
			double aux = Math.max(inicio.x, meio.x);
			aux = Math.max(aux, fim.x);
			for (int x = (int) Math.round(xmin); x <= (int) Math.round(xmax) && x < aux; x++) {
				calcularCor(x, scanlineY, t);
			}
			xmin += coefRetaMin;
			xmax += coefRetaMax;
		}
	}

	private static void preencherTrianguloInferior(GraphicsContext gc, Ponto2D inicio, Ponto2D meio, Ponto2D fim,
			Triangulo t) {
		double coefRetaMin, coefRetaMax;
		if (meio.x < inicio.x) {
			coefRetaMin = (meio.x - fim.x) / (meio.y - fim.y);
			coefRetaMax = (inicio.x - fim.x) / (inicio.y - fim.y);
		} else {
			coefRetaMin = (inicio.x - fim.x) / (inicio.y - fim.y);
			coefRetaMax = (meio.x - fim.x) / (meio.y - fim.y);
		}

		double xmax = fim.x;
		double xmin = fim.x;
		int scanlineY = 0;
		for (scanlineY = (int) fim.y; scanlineY >= (int) meio.y; scanlineY--) {
			for (int x = (int) Math.round(xmin); x <= (int) Math.round(xmax); x++) {
				calcularCor(x, scanlineY, t);
			}
			xmin -= coefRetaMin;
			xmax -= coefRetaMax;
		}

		double aux = coefRetaMax;
		coefRetaMax = coefRetaMin;
		coefRetaMin = aux;

		xmax = fim.x;
		xmin = fim.x;
		scanlineY = 0;
		for (scanlineY = (int) fim.y; scanlineY >= (int) meio.y; scanlineY--) {
			for (int x = (int) Math.round(xmin); x <= (int) Math.round(xmax); x++) {
				calcularCor(x, scanlineY, t);
			}
			xmin -= coefRetaMin;
			xmax -= coefRetaMax;
		}
	}

	private static void calcularCor(int x, int scanlineY, Triangulo t) {
//		Converter para coordenadas mundiais
		t.a.normal = t.original1.normal;
		t.b.normal = t.original2.normal;
		t.c.normal = t.original3.normal;
		Ponto3D q = coordBaricentricas2D(new Ponto2D(x, scanlineY), t.telaA, t.telaB, t.telaC);
		Ponto3D p = cartesianaDaBaricentrica(t.a, t.b, t.c, q.x, q.y, q.z);

//		Calcular normal de P
		double[][] n1 = produtoPorEscalar(t.a.normal, q.x);
		double[][] n2 = produtoPorEscalar(t.b.normal, q.y);
		double[][] n3 = produtoPorEscalar(t.c.normal, q.z);
		n1 = somarVetores3D(n1, n2);
		p.normal = normalizaVetor3D(somarVetores3D(n1, n3));

//		Calcular outros parâmetros de P
		double[][] N = p.normal;
		double[][] V = normalizaVetor3D(subtPontos3D(new Ponto3D(0, 0, 0), p));
		double[][] L = normalizaVetor3D(subtPontos3D(Testes.iluminacao.Pl, p));
		double[][] R = normalizaVetor3D(calcularR(N, L));

		Ponto3D Ia = null, Id = null, Is = null;

		Ia = produtoPonto3DPorEscalar(Testes.iluminacao.Iamb, Testes.iluminacao.Ka);

//		Casos Especiais
		if (produtoEscalar3D(N, L) < 0) {
			if (produtoEscalar3D(N, V) < 0) {
				N[0][0] -= 2 * N[0][0];
				N[1][0] -= 2 * N[1][0];
				N[2][0] -= 2 * N[2][0];
			} else {
				Is = new Ponto3D(0, 0, 0);
				Id = new Ponto3D(0, 0, 0);
			}
		}

		if (produtoEscalar3D(R, V) < 0) {
			Is = new Ponto3D(0, 0, 0);
		}

		if (Is == null) {
			Is = produtoPonto3DPorEscalar(Testes.iluminacao.Il,
					Testes.iluminacao.Ks * Math.pow(produtoEscalar3D(R, V), Testes.iluminacao.Eta));
		}
		if (Id == null) {
			Id = produtoComponentePonto3D(Testes.iluminacao.Kd, produtoComponentePonto3D(Testes.iluminacao.Il,
					produtoPonto3DPorEscalar(Testes.iluminacao.Od, produtoEscalar3D(N, L))));
		}

		Ponto3D I = somarPontos(Ia, Id);
		I = somarPontos(Is, I);

//		Limites RGB
		I.x = Math.min((int) Math.round(I.x), 255);
		I.x = Math.max((int) Math.round(I.x), 0);
		I.y = Math.min((int) Math.round(I.y), 255);
		I.y = Math.max((int) Math.round(I.y), 0);
		I.z = Math.min((int) Math.round(I.z), 255);
		I.z = Math.max((int) Math.round(I.z), 0);

		p.z = -p.z;
		atualizar_zBuffer(Math.round(x), scanlineY, p.z, Color.rgb((int) I.x, (int) I.y, (int) I.z));
	}

	private static double[][] calcularR(double[][] N, double[][] L) {
		double[][] R;
		R = produtoPorEscalar(N, 2 * produtoEscalar3D(N, L));
		R[0][0] -= L[0][0];
		R[1][0] -= L[1][0];
		R[2][0] -= L[2][0];
		return R;
	}

	@SuppressWarnings("unused")
	private static void analisarOrdem(Ponto2D inicio, Ponto2D meio, Ponto2D fim) {
		if (inicio.y <= meio.y && meio.y <= fim.y) {
			System.out.println("ordem ok\ninicio = " + inicio.x + " " + inicio.y + "\nmeio = " + meio.x + " " + meio.y
					+ "\nfim = " + fim.x + " " + fim.y);
		} else {
			System.err.println("Problema na ordem\ny1 = " + inicio.y + "\ny2 = " + meio.y + "\ny3 = " + fim.y);
			return;
		}
	}

	public static void malhaPontos(GraphicsContext gc, double xmax, double ymax) {
		gc.setFill(Color.BLUEVIOLET); // Cor do fundo
		gc.fillRect(0, 0, xmax, ymax); // Pinta o fundo
		double maxX = Testes.p[0].x, maxY = Testes.p[0].y;
		double minX = Testes.p[0].x, minY = Testes.p[0].y;
		for (int i = 1; i < Testes.p.length; i++) {
			if (Testes.p[i].x > maxX) {
				maxX = Testes.p[i].x;
			}
			if (Testes.p[i].y > maxY) {
				maxY = Testes.p[i].y;
			}

			if (Testes.p[i].x < minX) {
				minX = Testes.p[i].x;
			}
			if (Testes.p[i].y < minY) {
				minY = Testes.p[i].y;
			}
		}
		for (int i = 0; i < Testes.p.length; i++) {
			double ponto[] = projetaPontoNaTela(Testes.p[i], xmax, ymax);
			gc.setFill(Color.GOLD); // Cor do Ponto
			gc.fillRect(ponto[0], ponto[1], 3, 3); // Tamanho do Ponto
		}
	}

	private static void malhaTriangulos(GraphicsContext gc, double xmax, double ymax) {
		gc.setFill(Color.BLACK); // Cor do fundo
		gc.fillRect(0, 0, xmax, ymax); // Pinta o fundo
		double maxX = Testes.triangulosOrdenadosPara_zBuffer[0].a.x,
				maxY = Testes.triangulosOrdenadosPara_zBuffer[0].a.y;
		double minX = Testes.triangulosOrdenadosPara_zBuffer[0].a.x,
				minY = Testes.triangulosOrdenadosPara_zBuffer[0].a.y;
		for (int i = 0; i < Testes.triangulosOrdenadosPara_zBuffer.length; i++) {
			maxX = Math.max(maxX, Testes.triangulosOrdenadosPara_zBuffer[i].a.x);
			maxX = Math.max(maxX, Testes.triangulosOrdenadosPara_zBuffer[i].b.x);
			maxX = Math.max(maxX, Testes.triangulosOrdenadosPara_zBuffer[i].c.x);

			maxY = Math.max(maxY, Testes.triangulosOrdenadosPara_zBuffer[i].a.y);
			maxY = Math.max(maxY, Testes.triangulosOrdenadosPara_zBuffer[i].b.y);
			maxY = Math.max(maxY, Testes.triangulosOrdenadosPara_zBuffer[i].c.y);

			minX = Math.min(minX, Testes.triangulosOrdenadosPara_zBuffer[i].a.x);
			minX = Math.min(minX, Testes.triangulosOrdenadosPara_zBuffer[i].b.x);
			minX = Math.min(minX, Testes.triangulosOrdenadosPara_zBuffer[i].c.x);

			minY = Math.min(minY, Testes.triangulosOrdenadosPara_zBuffer[i].a.y);
			minY = Math.min(minY, Testes.triangulosOrdenadosPara_zBuffer[i].b.y);
			minY = Math.min(minY, Testes.triangulosOrdenadosPara_zBuffer[i].c.y);

		}
		for (int i = 0; i < Testes.triangulosOrdenadosPara_zBuffer.length; i++) {
			double pontoA[] = projetaPontoNaTela(Testes.triangulosOrdenadosPara_zBuffer[i].a, xmax, ymax);

			double pontoB[] = projetaPontoNaTela(Testes.triangulosOrdenadosPara_zBuffer[i].b, xmax, ymax);

			double pontoC[] = projetaPontoNaTela(Testes.triangulosOrdenadosPara_zBuffer[i].c, xmax, ymax);

			Testes.triangulosOrdenadosPara_zBuffer[i].telaA = new Ponto2D(pontoA[0], pontoA[1], "a");
			Testes.triangulosOrdenadosPara_zBuffer[i].telaB = new Ponto2D(pontoB[0], pontoB[1], "b");
			Testes.triangulosOrdenadosPara_zBuffer[i].telaC = new Ponto2D(pontoC[0], pontoC[1], "c");

			gc.setFill(Color.WHITE); // Cor do Ponto
			gc.fillRect(pontoA[0], pontoA[1], 1, 1); // Tamanho do Ponto
			gc.fillRect(pontoB[0], pontoB[1], 1, 1); // Tamanho do Ponto
			gc.fillRect(pontoC[0], pontoC[1], 1, 1); // Tamanho do Ponto
			scanLine(gc, Testes.triangulosOrdenadosPara_zBuffer[i]);

		}

	}

	@SuppressWarnings("unused")
	private static void malhaTriangulosDebug(GraphicsContext gc, double xmax, double ymax) {
		gc.setFill(Color.BLACK); // Cor do fundo
		gc.fillRect(0, 0, xmax, ymax); // Pinta o fundo
		double maxX = Testes.t[0].a.x, maxY = Testes.t[0].a.y;
		double minX = Testes.t[0].a.x, minY = Testes.t[0].a.y;
		for (int i = 0; i < Testes.t.length; i++) {
			maxX = Math.max(maxX, Testes.t[i].a.x);
			maxX = Math.max(maxX, Testes.t[i].b.x);
			maxX = Math.max(maxX, Testes.t[i].c.x);

			maxY = Math.max(maxY, Testes.t[i].a.y);
			maxY = Math.max(maxY, Testes.t[i].b.y);
			maxY = Math.max(maxY, Testes.t[i].c.y);

			minX = Math.min(minX, Testes.t[i].a.x);
			minX = Math.min(minX, Testes.t[i].b.x);
			minX = Math.min(minX, Testes.t[i].c.x);

			minY = Math.min(minY, Testes.t[i].a.y);
			minY = Math.min(minY, Testes.t[i].b.y);
			minY = Math.min(minY, Testes.t[i].c.y);

		}

		double pontoA[] = projetaPontoNaTela(Testes.t[Testes.atual].a, xmax, ymax);

		double pontoB[] = projetaPontoNaTela(Testes.t[Testes.atual].b, xmax, ymax);

		double pontoC[] = projetaPontoNaTela(Testes.t[Testes.atual].c, xmax, ymax);

		gc.setFill(Color.WHITE); // Cor do Ponto

		scanLine(gc, Testes.t[Testes.atual]);
		System.out.println(Testes.atual);
		Testes.atual++;
	}

	private static double[] projetaPontoNaTela(Ponto3D a, double xmax, double ymax) {
		double pontoA[] = new double[2];
		pontoA[0] = (Testes.camera.d * a.x) / (a.z * Testes.camera.hx);
		pontoA[1] = (Testes.camera.d * a.y) / (a.z * Testes.camera.hy);
		pontoA[0] = Math.floor(((pontoA[0] + 1) / 2) * xmax + 0.5);
		pontoA[1] = Math.floor(ymax - ((pontoA[1] + 1) / 2) * ymax + 0.5);
		return pontoA;
	}

	private static void atualizarCoordVista(String arquivoSemExtensao) {
		for (int i = 0; i < Testes.t.length; i++) {
			BibOps.atualizaTrianguloParaCoordVista(Testes.t[i], Testes.camera);
		}
	}

	private static double[][] normalDoTriangulo(Triangulo a) {
		return normalizaVetor3D(produtoVetorial3DMaoEsq(subtPontos3D(a.b, a.a), subtPontos3D(a.c, a.a)));
	}

	private static ArrayList<double[][]> normaisDosTriangulos(Triangulo[] t) {
		ArrayList<double[][]> listaDeNormais = new ArrayList<double[][]>();
		for (int i = 0; i < t.length; i++) {
			listaDeNormais.add(normalDoTriangulo(t[i]));
			t[i].normal = listaDeNormais.get(i);
		}
		return listaDeNormais;
	}

	private static double[][] somarVetores3D(double A[][], double B[][]) {
		double[][] C = new double[3][1];
		C[0][0] = A[0][0] + B[0][0];
		C[1][0] = A[1][0] + B[1][0];
		C[2][0] = A[2][0] + B[2][0];
		return C;
	}

	private static ArrayList<Triangulo> triangulosDeUmPonto(Ponto3D ponto, Triangulo t[]) {
		ArrayList<Triangulo> lista = new ArrayList<Triangulo>();
		for (int i = 0; i < t.length; i++) {
			if (t[i].original1 == ponto || t[i].original2 == ponto || t[i].original3 == ponto)
				lista.add(t[i]);
		}
		return lista;
	}

	private static double[][] normalDeUmVertice(Ponto3D vertice, Triangulo t[]) {
		double[][] normal = new double[3][1];
		normal[0][0] = 0;
		normal[1][0] = 0;
		normal[2][0] = 0;
		ArrayList<Triangulo> listaDeTriangulos = triangulosDeUmPonto(vertice, t);
		for (int i = 0; i < listaDeTriangulos.size(); i++) {
			normal = somarVetores3D(normal, listaDeTriangulos.get(i).normal);
		}
		return normal;
	}

	private static ArrayList<double[][]> normaisDosVertices(Ponto3D pontos[], Triangulo t[]) {
		ArrayList<double[][]> listaDeNormais = new ArrayList<double[][]>();
		for (int i = 0; i < pontos.length; i++) {
			listaDeNormais.add(normalizaVetor3D(normalDeUmVertice(pontos[i], t)));
			pontos[i].normal = listaDeNormais.get(i);
		}
		return listaDeNormais;
	}

	private static void setBaricentroTriangulos(Triangulo t[]) {
		for (int i = 0; i < t.length; i++) {
			t[i].setBaricentro();
		}
	}

	private static void setReferenciasOriginaisDosTriangulos() {
		for (int i = 0; i < Testes.t.length; i++) {
			Testes.t[i].setOriginais();
		}
	}

	private static void heapify(Triangulo[] t, int i, int n) {
		while (2 * (i + 1) - 1 <= n) {// tem filhos
			int maior = 0;
			if ((2 * (i + 1) - 1) < n) { // tem dois filhos
				if (t[2 * i + 1].baricentro.y < t[2 * i + 2].baricentro.y) {
					maior = 2 * i + 2;
				} else {
					maior = 2 * i + 1;
				}
			} else {
				maior = 2 * i + 1;
			}
			if (t[i].baricentro.y <= t[maior].baricentro.y) {
				double aux = t[i].baricentro.y;
				t[i].baricentro.y = t[maior].baricentro.y;
				t[maior].baricentro.y = aux;
				i = maior;
			} else {
				i = n;
			}
		}
	}

	private static void buildMaxHeap(Triangulo[] t) {
		for (int i = t.length / 2 - 1; i >= 0; i--) {
			heapify(t, i, t.length - 1);
		}

	}

	private static Triangulo[] ordenarTriangulos_zBuffer(Triangulo[] t) {
		Triangulo[] zBuffer = t.clone();
		buildMaxHeap(zBuffer);
		for (int i = zBuffer.length - 1; i >= 1; i--) {
			double aux = zBuffer[0].baricentro.y;
			zBuffer[0].baricentro.y = zBuffer[i].baricentro.y;
			zBuffer[i].baricentro.y = aux;
			heapify(zBuffer, 0, i - 1);
		}
		return zBuffer;
	}

	private static double[][] produtoPorEscalar(double[][] vet, double escalar) {
		double retorno[][] = new double[3][1];
		retorno[0][0] = vet[0][0] * escalar;
		retorno[1][0] = vet[1][0] * escalar;
		retorno[2][0] = vet[2][0] * escalar;
		return retorno;
	}

	public static void imprimeMatriz(double m[][], int x, int y) {
		System.out.print("(");
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				System.out.print(m[i][j] + ", ");
			}
		}
		System.out.println(")");
		System.out.println("---------------------");
	}
}
