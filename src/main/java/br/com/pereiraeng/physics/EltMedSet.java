package br.com.pereiraeng.physics;

import java.util.Arrays;

public enum EltMedSet {

	/**
	 * potências no alimentador
	 */
	P(Grandeza.POT_ATIVA, Grandeza.POT_REATIVA),
	/**
	 * corrente de alimentador
	 */
	L(Grandeza.CORRENTE),
	/**
	 * corrente de linha
	 */
	I(Grandeza.CORRENTE),
	/**
	 * tensão em uma barra
	 */
	V(Grandeza.TENSAO),
	/**
	 * <strong>F</strong>ronteira, demanda de cliente e geração de usina
	 */
	F(Grandeza.POT_ATIVA, Grandeza.POT_REATIVA, Grandeza.TENSAO),
	/**
	 * <strong>T</strong>riângulo das potências
	 */
	T(Grandeza.POT_ATIVA, Grandeza.POT_REATIVA, Grandeza.CORRENTE),
	/**
	 * potência reativa C<strong>E</strong>R
	 */
	E(Grandeza.POT_REATIVA),
	/**
	 * Unidades de bancos <strong>O</strong>peracionais
	 */
	O(Grandeza.ADIM),
	/**
	 * solução do caso
	 */
	S(Grandeza.TENSAO, Grandeza.ANGULO),
	/**
	 * Padrão {@link FNTD} para fluxo desequilibrado {@link Bloco9} (<strong>M</strong>édia tensão):
	 * IVM, IAZ, IBR, IN, VVM, VAZ, VBR, P, Q
	 */
	M(Grandeza.CORRENTE, Grandeza.CORRENTE, Grandeza.CORRENTE, Grandeza.CORRENTE, Grandeza.TENSAO, Grandeza.TENSAO, Grandeza.TENSAO, Grandeza.POT_ATIVA,
			Grandeza.POT_REATIVA),
	/**
	 * fluxo equilibrado (<strong>A</strong>lta tensão): P, Q, I, V
	 */
	A(Grandeza.POT_ATIVA, Grandeza.POT_REATIVA, Grandeza.CORRENTE, Grandeza.TENSAO);

	private Grandeza[] grds;

	private EltMedSet(Grandeza... grds) {
		this.grds = grds;
	}

	public Grandeza[] getGrds() {
		return grds;
	}

	/**
	 * Função que retorna as grandezas para cada padrão
	 * 
	 * @return vetor de grandezas analógicas
	 */
	public Grandeza[] get() {
		Grandeza[] out = new Grandeza[grds.length];
		for (int i = 0; i < out.length; i++)
			out[i] = (Grandeza) grds[i];
		return out;
	}

	public static EltMedSet getGrds(Grandeza... grds) {
		for (int i = 0; i < EltMedSet.values().length; i++) {
			EltMedSet ems = EltMedSet.values()[i];
			if (Arrays.equals(ems.grds, grds))
				return ems;
		}
		return null;
	}
}
