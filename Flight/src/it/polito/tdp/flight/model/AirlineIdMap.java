package it.polito.tdp.flight.model;

import java.util.HashMap;
import java.util.Map;

public class AirlineIdMap {
	
	private Map <Integer,Airline> map;

	public AirlineIdMap() {

		map = new HashMap <Integer,Airline>();
	} 
	
	public Airline get(Integer airlineId) {
		
		return map.get(airlineId);
	}
		
	public Airline get (Airline a) {
		
		Airline old = map.get(a.getAirlineId());
		
		if(old == null) {
			
			map.put(a.getAirlineId(), a);
			
			return a;
		}
		
		return old;
	}
	
	public void put (Airline a, Integer aId) {
		
		map.put(aId, a);
	}

}
