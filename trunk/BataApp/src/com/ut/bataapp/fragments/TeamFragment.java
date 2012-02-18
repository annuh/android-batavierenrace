package com.ut.bataapp.fragments;

//import com.actionbarsherlock.sample.shakespeare.Shakespeare;
import com.ut.bataapp.R;
import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.Etappe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.support.v4.view.SubMenu;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class TeamFragment extends Fragment {
    /**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
    public static TeamFragment newInstance(int index) {
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        
        TeamFragment f = new TeamFragment();
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	setHasOptionsMenu(true);
    	if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
        }

    	int id = getArguments().getInt("index", 0) + 1;
    	Etappe etappe = api.getEtappesByID(id);
    	View view = inflater.inflate(R.layout.route_fragment, container, false);
    	TextView routeafstand = (TextView) view.findViewById(R.id.text_routeafstand);
    	routeafstand.setText(Integer.toString(etappe.getAfstand()));
    	
    	TextView routegeslacht = (TextView) view.findViewById(R.id.text_routegeslacht);
    	String geslacht = (etappe.getGeslacht() == 'M') ? "Man" : "Vrouw"; 	
    	
    	routegeslacht.setText(geslacht);
    	return view;
    	
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {	
    	SubMenu subMenu1 = menu.addSubMenu("Routes");
    	
    	subMenu1.add(1,0,Menu.NONE,"Lopersroute");
    	subMenu1.add(1,1,Menu.NONE,"Autoroute");
    	subMenu1.add(1,2,Menu.NONE,"Overslagroute");
    	
    	MenuItem subMenu1Item = subMenu1.getItem();
    	subMenu1Item.setIcon(R.drawable.icon_maps);
    	subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case 0:
			Intent mapLopersroute = new Intent(Intent.ACTION_VIEW); 
			Uri uri0 = Uri.parse("geo:0,0?q=http://code.google.com/apis/kml/documentation/KML_Samples.kml"); 
			mapLopersroute.setData(uri0); 
			startActivity(Intent.createChooser(mapLopersroute, "Sample"));
			break;
		
			case 1:
			Intent mapAutoroute = new Intent(Intent.ACTION_VIEW); 
			Uri uri1 = Uri.parse("geo:0,0?q=http://code.google.com/apis/kml/documentation/KML_Samples.kml"); 
			mapAutoroute.setData(uri1); 
			startActivity(Intent.createChooser(mapAutoroute, "Sample"));
			break;
		
			case 2:
				Intent mapOverslagroute = new Intent(Intent.ACTION_VIEW); 
				Uri uri2 = Uri.parse("geo:0,0?q=http://code.google.com/apis/kml/documentation/KML_Samples.kml"); 
				mapOverslagroute.setData(uri2); 
				startActivity(Intent.createChooser(mapOverslagroute, "Sample"));
				break;
		}
		return false;
   }
}
