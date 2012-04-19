package com.ut.bataapp.adapters;

import java.util.ArrayList;
import com.ut.bataapp.R;
import com.ut.bataapp.objects.KlassementItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

public class KlassementAdapter extends ArrayAdapter<KlassementItem> {

	private final Context context;
	private ArrayList<KlassementItem> values;
	private ArrayList<KlassementItem> filteredItems;
	private KlassementFilter filter;

	public KlassementAdapter(Context context, ArrayList<KlassementItem> values) {
		super(context, R.layout.row_team_looptijd, values);
		this.context = context;
		this.values = new ArrayList<KlassementItem>();
		this.values.addAll(values);
		filteredItems = new ArrayList<KlassementItem>();
		filteredItems.addAll(values);
		getFilter();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row_klassement, parent, false);
		rowView.setId(values.get(position).getTeamStartNummer());

		TextView plaats = (TextView) rowView.findViewById(R.id.klassement_plaats);
		plaats.setText(String.valueOf(values.get(position).getPlaats()));

		TextView team = (TextView) rowView.findViewById(R.id.klassement_team);
		team.setText(values.get(position).getTeamNaam());

		TextView tijd = (TextView) rowView.findViewById(R.id.klassement_tijd);
		tijd.setText(values.get(position).getTijd());

		return rowView;
	}
	
	@Override
	public Filter getFilter() {
		if (filter == null){
			filter = new KlassementFilter();
		}
		return filter;
	}


	private class KlassementFilter extends Filter{

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {

			constraint = constraint.toString().toLowerCase();
			FilterResults result = new FilterResults();
			if(constraint != null && constraint.toString().length() > 0)
			{
				ArrayList<KlassementItem> filteredItems = new ArrayList<KlassementItem>();

				for(int i = 0, l = values.size(); i < l; i++)
				{
					KlassementItem m = values.get(i);
					if(m.getTeamNaam().toLowerCase().contains(constraint) || String.valueOf(m.getTeamStartNummer()).contains(constraint))
						filteredItems.add(m);
				}
				result.count = filteredItems.size();
				result.values = filteredItems;
			}
			else
			{
				synchronized(this)
				{
					result.values = values;
					result.count = values.size();
				}
			}
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			filteredItems = (ArrayList<KlassementItem>)results.values;
			notifyDataSetChanged();
			clear();
			for (int i = 0; i <filteredItems.size(); i++){
				add(filteredItems.get(i));
			}
			notifyDataSetInvalidated();

		}
	}
	
	public static ArrayList<KlassementItem> getDefault() {
		ArrayList<KlassementItem> niks = new ArrayList<KlassementItem>();
		KlassementItem niksitem = new KlassementItem();
		niks.add(niksitem);
		return niks;
	}

}