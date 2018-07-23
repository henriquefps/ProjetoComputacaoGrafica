package control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
	private AnchorPane janela;

	@FXML
	private Canvas desenho;

	@FXML
	private Button calcular;

	@FXML
	private Label alerta;

	@FXML
	private MenuButton menuButtonArquivo;

	@FXML
	private TextField kaTextField;

	@FXML
	private TextField iambTextField;

	@FXML
	private TextField kdTextField;

	@FXML
	private TextField odTextField;

	@FXML
	private TextField etaTextField;

	@FXML
	private TextField ksTextField;

	@FXML
	private TextField ilTextField;

	@FXML
	private TextField plTextField;

	@FXML
	void mudarPerspectiva() {
		alerta.setText("");
		Testes.camera.atualizarParametrosDeCamera(getV(), getN(), Double.parseDouble(dValue.getText()),
				Double.parseDouble(hxValue.getText()), Double.parseDouble(hyValue.getText()), getC());

		Testes.iluminacao.atualizarParametros(Double.parseDouble(kaTextField.getText()),
				Double.parseDouble(ksTextField.getText()), Double.parseDouble(etaTextField.getText()), getpl(), getil(),
				getIamb(), getkd(), getod());

		BibOps.executarTarefaBotao(desenho.getGraphicsContext2D());

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.nValue.setText("0 1.4 -1");
		this.vValue.setText("0 -1 -1");
		this.dValue.setText("5");
		this.hxValue.setText("1.5");
		this.hyValue.setText("1.5");
		this.cValue.setText("0 -500 500");
		this.kaTextField.setText("0.2");
		this.iambTextField.setText("100 100 100");
		this.ilTextField.setText("127 213 254");
		this.plTextField.setText("60 5 -10");
		this.kdTextField.setText("0.5 0.3 0.2");
		this.odTextField.setText("0.7 0 1");
		this.ksTextField.setText("0.5");
		this.etaTextField.setText("2");
		menuButtonArquivo.setText(Testes.arquivo);

		BibOps.executarTarefaInicial(desenho.getGraphicsContext2D());

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

	private Ponto3D getIamb() {
		double dres[][] = new double[3][1];
		String[] res = this.iambTextField.getText().split(" ");
		try {
			if (res.length != 3)
				throw new Exception();
			dres[0][0] = Double.parseDouble(res[0]);
			dres[1][0] = Double.parseDouble(res[1]);
			dres[2][0] = Double.parseDouble(res[2]);
		} catch (Exception e) {
			alerta.setText("Vetor N deve possuir 3 valores");
		}
		return BibOps.pontoDeVetor3x1(dres);
	}

	private Ponto3D getil() {
		double dres[][] = new double[3][1];
		String[] res = this.ilTextField.getText().split(" ");
		try {
			if (res.length != 3)
				throw new Exception();
			dres[0][0] = Double.parseDouble(res[0]);
			dres[1][0] = Double.parseDouble(res[1]);
			dres[2][0] = Double.parseDouble(res[2]);
		} catch (Exception e) {
			alerta.setText("Vetor N deve possuir 3 valores");
		}
		return BibOps.pontoDeVetor3x1(dres);
	}

	private Ponto3D getpl() {
		double dres[][] = new double[3][1];
		String[] res = this.plTextField.getText().split(" ");
		try {
			if (res.length != 3)
				throw new Exception();
			dres[0][0] = Double.parseDouble(res[0]);
			dres[1][0] = Double.parseDouble(res[1]);
			dres[2][0] = Double.parseDouble(res[2]);
		} catch (Exception e) {
			alerta.setText("Vetor N deve possuir 3 valores");
		}
		return BibOps.pontoDeVetor3x1(dres);
	}

	private Ponto3D getkd() {
		double dres[][] = new double[3][1];
		String[] res = this.kdTextField.getText().split(" ");
		try {
			if (res.length != 3)
				throw new Exception();
			dres[0][0] = Double.parseDouble(res[0]);
			dres[1][0] = Double.parseDouble(res[1]);
			dres[2][0] = Double.parseDouble(res[2]);
		} catch (Exception e) {
			alerta.setText("Vetor N deve possuir 3 valores");
		}
		return BibOps.pontoDeVetor3x1(dres);
	}

	private Ponto3D getod() {
		double dres[][] = new double[3][1];
		String[] res = this.odTextField.getText().split(" ");
		try {
			if (res.length != 3)
				throw new Exception();
			dres[0][0] = Double.parseDouble(res[0]);
			dres[1][0] = Double.parseDouble(res[1]);
			dres[2][0] = Double.parseDouble(res[2]);
		} catch (Exception e) {
			alerta.setText("Vetor N deve possuir 3 valores");
		}
		return BibOps.pontoDeVetor3x1(dres);
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

	@FXML
	void setStringCalice2() {
		Testes.arquivo = "calice2";
		menuButtonArquivo.setText(Testes.arquivo);
		mudarPerspectiva();
	}

	@FXML
	void setStringMaca() {
		Testes.arquivo = "maca";
		menuButtonArquivo.setText(Testes.arquivo);
		mudarPerspectiva();
	}

	@FXML
	void setStringMaca2() {
		Testes.arquivo = "maca2";
		menuButtonArquivo.setText(Testes.arquivo);
		mudarPerspectiva();
	}

	@FXML
	void setStringVaso() {
		Testes.arquivo = "vaso";
		menuButtonArquivo.setText(Testes.arquivo);
		mudarPerspectiva();
	}
	
    @FXML
    void setStringPiramide() {
		Testes.arquivo = "piramide";
		menuButtonArquivo.setText(Testes.arquivo);
		mudarPerspectiva();
    }

    @FXML
    void setStringTriangulo() {
		Testes.arquivo = "triangulo";
		menuButtonArquivo.setText(Testes.arquivo);
		mudarPerspectiva();
    }
}
