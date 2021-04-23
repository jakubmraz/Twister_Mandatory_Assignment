package com.example.twistermandatoryassignment.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.twistermandatoryassignment.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.app.Activity.RESULT_OK;

public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        Button registerButton = getView().findViewById(R.id.buttonFrontLogout);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchBackToLoginPage();
            }
        });

        String email = currentUser.getEmail();
        TextView welcomeText = view.findViewById(R.id.textViewFrontIntro);
        welcomeText.setText("Welcome, " + currentUser.getEmail());

    }

    private void switchBackToLoginPage() {
        FirebaseAuth.getInstance().signOut();

        Intent intentBack = new Intent();
        getActivity().setResult(RESULT_OK, intentBack);
        getActivity().finish();
    }
}