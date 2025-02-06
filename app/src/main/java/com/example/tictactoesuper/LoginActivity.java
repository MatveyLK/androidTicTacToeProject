package com.example.tictactoesuper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences spLang;
    private SharedPreferences.Editor langEditor;
    private String lang;
    private TextView tvLangRu, tvLangEn, tvLangHe, tvRegInvite;
    private EditText etName, etPassword;
    private Button btnLog, btnReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvLangEn = findViewById(R.id.tv_lang_en);
        tvLangRu = findViewById(R.id.tv_lang_ru);
        tvLangHe = findViewById(R.id.tv_lang_he);
        tvLangEn.setOnClickListener(this);
        tvLangRu.setOnClickListener(this);
        tvLangHe.setOnClickListener(this);

        tvRegInvite = findViewById(R.id.tv_reg_invite);

        etName = findViewById(R.id.et_name);
        etPassword = findViewById(R.id.et_password);

        spLang = getSharedPreferences("saved_data", MODE_PRIVATE);
        langEditor = spLang.edit();
        updateLanguage();
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
        etName.setHint(getResources().getIdentifier("name_hint_" + lang, "string", getPackageName()));
        etPassword.setHint(getResources().getIdentifier("password_hint_" + lang, "string", getPackageName()));
        tvRegInvite.setText(getResources().getIdentifier("reg_invite_" + lang, "string", getPackageName()));
    }
}