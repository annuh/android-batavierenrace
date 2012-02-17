package com.ut.bataapp.fragments;

//import com.actionbarsherlock.sample.shakespeare.Shakespeare;
import com.ut.bataapp.R;
import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.Etappe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class RouteFragment extends Fragment {
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

      /*LinearLayout layout = new LinearLayout(getActivity());
        
        TextView info_titel = new TextView(getActivity());
        info_titel.setText("Informatie");
        info_titel.setTextAppearance(getActivity(), R.style.titel);
        layout.addView(info_titel);

        TextView text = new TextView(getActivity());
        
       layout.addView(text);
       int id = getArguments().getInt("index", 0) + 1;
       int bla = api.getEtappesByID(i).getAfstand();
     text.setText("BLAA " + Integer.toString(i) + Integer.toString(bla));
      // text.setText(i);
    
       return layout;*/
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
}
