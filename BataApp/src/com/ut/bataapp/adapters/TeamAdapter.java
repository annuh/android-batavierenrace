package com.ut.bataapp.adapters;

import java.util.ArrayList;

import com.ut.bataapp.R;
import com.ut.bataapp.objects.Team;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

public class TeamAdapter extends ArrayAdapter<Team> {
	
	private final Context context;
	private ArrayList<Team> values;
	private ArrayList<Team> filteredItems;
    private TeamFilter filter;

	public TeamAdapter(Context context, ArrayList<Team> values) {
		super(context, R.layout.team_row, values);
		this.context = context;
		this.values = new ArrayList<Team>();
		this.values.addAll(values);
		filteredItems = new ArrayList<Team>();
		filteredItems.addAll(values);
		getFilter();
		 
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.team_row, parent, false);
		
		TextView text_start = (TextView) rowView.findViewById(R.id.team_start);
		text_start.setText(Integer.toString(filteredItems.get(position).getStartnummer()));
		
		TextView text_naam = (TextView) rowView.findViewById(R.id.team_naam);
		text_naam.setText(filteredItems.get(position).getNaam());
		

		return rowView;
	}
	
	
	
	@Override
	public Filter getFilter() {
		if (filter == null){
			filter = new TeamFilter();
		}
		return filter;
	}


	private class TeamFilter extends Filter{
		 
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
            
            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if(constraint != null && constraint.toString().length() > 0)
            {
                ArrayList<Team> filteredItems = new ArrayList<Team>();
               
                for(int i = 0, l = values.size(); i < l; i++)
                {
                    Team m = values.get(i);
                    if(m.getNaam().toLowerCase().contains(constraint))
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
			filteredItems = (ArrayList<Team>)results.values;
			notifyDataSetChanged();
			clear();
			Log.d("Filter", "Starting to publish the results with " + filteredItems.size() + " items");
			for (int i = 0; i <filteredItems.size(); i++){
				add(filteredItems.get(i));
			}
			Log.d("Filter", "Finished publishing results");
			notifyDataSetInvalidated();
	
    }
	}
	

}
