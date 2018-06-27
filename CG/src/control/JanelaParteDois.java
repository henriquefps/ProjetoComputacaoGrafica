package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import va1.BibOps;
import va1.Ponto3D;
import va1.Testes;

public class JanelaParteDois implements Initializable {

	@FXML
	private TextField nValue;

	@FXML
	private TextField vValue;

	@FXML
	private TextField dValue;

	@FXML
	private TextField hxValue;

	@FXML
	private TextField hyValue;

	@FXML
	private TextField cValue;
	
	@FXML
	private TextField labelArquivo;

	@FXML
	private AnchorPane janela;

	@FXML
	private Canvas desenho;

	@FXML
	private Button calcular;

	@FXML
	private Label alerta;

	@FXML
	void mudarPerspectiva(){
		Testes.camera.atualizarParametrosDeCamera(getV(), getN(), Double.parseDouble(dValue.getText()),
				Double.parseDouble(hxValue.getText()), Double.parseDouble(hyValue.getText()), getC());
		
		Testes.arquivo = labelArquivo.getText();

		BibOps.atualizarCoordVista(Testes.arquivo);
		desenho.getGraphicsContext2D().fillRect(0, 0, Testes.xmax, Testes.ymax);
		BibOps.malhaTriangulos(desenho.getGraphicsContext2D(), Testes.xmax, Testes.ymax);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.nValue.setText("0 1.4 -1");
		this.vValue.setText("0 -1 -1");
		this.dValue.setText("10");
		this.hxValue.setText("1.5");
		this.hyValue.setText("1.5");
		this.cValue.setText("0 -500 500");
		this.labelArquivo.setText(Testes.arquivo);
		BibOps.atualizarCoordVista(Testes.arquivo);
		BibOps.malhaTriangulos(desenho.getGraphicsContext2D(), Testes.xmax, Testes.ymax);
		
	}

	private Ponto3D getC() {
		String linha[] = cValue.getText().split(" ");
		return new Ponto3D(Double.parseDouble(linha[0]), Double.parseDouble(linha[1]), Double.parseDouble(linha[2]));
	}

	private double[][] getN() {
		double dres[][] = new double[3][1];
		String[] res = this.nValue.getText().split(" ");
		try {
			if (res.length != 3)
				throw new Exception();
			dres[0][0] = Double.parseDouble(res[0]);
			dres[1][0] = Double.parseDouble(res[1]);
			dres[2][0] = Double.parseDouble(res[2]);
		} catch (Exception e) {
			alerta.setText("Vetor N deve possuir 3 valores");
		}
		return dres;
	}

	private double[][] getV() {
		double dres[][] = new double[3][1];
		String[] res = this.vValue.getText().split(" ");
		try {
			if (res.length != 3)
				throw new Exception();
			dres[0][0] = Double.parseDouble(res[0]);
			dres[1][0] = Double.parseDouble(res[1]);
			dres[2][0] = Double.parseDouble(res[2]);
		} catch (Exception e) {
			alerta.setText("Vetor V deve possuir 3 valores");
		}
		return dres;
	}
}
