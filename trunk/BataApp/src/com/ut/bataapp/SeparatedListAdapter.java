package com.ut.bataapp;

import java.util.LinkedHashMap;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

/**
 * Klasse die het mogelijk maakt om meerdere ListView's gescheiden in een ListView weer te geven.
 * @author Anne van de Venis
 * @version 1.0
 */
public class SeparatedListAdapter extends BaseAdapter {
	/** Lijst met Adapters en bijbehorende titels. */
	public final Map<String,Adapter> sections = new LinkedHashMap<String,Adapter>();
	/** Adapter die de ListViews bevat. */
	public final ArrayAdapter<String> headers;
	/** Constante voor het type header. */
	public final static int TYPE_SECTION_HEADER = 0;

	/**
	 * Constructor voor deze klasse. Initaliseert de variabele headers.
	 * @param context
	 */
	public SeparatedListAdapter(Context context) {
		headers = new ArrayAdapter<String>(context, R.layout.list_header);
	}

	/**
	 * Voegt een Adapter toe aan.
	 * @param section
	 * @param adapter
	 */
	public void addSection(String section, Adapter adapter) {
		this.headers.add(section);
		this.sections.put(section, adapter);
	}
	
	@Override
	public Object getItem(int position) {
		for(Object section : this.sections.keySet()) {
			Adapter adapter = sections.get(section);
			int size = adapter.getCount() + 1;

			// check if position inside this section
			if(position == 0) return section;
			if(position < size) return adapter.getItem(position - 1);

			// otherwise jump into next section
			position -= size;
		}
		return null;
	}

	@Override
	public int getCount() {
		int total = 0;
		for(Adapter adapter : this.sections.values())
			total += adapter.getCount() + 1;
		return total;
	}

	@Override
	public int getViewTypeCount() {
		// assume that headers count as one, then total all sections
		int total = 1;
		for(Adapter adapter : this.sections.values())
			total += adapter.getViewTypeCount();
		return total;
	}

	@Override
	public int getItemViewType(int position) {
		int type = 1;
		for(Object section : this.sections.keySet()) {
			Adapter adapter = sections.get(section);
			int size = adapter.getCount() + 1;

			// check if position inside this section
			if(position == 0) return TYPE_SECTION_HEADER;
			if(position < size) return type + adapter.getItemViewType(position - 1);

			// otherwise jump into next section
			position -= size;
			type += adapter.getViewTypeCount();
		}
		return -1;
	}

	/**
	 * Check of alle Views selectbaar zijn
	 * @return True als alle items selectbaar zijn
	 */
	public boolean areAllItemsSelectable() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return (getItemViewType(position) != TYPE_SECTION_HEADER);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int sectionnum = 0;
		for(Object section : this.sections.keySet()) {
			Adapter adapter = sections.get(section);
			int size = adapter.getCount() + 1;

			// check if position inside this section
			if(position == 0) return headers.getView(sectionnum, convertView, parent);
			if(position < size) return adapter.getView(position - 1, convertView, parent);

			// otherwise jump into next section
			position -= size;
			sectionnum++;
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}