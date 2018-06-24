package va1;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


@SuppressWarnings("serial")
public class Painel extends JPanel {

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
//		int pixel = 0;
//
//		int xa = 100, ya = 100, xb = 300, yb = 100, xc = 100, yc = 300, xd = 300, yd = 300;
//		pintaQuadrado(xa, ya, xb, yb, xc, yc, xd, yd, g);
		

	}
	private void malhaTriangulos(GraphicsContext gc) {
	gc.setFill(Color.BLACK);
	gc.fillRect(0, 0, 400, 400);
		double maxX = Testes.p[0].x, maxY = Testes.p[0].y;
		double minX = Testes.p[0].x, minY = Testes.p[0].y;
		for(int i = 1; i < Testes.p.length;i++) {
			if(Testes.p[i].x > maxX) {
				maxX = Testes.p[i].x;
			}
			if(Testes.p[i].y > maxY) {
				maxY = Testes.p[i].y;
			}
			
			if(Testes.p[i].x < minX) {
				minX = Testes.p[i].x;
			}
			if(Testes.p[i].y < minY) {
				minY = Testes.p[i].y;
			}
		}
		for(int i = 0; i < Testes.p.length;i++) {
			double ponto[] = {Testes.p[i].x, Testes.p[i].y};
			ponto[0] = ((ponto[0]-minX)/(maxX-minX))*(400-1);
			ponto[1] = ((ponto[1]-minY)/(maxY-minY))*(400-1);
			gc.setFill(Color.WHITE);
			gc.fillRect(ponto[0], ponto[1], 10, 10);
		}

	}


	public void pintaQuadrado(int xa, int ya, int xb, int yb, int xc, int yc, int xd, int yd, Graphics g) {
		// pintando AB
		for (int i = xa; i < xb; i++) {
			g.drawLine(xa, ya, i, yb);
			g.drawString("A", xa, ya);
		}

		// pintando AC
		for (int i = ya; i < yc; i++) {
			g.drawLine(xa, ya, xc, i);
			g.drawString("C", xc, yc);
		}

		// pintando BD
		for (int i = yb; i < yd; i++) {
			g.drawLine(xb, yb, xd, i);
			g.drawString("B", xb, yb);
		}
		// pintando CD
		for (int i = xc; i < xd; i++) {
			g.drawLine(xc, yc, i, yd);
			g.drawString("D", xd, yd);
		}
	}
}