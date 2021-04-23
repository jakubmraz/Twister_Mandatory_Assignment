package com.example.twistermandatoryassignment.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.twistermandatoryassignment.R;
import com.example.twistermandatoryassignment.classes.LoginData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        Button registerButton = findViewById(R.id.buttonLoginRegister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToRegisterActivity();
           }
        });

        Button loginButton = findViewById(R.id.buttonLoginLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        LoginData loginData = GetEnteredCredentials();

        mAuth.signInWithEmailAndPassword(loginData.getEmail(), loginData.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("kek", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            switchToMainActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("kek", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private LoginData GetEnteredCredentials() {
        EditText enteredEmailField = findViewById(R.id.editTextLoginEmail);
        String enteredEmail = enteredEmailField.getText().toString();

        EditText enteredPasswordField = findViewById(R.id.editTextLoginPassword);
        String enteredPassword = enteredPasswordField.getText().toString();

        return new LoginData(enteredEmail, enteredPassword);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check if user is signed in (non-null) and switch activity accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            switchToMainActivity();
        }
    }

    private void switchToRegisterActivity(){
        Intent intentRegisterActivity = new Intent(this, RegisterActivity.class);
        startActivity(intentRegisterActivity);
    }

    private void switchToMainActivity() {
        Intent intentMainActivity = new Intent(this, MainActivity.class);
        startActivity(intentMainActivity);
    }
}