package com.example.supraja.fooddonate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Logout extends AppCompatActivity {

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_logout );
        mAuth = FirebaseAuth.getInstance( );




    }

    private void Logout() {
        mAuth.signOut( );
        finish( );
        startActivity( new Intent( Logout.this, MainActivity.class ) );


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu,menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutMenu:{
                Logout();
                break;
            }
            case R.id.donateblood:{
                startActivity( new Intent( Logout.this, Detail.class ) );
                break;

            }
            case R.id.need:{
                startActivity( new Intent( Logout.this, Retrieve.class ) );
                break;


            }
        }

        return super.onOptionsItemSelected( item );
    }

}