package com.clonefish.cocktail.utils;

import java.util.ArrayList;
import java.util.Collection;

public class UniqueArrayList<T extends Object> extends ArrayList 
{
	private static final long serialVersionUID = 1L;

	@Override
	public boolean add(Object obj) {
        if(this.contains(obj))
           return false;
        return super.add(obj);
    }

	@Override
    public boolean addAll(Collection c) {
        boolean result = false;
        for (Object t : c) {
            if (add(t)) {
                result = true;
            }
        }
        return result;
    }
}
