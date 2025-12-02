package br.com.pereiraeng.physics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UnidadeTests {

	@Test
	void testUnidades() {
		Unidade.loadUnitSets(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
		int[] expectedUnits = { 1, 4, 11, 6, 5, 2, 3, 2, 1, 2, 6, 5, 3, 0, 0, 3, 0, 0, 6, 4, 0, 0, 0, 2, 4, 4, 6, 3, 3,
				3, 4, 4, 2, 1, 1, 1, 5, 3 };

		int index = 0;
		for (Grandeza grandeza : Grandeza.values()) {
			Unidades unidades = Unidade.getUnits(grandeza);
			assertEquals(expectedUnits[index], unidades.size(),
					String.format("Grandeza %s (#%d) tem %d unidades ao invés de %d", grandeza, index, unidades.size(),
							expectedUnits[index]));
			index++;
		}
	}
}
