package com.ut.bataapp.adapters;

import java.util.ArrayList;

import com.ut.bataapp.R;
import com.ut.bataapp.R.drawable;
import com.ut.bataapp.R.id;
import com.ut.bataapp.R.layout;
import com.ut.bataapp.objects.Etappe;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EtappeAdapter extends ArrayAdapter<String> {
	
	private final Context context;
	private final ArrayList<Etappe> values;

	public EtappeAdapter(Context context, ArrayList<Etappe> values) {
		super(context, R.layout.etappe_row, getIds(values));
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.etappe_row, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.etappe);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.geslacht);
		textView.setText("Etappe "+ Integer.toString(values.get(position).getId()));
		
		// Change the icon
		
		if(values.get(position).getGeslacht() == 'H') {
			
			
		} else {
			imageView.setImageResource(R.drawable.female);
		}
		

		return rowView;
	}
	
	public static ArrayList<String> getIds(ArrayList<Etappe> etappes) {
		ArrayList<String> ids = new ArrayList<String>();
		for(int i = 0; i < etappes.size(); i++) {
			ids.add(Integer.toString(etappes.get(i).getId()));
		}
		return ids;
	}
}
