package com.hadianoor.i190404_i190405;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText email,pass;
    Button signup,button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();

        email=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        signup=findViewById(R.id.signup);
        button=findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Signin.class);
                startActivity(intent);
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.createUserWithEmailAndPassword(email.getText().toString(),pass.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(MainActivity.this,"Succesfully created user",Toast.LENGTH_LONG).show();
                                    sendUsertoPlaylist();
                                    Toast.makeText(MainActivity.this,mAuth.getCurrentUser().getUid(),Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this," Failed to created user",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null)
        {
            Toast.makeText(MainActivity.this,user.getUid()+" ",Toast.LENGTH_SHORT).show();
        }
    }

    protected void sendUsertoPlaylist() {
        Intent intent=new Intent(MainActivity.this,Profile.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}