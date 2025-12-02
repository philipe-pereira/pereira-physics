package br.com.pereiraeng.physics;

import java.util.ArrayList;

/**
 * Classe dos objetos que representam uma lista de unidades de medida uma mesma
 * grandeza física
 * 
 * @author Philipe PEREIRA
 *
 */
public class Unidades extends ArrayList<Unidade> {
	private static final long serialVersionUID = 1L;

	/**
	 * Índice da unidade selecionada
	 */
	private int index = 0;

	private Grandeza grandeza;

	public Unidades(Unidade... units) {
		this(null, units);
	}

	public Unidades(Grandeza grandeza, Unidade... units) {
		super(units.length);
		this.grandeza = grandeza;
		for (Unidade u : units)
			super.add(u);
	}

	// -------------------------- GETTER N' SETTER --------------------------

	public Grandeza getGrandeza() {
		return grandeza;
	}

	public void set(int index) {
		if (index >= 0 && index < super.size())
			this.index = index;
	}

	public void set(Unidade unit) {
		int index = this.indexOf(unit);
		if (index == -1) {
			if (unit.getGrandeza() == grandeza) {
				this.add(unit);
				index = this.size() - 1;
			}
		}
		this.index = index;
	}

	/**
	 * Função que retorna a unidade que foi selecionada neste conjunto
	 * 
	 * @return unidade selecionada
	 */
	public Unidade get() {
		return super.get(index);
	}

	public Unidade next() {
		return super.get(index = (index + 1) % super.size());
	}

	/**
	 * Função que retorna a unidade principal em que a grandeza é medida. Esta seria
	 * àquela cuja valor é unitário ({@link Unidade#value value} = 1.) ou, caso
	 * nenhuma possua valor unitário, seria aquela na posição 0 da lista.
	 * 
	 * @return objeto {@link Unidade Unit} da unidade principal de medida de uma
	 *         grandeza
	 */
	public Unidade getMainUnit() {
		for (Unidade u : this)
			if (u.getValue() == 1.)
				return u;
		return super.get(0);
	}
}