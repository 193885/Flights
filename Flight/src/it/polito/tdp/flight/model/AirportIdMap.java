package it.polito.tdp.flight.model;

import java.util.HashMap;
import java.util.Map;

public class AirportIdMap {

	private Map <Integer, Airport> map;

	public  AirportIdMap() {

		map = new HashMap <Integer, Airport>();
	} 
	
	public  Airport get(Integer  airportId) {
		
		return map.get(airportId);
	}
		
	public  Airport get ( Airport a) {
		
		 Airport old = map.get(a.getAirportId());
		
		if(old == null) {
			
			map.put(a.getAirportId(), a);
			
			return a;
		}
		
		return old;
	}
	
	public void put ( Airport a, Integer aId) {
		
		map.put(aId, a);
	}
	
	
}
