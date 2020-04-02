package com.darpan.studendetails;

import android.Manifest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

/*
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

*/

import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.database.ChangeEventListener;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.ObservableSnapshotArray;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DetailsListActivity extends AppCompatActivity {

    private static final String REQUEST_WRITE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final int REQUEST_PERMISSION_CODE = 1001;
    private RecyclerView recyclerView;
    private DetailsAdapter detailsAdapter;
    private ProgressDialog pDialog;
    public int count = 0;
    public List<UserDetails> userDetailsList = new ArrayList<>();
    public int childCount = 0;
    String TAG = "Details List Activity: ";
    private Animation animationDown;
    //drawer layout
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Toolbar toolbar;
    LinearLayout linearLayout;
    ImageView CrossButton;
    TextView clear_text_view_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_list);
        initActionBar();
       /* System.setProperty("org.apache.poi.java.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.java.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");*/
     /*   System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLInputFactory",
                "com.fasterxml.aalto.stax.InputFactoryImpl"
        );
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLOutputFactory",
                "com.fasterxml.aalto.stax.OutputFactoryImpl"
        );
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLEventFactory",
                "com.fasterxml.aalto.stax.EventFactoryImpl"
        );*/

        pDialog = new ProgressDialog(this);
        pDialog.setTitle("database is");
        pDialog.setMessage("loading..");
        pDialog.show();
        animationDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        CrossButton=findViewById(R.id.cross);
        linearLayout=findViewById(R.id.clear_text_ll);
        clear_text_view_text=findViewById(R.id.file_saved_location);

        CrossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linearLayout.getVisibility()==View.VISIBLE)
                {
                    linearLayout.setVisibility(View.GONE);
                }
            }
        });

        recyclerView = findViewById(R.id.RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this
        ));
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference();

        FirebaseDatabase.getInstance();

        final FirebaseRecyclerOptions<UserDetails> options = new
                FirebaseRecyclerOptions.Builder<UserDetails>()
                .setQuery(mref
                        , UserDetails.class
                )

                .build();

        detailsAdapter = new DetailsAdapter(options);
        recyclerView.setAdapter(detailsAdapter);

        // detailsAdapter


        ObservableSnapshotArray<UserDetails> mSnapshots = detailsAdapter.getSnapshots();


        mSnapshots.addChangeEventListener(new ChangeEventListener() {
            @Override
            public void onChildChanged(@NonNull ChangeEventType type,
                                       @NonNull DataSnapshot snapshot,
                                       int newIndex, int oldIndex) {
                System.out.println("on Childchange");
            }

            @Override
            public void onDataChanged() {
                System.out.println("on DataChanged");
                pDialog.dismiss();
                childCount = detailsAdapter.getItemCount();
                System.out.println("childCount " + childCount);
                if (!userDetailsList.isEmpty()) {
                    userDetailsList.clear();
                }
                for (int i = 0; i < childCount; i++) {
                    userDetailsList.add(detailsAdapter.getItem(i));

                }
                System.out.println("user list size+" + userDetailsList.size());
                System.out.println("child name at" + childCount + ":" + userDetailsList.get(4).getName());

                //SortTheUserDetails(userDetailsList);


            }

            @Override
            public void onError(@NonNull DatabaseError databaseError) {
                System.out.println("On error");
                pDialog.dismiss();
                Toast.makeText(DetailsListActivity.this, "Database Error", Toast.LENGTH_SHORT).show();

            }
        });

        isNeedGrantPermission();
    }


    private void initActionBar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navView);
        navigationView.getMenu().getItem(2).setVisible(true);
        navigationView.getMenu().getItem(2).setTitle("Download Details");

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
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();

                    case R.id.download_in_xl_home:
                        SortTheUserDetails(userDetailsList);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.nav_detils:
                        drawerLayout.closeDrawers();
                        break;


                }
                return false;
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);// ad the drawer listner when to open
        actionBarDrawerToggle.syncState();//o syn all the activity like close and open

    }

    @Override
    protected void onStart() {
        super.onStart();
        detailsAdapter.startListening();
//        UserDetails userDetails=detailsAdapter.getItem(1);


    }

    @Override
    protected void onStop() {
        super.onStop();
        detailsAdapter.stopListening();
    }

    public void SortTheUserDetails(List<UserDetails> userDetails) {
        Arrays.asList(userDetails);
        Collections.sort(userDetails, new LexicographicComparator());
        System.out.println(userDetails);
        /* Collections.sort(people, new AgeComparator());
        System.out.println(people);*/
        ExportList(userDetails);
    }


    static class LexicographicComparator implements Comparator<UserDetails> {
        @Override
        public int compare(UserDetails a, UserDetails b) {
            return a.getName().compareToIgnoreCase(b.getName());
        }
    }

   /* static  class AgeComparator implements Comparator<UserDetails> {
        @Override
        public int compare(UserDetails a, UserDetails b) {
            return a.getRollNumber < b.getRollNumber ? -1 : a.getRollNumber == b.getRollNumber ? 0 : 1;
        }
    }*/


    private void ExportList(List<UserDetails> userDetails) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();

            Sheet sheet = workbook.createSheet("sheet1");// creating a blank sheet

            int rownum = 0;
            for (UserDetails user : userDetails) {
                Row row = sheet.createRow(rownum++);
                createList(user, row);

            }
            String fileName = "StudentDetails.xlsx";
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "Students");
            folder.mkdir();
            File file = new File(folder, fileName);
            try {
                file.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
                Log.e(TAG + " Filo create Excep", e1.getMessage());
            }


            try {
                FileOutputStream fileOut = new FileOutputStream(file);
                workbook.write(fileOut);
                fileOut.close();
                Toast.makeText(this, "file saved", Toast.LENGTH_SHORT).show();
                linearLayout.setVisibility(View.VISIBLE);
                clear_text_view_text.startAnimation(animationDown);
                clear_text_view_text.setText("file:/ "+extStorageDirectory+"/Student");

            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG + " Filo/p Excep", e.getMessage());
            }


        } catch (Exception e) {
            Toast.makeText(this, "Exception " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e(TAG + "ExportList", e.getMessage());
            Toast.makeText(this, "not Saved ", Toast.LENGTH_SHORT).show();

        }
    }

    private static void createList(UserDetails user, Row row) {

        Cell cell = row.createCell(0);
        cell.setCellValue(user.getName());
        cell = row.createCell(1);
        cell.setCellValue(user.getRollNumber());
        cell = row.createCell(2);
        cell.setCellValue(user.getEmail());
        cell = row.createCell(3);
        cell.setCellValue(user.getContact());
        cell = row.createCell(4);
        cell.setCellValue(user.getSemester());
        cell = row.createCell(5);
        cell.setCellValue(user.getYear());
        cell = row.createCell(6);
        cell.setCellValue(user.getBranch());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    public static boolean hasPermission(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void isNeedGrantPermission() {
        try {
            if (Build.VERSION.SDK_INT >= 19) {
                if (ContextCompat.checkSelfPermission(this, REQUEST_WRITE_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(DetailsListActivity.this, REQUEST_WRITE_PERMISSION)) {
                        final String msg = String.format(getString(R.string.format_request_permision), getString(R.string.app_name));

                        AlertDialog.Builder localBuilder = new AlertDialog.Builder(DetailsListActivity.this);
                        localBuilder.setTitle("Permission Required!");
                        localBuilder
                                .setMessage(msg).setNeutralButton("Grant",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            DialogInterface paramAnonymousDialogInterface,
                                            int paramAnonymousInt) {
                                        ActivityCompat.requestPermissions(DetailsListActivity.this, new String[]{REQUEST_WRITE_PERMISSION}, REQUEST_PERMISSION_CODE);
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            DialogInterface paramAnonymousDialogInterface,
                                            int paramAnonymousInt) {


                                        paramAnonymousDialogInterface.dismiss();
                                        finish();
                                    }
                                });
                        localBuilder.show();

                    } else {
                        ActivityCompat.requestPermissions(this, new String[]{REQUEST_WRITE_PERMISSION}, REQUEST_PERMISSION_CODE);
                    }

                }

            }
        } catch (Exception e) {
            Log.e(TAG + "Eception In Permision", Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            if (requestCode == REQUEST_PERMISSION_CODE) {

                if (grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //
                } else {
                    //    Toast(DetailsListActivity.this, getString(R.string.info_permission_denied),Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, getString(R.string.info_permission_denied), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.info_permission_denied), Toast.LENGTH_SHORT).show();
            Log.e(TAG + " requestPermissionExc", e.getStackTrace().toString());
            finish();
        }

    }


}

