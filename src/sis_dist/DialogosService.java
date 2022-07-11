package sis_dist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class DialogosService {

	static final List<String> TOKENS = List.of("o", "que", "é", "são");

	Map<String, List<String>> mapa = new HashMap<String, List<String>>();

	List<String> perguntas = new ArrayList<String>();

	List<String> despedidas = new ArrayList<String>();

	List<String> questionar = new ArrayList<String>();

	List<String> boasVindas = new ArrayList<String>();

	List<String> naoSei = new ArrayList<String>();

	// gera um pseudo chat
	DialogosService() {

		mapa.put("e voce",
				List.of("Eu estou bem", "melhor impossível", "mais ou menos, o dia não foi muito bom hoje!"));
		mapa.put("sapos", List.of("são répteis", "têm sangue frio", "têm espécies venenosas"));
		mapa.put("a terra", List.of("é um planeta", "fica na via Láctea", "está à 384.400 da Lua"));
		despedidas.addAll(List.of("Até mais!", "Volte mais aqui!", "Tchau"));
		perguntas.addAll(List.of("O que você precisa?", "O que te trouxe aqui?", "Precisa de algo?"));
		naoSei.addAll(List.of("Ah que legal, vou anotar aqui", "Hummm, que interessante"));
		questionar.addAll(List.of("Me pegunta algo que eu saiba...", "Sei lá.. me pergunta outra coisa"));
		boasVindas.addAll(List.of("Tudo?", "Como vai você?"));
	}

	String geraPergunta() {
		int index = new Random().nextInt(perguntas.size());
		return perguntas.get(index);
	}

	String geraDespedida() {
		int index = new Random().nextInt(despedidas.size());
		return despedidas.get(index);
	}

	String geraBoasVindas() {
		int index = new Random().nextInt(perguntas.size());
		return boasVindas.get(index);
	}

	String geraResposta(String frase) {
		List<String> respostasPossiveis = getRespostas(frase, true);
		if (respostasPossiveis.size() > 0) {
			int index = new Random().nextInt(respostasPossiveis.size());
			return respostasPossiveis.get(index);
		} else if (!ehPergunta(frase)) {
			mapeia(frase);
			int index = new Random().nextInt(naoSei.size());
			return naoSei.get(index);
		} else {
			int index = new Random().nextInt(questionar.size());
			return questionar.get(index);
		}

	}

	private List<String> getRespostas(String frase, boolean trata) {
		Optional<String> objeto = obtemObjeto(frase, trata);

		return objeto.isPresent() && mapa.containsKey(trataPlural(objeto.get())) ? mapa.get(trataPlural(objeto.get()))
				: new ArrayList<String>();
	}

	void mapeia(String questao) {
		if (questao.contains("é")) {
			String[] split = questao.split("é");
			String key = split[0].trim().substring(0,split[0].lastIndexOf(" ")).toLowerCase();
			trataRespostas(trataPlural(key), split[1].trim());
		} else if (questao.toLowerCase().startsWith("sabia que")) {
			String obj = new String(questao.substring(questao.indexOf("sabia que".length()), questao.length()));
			String key = obj.substring(obj.indexOf(" ")).toLowerCase().trim();
			trataRespostas(trataPlural(key),
					obj.substring(obj.indexOf(" "), obj.length()).trim());
		}

	}

	private void trataRespostas(String key, String resposta) {
		Optional<String> objeto = obtemObjeto(key, true);
		if (mapa.containsKey(trataPlural(key))) {
			List<String> respostas = mapa.get(key);
			respostas.add(resposta);
			mapa.replace(trataPlural(key), respostas);
		} else {
			mapa.put(trataPlural(key), List.of(resposta));
		}
	}

	private boolean ehPergunta(String frase) {
		return frase.endsWith("?") || frase.toLowerCase().startsWith("por que");
	}

	private Optional<String> obtemObjeto(String frase, boolean replace) {
		String fraseTratada = replace
				? new String(frase.toLowerCase().replaceAll("[^a-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]+", ""))
				: frase;
		List<String> tokens = List.of(fraseTratada.split(" "));
		Optional<String> res = tokens.stream().filter(s -> !TOKENS.contains(s) && !s.isBlank() && !s.isEmpty())
				.findFirst();
		return res;
	}

	private String trataPlural(String objeto) {
		String stringReturn = objeto.endsWith("s") ? objeto.substring(0, objeto.length() - 1) : objeto;
		return stringReturn;
	}

}
