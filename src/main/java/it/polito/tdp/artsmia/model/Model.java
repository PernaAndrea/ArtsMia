package it.polito.tdp.artsmia.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	//TEMA D ESAME IN CUI SVOLGIAMO PUNTO A e B DEL PUNTO 1 
	
	private Graph<ArtObject,DefaultWeightedEdge> grafo;
	private ArtsmiaDAO dao ;
	//quando si usa il pattern con i grafi è comodo avere un identity Map (idMap) 
	private HashMap<Integer,ArtObject> idMap ;
	
	public Model() {
		
		dao = new ArtsmiaDAO();
		idMap = new HashMap<Integer,ArtObject>(); 
		
	}
	
	//meglio inserire qui il costruttore del grafo e non nel costruttore del model
	public void creaGrafo() {
		
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//******Adesso dobbiamo riempire il grafo********
		//AGGIUNGO I VERTICI 
			//recupero gli ArtObject dal db e li inserisco come vertici 
		dao.listObjects(idMap); // mi salvo i ArtObject nella idMap e solo se c'è nè di nuovi li aggiungo all'idMap altrimenti rimane uguale la mappa
		Graphs.addAllVertices(grafo,idMap.values());
		
		//AGGIUNGO GLI ARCHI 
			//Approccio 1
				//piu facile , doppio ciclo for dove confronto tutti i vertici fra di loro 
				//dati 2 vertici faccio il controllo
		/*
		for(ArtObject a1 : this.grafo.vertexSet()) {
			for(ArtObject a2 : this.grafo.vertexSet()) {
				if(!a1.equals(a2) && !this.grafo.containsEdge(a1, a2)) {
					//devo collegare a1 ad a2 ? lo chiedo al dao e faccio di conseguenza 
					int peso = dao.getPeso(a1,a2);
					if(peso > 0) {
						Graphs.addEdge(this.grafo, a1, a2, peso);
					}
				}
			}
		}
		*/
		//ABBIAMO VISTO CHE NON CARICAAAAA ..... CI METTE TROPPO TEMPO .... NON FARLOOO!!!!! 
		//approccio 1 va solo bene quando --> vertice x vertice ( doppio ciclo for ) x "quanto ci mette la query" < millisecondi x minuti a disposizione
		
			//Approccio 2
				//lo salto perche ci metete 30 min ... troppo
				
			//Approccio 3
				// Mi faccio dare direttamente la lista di tutti i artObjects a due a due con il loro peso 
				//BISOGNA SEMPRE FARE L APPROCCIO 3 .... E L UNICO CHE VA.... E SIAMO SICURI CHE VADA ....
		for(Adiacenza a : dao.getAdiacenze()){
				Graphs.addEdge(this.grafo, idMap.get(a.getId1()), idMap.get(a.getId2()), a.getPeso());
		}
		
		System.out.println("GRAFO CREATO !!!\n");
		System.out.println("# VERTICI : "+ grafo.vertexSet().size());
		System.out.println(";   ARCHI : "+ grafo.edgeSet().size()+"\n");
	}
	

}
