package com.rasheek.iconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.rasheek.iconnect.Model.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class login extends AppCompatActivity {


    Button loginBtn;
    private static final int RC_SIGN_IN = 123;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       loginBtn = findViewById(R.id.loginBtn);
       loginBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
            createSignInIntent();
           }
       });

    }


    public void createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(

                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_create_intent]
    }

    // [START auth_fui_result]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            Toast.makeText(this, "Login success "+ response.getEmail(), Toast.LENGTH_LONG).show();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            final String email = user.getEmail();
            final String name = user.getDisplayName();
            final String authId = user.getUid();

            @SuppressWarnings("Is register not completed")
            boolean isRegistered = false;
            db.collection("users").whereEqualTo("email", email).get().addOnSuccessListener(
                    new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<User> userList = queryDocumentSnapshots.toObjects(User.class);
                            if(userList.size() > 0){
                                User user = userList.get(0);
                                Intent homeIntent = new Intent(login.this, home.class);
                                homeIntent.putExtra("auth_name", name + "");
                                homeIntent.putExtra("auth_email", email + "");
                                homeIntent.putExtra("auth_id", authId + "");
                                homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(homeIntent);
                            }else{
                                Intent registerIntent = new Intent(login.this, register.class);
                                registerIntent.putExtra("auth_name", name + "");
                                registerIntent.putExtra("auth_email", email + "");
                                registerIntent.putExtra("auth_id", authId + "");
                                registerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(registerIntent);
                            }
                        }
                    }
            );


        }else{
            Toast.makeText(this, "Login Failed  ", Toast.LENGTH_LONG).show();
        }
    }
    // [END auth_fui_result]


    public void delete() {
        // [START auth_fui_delete]
        AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        // [END auth_fui_delete]
    }

    public void themeAndLogo() {
        List<AuthUI.IdpConfig> providers = Collections.emptyList();

        // [START auth_fui_theme_logo]
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        //             .setLogo(R.drawable.)      // Set logo drawable
                        //                      .setTheme(R.style.MySuperAppTheme)      // Set theme
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_theme_logo]
    }

    public void privacyAndTerms() {
        List<AuthUI.IdpConfig> providers = Collections.emptyList();
        // [START auth_fui_pp_tos]
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTosAndPrivacyPolicyUrls(
                                "https://example.com/terms.html",
                                "https://example.com/privacy.html")
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_pp_tos]
    }
}