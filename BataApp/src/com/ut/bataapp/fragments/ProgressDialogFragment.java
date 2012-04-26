package com.ut.bataapp.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class ProgressDialogFragment extends SherlockDialogFragment {
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