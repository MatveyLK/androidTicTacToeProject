package com.example.tictactoesuper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHandler {
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    private Context context;
    public FirebaseHandler(Context context)
    {
        this.context = context;
        this.authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null)
                {
                    Toast.makeText(context, "user issigned in.", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(context,MenuActivity.class);
                    context.startActivity(new Intent(context, MenuActivity.class));
                    ((Activity) context).finish();
                }
                else
                    Toast.makeText(context, "user signed out.", Toast.LENGTH_SHORT).show();
            }
        };

        this.auth = FirebaseAuth.getInstance();
    }

    public void signIn(String email, String passw) {
        auth.signInWithEmailAndPassword(email,passw).addOnCompleteListener(
                (Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(context, "signIn Successful.", Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(context, MenuActivity.class);
                            context.startActivity(new Intent(context, MenuActivity.class));
                            ((Activity) context).finish();
                        }
                        else
                        {
                            Toast.makeText(context, "signIn failed." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            task.getException().printStackTrace();
                        }
                    }
                });
    }

    public void createAccount(String email, String password, String name){

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(((Activity) context),
                new OnCompleteListener<AuthResult>() {
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {

                            String uRef= FirebaseAuth.getInstance().getCurrentUser().getUid();
                            User user = new User(email, name);
                            user.setID(uRef);
                            dbRef.child("USERS").child(uRef).setValue(user);



                            Toast.makeText(context, "Authentication successful", Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(context, MenuActivity.class));
                            ((Activity) context).finish();
                        }
                        else{
                            Toast.makeText(context, "Authentication fail"
                                    +task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            task.getException().printStackTrace();
                        }
                    }
                });
    }

    public void addListener()
    {
        auth.addAuthStateListener(authStateListener);
    }

    public void removeListener()
    {
        auth.removeAuthStateListener(authStateListener);
    }
}
