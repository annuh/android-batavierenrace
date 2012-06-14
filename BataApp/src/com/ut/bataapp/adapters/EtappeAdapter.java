package com.ut.bataapp.adapters;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.ut.bataapp.R;
import com.ut.bataapp.objects.Etappe;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adapter voor etappes. Etappes worden getoond met een het etappenummer, afstand, startpunt en een afbeelding om aan te geven
 * of het een heren of dames etappe is.
 * Onderdeel van ontwerpproject BataApp.
 * @author Anne vd Venis
 * @version 1.0
 */
public class EtappeAdapter extends ArrayAdapter<Etappe> {
	
	/** Context waarin deze Adapter wordt aangeroepen */
	private final Context context;
	/** Lijst met alle etappes die getoond worden */
	private final ArrayList<Etappe> values;

	/**
	 * Constructor van EtappeAdapter
	 * @param context De context waarin deze Adapter wordt aangeroepen
	 * @param values De etappes die getoond moeten worden
	 */
	public EtappeAdapter(Context context, ArrayList<Etappe> values) {
		super(context, R.layout.row_etappe, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row_etappe, parent, false);
		rowView.setId(values.get(position).getId());
		
		String formatetappe = context.getResources().getString(R.string.etappes_etappe);
		String formatkm = context.getResources().getString(R.string.km);
		
		TextView textView = (TextView) rowView.findViewById(R.id.etappe);
		textView.setText(String.format(formatetappe,String.valueOf(values.get(position).getId())));

		TextView naam = (TextView) rowView.findViewById(R.id.naam);
		naam.setText(values.get(position).getVan());
		
		TextView afstand = (TextView) rowView.findViewById(R.id.afstand);
		BigDecimal afst = new BigDecimal((values.get(position).getAfstand()));
		afst = afst.divide(new BigDecimal(1000), 1, BigDecimal.ROUND_FLOOR);
		afstand.setText(String.format(formatkm, afst));
		
		ImageView imageView = (ImageView) rowView.findViewById(R.id.geslacht);		
		if(values.get(position).getGeslacht() == 'H') {
			//imageView.setImageResource(R.drawable.male);
		} else {
			imageView.setImageResource(R.drawable.female);
		}

		return rowView;
	}
	
}
