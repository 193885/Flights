package it.polito.tdp.flight.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TestModel {

	public static void main(String[] args) {
		
		Model m = new Model();
			
		System.out.println(m.getAirports().get(0));

		System.out.println(m.getAirports().get(0).getRoutes());
		
		m.createGraph();
		
		m.printStats();
		
		Set < Airport > biggestSSC = m.getBiggestComponenteFortementeConnessa();
		
		System.out.println(biggestSSC.size());
		try {
		
			List <Airport> airportList = new ArrayList <Airport> (biggestSSC);
			
			int id1 = airportList.get(0).getAirportId();
			int id2 = airportList.get(1).getAirportId();
			
		
		System.out.println(m.getShortestPath(id1,id2));
		
		}catch(RuntimeException e){
			System.out.println("Airport code error");
		}
		

	}

}
