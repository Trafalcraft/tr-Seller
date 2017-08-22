package com.trafalcraft.seller.util;

import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;

public class MyTrait extends Trait {
    @Persist
    private String name = ""; // saves and loads automatically under "data"; can be done with lists, sets, and Map<String, Object>s.

    public MyTrait() {
        super("tr-seller");
    }

    @Override
    public void onSpawn() {
        super.onSpawn();
        if (this.getNPC() == null) {
            return;
        }
        name = this.getNPC().getName();
    }

}