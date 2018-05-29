package it.polito.tdp.flight.model;

import java.util.HashMap;
import java.util.Map;

	public class RouteIdMap {
		
		private Map <Integer,Route> map;

		public RouteIdMap() {

			map = new HashMap <Integer,Route>();
		} 
		
		public Route get(Integer routeId) {
			
			return map.get(routeId);
		}
			
		public Route get (Route r) {
			
			Route old = map.get(r.getRouteId());
			
			if(old == null) {
				
				map.put(r.getRouteId(), r);
				
				return r;
			}
			
			return old;
		}
		
		public void put (Route r, Integer rId) {
			
			map.put(rId, r);
		}

}
