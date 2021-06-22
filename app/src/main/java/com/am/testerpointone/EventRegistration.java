package com.am.testerpointone;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class EventRegistration extends AppCompatActivity {

    EditText edname, emob, edmail, edaddress, edob;
    EditText edcolname, edschl, edyrsem;
    Spinner spdist, sptaluk, partifor;
    String stname, stmail, staddress, stdob, stschl, stdist, sttaluk, stgender, stparfor;
    String stcollname, styrsem;
    RadioGroup rg;
    CheckBox checkBox;
    PrefManager prefManager;
    FirebaseDatabase database;
    DatabaseReference myRef;
    TextView conftxt;
    ArrayAdapter districtAdapter;
    ArrayAdapter talukAdapter;
    ArrayAdapter parAdapter;
    ArrayList<String> Pararray;
    String[] o;
    String[] Distarray;
    String[] Talukarray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_registration);
        edcolname = findViewById(R.id.edcolname);
        edschl = findViewById(R.id.edschl);
        edyrsem = findViewById(R.id.edyrsem);
        edname = findViewById(R.id.edname);
        emob = findViewById(R.id.emob);
        edmail = findViewById(R.id.edmail);
        edaddress = findViewById(R.id.edaddress);
        edob = findViewById(R.id.edob);
        spdist = findViewById(R.id.spinerdis);
        partifor = findViewById(R.id.spinerparfor);
        sptaluk = findViewById(R.id.spinertaluk);
        checkBox = findViewById(R.id.checkBox);
        rg = findViewById(R.id.rg);
        conftxt = findViewById(R.id.conftxt);
        prefManager = new PrefManager(EventRegistration.this);
        emob.setText(prefManager.getMyMobNum());
        database = FirebaseDatabase.getInstance();

        o = new String[31];
        Distarray = new String[31];
        Talukarray = new String[31];

        Distarray = getResources().getStringArray(R.array.districts);

        Pararray = new ArrayList<>();
        Pararray.add("School Day");
        Pararray.add("College Day");
        Pararray.add("Seniors Day");
        parAdapter = new ArrayAdapter(EventRegistration.this, android.R.layout.simple_spinner_dropdown_item, Pararray);

        partifor.setAdapter(parAdapter);
        partifor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stparfor = Pararray.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        districtAdapter = new ArrayAdapter(EventRegistration.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.districts));
        spdist.setAdapter(districtAdapter);

        spdist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                stdist = Distarray[i];
                //--i;
             //   Toast.makeText(EventRegistration.this, ""+stdist+"\n"+i, Toast.LENGTH_SHORT).show();

                switch (i) {
                    case 0:
                        o = getResources().getStringArray(R.array.Bagalkot);
                        break;
                    case 1:
                        o = getResources().getStringArray(R.array.Bangalore_Urban_taluk);
                        break;
                    case 2:
                        o = getResources().getStringArray(R.array.Bangalore_Rural);
                        break;
                    case 3:
                        o = getResources().getStringArray(R.array.Belgaum);
                        break;
                    case 4:
                        o = getResources().getStringArray(R.array.Bellary);
                        break;
                    case 5:
                        o = getResources().getStringArray(R.array.Bidar);
                        break;
                    case 6:
                        o = getResources().getStringArray(R.array.Vijayapura);
                        break;
                    case 7:
                        o = getResources().getStringArray(R.array.Chamarajanagar);
                        break;
                    case 8:
                        o = getResources().getStringArray(R.array.Chikballapur);
                        break;
                    case 9:
                        o = getResources().getStringArray(R.array.Chikmagalur);
                        break;
                    case 10:
                        o = getResources().getStringArray(R.array.Chitradurga);
                        break;
                    case 11:
                        o = getResources().getStringArray(R.array.Dakshina_Kannada);
                        break;
                    case 12:
                        o = getResources().getStringArray(R.array.Davanagere);
                        break;
                    case 13:
                        o = getResources().getStringArray(R.array.Dharwad);
                        break;
                    case 14:
                        o = getResources().getStringArray(R.array.Gadag);
                        break;
                    case 15:
                        o = getResources().getStringArray(R.array.Gulbarga);
                        break;
                    case 16:
                        o = getResources().getStringArray(R.array.Hassan);
                        break;
                    case 17:
                        o = getResources().getStringArray(R.array.Haveri);
                        break;
                    case 18:
                        o = getResources().getStringArray(R.array.Kodagu);
                        break;
                    case 19:
                        o = getResources().getStringArray(R.array.Kolar);
                        break;
                    case 20:
                        o = getResources().getStringArray(R.array.Koppal);
                        break;
                    case 21:
                        o = getResources().getStringArray(R.array.Mandya);
                        break;
                    case 22:
                        o = getResources().getStringArray(R.array.Mysore);
                        break;
                    case 23:
                        o = getResources().getStringArray(R.array.Raichur);
                        break;
                    case 24:
                        o = getResources().getStringArray(R.array.Ramanagara);
                        break;
                    case 25:
                        o = getResources().getStringArray(R.array.Shimoga);
                        break;
                    case 26:
                        o = getResources().getStringArray(R.array.Tumkur);
                        break;
                    case 27:
                        o = getResources().getStringArray(R.array.Udupi);
                        break;
                    case 28:
                        o = getResources().getStringArray(R.array.Uttara_Kannada);
                        break;
                    case 29:
                        o = getResources().getStringArray(R.array.Vijayanagara);
                        break;
                    case 30:
                        o = getResources().getStringArray(R.array.Yadgir);
                        break;
                }

                talukAdapter = new ArrayAdapter(EventRegistration.this, android.R.layout.simple_spinner_dropdown_item, o);
                sptaluk.setAdapter(talukAdapter);

                sptaluk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        sttaluk = sptaluk.getSelectedItem().toString();
             //           Toast.makeText(EventRegistration.this, "" + sttaluk, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    public void gotomain3(View view) {

        if (checkBox.isChecked()) {
            stname = edname.getText().toString();
            stmail = edmail.getText().toString();
            staddress = edaddress.getText().toString();
            stschl = edschl.getText().toString();
            stcollname = edcolname.getText().toString();
            styrsem = edyrsem.getText().toString();
            stdob = edob.getText().toString();

            int id = rg.getCheckedRadioButtonId();
            if (id == R.id.ra1) {
                stgender = "Male";
            } else if (id == R.id.ra2) {
                stgender = "Female";
            } else if (id == R.id.ra3) {
                stgender = "Other";
            }

            DatabaseReference myRef = database.getReference(prefManager.getMyMobNum()+"EVENT");


            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    UserDetails userDetails = new UserDetails();
                    userDetails.setStname(stname);
                    userDetails.setStaddress(staddress);
                    userDetails.setStcollname(stcollname);
                    userDetails.setStdist(stdist);
                    userDetails.setStmail(stmail);

                    userDetails.setStgender(stgender);
                    userDetails.setStparfor(stparfor);
                    userDetails.setStyrsem(styrsem);
                    userDetails.setSttaluk(sttaluk);
                    userDetails.setStdob(stdob);
                    userDetails.setStdist(stdist);
                    userDetails.setStschl(stschl);
                    myRef.setValue(userDetails);
                    prefManager.setiseventregistered(true);
                    Toast.makeText(EventRegistration.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();

                    edname.setText("");
                    edaddress.setText("");
                    edmail.setText("");
                    edschl.setText("");
                    edname.setText("");
                    edcolname.setText("");
                    edyrsem.setText("");
                    //ed
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Toast.makeText(EventRegistration.this, "Technical error in saving Data.. please try again!", Toast.LENGTH_SHORT).show();

                }
            });

            ///String c = stname + "\n" + stmail + "\n" + stgender + "\n" + staddress + "\n" + stdist + "\n" + sttaluk + "\n" + stparfor + "\n" + styrsem + "\n" + stcollname + "\n" + stschl + "\n" + stdob;
//            conftxt.setText(c);
        } else {
            Toast.makeText(this, "Please agree to terms and conditions.", Toast.LENGTH_SHORT).show();
        }
        // startActivity(new Intent(Registration.this,MainActivity3.class));

    }

    public void dat(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(EventRegistration.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                stdob = i2 + "/" + i1 + "/" + i;
                edob.setText(i2 + "/" + (1 + i1) + "/" + i);
            }
        }, 2000, 1, 0);
        datePickerDialog.show();
    }
}