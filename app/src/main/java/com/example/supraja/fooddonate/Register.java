package com.example.supraja.fooddonate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

@SuppressWarnings("ALL")
public class Register extends AppCompatActivity {
    private EditText userName,userPassword,userEmail;
    private Button registerButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        userName= findViewById( R.id.editName );
        userPassword= findViewById( R.id.etUserPassword );
        userEmail= findViewById( R.id.etUserEmail );
        registerButton= findViewById( R.id.btnUserRegister );
        userLogin= findViewById( R.id.tvUserLogin);
        firebaseAuth = FirebaseAuth.getInstance();




        registerButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    //upload data to database;
                    String user_email=userEmail.getText().toString().trim();
                    String user_password=userPassword.getText().toString().trim();

                    //noinspection deprecation
                    firebaseAuth.createUserWithEmailAndPassword( user_email,user_password ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText( Register.this,"Registration Successful",Toast.LENGTH_SHORT ).show();

                            }else{
                                Toast.makeText( Register.this,"Registration Failed",Toast.LENGTH_SHORT ).show();
                            }

                        }
                    } );

                }
            }
        } );

        userLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent( Register.this,MainActivity.class ) );
            }
        } );

    }


    private Boolean validate()
    {
        Boolean result = false;


        String name = userName.getText().toString();
        String password = userPassword.getText().toString();
        String email = userEmail.getText().toString();

        if(name.isEmpty() || password.isEmpty() || email.isEmpty()){
            Toast.makeText( this,"please enter all the details",Toast.LENGTH_SHORT ).show();
        }else{
            result=true;
        }
        return result;
    }
}
