package com.example.tictactoesuper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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


    private Button btnReg;

    private EditText etEmail, etName, etPassword, etPassRep;
    private FirebaseHandler fbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        fbHandler = new FirebaseHandler(this);

        etEmail = findViewById(R.id.et_email_reg);
        etName = findViewById(R.id.et_name_reg);
        etPassword = findViewById(R.id.et_pass_reg);
        etPassRep = findViewById(R.id.et_passrep_reg);


        btnReg = findViewById(R.id.btn_reg);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String passw = etPassword.getText().toString();
                String name = etName.getText().toString();
                Boolean checkForNum = false;
                for (int num = 0; num < 10; num++)
                {
                    checkForNum = passw.contains(Integer.toString(num));
                    if (checkForNum)
                        num = 20;
                }
                if (!email.contains("@"))
                    Toast.makeText(RegActivity.this, "No @ in your email", Toast.LENGTH_SHORT).show();
                else if (passw.length() < 8 || passw.toLowerCase().equals(passw) || passw.toUpperCase().equals(passw) || !checkForNum)
                    Toast.makeText(RegActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                else
                {
                    fbHandler.createAccount(email, passw, name);
                }
            }
        });


    }





}