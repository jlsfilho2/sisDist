package sis_dist;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class testesUnitarios {
	
	DialogosService dialogosServices = new DialogosService();

	@Test
	void testBoasVindas() {
		String resultado = dialogosServices.geraBoasVindas();
		Assert.assertNotNull(resultado);
	}
	
	@Test
	void testDespedida() {
		String resultado = dialogosServices.geraDespedida();
		Assert.assertNotNull(resultado);
	}
	
	@Test
	void testRespostaPergunta() {
		String resultado = dialogosServices.geraResposta("o que são sapos?");
		Assert.assertNotNull(resultado);
	}
	
	@Test
	void testRespostaAfirmacao() {
		String resultado = dialogosServices.geraResposta("carro é um veículo que transporta");
		Assert.assertNotNull(resultado);
		String testeAprendizado = dialogosServices.geraResposta("o que são carros?");
		Assert.assertEquals("é um veículo que transporta",resultado);
	}

}
