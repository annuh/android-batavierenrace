package com.ut.bataapp.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.Utils;
import com.ut.bataapp.activities.InformatieActivity.InformatieFragmentAdapter;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;

public class LustrumActivity extends SherlockFragmentActivity{
	String text1 = "Batavierenrace 40 jaar!! \n\n1973: de eerste Batavierenrace vindt plaats \n1974: de Batavierenrace gaat van Nijmegen naar Enschede\n2012: de 40e Batavierenrace vindt plaats!";
	String text2 = "Ter ere van het lustrum zal er tijdens de race een lustrumbus aanwezig zijn. \nDeze is te zien op de herstarts Ulft, Barchem en Enschede en enkele wisselpunten. \n" +
					"Er zullen dan verschillende activiteiten plaats vinden, waar leuke gadgets van de Batavierenrace te verdienen zijn.\n" +
					"\n\nVereeuwig je boodschap aan de Batavierenrace en het Batavierenfeest in steen.";
	 @Override
	   public void onCreate(Bundle savedInstanceState) {
		   super.onCreate(savedInstanceState);
		   setTitle("Bata XL");
		   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		   setContentView(R.layout.lustrum);	 
		   
		   TextView lustrum_omschr1 = (TextView) findViewById(R.id.lustrum_omschrijving1);
		   lustrum_omschr1.setText(text1);
		   
		   ImageView lustrum_plaatje = (ImageView) findViewById(R.id.lustrum_plaatje);
		   int id = this.getResources().getIdentifier(getPackageName()+":drawable/lustrum", null, null);
		   lustrum_plaatje.setImageResource(id);
		   
		   TextView lustrum_omschr2 = (TextView) findViewById(R.id.lustrum_omschrijving2);
		   lustrum_omschr2.setText(text2);
	   }
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
				case android.R.id.home:
					Utils.goHome(getApplicationContext());
					break;
			}
			return super.onOptionsItemSelected(item);
		}
	 
}
