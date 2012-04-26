package com.ut.bataapp.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ut.bataapp.Utils;

public class LustrumActivity extends SherlockFragmentActivity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.lustrum_titel);
		getSupportActionBar().setHomeButtonEnabled(true);
		setContentView(R.layout.lustrum);

		TextView lustrum_omschr1 = (TextView) findViewById(R.id.lustrum_omschrijving1);
		lustrum_omschr1.setText(R.string.lustrum_text1);

		ImageView lustrum_plaatje = (ImageView) findViewById(R.id.lustrum_plaatje);
		int id = this.getResources().getIdentifier(getPackageName()+":drawable/lustrum", null, null);
		lustrum_plaatje.setImageResource(id);

		TextView lustrum_omschr2 = (TextView) findViewById(R.id.lustrum_omschrijving2);
		lustrum_omschr2.setText(R.string.lustrum_text2);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Utils.goHome(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
