package com.rasheek.iconnect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rasheek.iconnect.Model.User;
import com.squareup.picasso.Picasso;

public class register extends AppCompatActivity {
    EditText name, mobile, nic;
    RadioGroup gender;
    ImageView profile;
    Button pickProfileImage, register;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Uri profilePhotoData;
    int FILE_CHOOSE_ACTIVITY_RESULT_CODE_PROFILE = 1001;
    private StorageReference mStorageRef;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.register_name);
        mobile = findViewById(R.id.register_mobile);
        nic = findViewById(R.id.register_nic);
        gender =findViewById(R.id.gender_radio);
        profile = findViewById(R.id.register_profile);
        pickProfileImage = findViewById(R.id.register_profileImage);
        register = findViewById(R.id.register_Btn);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        Bundle bundle = getIntent().getExtras();
         email = bundle.getString("auth_email");


        pickProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fileChooser = new Intent();
                fileChooser.setAction(Intent.ACTION_GET_CONTENT);
                fileChooser.setType("image/*");
                startActivityForResult(Intent.createChooser(fileChooser, "Select profile photo"), FILE_CHOOSE_ACTIVITY_RESULT_CODE_PROFILE);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(profile == null ){
                    Toast.makeText(register.this, "Please upload profile photo", Toast.LENGTH_LONG).show();
                }else{
                    doRegister();
                }


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == FILE_CHOOSE_ACTIVITY_RESULT_CODE_PROFILE){
            if(resultCode == RESULT_OK){
                profilePhotoData  = data.getData();
                Picasso.get().load(profilePhotoData).into(profile);

            }
        } if(requestCode == FILE_CHOOSE_ACTIVITY_RESULT_CODE_PROFILE){
            if(resultCode == RESULT_OK){
                profilePhotoData  = data.getData();
                Picasso.get().load(profilePhotoData).into(profile);

            }
        }
    }

   void doRegister(){
       int id = gender.getCheckedRadioButtonId();
       String gender= "Male";
       if(id == 2131361976){
           gender = "Female";
       }


       String  profileImageName = name.getText().toString()+"-"+email;


       final User u = new User(name.getText().toString(), mobile.getText().toString(), nic.getText().toString(),email, gender, profileImageName);
       StorageReference riversRef = mStorageRef.child("profile/"+profileImageName);

       riversRef.putFile(profilePhotoData)
               .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                       Toast.makeText(register.this, "Profile image uploaded", Toast.LENGTH_LONG).show();
                       saveData(u);
                   }
               })
               .addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception exception) {

                       Toast.makeText(register.this, "Driver image upload failed", Toast.LENGTH_LONG).show();
                   }
               }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
               String imageURL = task.getResult().toString();

           }
       });

   }

  void saveData(User u){
      db.collection("users")
              .add(u)
              .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                  @Override
                  public void onSuccess(DocumentReference documentReference) {



                      Log.d("Register", "DocumentSnapshot added with ID: " + documentReference.getId());
                      Intent home = new Intent(register.this, home.class);
                      home.putExtra("auth_name", name.getText().toString());
                      home.putExtra("auth_email", email);
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