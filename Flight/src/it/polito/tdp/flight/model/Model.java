package it.polito.tdp.flight.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.flight.db.FlightDAO;

public class Model {

	FlightDAO fdao = null;
	
	List <Airport> airports;
	List <Airline> airlines;
	List <Route> routes;
	
	AirlineIdMap airlineIDMap;
	AirportIdMap airportIDMap;
	RouteIdMap routeIDMap;
	
	SimpleDirectedWeightedGraph <Airport, DefaultWeightedEdge> grafo;
	
	
	public Model() {
		
		fdao = new FlightDAO();
		
		airlineIDMap = new AirlineIdMap();
		airportIDMap = new AirportIdMap();
		routeIDMap = new RouteIdMap();
		
		airlines = fdao.getAllAirlines(airlineIDMap);
		
		System.out.println(airlines.size());
		
		airports = fdao.getAllAirports(airportIDMap);
		
		System.out.println(airports.size());
	
		routes = fdao.getAllRoutes(routeIDMap,airlineIDMap,airportIDMap);
		
		System.out.println(routes.size());
	
	}
	
	public List <Airport> getAirports(){
		
		if(this.airports == null) //controllo per evitare possibili eccezioni se dao restituisse una lista nulla
			
			return new ArrayList<Airport>();
		
		return this.airports;
		
	}
	
	public void createGraph() {
		
		grafo = new SimpleDirectedWeightedGraph<>( DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, airports);
		
		for ( Route r : routes) {
			
			Airport source =  r.getSourceAirport();
			Airport dest = r.getDestinationAirport();
			
			if(!source.equals(dest)) {
			
			double weight = LatLngTool.distance(new LatLng(source.getLatitude(), source.getLongitude()), 
												new LatLng(dest.getLatitude(), dest.getLongitude()), LengthUnit.KILOMETER);
			
			Graphs.addEdge(grafo, source, dest, weight);
			
			} 
		}
		
		System.out.println(grafo.vertexSet().size());
		System.out.println(grafo.edgeSet().size());

	}
	
	public void printStats() { // per ottenere set di tutte le componenti connesse
		
		if(grafo==null) {
			
			this.createGraph();
		}
		
		ConnectivityInspector <Airport, DefaultWeightedEdge> c = new ConnectivityInspector<>(grafo);
		
		System.out.println(c.connectedSets().size());

	}
	
	
	public Set <Airport> getBiggestComponenteFortementeConnessa(){ 
		
		ConnectivityInspector <Airport, DefaultWeightedEdge> c = new ConnectivityInspector<>(grafo);

		Set<Airport> best = null;
		
		int size = 0;
		
		for (Set<Airport> s : c.connectedSets() ) {
			
			if(s.size() > size) {
				
				best = new HashSet<Airport>(s);
			
				size = s.size();
				
			}
			
		}
		
		return best;
				
	}

	public List <Airport> getShortestPath(int id1, int id2) {
		
		Airport nyc = airportIDMap.get(id1);
		
		Airport bgy = airportIDMap.get(id2);
		
		if(nyc == null || bgy == null)
			
			throw new RuntimeException("Gli aereoporti selezionati non sono presenti nel grafo");
		
		ShortestPathAlgorithm<Airport, DefaultWeightedEdge> spa = new DijkstraShortestPath<Airport, DefaultWeightedEdge> (grafo); 
		
		GraphPath<Airport, DefaultWeightedEdge> gp = spa.getPath(nyc, bgy);
		
		double weight = spa.getPathWeight(nyc, bgy);
		
		System.out.println(weight);
		
		return gp.getVertexList();
	}
	
}
