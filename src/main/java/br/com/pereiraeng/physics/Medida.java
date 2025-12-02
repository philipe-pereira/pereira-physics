package br.com.pereiraeng.physics;

/**
 * Classe do objeto que representa uma dada medida de uma dada grandeza
 * 
 * @author Philipe PEREIRA
 *
 */
public class Medida extends Number {
	private static final long serialVersionUID = 1L;

	private double value;

	private Unidade unit;

	public Medida(double value, Unidade unit) {
		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public Unidade getUnit() {
		return unit;
	}

	@Override
	public double doubleValue() {
		return getValue();
	}

	@Override
	public float floatValue() {
		return (float) getValue();
	}

	@Override
	public int intValue() {
		return (int) getValue();
	}

	@Override
	public long longValue() {
		return (long) getValue();
	}
}
