package com.cyberlabs.clubs.sharedcalender;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.Arrays;
import java.util.Collections;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

    }

       @Override
        protected void onStart () {
            super.onStart();
            Intent i = new Intent(getBaseContext(), MainActivity.class);  //change not for push
            startActivity(i);  //change noyt for push
            Log.e("TAG", "onStart: ");
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                    if (firebaseUser == null) {
                        Button loginButton = findViewById(R.id.login_button);
                        loginButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent loginIntent = AuthUI.getInstance()
                                        .createSignInIntentBuilder()
                                        .setLogo(R.drawable.ic_launcher_background)
                                        // .setIsSmartLockEnabled(false)
                                        .setAvailableProviders(Collections.singletonList(

                                                new AuthUI.IdpConfig.FacebookBuilder().build()))


                                        .build();
                                startActivity(loginIntent);

                            }
                        });


                    } else {
                        Log.e("TAG", "onAuthStateChanged: " + firebaseUser.getDisplayName());
                        Intent i = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(i);
                    }
                }
            });
        }
    }

