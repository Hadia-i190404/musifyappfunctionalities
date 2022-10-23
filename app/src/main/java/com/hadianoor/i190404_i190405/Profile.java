package com.hadianoor.i190404_i190405;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    ImageView dp;
    FirebaseAuth mAuth;
    TextView fname, lname, gender, bio;
    //String fullname, lastname, gen, bi;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        gender = findViewById(R.id.gender);
        bio = findViewById(R.id.bio);
        add = findViewById(R.id.add);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();

                DatabaseReference myRef = database.getReference("message");
                MyModel m = new MyModel(
                        fname.getText().toString(),
                        lname.getText().toString(),
                        gender.getText().toString(),
                        bio.getText().toString()
                );

                //myRef.push().setValue(m);
                myRef = myRef.push();
                myRef.getKey();
                myRef.setValue(m);
                finish();

            }
        });

        dp = findViewById(R.id.dp);
        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,20);


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==20 & resultCode==RESULT_OK){
            Uri image=data.getData();
            Calendar c=Calendar.getInstance();
            //dp.setImageURI(image);
            FirebaseStorage storage=FirebaseStorage.getInstance();
            StorageReference ref=storage.getReference().child("dp/mydp"+c.getTimeInMillis()+".jpg");
            ref.putFile(image)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> task=taskSnapshot.getStorage().getDownloadUrl();
                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                  //  tv.setText(uri.toString());
                                    Picasso.get().load(uri.toString()).into(dp);
                                }
                            });

                            Toast.makeText(
                                    Profile.this,
                                    "Success",Toast.LENGTH_LONG
                            ).show();
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(
                                    Profile.this,
                                    "Failed",Toast.LENGTH_LONG
                            ).show();

                        }
                    });


        }
    }

}
/*        mAuth= FirebaseAuth.getInstance();
        FirebaseUser firebaseUser= mAuth.getCurrentUser();
        ReadWriteUserDetails writeUserDetails=new ReadWriteUserDetails(fullname,lastname,gen,bi);

        DatabaseReference referenceProfile=FirebaseDatabase.getInstance().getReference("Registered Users");

        referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Profile.this,"User Registered Successfully",Toast.LENGTH_LONG).show();

                            Intent intent=new Intent(Profile.this,Playlist.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(Profile.this,"User Registered Failure",Toast.LENGTH_LONG).show();

                        }
                    }
                });



        if(firebaseUser==null)
        {
            Toast.makeText(Profile.this,"Something is wrong user's details not available",
                    Toast.LENGTH_LONG).show();

        }
        else
        {
            showUserProfile(firebaseUser);
        }

    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID= firebaseUser.getUid();

        DatabaseReference referenceProfile= FirebaseDatabase.getInstance().getReference("Registered User");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readWriteUserDetails=snapshot.getValue(ReadWriteUserDetails.class);
                if (readWriteUserDetails!=null)
                {
                    fullname=readWriteUserDetails.fname;
                    lastname=readWriteUserDetails.lname;
                    gen=readWriteUserDetails.gender;
                    bi=readWriteUserDetails.bio;

                    fname.setText(fullname);
                    lname.setText(lastname);
                    gender.setText(gen);
                    bio.setText(bi);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this,"Something is wrong user's details not available",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createprofile(String fname,String lname, String gender, String bio)
    {
*/



