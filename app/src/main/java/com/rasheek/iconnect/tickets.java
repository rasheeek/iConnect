package com.rasheek.iconnect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rasheek.iconnect.Model.Tickets;

public class tickets extends AppCompatActivity {
EditText title, body;
Button submit;
RadioGroup type;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = findViewById(R.id.ticketTitle);
        body = findViewById(R.id.ticketBody);
        submit = findViewById(R.id.submitBtn);
        type = findViewById(R.id.ticketType);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });


    }

    void submit(){

        int id = type.getCheckedRadioButtonId();
        String ticketType = "";

        switch (id){
                case 2131362110 :
                ticketType = "Information Request";
                break;
                case 2131362111 :
                ticketType = "Complain";
                break;
                case 2131362112 :
                ticketType = "Compliment";
                break;


        }
        Tickets t = new Tickets(title.getText().toString(), body.getText().toString(), ticketType);

        db.collection("tickets")
                .add(t)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {


                        Toast.makeText(tickets.this, "Ticket successfully submitted ", Toast.LENGTH_LONG).show();
                        Log.d("Tickets", "DocumentSnapshot added with ID: " + documentReference.getId());
                        Intent home = new Intent(tickets.this, home.class);
                        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(home);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Register", "Error adding document", e);
                    }
                });

    }
}