package com.ut.bataapp.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockDialogFragment;

/**
 * Klasse voor het representeren van een een ProgressDialogFragment.
 * In dit generieke DialogFragment wordt een laadbalk getoond. Deze Fragment is nodig om vanuit een Fragment een Dialog aan te maken.
 * Onderdeel van ontwerpproject BataApp.
 * @author Anne vd Venis
 * @version 1.0
 */
public class ProgressDialogFragment extends SherlockDialogFragment {
	
	/**
	 * Maakt een nieuw ProgressDialogFragment aan.
	 * @param title Titel van de ProgressDialogFragment
	 * @param message Tekst dat wordt weergegeven in deze ProgressDialogFragment
	 * @return ProgressDialogFragment
	 */
	public static ProgressDialogFragment newInstance(String title, String message) {
		ProgressDialogFragment fragment = new ProgressDialogFragment();
		Bundle args = new Bundle();
		args.putString("title", title);
		args.putString("message", message);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public ProgressDialog onCreateDialog(Bundle savedInstanceState) {
		String title = getArguments().getString("title");
		String message = getArguments().getString("message");

		ProgressDialog progressDialog = new ProgressDialog(getActivity());
		progressDialog.setTitle(title);
		progressDialog.setMessage(message);
		return progressDialog;
	}
	
	@Override
	public void onCancel(DialogInterface dialog) {
		getActivity().setResult(Activity.RESULT_CANCELED);
		getActivity().finish();
	}
}