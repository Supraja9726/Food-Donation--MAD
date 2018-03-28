package com.example.supraja.fooddonate;


        import android.annotation.SuppressLint;
        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

public class Detail  extends AppCompatActivity {
    EditText editName,editContact,editFoodtype,editCity,editState;
    Button btnRegister;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail );
        editName = findViewById( R.id.editName );
        editContact = findViewById( R.id.editContact );
        editFoodtype = findViewById( R.id.editFoodtype );
        editCity = findViewById( R.id.editCity );
        editState = findViewById( R.id.editState );
        btnRegister = findViewById( R.id.btnRegister );
        databaseReference = FirebaseDatabase.getInstance( ).getReference( "Donor" );


        btnRegister.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance( );
                DatabaseReference myRef = database.getReference( "message" );
                addArrayList();
            }
        } );
    }

    private void addArrayList(){
        String name = editName.getText().toString().trim();
        String contact = editContact.getText().toString().trim();
        String foodtype = editFoodtype.getText().toString().trim();
        String city = editCity.getText().toString().trim();
        String state = editState.getText().toString().trim();
        if(name.isEmpty() || contact.isEmpty() || foodtype.isEmpty() || city.isEmpty() || state.isEmpty() ) {
            Toast.makeText( Detail.this, "please enter all the details", Toast.LENGTH_SHORT ).show( );
        }else{
            String id=databaseReference.push().getKey();
            Donor donor=new Donor(name,contact,foodtype,city,state);
            databaseReference.child(id).child( "name" ).setValue( name );
            databaseReference.child(id).child( "contact" ).setValue( contact );
            databaseReference.child(id).child( "foodtype" ).setValue( foodtype );
            databaseReference.child(id).child( "city" ).setValue( city );
            databaseReference.child(id).child( "state" ).setValue( state );


            clearText();
            Toast.makeText( Detail.this, "Record added and you are successfully registerd", Toast.LENGTH_LONG ).show( );
            startActivity( new Intent( Detail.this,Logout.class ) );
        }
    }
    public void clearText() {
        editName.setText( "" );
        editContact.setText( "" );
        editFoodtype.setText( "" );
        editCity.setText( "" );
        editState.setText( "" );
        editContact.requestFocus();
    }


}
