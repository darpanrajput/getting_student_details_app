package com.darpan.studendetails;


import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;

import com.rengwuxian.materialedittext.MaterialEditText;


public class MainActivity extends AppCompatActivity {
    public String name, rollnumber, email, year, sem, contact, branch;
    public MaterialEditText edtName, edtEmail, edtRollNumber, edtyear,
            edtSemeter, edtContact, edtBranch;
    public Button submit;
    Spinner spinner;
    Firebase firebase;

    ProgressBar pgr;
    //drawer layout
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActionBar();
        getDetails();


        String[] branches = {"CSE", "ECE", "EEE", "DEMO FILED", "EMPTY"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item, branches);
        spinner.setAdapter(adapter);

        String UniqueId = Settings.Secure.getString(getApplicationContext()
                .getContentResolver(), Settings.Secure.ANDROID_ID);

        firebase.setAndroidContext(this);//https://studentdetails-a50ff.firebaseio.com/

        firebase = new Firebase("https://studentdetails-a50ff.firebaseio.com/Users" + UniqueId);


    }

    private void initActionBar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navView);
        navigationView.getMenu().getItem(2).setVisible(false);

        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.nav_home:
                        getSupportActionBar().setTitle("Home");
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_detils:
                        Intent deatislIntent = new Intent(getApplicationContext(), DetailsListActivity.class);
                        startActivity(deatislIntent);
                        break;
                }
                return false;
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);// ad the drawer listner when to open
        actionBarDrawerToggle.syncState();//o syn all the activity like close and open

    }


    private void getDetails() {
        edtName = findViewById(R.id.editTextName);
        edtEmail = findViewById(R.id.editTextEmail);
        edtRollNumber = findViewById(R.id.editTextRollNumber);
        edtyear = findViewById(R.id.editTextYear);
        edtSemeter = findViewById(R.id.editTextSem);
        edtContact = findViewById(R.id.editTextContact);
        edtBranch = findViewById(R.id.editTextBranch);
        spinner = (Spinner) findViewById(R.id.spinner);

        pgr = findViewById(R.id.pgr);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + tutorialsName, Toast.LENGTH_LONG).show();
                edtBranch.setText(tutorialsName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        submit = findViewById(R.id.Submit_btn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = edtName.getText().toString();
                email = edtEmail.getText().toString();
                rollnumber = edtRollNumber.getText().toString();
                year = edtyear.getText().toString();
                sem = edtSemeter.getText().toString();
                contact = edtContact.getText().toString();
                branch = edtBranch.getText().toString();

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Confirm Details")
                        .setMessage("Name=" + name + "\n\nEmail=" + email + "\n\n"
                                + "Roll Number:" + rollnumber + "\n\n" + "Year=" + year + "\n\nSemester=" + sem + "\n\nContact=" + contact
                                + "\n\nBranch=" + branch)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pgr.setVisibility(View.VISIBLE);
                                checkInput();

                                pgr.setVisibility(View.GONE);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                pgr.setVisibility(View.GONE);

                            }
                        })
                        .show();


/*

                Firebase child_Rating = firebase.child("Rating");
                child_Rating.setValue(smileRating.getSmileName(smileRating.getRating()));
*/


            }
        });
    }

    private void checkInput() {

        if (name.isEmpty()) {
            edtName.setError("Name is Required");
            submit.setEnabled(false);

        } else {
            edtName.setError(null);
            submit.setEnabled(true);

            ;

            Firebase child_name = firebase.child("Name");
            child_name.setValue(name);


        }


        if (email.isEmpty()) {
            edtEmail.setError("This is Required");
            submit.setEnabled(false);

        } else {
            edtEmail.setError(null);
            submit.setEnabled(true);

            Firebase child_email = firebase.child("Email");
            child_email.setValue(email);

        }

        if (rollnumber.isEmpty()) {
            edtRollNumber.setError("This is Required");
            submit.setEnabled(false);
        } else {
            edtRollNumber.setError(null);
            submit.setEnabled(true);

            Firebase child_rollnumber = firebase.child("RollNumber");
            child_rollnumber.setValue(rollnumber);
        }


        if (year.isEmpty()) {
            edtyear.setError("This is Required");
            submit.setEnabled(false);
        } else {
            edtyear.setError(null);
            submit.setEnabled(true);

            Firebase child_year = firebase.child("Year");
            child_year.setValue(year);
        }

        if (sem.isEmpty()) {
            edtSemeter.setError("This is Required");
            submit.setEnabled(false);
        } else {
            edtSemeter.setError(null);
            submit.setEnabled(true);

            Firebase child_sem = firebase.child("Semester");
            child_sem.setValue(sem);

        }


        if (contact.isEmpty()) {
            edtContact.setError("This is Required");
            submit.setEnabled(false);
        } else {
            edtContact.setError(null);
            submit.setEnabled(true);


            Firebase child_contact = firebase.child("Contact");
            child_contact.setValue(contact);
        }

        if (branch.isEmpty()) {
            edtBranch.setError("This is Required");
            submit.setEnabled(false);
        } else {
            edtBranch.setError(null);
            submit.setEnabled(true);


            Firebase child_branch = firebase.child("Branch");
            child_branch.setValue(branch);
        }

    }


}
