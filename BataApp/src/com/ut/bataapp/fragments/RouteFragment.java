package com.ut.bataapp.fragments;

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

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RouteFragment extends Fragment {
    
	private final int MENU_ROUTE_AUTO = Menu.FIRST + 1;
	private final int MENU_ROUTE_OVERSLAG = Menu.FIRST + 2;
	private final int MENU_MAP_LOOPROUTE = Menu.FIRST + 3;
	private final int MENU_MAP_AUTOROUTE = Menu.FIRST+ 4;
	private final int MENU_MAP_OVERSLAGROUTE = Menu.FIRST+ 5;
	
	
	/**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
    public static RouteFragment newInstance(int index) {
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        
        RouteFragment f = new RouteFragment();
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
    	
    	ImageView hoogte = (ImageView) view.findViewById(R.id.image_hoogteverschil);
    	
    	TextView routegeslacht = (TextView) view.findViewById(R.id.text_routegeslacht);
    	String geslacht = (etappe.getGeslacht() == 'M') ? "Man" : "Vrouw"; 	
    	routegeslacht.setText(geslacht);
    	
    	TextView recordtijd_team = (TextView) view.findViewById(R.id.recordtijd_team);
    	recordtijd_team.setText(etappe.getRecordTeam());
    	
    	TextView recordtijd_tijd = (TextView) view.findViewById(R.id.recordtijd_tijd);
    	recordtijd_tijd.setText(etappe.getRecordTijd());
    	
    	return view;
    	
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {	
    	SubMenu subMenu1 = menu.addSubMenu("Routebeschrijving");
    	
    	subMenu1.add(0, MENU_ROUTE_AUTO, MENU_ROUTE_AUTO,"Autoroute");
    	subMenu1.add(0, MENU_ROUTE_OVERSLAG,MENU_ROUTE_OVERSLAG,"Overslagroute");
    	
    	
    	MenuItem subMenu1Item = subMenu1.getItem();
    	subMenu1Item.setIcon(R.drawable.ic_action_car);
    	subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    	
    	SubMenu subMenu2 = menu.addSubMenu("Bekijk in Maps");
    	
    	subMenu2.add(0,MENU_MAP_LOOPROUTE,MENU_MAP_LOOPROUTE,"Lopersroute");
    	subMenu2.add(0,MENU_MAP_AUTOROUTE,MENU_MAP_AUTOROUTE,"Autoroute");
    	subMenu2.add(0,MENU_MAP_OVERSLAGROUTE,MENU_MAP_OVERSLAGROUTE,"Overslagroute");
    	
    	MenuItem subMenu2Item = subMenu2.getItem();
    	subMenu2Item.setIcon(R.drawable.icon_maps);
    	subMenu2Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case MENU_MAP_LOOPROUTE:
				Intent mapLopersroute = new Intent(Intent.ACTION_VIEW); 
				Uri uri0 = Uri.parse("geo:0,0?q=http://maps.google.com/maps?f=d&source=s_d&saddr=51.81998,+5.86776&daddr=51.8295,+5.9416&geocode=Fcy1FgMd8IhZAA%3BFfzaFgMdYKlaAA&aq=&sll=51.83294,5.898995&sspn=0.043758,0.111494&vpsrc=0&hl=nl&mra=ls&ie=UTF8&ll=51.832967,5.898886&spn=0.043758,0.111494&t=h&z=14&dirflg=d"); 
				mapLopersroute.setData(uri0); 
				startActivity(Intent.createChooser(mapLopersroute, "Sample"));
			break;
		
			case MENU_MAP_AUTOROUTE:
				Intent mapAutoroute = new Intent(Intent.ACTION_VIEW); 
				Uri uri1 = Uri.parse("geo:0,0?q=http://maps.google.com/maps?f=d&source=s_d&saddr=51.81998,+5.86776&daddr=51.8295,+5.9416&geocode=Fcy1FgMd8IhZAA%3BFfzaFgMdYKlaAA&aq=&sll=51.83294,5.898995&sspn=0.043758,0.111494&vpsrc=0&hl=nl&mra=ls&ie=UTF8&ll=51.832967,5.898886&spn=0.043758,0.111494&t=h&z=14&dirflg=d"); 
				mapAutoroute.setData(uri1); 
				startActivity(Intent.createChooser(mapAutoroute, "Sample"));
			break;
		
			case MENU_MAP_OVERSLAGROUTE:
				Intent mapOverslagroute = new Intent(Intent.ACTION_VIEW); 
				Uri uri2 = Uri.parse("geo:0,0?q=http://maps.google.com/maps?f=d&source=s_d&saddr=51.81998,+5.86776&daddr=51.8295,+5.9416&geocode=Fcy1FgMd8IhZAA%3BFfzaFgMdYKlaAA&aq=&sll=51.83294,5.898995&sspn=0.043758,0.111494&vpsrc=0&hl=nl&mra=ls&ie=UTF8&ll=51.832967,5.898886&spn=0.043758,0.111494&t=h&z=14&dirflg=d"); 
				mapOverslagroute.setData(uri2); 
				startActivity(Intent.createChooser(mapOverslagroute, "Sample"));
				break;
				
			case MENU_ROUTE_AUTO:
				Intent mapAutoroute1 = new Intent(Intent.ACTION_VIEW); 
				Uri uri11 = Uri.parse("geo:0,0?q=http://maps.google.com/maps?f=d&source=s_d&saddr=51.81998,+5.86776&daddr=51.8295,+5.9416&geocode=Fcy1FgMd8IhZAA%3BFfzaFgMdYKlaAA&aq=&sll=51.83294,5.898995&sspn=0.043758,0.111494&vpsrc=0&hl=nl&mra=ls&ie=UTF8&ll=51.832967,5.898886&spn=0.043758,0.111494&t=h&z=14&dirflg=d"); 
				mapAutoroute1.setData(uri11); 
				startActivity(Intent.createChooser(mapAutoroute1, "Sample"));
			break;
		
			case MENU_ROUTE_OVERSLAG:
				Intent mapOverslagroute2 = new Intent(Intent.ACTION_VIEW); 
				Uri uri21 = Uri.parse("geo:0,0?q=http://maps.google.com/maps?f=d&source=s_d&saddr=51.81998,+5.86776&daddr=51.8295,+5.9416&geocode=Fcy1FgMd8IhZAA%3BFfzaFgMdYKlaAA&aq=&sll=51.83294,5.898995&sspn=0.043758,0.111494&vpsrc=0&hl=nl&mra=ls&ie=UTF8&ll=51.832967,5.898886&spn=0.043758,0.111494&t=h&z=14&dirflg=d"); 
				mapOverslagroute2.setData(uri21); 
				startActivity(Intent.createChooser(mapOverslagroute2, "Sample"));
				break;
		}
		return false;
   }
}
