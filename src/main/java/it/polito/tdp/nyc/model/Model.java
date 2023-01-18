package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.nyc.db.NYCDao;

public class Model {

	private NYCDao dao;
	private Graph <String, DefaultWeightedEdge> grafo;
	private List<Adiacenza> adiacenzeList;
	
	public Model() {
		dao= new NYCDao();
		adiacenzeList= new ArrayList<>();
	}
	
	public List <String> getProvider() {
		return this.dao.getProvider();
	}
	

	public String creaGrafo(String p) {		
		this.grafo= new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		//creoVertici
		Graphs.addAllVertices(this.grafo, dao.getVertici(p));
		//creoArchi
		for(Adiacenza a:this.dao.getArchi(p)) {
			if(!grafo.containsEdge(a.getC2(), a.getC1())) {
				Graphs.addEdgeWithVertices(this.grafo, a.getC1(), a.getC2(), a.getPesoArco());
				adiacenzeList.add(a);
			}
		}
		return "Grafo creato #Vertici: "+this.grafo.vertexSet().size()+"#Archi: "+this.grafo.edgeSet().size();
	}
	
	public Set<String> getQuartieri(){
		return this.grafo.vertexSet();
	}
	

	public List<CityDistance> quartieriAdiacenti(String q) {
		List<CityDistance> result= new ArrayList<>();
		List<String> vicini= Graphs.neighborListOf(this.grafo, q);
		for(String v : vicini) {
			if(this.grafo.getEdge(q, v)!=null) {
				 result.add(new CityDistance(v, 
					        this.grafo.getEdgeWeight(this.grafo.getEdge(q, v))));
			}
			else {
				 result.add(new CityDistance(v, 
							this.grafo.getEdgeWeight(this.grafo.getEdge(v, q))));
			}
		}
		
		Collections.sort(result,new Comparator<CityDistance>() {
			@Override
			public int compare(CityDistance o1, CityDistance o2) {
				return (int) (o1.getDistanza()-o2.getDistanza());
			}});
		
		return result;
	}
	
	
	
}
