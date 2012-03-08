package com.ut.bataapp;

import java.util.ArrayList;

import com.ut.bataapp.objects.Bericht;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageArrayAdapter extends ArrayAdapter<String> {
	
	private final Context context;
	private final ArrayList<Bericht> berichten;

	public MessageArrayAdapter(Context context, ArrayList<Bericht> berichten) {
		super(context, R.layout.message_row, getIds(berichten));
		this.context = context;
		this.berichten = berichten;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.message_row, parent, false);
		TextView headerView = (TextView) rowView.findViewById(R.id.message_header);
		TextView subheaderView = (TextView) rowView.findViewById(R.id.message_subtitel);
		TextView contentView = (TextView) rowView.findViewById(R.id.message_content);
		ImageView codeView = (ImageView) rowView.findViewById(R.id.code);
		
		headerView.setText("Titel");
		int code = berichten.get(position).getCode();
		int colour;
		if(code == Bericht.ROOD){
			colour = R.color.red;
		} else if(code == Bericht.GEEL){
			colour = R.color.geel;
		} else{
			colour = R.color.green;
		}
		codeView.setBackgroundResource(colour);
		contentView.setText("Afzender "+ berichten.get(position).getAfzender());
		subheaderView.setText("Afzender: " + berichten.get(position).getAfzender() + " om " + berichten.get(position).getDatum());
		
		// Change the icon
		
		if(berichten.get(position).getCode() == Bericht.GEEL) {
			
			
		}
		

		return rowView;
	}
	
	public static ArrayList<String> getIds(ArrayList<Bericht> berichten) {
		ArrayList<String> ids = new ArrayList<String>();
		for(int i = 0; i < berichten.size(); i++) {
			ids.add(berichten.get(i).getId());
		}
		return ids;
	}
}
