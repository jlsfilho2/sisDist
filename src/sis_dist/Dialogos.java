package sis_dist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class Dialogos {
	
	
	Map<String, List<String>> mapa = new HashMap<String,List<String>>();
	
	List<String> perguntas = new ArrayList<String>(); 
	
	List<String> respostas = new ArrayList<String>(); 
	
	// gera um pseudo chat
	Dialogos (){
		mapa.put("ola",List.of("Tudo?","Como vai você?"));
		mapa.put("e voce",List.of("Eu estou bem","melhor impossível","mais ou menos, o dia não foi muito bom hoje!"));
		mapa.put("tchau", List.of("Até mais!", "Volte mais aqui!","Tchau"));
		perguntas.addAll(List.of("O que você precisa?", "O que te trouxe aqui?","Precisa de algo?"));
		respostas.addAll(List.of("Me pegunta algo que eu saiba...", "Sei lá.. me pergunta outra coisa"));
	}
	
	String geraPergunta() {
		int index = new Random().nextInt(perguntas.size());
		return perguntas.get(index);
	}
	
	String geraResposta(String pergunta) {
		List<String> respostasPossiveis = getRespostas(pergunta);
		if(respostasPossiveis.size() > 0) {
			int index = new Random().nextInt(respostasPossiveis.size());
			return respostasPossiveis.get(index);
		} else {
			int index = new Random().nextInt(respostas.size());
			return respostas.get(index);
		}
		
	}
	
	private List<String> getRespostas(String pergunta) {
		Optional<List<String>> result = mapa.entrySet().stream().filter( s ->
			s.getKey().contains(pergunta.replaceAll("[a-zA-z]+", "").toLowerCase()))
			.map(map -> map.getValue())
		    .findFirst();
		return result.isPresent() ? result.get() : new ArrayList<String>();
	}
	
	
	
	
	
	
}
