package br.com.pereiraeng.physics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import br.com.pereiraeng.core.StringUtils;
import br.com.pereiraeng.math.Multiplicador;

/**
 * Classe dos objetos que representam uma unidade de medida de uma grandeza
 * física
 * 
 * @author Philipe PEREIRA
 *
 */
public class Unidade implements Comparable<Unidade> {

	

	private final Grandeza grandeza;
	private final double value;
	private final String[] symbols;

	public Unidade(Grandeza grandeza, Multiplicador mult) {
		this(grandeza.getDefault(), mult);
	}

	public Unidade(Unidade unit, Multiplicador mult) {
		this(unit.getGrandeza(), unit.getValue() * mult.getMultiplier(),
				StringUtils.addPrefix(mult.getSymbol(), unit.symbols));
	}

	public Unidade(Grandeza grandeza, double value, String... symbols) {
		this.grandeza = grandeza;
		this.value = value;
		for (int i = 0; i < symbols.length; i++)
			symbols[i] = StringUtils.replaceUnicode(symbols[i]);
		this.symbols = symbols;
	}

	public Grandeza getGrandeza() {
		return grandeza;
	}

	public double getValue() {
		return value;
	}

	public String getSymbol() {
		return symbols[0];
	}

	@Override
	public String toString() {
		return getSymbol();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Unidade) {
			Unidade u = (Unidade) obj;
			return u.getGrandeza().equals(this.grandeza)
					&& (u.getSymbol().equals(this.getSymbol()) || u.getValue() == this.getValue());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getSymbol().hashCode();
	}

	@Override
	public int compareTo(Unidade unit) {
		double diff = unit.getValue() - this.getValue();
		return diff > 0 ? -1 : (diff < 0 ? 1 : 0);
	}

	private boolean hasSymbol(String symbol) {
		for (String s : symbols)
			if (s.equals(symbol))
				return true;
		return false;
	}

	// ---------------------- UNIDADES DEFAULT ----------------------

	public static Unidades getUnits(Grandeza grandeza) {
		Unidades out = new Unidades(grandeza);
		if (unidades != null) {
			for (Unidade unidade : unidades)
				if (unidade.getGrandeza().equals(grandeza))
					out.add(unidade);
			Collections.sort(out);
		} else
			out.add(grandeza.getDefault());
		return out;
	}

	/**
	 * Função que retorna a unidade a partir de seu símbolo
	 * 
	 * @param symbol símbolo da unidade
	 * @return unidades de medida
	 */
	public static Unidade get(String symbol) {
		for (Unidade unit : unidades)
			if (unit.hasSymbol(symbol))
				return unit;
		return null;
	}

	/**
	 * Conjunto das unidades que estão carregadas e podem ser utilizadas durante a
	 * execução do programa
	 */
	private static HashSet<Unidade> unidades;

	/**
	 * Função que carrega o conjunto de unidades a serem utilizados durante a
	 * execução do programa
	 * 
	 * @param groups vetor com a numeração dos grupos a serem utilizados:
	 *               <ol start=0>
	 *               <li>unidades de tempo maiores que o segundo;</i>
	 *               <li>unidades de comprimento SI;</i>
	 *               <li>unidades de comprimento do sistema Americano;</i>
	 *               <li>unidades de comprimento marítimo;</i>
	 *               <li>ângulos;</i>
	 *               <li>velocidades não SI;</i>
	 *               <li>velocidades marítimas;</i>
	 *               <li>unidades utilizadas na mecânica;</i>
	 *               <li>unidades utilizadas na termodinâmica;</i>
	 *               <li>unidades utilizadas no eletromagnetismo;</i>
	 *               <li>unidades utilizadas na mecânica dos meios contínuos;</i>
	 *               <li>unidades de massa;</i>
	 *               <li>unidades de área;</i>
	 *               <li>unidades de volume;</i>
	 *               <li>unidades de massa do padrão imperial;</i>
	 *               <li>unidades de comprimento usadas na tipografia;</i>
	 *               <li>unidades de tempo menores que o segundo.</i>
	 *               </ol>
	 */
	public static void loadUnitSets(int... groups) {
		if (groups.length > 0) {
			if (unidades == null)
				unidades = new HashSet<>();
			try {
				InputStream stream = Unidade.class.getResourceAsStream("/units.txt");
				BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

				String str = null;
				int g = -1, k = 0;
				boolean found = false;

				while ((str = reader.readLine()) != null) {
					String[] strs = str.split("\t");
					if (strs.length == 1) { // indicador de começo de um novo bloco

						// índice do bloco a ser lido
						g++;
						// se todos os grupos requisitados já foram lidos, encerra a leitura
						if (k >= groups.length)
							break;
						// se o bloco a ser lido está na lista
						if (g == groups[k]) {
							found = true;
							k++;
						} else {
							found = false;
						}
					} else {
						if (found) {
							// grandeza
							Grandeza grandeza = Grandeza.valueOf(strs[0]);
							// valor
							double vl = Double.parseDouble(strs[1]);
							// multiplicadores
							Multiplicador[] mults = getMults(strs[2]);
							// símbolos
							String[] sy = Arrays.copyOfRange(strs, 3, strs.length);

							// criar unidades com seus multiplicadores
							unidades.add(new Unidade(grandeza, vl, sy));
							for (Multiplicador m : mults)
								unidades.add(
										new Unidade(grandeza, Math.pow(10, m.getPower()) * vl, m.getSymbol() + sy[0]));
						}
					}
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static Multiplicador[] getMults(String symbols) {
		// símbolos dos multiplicadores
		String[] mult = null;
		if (!symbols.equals("0"))
			mult = symbols.split(";");

		// multiplicadores
		Multiplicador[] mults = null;
		if (mult != null) {
			mults = new Multiplicador[mult.length];
			for (int i = 0; i < mult.length; i++) {
				String m = mult[i];
				if (m.startsWith("\\u"))
					m = Character.toString((char) Integer.parseInt(m.substring(2), 16));
				mults[i] = Multiplicador.getMultiplier(m);
			}
		} else {
			mults = new Multiplicador[0];
		}
		return mults;
	}
}