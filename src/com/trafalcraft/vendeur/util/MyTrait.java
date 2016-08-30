package com.trafalcraft.vendeur.util;

import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;

public class MyTrait extends Trait {
	public MyTrait() {
		super("tr-seller");
	}

	@Persist private String name = ""; // saves and loads automatically under "data"; can be done with lists, sets, and Map<String, Object>s.
	
	@Override
	public void onSpawn() {
		super.onSpawn();
		if(this.getNPC() == null){
			return;
		}
		name = this.getNPC().getName();
	}
	
	/*@Override
	public void run() {
		super.run();
		//this.getNPC().getNavigator().setTarget(arg0);
	}*/
	
}