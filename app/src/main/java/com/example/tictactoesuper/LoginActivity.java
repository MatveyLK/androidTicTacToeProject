package com.example.tictactoesuper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences spLang;
    private SharedPreferences.Editor langEditor;
    private String lang;
    private TextView tvLangRu, tvLangEn, tvLangHe;
    private EditText etName, etPassword;
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
        Log.d("MY_TAG", "hi");
        lang = spLang.getString("language", "en");
        etName.setHint(getString(R.string.name_hint_en));
    }
}