/*
 * Versie: v1
 * Date: 07-03-12 17:20
 * By: Jochem Elsinga
 * Created: Small class used to compare names
 */

package com.ut.bataapp.api;

import java.util.Comparator;

import com.ut.bataapp.objects.Team;

public class TeamNaamComparator implements Comparator{

	@Override
	public int compare(Object e0, Object e1) {
		return ((Team)e0).getNaam().compareTo(((Team)e1).getNaam());
	}

}
