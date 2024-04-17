package com.example.taskmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import dao.UserDao;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword, editTextUsername, editTextPhoneNumber;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiter); // Correction du nom du layout

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);

        buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        TextView signupLoginTextView = findViewById(R.id.signup_login_hint_textview);
        signupLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirection vers l'activité de login
                Intent loginIntent = new Intent(RegisterActivity.this, AuthActivity.class);
                startActivity(loginIntent);
            }
        });

    }

    private void registerUser() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String username = editTextUsername.getText().toString();
        String phoneNumber = editTextPhoneNumber.getText().toString();

        // Check if the user has entered information
        if (email.isEmpty() || password.isEmpty() || username.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Register the user with Firebase Authentication
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // User registration successful
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (firebaseUser != null) {
                            // Update the user's display name
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username)
                                    .build();
                            firebaseUser.updateProfile(profileUpdates);

                            // Add user to Firestore database
                            addUserToFirestore(email, username, phoneNumber, firebaseUser);
                        }
                    } else {
                        // Registration failed
                        Toast.makeText(RegisterActivity.this, "Failed to register user: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addUserToFirestore(String email, String username, String phoneNumber, FirebaseUser firebaseUser) {
        UserDao userDao = new UserDao(FirebaseFirestore.getInstance());
        userDao.addUser(email, username, phoneNumber, String.valueOf(firebaseUser)) // Correction de la méthode addUser
                .addOnSuccessListener(documentReference -> {
                    // User added to Firestore successfully
                    Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, AuthActivity.class));
                    finish(); // Finish the current activity
                })
                .addOnFailureListener(e -> {
                    // Failed to add user to Firestore
                    Toast.makeText(RegisterActivity.this, "Failed to register user in Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
