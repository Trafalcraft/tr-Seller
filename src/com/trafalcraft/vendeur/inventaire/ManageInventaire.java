package com.trafalcraft.vendeur.inventaire;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

public class ManageInventaire {

	
	private final Map<String, Inventaire> listInventaire = Maps.newHashMap();
	
	public void addPlayerCache(String name){
		if(!this.listInventaire.containsKey(name)){
			Inventaire inventaire = new Inventaire();
			listInventaire.put(name, inventaire);
		}
		
	}
	
	public boolean contains(String name){
		if(this.listInventaire.containsKey(name)){
			return true;
		}
		return false;
	}
	
	public void removeInventaire(String name){
		if(this.listInventaire.containsKey(name)){
			listInventaire.remove(name);
		}
	}
	
	public Map<String, Inventaire> listInventaire(){
		return listInventaire;
	}
	
	public Collection<Inventaire> getAllInventaire(){
		return listInventaire.values();
	}
	
	public Inventaire getInventaire(String name){
			return listInventaire.get(name);
	}
	
	public void clear(){
		listInventaire.clear();
	}
}
