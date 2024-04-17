package com.example.taskmanagement;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import dao.TacheDao; // Importez votre DAO TacheDao
import model.Tache;

public class AddTaskActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextDeadline;

    private TacheDao tacheDao; // Utilisez votre DAO TacheDao
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDeadline = findViewById(R.id.editTextDeadline);
        Button buttonAddTask = findViewById(R.id.buttonAddTask);

        mAuth = FirebaseAuth.getInstance();
        tacheDao = new TacheDao(mAuth.getCurrentUser(), FirebaseFirestore.getInstance());

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaskToDatabase();
            }
        });
    }

    private void addTaskToDatabase() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String deadline = editTextDeadline.getText().toString();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Log.e("AddTaskActivity", "User not signed in");
            return;
        }

        Tache task = new Tache(title, description, deadline, null, currentUser.getUid());

        tacheDao.addTache(task)
                .addOnSuccessListener(documentReference -> {
                    Log.d("AddTaskActivity", "Task added successfully with ID: " + documentReference.getId());
                    Toast.makeText(AddTaskActivity.this, "Task added successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.e("AddTaskActivity", "Failed to add task: " + e.getMessage());
                    Toast.makeText(AddTaskActivity.this, "Failed to add task: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
