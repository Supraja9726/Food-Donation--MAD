package com.example.supraja.fooddonate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private TextView userReg;
    private Button Login;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private SignInButton mgoogleBtn;
    private static final int RC_SIGN_IN=1;
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = "Signin";
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Name = findViewById( R.id.etName );
        Password = findViewById( R.id.etPassword );
        userReg = findViewById( R.id.tvRegister );
        Login = findViewById( R.id.btnLogin );

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    startActivity( new Intent( MainActivity.this,Retrieve.class ) );
                }
            }
        };

        progressDialog=new ProgressDialog( this );
        mgoogleBtn= findViewById( R.id.GoogleBtn );
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder( GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient=new GoogleApiClient.Builder(getApplicationContext() )
                .enableAutoManage( this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(MainActivity.this, "error",
                                Toast.LENGTH_SHORT).show();
                    }
                } )

                .addApi( Auth.GOOGLE_SIGN_IN_API,gso )
                .build();
        mgoogleBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        } );

        FirebaseUser user= firebaseAuth.getCurrentUser();
        if(user != null){
            finish();
            startActivity( new Intent( MainActivity.this,Retrieve.class ) );
        }

        Login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    progressDialog.setMessage( "verification on progress" );
                    progressDialog.show();

                    //upload data to database;
                    String user_email=Name.getText().toString().trim();
                    String user_password=Password.getText().toString().trim();

                    firebaseAuth.signInWithEmailAndPassword( user_email,user_password ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText( MainActivity.this,"Login Successful",Toast.LENGTH_SHORT ).show();
                                startActivity( new Intent( MainActivity.this,Retrieve.class ) );
                            }else{
                                Toast.makeText( MainActivity.this,"Make sure your mail id and password is Correct",Toast.LENGTH_SHORT ).show();
                                progressDialog.dismiss();
                            }

                        }
                    } );

                }
            }
        } );

        userReg.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent( MainActivity.this,Register.class ) );
            }
        } );
    }

    @Override
    protected void onStart(){
        super.onStart();
        firebaseAuth.addAuthStateListener( mAuthListener );
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success" + task.isSuccessful());
                        if( !task.isSuccessful()){

                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication Failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private Boolean validate()
    {
        Boolean result = false;


        String email = Name.getText().toString();
        String password = Password.getText().toString();

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText( this,"please enter all the details",Toast.LENGTH_SHORT ).show();
        }else{
            result=true;
        }
        return result;
    }
}



