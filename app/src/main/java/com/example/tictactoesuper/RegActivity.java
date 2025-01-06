package com.example.tictactoesuper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        auth = FirebaseAuth.getInstance();
    }




    private void createAccount(String email, String password, String name){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {

                            String uRef=FirebaseAuth.getInstance().getCurrentUser().getUid();
                            User user = new User(email, name);
                            user.setID(uRef);
                            dbRef.child("USERS").child(uRef).setValue(user);



                            Toast.makeText(RegActivity.this, "Authentication successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegActivity.this, MenuActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(RegActivity.this, "Authentication fail"
                                    +task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            task.getException().printStackTrace();
                        }
                    }
                });
    }
}