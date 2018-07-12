package va1;

import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BibOps {

	public static void executarTarefaInicial(GraphicsContext desenho) {
		Testes.getPontosArquivo(Testes.arquivo);
		BibOps.iniciarMatriz_zBuffer();
		BibOps.setReferenciasOriginaisDosTriangulos();
		BibOps.atualizarCoordVista(Testes.arquivo);
		BibOps.normaisDosTriangulos(Testes.t);
		BibOps.normaisDosVertices(Testes.p, Testes.t);
		BibOps.setBaricentroTriangulos(Testes.t);
		Testes.triangulosOrdenadosPara_zBuffer = BibOps.ordenarTriangulos_zBuffer(Testes.t);
		// BibOps.malhaPontos(desenho.getGraphicsContext2D(), Testes.xmax, Testes.ymax);
		BibOps.malhaTriangulos(desenho, Testes.xmax, Testes.ymax);
	}

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
		// BibOps.malhaPontos(desenho.getGraphicsContext2D(), Testes.xmax, Testes.ymax);
		BibOps.malhaTriangulos(desenho, Testes.xmax, Testes.ymax);
	}

	public static void iniciarMatriz_zBuffer() {
		Testes.matrix_zBuffer = new double[Testes.xmax][Testes.ymax];
		for (int i = 0; i < Testes.matrix_zBuffer.length; i++) {
			for (int j = 0; j < Testes.matrix_zBuffer.length; j++) {
				Testes.matrix_zBuffer[i][j] = Double.MIN_VALUE;
			}
		}
	}

	public static void atualizar_zBuffer(int i, int j, double profundidadeAtual) {
		if (Testes.matrix_zBuffer[i][j] < profundidadeAtual) {
			Testes.matrix_zBuffer[i][j] = profundidadeAtual;
		}
	}

	public static double[][] matrixMulti(double[][]A, double[][] B, int x1, int y1, int x2, int y2) {
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

	public static double produtoEscalar3D(double[][] A, double[][] B) {
		double v = 0;
		for (int i = 0; i < 3; i++) {
			v += A[i][0] * B[i][0];
		}
		return v;
	}

	public static Ponto3D produtoComponentePonto3D(Ponto3D a, Ponto3D b) {
		return new Ponto3D(a.x * b.x, a.y * b.y, a.z * b.z);
	}

	public static Ponto3D somarPontos(Ponto3D a, Ponto3D b) {
		return new Ponto3D(a.x + b.x, a.y + b.y, a.z + b.z);
	}

	public static double[][] produtoComponenteVetor(double[][] a, double[][] b) {
		double[][] c = new double[3][1];
		c[0][0] = a[0][0] * c[0][0];
		c[1][0] = a[1][0] * c[1][0];
		c[2][0] = a[2][0] * c[2][0];
		return c;
	}

	public static Ponto3D produtoPonto3DPorEscalar(Ponto3D a, double escalar) {
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

	public static Ponto2D cartesianaDaBaricentrica(Ponto2D p1, Ponto2D p2, Ponto2D p3, double alfa, double beta,
			double gama) {

		double x = alfa * p1.x + beta * p2.x + gama * p3.x;
		double y = alfa * p1.y + beta * p2.y + gama * p3.y;

		return new Ponto2D(x, y);
	}

	public static double[][] inversa(double m[][], double determinante) {
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

	public static Ponto3D coordenadasDeVista(CameraVirtual camera, Ponto3D ponto) {
		double p_c[][] = subtPontos3D(ponto, camera.C);
		return pontoDeVetor3x1(matrixMulti(camera.retorneBaseOrtonormal(), p_c, 3, 3, 3, 1));
	}

	public static Ponto3D pontoDeVetor3x1(double v[][]) {
		return new Ponto3D(v[0][0], v[1][0], v[2][0]);
	}

	public static void atualizaTrianguloParaCoordVista(Triangulo t, CameraVirtual camera) {
		t.a = BibOps.coordenadasDeVista(camera, t.a);
		t.b = BibOps.coordenadasDeVista(camera, t.b);
		t.c = BibOps.coordenadasDeVista(camera, t.c);
	}

	public static void scanLine(Ponto2D a, Ponto2D b, Ponto2D c, GraphicsContext gc, Triangulo t) {
		double ymin = Math.min(a.y, b.y);
		ymin = Math.min(ymin, c.y);

		Ponto2D inicio = null, meio = null, fim = null;
		if (ymin == a.y) {
			inicio = a;
			if (b.y <= c.y) {
				meio = b;
				fim = c;
			} else {
				meio = c;
				fim = b;
			}
		} else if (ymin == b.y) {
			inicio = b;
			if (a.y <= c.y) {
				meio = a;
				fim = c;
			} else {
				meio = c;
				fim = a;
			}
		} else if (ymin == c.y) {
			inicio = c;
			if (b.y <= a.y) {
				meio = b;
				fim = a;
			} else {
				meio = a;
				fim = b;
			}
		} else {
			System.err.println("ymin não corresponde a nada");
		}

		if ((int) meio.y == (int) fim.y /* || (int)meio.y == ((int)fim.y + 1) || (int)meio.y == ((int)fim.y - 1) */) {
			// System.out.println("Base Chata");
			// analisarOrdem(inicio, meio, fim);
			// gc.setFill(Color.RED);
			preencherTrianguloSuperior(gc, inicio, meio, fim, t);
		} else if ((int) inicio.y == (int) meio.y/*
													 * || (int)inicio.y == ((int)meio.y + 1) || (int)inicio.y ==
													 * ((int)meio.y - 1)
													 */) {
			// System.out.println("Topo Chato");
			// analisarOrdem(inicio, meio, fim);
			// gc.setFill(Color.GREEN);
			preencherTrianguloInferior(gc, inicio, meio, fim, t);
		} else {
			// System.out.println("Normal");
			// analisarOrdem(inicio, meio, fim);
			// gc.setFill(Color.BLUE);
			// Ponto2D v4 = new Ponto2D((double)(inicio.x + ((double)(meio.y - inicio.y) /
			// (double)(fim.y - inicio.y)) * (fim.x - inicio.x)), meio.y);
			preencherTrianguloSuperior(gc, inicio, meio, fim, t);
			preencherTrianguloInferior(gc, inicio, meio, fim, t);
		}

	}

	public static void preencherTrianguloSuperior(GraphicsContext gc, Ponto2D inicio, Ponto2D meio, Ponto2D fim,
			Triangulo t) {
//		if (Testes.atual == 945) {
//			System.out.println("Inicio = (" + inicio.x + ", " + inicio.y + ")");
//			System.out.println("Meio = (" + meio.x + ", " + meio.y + ")");
//			System.out.println("Fim = (" + fim.x + ", " + fim.y + ")");
//		}
		double coefRetaMin, coefRetaMax;
		if (meio.x < fim.x) {
			coefRetaMin = (meio.x - inicio.x) / (meio.y - inicio.y);
			coefRetaMax = (fim.x - inicio.x) / (fim.y - inicio.y);
		} else {

			coefRetaMin = (fim.x - inicio.x) / (fim.y - inicio.y);
			coefRetaMax = (meio.x - inicio.x) / (meio.y - inicio.y);
		}

		double xmax = (int) inicio.x;
		double xmin = (int) inicio.x;

		// if (Testes.atual == 945) {
		// System.out.println("cmin " + coefRetaMin);
		// System.out.println("cmax " + coefRetaMax + "\n");
		// System.out.println("xmin " + xmin);
		// System.out.println("xmax " + xmax + "\n");
		// }
		int scanlineY;
		for (scanlineY = (int) inicio.y; scanlineY <= (int) meio.y; scanlineY++) {
			double aux = Math.max(inicio.x, meio.x);
			aux = Math.max(aux, fim.x);
			for (int x = (int) xmin; x <= (int) xmax && x < aux; x++) {
				// gc.setFill(Color.RED);
				Ponto3D p = coordBaricentricas2D(new Ponto2D(x, scanlineY), inicio, meio, fim);
				atualizar_zBuffer(x, scanlineY, p.z);
				double[][] n1 = produtoPorEscalar(t.original1.normal, p.x);
				double[][] n2 = produtoPorEscalar(t.original2.normal, p.y);
				double[][] n3 = produtoPorEscalar(t.original3.normal, p.z);
				n1 = somarVetores3D(n1, n2);
				p.normal = normalizaVetor3D(somarVetores3D(n1, n3));
				double[][] N = p.normal;
				double[][] V = normalizaVetor3D(subtPontos3D(Testes.camera.C, p));
				double[][] L = normalizaVetor3D(subtPontos3D(Testes.iluminacao.Pl, p));
				double[][] R = calcularR(N, L);
				Ponto3D Ia, Id, Is;
				if (produtoEscalar3D(R, V) < 0) {
					Is = new Ponto3D(0, 0, 0);
				} else {
					Is = produtoPonto3DPorEscalar(Testes.iluminacao.Il, produtoEscalar3D(R, V) * Testes.iluminacao.Ks);
				}
				if (produtoEscalar3D(N, L) < 0) {
					Is = new Ponto3D(0, 0, 0);
					Id = new Ponto3D(0, 0, 0);
				} else {
					Id = produtoComponentePonto3D(Testes.iluminacao.Il, produtoPonto3DPorEscalar(Testes.iluminacao.Od,
							produtoEscalar3D(N, L) * Testes.iluminacao.Ks));
				}
				Ia = produtoPonto3DPorEscalar(Testes.iluminacao.Iamb, Testes.iluminacao.Ka);

				Ponto3D I = somarPontos(Ia, Id);
				I = somarPontos(Is, I);

				gc.setFill(Color.rgb((int)Math.round(I.x), (int)Math.round(I.y), (int)Math.round(I.z)));
				gc.fillRect(x, scanlineY, 1, 1);
			}
			xmin += coefRetaMin;
			xmax += coefRetaMax;
		}
	}

	public static void preencherTrianguloInferior(GraphicsContext gc, Ponto2D inicio, Ponto2D meio, Ponto2D fim,
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
		for (int scanlineY = (int) fim.y; scanlineY >= meio.y; scanlineY--) {
			// gc.setFill(Color.BLUE);

			for (int x = (int) xmin; x <= (int) xmax; x++) {
				Ponto3D p = coordBaricentricas2D(new Ponto2D(x, scanlineY), inicio, meio, fim);
				atualizar_zBuffer(x, scanlineY, p.z);
				double[][] n1 = produtoPorEscalar(t.original1.normal, p.x);
				double[][] n2 = produtoPorEscalar(t.original2.normal, p.y);
				double[][] n3 = produtoPorEscalar(t.original3.normal, p.z);
				n1 = somarVetores3D(n1, n2);
				p.normal = normalizaVetor3D(somarVetores3D(n1, n3));
				double[][] N = p.normal;
				double[][] V = normalizaVetor3D(subtPontos3D(Testes.camera.C, p));
				double[][] L = normalizaVetor3D(subtPontos3D(Testes.iluminacao.Pl, p));
				double[][] R = calcularR(N, L);
				Ponto3D Ia, Id, Is;
				if (produtoEscalar3D(R, V) < 0) {
					Is = new Ponto3D(0, 0, 0);
				} else {
					Is = produtoPonto3DPorEscalar(Testes.iluminacao.Il, produtoEscalar3D(R, V) * Testes.iluminacao.Ks);
				}
				if (produtoEscalar3D(N, L) < 0) {
					Is = new Ponto3D(0, 0, 0);
					Id = new Ponto3D(0, 0, 0);
				} else {
					Id = produtoComponentePonto3D(Testes.iluminacao.Il, produtoPonto3DPorEscalar(Testes.iluminacao.Od,
							produtoEscalar3D(N, L) * Testes.iluminacao.Ks));
				}
				Ia = produtoPonto3DPorEscalar(Testes.iluminacao.Iamb, Testes.iluminacao.Ka);

				Ponto3D I = somarPontos(Ia, Id);
				I = somarPontos(Is, I);
				
				
//				System.out.println("Ix = " + I.x + "\nIy = " + I.y + "\nIz = " + I.z);
//				System.out.println("Isx = " + Is.x + "\nIsy = " + Is.y + "\nIsz = " + Is.z);
//				System.out.println("<N, L> = " + produtoEscalar3D(N, L));
//				System.out.println("<R, V> = " + produtoEscalar3D(R, V));
//				System.out.println("Idx = " + Id.x + "\nIdy = " + Id.y + "\nIdz = " + Id.z);
//				System.out.println("Iax = " + Ia.x + "\nIay = " + Ia.y + "\nIaz = " + Ia.z);
				

				gc.setFill(Color.rgb((int)Math.round(I.x), (int)Math.round(I.y), (int)Math.round(I.z)));
				gc.fillRect(x, scanlineY, 1, 1);
			}
			xmin -= coefRetaMin;
			xmax -= coefRetaMax;
		}
	}

	private static double[][] calcularR(double[][] N, double[][] L) {
		double[][] R = new double[3][1];
		R = produtoPorEscalar(N, 2 * produtoEscalar3D(N, L));
		R[0][0] -= L[0][0];
		R[1][0] -= L[1][0];
		R[2][0] -= L[2][0];
		return R;
	}

	@SuppressWarnings("unused")
	private static void analisarOrdem(Ponto2D inicio, Ponto2D meio, Ponto2D fim) {
		if (inicio.y <= meio.y && meio.y <= fim.y) {
			System.out.println("ordem ok\ny1 = " + inicio.y + "\ny2 = " + meio.y + "\ny3 = " + fim.y);
		} else {
			System.err.println("Problema na ordem\ny1 = " + inicio.y + "\ny2 = " + meio.y + "\ny3 = " + fim.y);
			return;
		}
	}

	public static void malhaPontos(GraphicsContext gc, int xmax, int ymax) {
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
			double ponto[] = { Testes.p[i].x, Testes.p[i].y };
			ponto[0] = ((ponto[0] - minX) / (maxX - minX)) * (xmax - 11) + 1;
			ponto[1] = ((ponto[1] - minY) / (maxY - minY)) * (ymax - 11) + 1;
			gc.setFill(Color.GOLD); // Cor do Ponto
			gc.fillRect(ponto[0], ponto[1], 3, 3); // Tamanho do Ponto
		}
	}

	public static void malhaTriangulos(GraphicsContext gc, int xmax, int ymax) {
		ArrayList<double[][]> listaDePontos = new ArrayList<double[][]>();

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
		for (int i = 0; i < Testes.t.length; i++) {
			double pontoA[] = new double[2];
			pontoA[0] = (Testes.camera.d * Testes.t[i].a.x / Testes.t[i].a.z) / Testes.camera.hx;
			pontoA[1] = (Testes.camera.d * Testes.t[i].a.y / Testes.t[i].a.z) / Testes.camera.hy;
			pontoA[0] = Math.floor((pontoA[0] + Testes.camera.hx) / (2 * Testes.camera.hx) * xmax + 0.5);
			pontoA[1] = Math.floor(ymax - (pontoA[1] + Testes.camera.hy) / (2 * Testes.camera.hy) * ymax + 0.5);

			double pontoB[] = new double[2];
			pontoB[0] = (Testes.camera.d * Testes.t[i].b.x / Testes.t[i].b.z) / Testes.camera.hx;
			pontoB[1] = (Testes.camera.d * Testes.t[i].b.y / Testes.t[i].b.z) / Testes.camera.hy;
			pontoB[0] = Math.floor((pontoB[0] + Testes.camera.hx) / (2 * Testes.camera.hx) * xmax + 0.5);
			pontoB[1] = Math.floor(ymax - (pontoB[1] + Testes.camera.hy) / (2 * Testes.camera.hy) * ymax + 0.5);

			double pontoC[] = new double[2];
			pontoC[0] = (Testes.camera.d * Testes.t[i].c.x / Testes.t[i].c.z) / Testes.camera.hx;
			pontoC[1] = (Testes.camera.d * Testes.t[i].c.y / Testes.t[i].c.z) / Testes.camera.hy;
			pontoC[0] = Math.floor((pontoC[0] + Testes.camera.hx) / (2 * Testes.camera.hx) * xmax + 0.5);
			pontoC[1] = Math.floor(ymax - (pontoC[1] + Testes.camera.hy) / (2 * Testes.camera.hy) * ymax + 0.5);

			listaDePontos.add(new double[][] { pontoA, pontoB, pontoC });

			gc.setFill(Color.WHITE); // Cor do Ponto
			gc.fillRect(pontoA[0], pontoA[1], 1, 1); // Tamanho do Ponto
			gc.fillRect(pontoB[0], pontoB[1], 1, 1); // Tamanho do Ponto
			gc.fillRect(pontoC[0], pontoC[1], 1, 1); // Tamanho do Ponto

		}

		scanLineEmListaDePontos(listaDePontos, gc, Testes.t);

	}

	private static void scanLineEmListaDePontos(ArrayList<double[][]> listaDePontos, GraphicsContext gc,
			Triangulo t[]) {
		for (int i = 0; i < listaDePontos.size(); i++) {
			BibOps.scanLine(new Ponto2D(listaDePontos.get(i)[0][0], listaDePontos.get(i)[0][1]),
					new Ponto2D(listaDePontos.get(i)[1][0], listaDePontos.get(i)[1][1]),
					new Ponto2D(listaDePontos.get(i)[2][0], listaDePontos.get(i)[2][1]), gc, t[i]);
			Testes.atual++;
		}
	}

	public static void atualizarCoordVista(String arquivoSemExtensao) {
		for (int i = 0; i < Testes.t.length; i++) {
			BibOps.atualizaTrianguloParaCoordVista(Testes.t[i], Testes.camera);
		}
	}

	private static double[][] normalDoTriangulo(Triangulo a) {
		return produtoVetorial3D(subtPontos3D(a.b, a.a), subtPontos3D(a.c, a.a));
	}

	public static ArrayList<double[][]> normaisDosTriangulos(Triangulo[] t) {
		ArrayList<double[][]> listaDeNormais = new ArrayList<double[][]>();
		for (int i = 0; i < t.length; i++) {
			listaDeNormais.add(normalizaVetor3D(normalDoTriangulo(t[i])));
			t[i].normal = listaDeNormais.get(i);
		}
		return listaDeNormais;
	}

	public static double[][] somarVetores3D(double A[][], double B[][]) {
		double[][] C = new double[3][1];
		C[0][0] = A[0][0] + B[0][0];
		C[1][0] = A[1][0] + B[1][0];
		C[2][0] = A[2][0] + B[2][0];
		return C;
	}

	private static ArrayList<Triangulo> triangulosDeUmPonto(Ponto3D ponto, Triangulo t[]) {
		ArrayList<Triangulo> lista = new ArrayList<Triangulo>();
		for (int i = 0; i < t.length; i++) {
			if (t[i].original1 == ponto || t[i].original2 == ponto || t[i].original3 == ponto) {
				lista.add(t[i]);
			}
		}
		return lista;
	}

	private static double[][] normalDeUmVertice(Ponto3D vertice, Triangulo t[]) {
		double[][] normal = new double[3][1];
		normal[0][0] = 0;
		normal[1][0] = 0;
		normal[2][0] = 0;
		ArrayList<Triangulo> listaDeTriangulos = triangulosDeUmPonto(vertice, t);
//		System.out.println(listaDeTriangulos.size());
		for (int i = 0; i < listaDeTriangulos.size(); i++) {
			normal = somarVetores3D(normal, listaDeTriangulos.get(i).normal);

		}
		return normal;
	}

	public static ArrayList<double[][]> normaisDosVertices(Ponto3D pontos[], Triangulo t[]) {
		ArrayList<double[][]> listaDeNormais = new ArrayList<double[][]>();
		for (int i = 0; i < pontos.length; i++) {
			listaDeNormais.add(normalizaVetor3D(normalDeUmVertice(pontos[i], t)));
			pontos[i].normal = listaDeNormais.get(i);
		}
		return listaDeNormais;
	}

	public static void setBaricentroTriangulos(Triangulo t[]) {
		for (int i = 0; i < t.length; i++) {
			t[i].setBaricentro();
		}
	}

	public static void setReferenciasOriginaisDosTriangulos() {
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

	public static Triangulo[] ordenarTriangulos_zBuffer(Triangulo[] t) {
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

	public static double[][] produtoPorEscalar(double[][] vet, double escalar) {
		double retorno[][] = new double[3][1];
		retorno[0][0] = vet[0][0] * escalar;
		retorno[1][0] = vet[1][0] * escalar;
		retorno[2][0] = vet[2][0] * escalar;

		return retorno;
	}
}
