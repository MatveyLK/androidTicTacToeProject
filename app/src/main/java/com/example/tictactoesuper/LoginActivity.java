package com.example.tictactoesuper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences spLang;
    private SharedPreferences.Editor langEditor;
    private String lang;
    private TextView tvLangRu, tvLangEn, tvLangHe, tvRegInvite;
    private EditText etEmail, etPassword;
    private Button btnLog, btnReg;
    private FirebaseHandler fbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fbHandler = new FirebaseHandler(this);

        tvLangEn = findViewById(R.id.tv_lang_en);
        tvLangRu = findViewById(R.id.tv_lang_ru);
        tvLangHe = findViewById(R.id.tv_lang_he);
        tvLangEn.setOnClickListener(this);
        tvLangRu.setOnClickListener(this);
        tvLangHe.setOnClickListener(this);

        tvRegInvite = findViewById(R.id.tv_reg_invite);

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);

        spLang = getSharedPreferences("saved_data", MODE_PRIVATE);
        langEditor = spLang.edit();
        updateLanguage();




        btnLog = findViewById(R.id.btn_log);
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String passw = etPassword.getText().toString();
                if (!email.contains("@"))
                    Toast.makeText(LoginActivity.this, "No @ in your email", Toast.LENGTH_SHORT).show();
                else if (passw.length() < 8)
                    Toast.makeText(LoginActivity.this, "Password looks too short", Toast.LENGTH_SHORT).show();
                else
                    fbHandler.signIn(email, passw);
            }
        });

        btnReg = findViewById(R.id.btn_to_reg);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        fbHandler.addListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        fbHandler.removeListener();
    }



    @Override
    public void onClick(View view) {
        if (view == tvLangEn)
            langEditor.putString("language", "en");
        else if (view == tvLangHe)
            langEditor.putString("language", "he");
        else if (view == tvLangRu)
            langEditor.putString("language", "ru");
        langEditor.apply();
        updateLanguage();
    }

    public void updateLanguage()
    {
        lang = spLang.getString("language", "en");
        LocaleListCompat appLocale = LocaleListCompat.forLanguageTags(lang);
        AppCompatDelegate.setApplicationLocales(appLocale);
    }
}