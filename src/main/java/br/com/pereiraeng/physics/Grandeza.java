package br.com.pereiraeng.physics;

import java.awt.Color;

import br.com.pereiraeng.math.Vec;

/**
 * Enumeração de grandezas físicas analógicas (contínuas) cujas propriedades são
 * usadas no Subespaço
 * 
 * @author Philipe PEREIRA
 * 
 */
public enum Grandeza implements Propriedade {
	ADIM(0, 0, 0, 0, 0), MASSA(1, 0, 0, 0, 0), COMPRIMENTO(0, 1, 0, 0, 0), TEMPO(0, 0, 1, 0, 0),
	VELOCIDADE(0, 1, -1, 0, 0), AREA(0, 2, 0, 0, 0), VOLUME(0, 3, 0, 0, 0), ANGULO(0, 0, 0, 0, 0),
	TEMPERATURA(0, 0, 0, 1, 0), FORCA(1, 1, -2, 0, 0), PRESSAO(1, -1, -2, 0, 0), CARGA(0, 0, 1, 0, 1),
	CORRENTE(0, 0, 0, 0, 1), CORRENTE_FALTA(0, 0, 0, 0, 1), CORRENTE_NEUTRO(0, 0, 0, 0, 1), TENSAO(1, 2, -3, 0, -1),
	TENSAO_CC(1, 2, -3, 0, -1), POTENCIA(1, 2, -3, 0, 0), ENERGIA(1, 2, -2, 0, 0), FREQUENCIA(0, 0, -1, 0, 0),
	FP(0, 0, 0, 0, 0), N(0, 0, 0, 0, 0), TAP(0, 0, 0, 0, 0), PORCENT(0, 0, 0, 0, 0), ENR_ATIVA(1, 2, -2, 0, 0),
	ENR_REATIVA(1, 2, -2, 0, 0), POT_MEC(1, 2, -3, 0, 0), POT_ATIVA(1, 2, -3, 0, 0), POT_REATIVA(1, 2, -3, 0, 0),
	POT_APARENTE(1, 2, -3, 0, 0), RESISTENCIA(1, 2, -3, 0, -2), REATANCIA(1, 2, -3, 0, -2),
	SUSCEPTANCIA(-1, -2, 3, 0, 2), FLUXO_MAG(1, 2, -2, 0, -1), FLUXO(0, 3, -1, 0, 0), RADIACAO(1, 0, -3, 0, 0),
	CAPACITANCIA(-1, -2, 4, 0, 2), INDUTANCIA(1, 2, -2, 0, -2);

	/**
	 * Dimensão de cada uma das grandezas analógicas que possam vir a compor a
	 * grandeza considerada
	 */
	private int m, l, t, k, a;

	/**
	 * Construtor de uma grandeza analógica
	 * 
	 * @param m dimensão da massa
	 * @param l dimensão do comprimento
	 * @param t dimensão do tempo
	 * @param k dimensão da temperatura
	 * @param a dimensão da corrente elétrica
	 */
	private Grandeza(int m, int l, int t, int k, int a) {
		this.m = m;
		this.l = l;
		this.t = t;
		this.k = k;
		this.a = a;
	}

	// GETTERS

	/**
	 * Função que retorna o vetor com as dimensões da grandeza, na ordem: massa,
	 * comprimento, tempo, temperatura e corrente
	 * 
	 * @return vetor com as dimensões da grandeza
	 */
	public int[] getDimensions() {
		return new int[] { m, l, t, k, a };
	}

	public Unidade getDefault() {
		switch (this) {
		case COMPRIMENTO:
			return new Unidade(this, 1., "m");
		case ANGULO:
			return new Unidade(this, 0.017453, "\u00B0");
		case TEMPERATURA:
			return new Unidade(this, 1., "\u00B0C");
		case TEMPO:
			return new Unidade(this, 1., "s");
		case TENSAO:
			return new Unidade(this, 1., "V");
		case VELOCIDADE:
			return new Unidade(this, 1., "m/s");
		case FREQUENCIA:
			return new Unidade(this, 1., "Hz");
		case PORCENT:
			return new Unidade(this, 0.01, "%");
		case PRESSAO:
			return new Unidade(this, 1., "Pa");
		default:
			return null;
		}
	}

	/**
	 * Função que retorna a grandeza associada a uma dada combinação de grandezas
	 * 
	 * @param dimensions vetor com as potências da grandeza a ser analizada
	 * @return grandeza com as dimensões do vetor
	 */
	public static Grandeza get(int[] dimensions) {
		for (Grandeza g : Grandeza.values()) {
			if (g.equivalent(dimensions))
				return g;
		}
		return null;
	}

	// OPERARAÇÕES ENTRE GRANDEZAS

	public static Grandeza product(Grandeza... grandeza) {
		int[] dimensions = new int[5];
		for (int i = 0; i < grandeza.length; i++)
			dimensions = Vec.sum(dimensions, grandeza[i].getDimensions());
		return Grandeza.get(dimensions);
	}

	public static Grandeza division(Grandeza g1, Grandeza g2) {
		return Grandeza.get(Vec.sub(g1.getDimensions(), g2.getDimensions()));
	}

	// COMPARAÇÃO DE GRANDEZAS

	/**
	 * Função que compara as dimensões das grandezas que compõe uma dada grandeza e
	 * diz se são a mesma
	 * 
	 * @param dimensions vetor com as potências da grandeza
	 * @return <code>true</code> se as grandezas são iguais, <code>false</code>
	 *         senão
	 */
	public boolean equivalent(int[] dimensions) {
		if (this.m != dimensions[0])
			return false;
		if (this.l != dimensions[1])
			return false;
		if (this.t != dimensions[2])
			return false;
		if (this.k != dimensions[3])
			return false;
		if (this.a != dimensions[4])
			return false;
		return true;
	}

	// --------------- PARTE GRÁFICA ---------------

	/**
	 * Função que retorna o texto que será escrito dentro da área do objeto no
	 * diagrama de objetos que representa o medidor da grandeza
	 * 
	 * @return texto do medidor
	 */
	public String getMeterText() {
		switch (this) {
		case TENSAO:
			return "V";
		case CORRENTE:
			return "A";
		case CORRENTE_NEUTRO:
			return "N";
		case CORRENTE_FALTA:
			return "\u23DA";
		case POT_ATIVA:
			return "W";
		case POT_REATIVA:
			return "VAr";
		case POT_APARENTE:
			return "VA";
		case ENR_ATIVA:
			return "Wh";
		case ENR_REATIVA:
			return "VArh";
		case FREQUENCIA:
			return "Hz";
		case N:
			return "#";
		case TEMPERATURA:
			return "\u00B0C";
		case FP:
			return "cos\u0278";
		case TAP:
			return "Tap";
		case PRESSAO:
			return "Pa";
		case ANGULO:
			return "\u2220";
		default:
			return "";
		}
	}

	public Color getColor() {
		switch (this) {
		case ADIM:
		case N:
		case TAP:
		case PORCENT:
			return Color.LIGHT_GRAY;
		case CORRENTE:
			return Color.BLUE.brighter();
		case POT_ATIVA:
			return new Color(255, 128, 0);
		case POT_REATIVA:
			return new Color(0, 128, 0);
		case TEMPERATURA:
			return Color.RED;
		case TENSAO:
			return Color.YELLOW;
		case FREQUENCIA:
			return Color.CYAN;
		default:
			return Color.WHITE;
		}
	}
}