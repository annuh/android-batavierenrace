package com.ut.bataapp.adapters;

import java.util.ArrayList;
import com.ut.bataapp.R;
import com.ut.bataapp.objects.Bericht;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adapter voor berichten. Berichten worden getoond met een kleurcode, titel en het bericht.
 * Onderdeel van ontwerpproject BataApp.
 * @author Anne vd Venis
 * @version 1.0
 */
public class BerichtAdapter extends ArrayAdapter<Bericht> {

	/** Context waarin deze Adapter wordt aangeroepen */
	private final Context context;
	/** Lijst met alle berichten die getoond worden */
	private final ArrayList<Bericht> berichten;
	
	/**
	 * Constructor van BerichtAdapter
	 * @param context Context waarin deze Adapter wordt aangeroepen
	 * @param berichten Berichten die getoond gaan worden
	 */
	public BerichtAdapter(Context context, ArrayList<Bericht> berichten) {
		super(context, R.layout.message_row, berichten);
		this.context = context;
		this.berichten = berichten;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.message_row, parent, false);
		TextView headerView = (TextView) rowView.findViewById(R.id.message_header);
		TextView contentView = (TextView) rowView.findViewById(R.id.message_content);
		ImageView codeView = (ImageView) rowView.findViewById(R.id.code);

		Bericht bericht = berichten.get(position);

		headerView.setText(bericht.getTitel());
		int code = bericht.getCode();
		int colour;
		if(code == Bericht.ROOD){
			colour = R.color.red;
		} else if(code == Bericht.GEEL){
			colour = R.color.geel;
		} else{
			colour = R.color.green;
		}
		codeView.setBackgroundResource(colour);
		contentView.setText(Html.fromHtml(bericht.getBericht()));

		return rowView;
	}
}
