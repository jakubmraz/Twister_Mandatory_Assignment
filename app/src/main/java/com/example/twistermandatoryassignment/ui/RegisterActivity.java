package com.example.twistermandatoryassignment.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.twistermandatoryassignment.R;
import com.example.twistermandatoryassignment.classes.RegistrationData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        Button backButton = findViewById(R.id.buttonRegisterBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchBackToLoginPage();
            }
        });

        Button createAccountButton = findViewById(R.id.buttonRegisterCreate);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEnteredInformation())
                    createUserAccount();
            }
        });
    }

    private void createUserAccount() {
        RegistrationData registrationData = getEnteredCredentials();

        mAuth.createUserWithEmailAndPassword(registrationData.getEmail(), registrationData.getPassword1())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("kek", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            switchBackToLoginPage();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("kek", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    private boolean validateEnteredInformation() {
        boolean result = false;

        RegistrationData registrationData = getEnteredCredentials();

        if(validateEnteredName(registrationData.getUsername())) {
            if(validateEnteredEmail(registrationData.getEmail())) {
                if(validateEnteredPassword(registrationData.getPassword1(),
                        registrationData.getPassword2())) {
                    collapseErrors();
                    result = true;
                }
                else
                    displayPasswordError();
            }
            else
                displayEmailError();
        }
        else
            displayUsernameError();

        //Log.d("kek", "" + result);
        return result;

    }

    private void displayUsernameError() {
        EditText enteredNameField = findViewById(R.id.editTextRegisterName);
        enteredNameField.setError("Please enter a valid name");
    }

    private void displayEmailError() {
        EditText enteredEmailField = findViewById(R.id.editTextRegisterEmail);
        enteredEmailField.setError("Please enter a valid email address");
    }

    private void displayPasswordError() {
        EditText enteredPasswordField1 = findViewById(R.id.editTextRegisterPassword1);
        EditText enteredPasswordField2 = findViewById(R.id.editTextRegisterPassword2);
        enteredPasswordField1.setError("Entered passwords do not match or are too short");
        enteredPasswordField2.setError("Entered passwords do not match or are too short");
    }

    private void collapseErrors(){
        EditText enteredNameField = findViewById(R.id.editTextRegisterName);
        EditText enteredEmailField = findViewById(R.id.editTextRegisterEmail);
        EditText enteredPasswordField1 = findViewById(R.id.editTextRegisterPassword1);
        EditText enteredPasswordField2 = findViewById(R.id.editTextRegisterPassword2);
        enteredNameField.setError(null);
        enteredEmailField.setError(null);
        enteredPasswordField1.setError(null);
        enteredPasswordField2.setError(null);
    }

    private boolean validateEnteredPassword(String password1, String password2) {
        return (!TextUtils.isEmpty(password1) && !TextUtils.isEmpty(password2)
                && password1.length() >= 6 && password1.equals(password2));
    }

    private boolean validateEnteredEmail(String email) {
        //Some code for checking emails I got from StackOverflow,
        //checks if the field is empty and if it matches some internal email pattern
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private boolean validateEnteredName(String username) {
        return (!TextUtils.isEmpty(username) && username.matches("[a-zA-Z]+"));
    }

    private RegistrationData getEnteredCredentials() {
        EditText enteredNameField = findViewById(R.id.editTextRegisterName);
        String enteredName = enteredNameField.getText().toString();
        //Log.d("kek", enteredName);

        EditText enteredEmailField = findViewById(R.id.editTextRegisterEmail);
        String enteredEmail = enteredEmailField.getText().toString();
        //Log.d("kek", enteredEmail);

        EditText enteredPasswordField1 = findViewById(R.id.editTextRegisterPassword1);
        String enteredPassword1 = enteredPasswordField1.getText().toString();
        //Log.d("kek", enteredPassword1);

        EditText enteredPasswordField2 = findViewById(R.id.editTextRegisterPassword2);
        String enteredPassword2 = enteredPasswordField2.getText().toString();
        //Log.d("kek", enteredPassword2);

        RegistrationData registrationData = new RegistrationData(enteredName, enteredEmail,
                enteredPassword1, enteredPassword2);
        return registrationData;
    }

    private void switchBackToLoginPage() {
        Intent intentBack = new Intent();
        setResult(RESULT_OK, intentBack);
        finish();
    }
}
