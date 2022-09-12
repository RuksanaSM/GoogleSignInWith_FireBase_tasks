package com.example.googlesigninwith_firebase_task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {

    private EditText titleEdt,desEdt;
    ImageView img;
    private String titlestr,descstr;
    private FirebaseFirestore db;
ModelClass modelClass;

    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

       ModelClass modelClass= (ModelClass) getIntent().getSerializableExtra("fb");

//        final Intent i=getIntent();
//
//        String gettitle=i.getStringExtra("title");
//        String getdesc=i.getStringExtra("desc");
//        final String id=i.getStringExtra("id");
            db = FirebaseFirestore.getInstance();

          titleEdt=findViewById(R.id.title);
          desEdt=findViewById(R.id.desc);
          img=findViewById(R.id.updateimg);

        // creating variable for button
            Button updateBtn = findViewById(R.id.updatesbutton);
           Button deletebtn=findViewById(R.id.deletedbutton);
        Button cancelbtn=findViewById(R.id.cancelbutton);

        titleEdt.setText(modelClass.getTitle());
        desEdt.setText(modelClass.getDesc());
//img.setImageURI(Uri.parse(modelClass.getImg()));

        cancelbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(UpdateActivity.this,AddingData_Activity.class);
                    startActivity(i);
                }
            });



         deletebtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                         deleteData(modelClass);
             }
         });

            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   titlestr = titleEdt.getText().toString();
                   descstr= desEdt.getText().toString();
//                   String imgstr=img.getResources().toString();
                    if (TextUtils.isEmpty( titlestr)) {
                       titleEdt.setError("Please enter Title");
                    } else if (TextUtils.isEmpty(descstr)) {
                       desEdt.setError("Please enter Description");
                    }  else {
                        updateCourses(modelClass,titlestr,descstr);

                    }
                }
            });
        }




    private void deleteData(ModelClass modelClass) {

        db.collection("ODSIMAGES").document(modelClass.getId()).delete().
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            // this method is called when the task is success
                            // after deleting we are starting our MainActivity.
                            Toast.makeText(UpdateActivity.this, "Data has been deleted from Database.", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(UpdateActivity.this,AddingData_Activity.class);
                            startActivity(i);
                        } else {
                            // if the delete operation is failed
                            // we are displaying a toast message.
                            Toast.makeText(UpdateActivity.this, "Fail to delete the course. ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void updateCourses(ModelClass modelClass, String titlestr, String descstr) {

          ModelClass updatedData = new ModelClass(titlestr,descstr);
          db.collection("ODSIMAGES").document(modelClass.getId()).set(updatedData).
                            addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateActivity.this, "Data has been updated..", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(UpdateActivity.this,AddingData_Activity.class);
                            startActivity(i);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UpdateActivity.this, "Fail to update the data..", Toast.LENGTH_SHORT).show();
                }
            });
        }





}




