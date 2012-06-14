/*
 * Versie: v1
 * Date: 07-03-12 17:20
 * By: Jochem Elsinga
 * Created: Small class used to compare names
 */

package com.ut.bataapp.api;

import java.util.Comparator;

import com.ut.bataapp.objects.Team;

/**
 * Deze klasse zorgt dat Teams sorteerbaar zijn op naam in lijsten.
 */
public class TeamNaamComparator implements Comparator<Team>{

	@Override
	public int compare(Team e0, Team e1) {
		return e0.getNaam().compareTo(e1.getNaam());
	}

}
