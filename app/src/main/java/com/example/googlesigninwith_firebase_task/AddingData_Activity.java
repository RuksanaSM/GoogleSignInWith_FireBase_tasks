package com.example.googlesigninwith_firebase_task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AddingData_Activity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;

    private ArrayList<ModelClass> modelClassArrayList;

    private AdapterClass adapterClass;
    private EditText titleedt, descedt;
    private String titlestr, descstr;
    private FirebaseFirestore firebaseFirestore;
   private DatabaseReference root= FirebaseDatabase.getInstance().getReference("ODS/");
    private Button btnSelect, btnUpload;
    private ImageView imageView;
ImageView imgages;
    // Uri indicates, where the image will be picked from
    private Uri filePath;
    ProgressDialog progressDialog;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;



    ImageView setImage;
    Uri uri;
    String filenameStr,filenameStr1,id1;
    //Storage
    StorageReference storageReference;
    UploadTask uploadTask;
    FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_data);

        floatingActionButton = findViewById(R.id.addnotebtn);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);

progressDialog.dismiss();





imgages=findViewById(R.id.recyclrimg);
        recyclerView = findViewById(R.id.recyclerid);

        firebaseFirestore = FirebaseFirestore.getInstance();
        modelClassArrayList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterClass = new AdapterClass(modelClassArrayList, this);
        recyclerView.setAdapter(adapterClass);
        loadPhotos();

        //Reading from firebase to recyclerview

//        firebaseFirestore.collection("FbNotes").get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
//                        if (!queryDocumentSnapshots.isEmpty()) {
////                            loadingPB.setVisibility(View.GONE);
//                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                            for (DocumentSnapshot d : list) {
//
//                                ModelClass c = d.toObject(ModelClass.class);
//                                c.setId(d.getId());
//                                modelClassArrayList.add(c);
//                            }
//
//                            adapterClass.notifyDataSetChanged();
//                        } else {
//
//                            Toast.makeText(AddingData_Activity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                        Toast.makeText(AddingData_Activity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
    }

    private void loadPhotos() {
        try {
            progressDialog.show();
            progressDialog.setMessage("Loading...");
            firebaseFirestore.collection("ODSIMAGES").get().addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(AddingData_Activity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    progressDialog.dismiss();
                    if(!task.isSuccessful())
                    {
                        Toast.makeText(AddingData_Activity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        for(QueryDocumentSnapshot qb:task.getResult())
                        {
                            String tname=qb.getString("title1");
                            String  dname=qb.getString("desc1");
                            String url=qb.getString("photoUrl");
    //                        String id=qb.getString("id");

                            ModelClass modelClass=new ModelClass(tname,dname,url);

                            modelClassArrayList.add(modelClass);
                            adapterClass.notifyDataSetChanged();
                        }


                    }


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//////
    private void uploadImage() {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference.child("images/" + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast.makeText(AddingData_Activity.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast.makeText(AddingData_Activity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                                double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + (int)progress + "%");

                        }
                    });
        }
    }


    private void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    ////////////////////////////////////
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        super.onActivityResult(requestCode,
//                resultCode,
//                data);
//
//        if (requestCode == PICK_IMAGE_REQUEST
//                && resultCode == RESULT_OK
//                && data != null
//                && data.getData() != null) {
//
//            // Get the Uri of data
//            filePath = data.getData();
//            try {
//
//                // Setting image on image view using Bitmap
//                Bitmap bitmap = MediaStore.Images.Media
//                        .getBitmap(getContentResolver(), filePath);
//                imageView.setImageBitmap(bitmap);
//            }
//
//            catch (IOException e) {
//                // Log the exception
//                e.printStackTrace();
//            }
//        }
//    }

    public void showAddNote(View view) {


        // Create an alert builder
        AlertDialog.Builder builder
                = new AlertDialog.Builder(this);
//                builder.setTitle("Notes");

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(
                R.layout.custom_add_dialog,
                null);
        builder.setView(customLayout);


        ///////////////
        firebaseStorage=FirebaseStorage.getInstance();
        //Storage location Created in Firebase Storage
        storageReference=firebaseStorage.getReference("ODS");

        firebaseFirestore=FirebaseFirestore.getInstance();

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);

//        findViewById(R.id.chooseid).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pickImageFromGallery();
//            }
//        });

//        findViewById(R.id.uploadid).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                uploadtoStorage();
//            }
//        });


        Button save = customLayout.findViewById(R.id.saveadd);
        titleedt = customLayout.findViewById(R.id.titleadd);
        descedt = customLayout.findViewById(R.id.descadd);


        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0F9D58"));
        actionBar.setBackgroundDrawable(colorDrawable);

        btnSelect =customLayout.findViewById(R.id.chooseid);
        setImage=customLayout.findViewById(R.id.addimageid);
//        btnUpload =customLayout.findViewById(R.id.uploadid);
       // imageView =customLayout.findViewById(R.id.addimageid);

        // get the Firebase  storage reference
//        storage = FirebaseStorage.getInstance();
//        storageReference = storage.getReference();

      btnSelect.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
             // SelectImage();

              pickImageFromGallery();
          }
      });
//
//        btnUpload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                uploadImage();
//            }
//        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // getting data from edittext fields.
                titlestr = titleedt.getText().toString();
                descstr = descedt.getText().toString();


                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(titlestr)) {
                    titleedt.setError("Please enter Course Name");
                } else if (TextUtils.isEmpty(descstr)) {
                    descedt.setError("Please enter Course Description");
                } else {
                    // calling method to add data to Firebase Firestore.
                   // addDataToFirestore(titlestr, descstr);

                   uploadtoStorage();
                }
            }
        });
        AlertDialog dialog
                = builder.create();
        dialog.show();

    }

    private void uploadtoStorage() {
        try {
            progressDialog.show();
            progressDialog.setMessage("Image Uploading...");

        filenameStr=titleedt.getText().toString();
        filenameStr1=descedt.getText().toString();

            StorageReference riversRef = storageReference.child(uri.getLastPathSegment());
            uploadTask = riversRef.putFile(uri);

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    progressDialog.dismiss();
                    Toast.makeText(AddingData_Activity.this, ""+exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    progressDialog.dismiss();

                    downloadURL(uploadTask,riversRef);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void downloadURL(UploadTask uploadTask, StorageReference riversRef) {

        try {
            progressDialog.show();
            progressDialog.setMessage("downloading url..");

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    progressDialog.dismiss();
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return riversRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    progressDialog.dismiss();

                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String myurl=downloadUri.toString();
                        cloudFireStore(myurl);

                    } else {
                        // Handle failures
                        // ...
                        Toast.makeText(AddingData_Activity.this, "No url found", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cloudFireStore(String myurl) {

        try {
            progressDialog.show();
            progressDialog.setMessage("Saving Image almost Done");

            Map<String,Object> map=new HashMap<>();
            map.put("photoUrl",myurl);
            map.put("title1",filenameStr);
            map.put("desc1",filenameStr1);
//map.put("id",null);
//

            firebaseFirestore.collection("ODSIMAGES").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    progressDialog.dismiss();

                    Toast.makeText(AddingData_Activity.this, "Upload Success", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(AddingData_Activity.this, "Failed to upload"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pickImageFromGallery() {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,202);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 202:
                if(resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
                {
                    uri=data.getData();
                    setImage.setImageURI(uri);
                    //Log.d("Name0",""+uri);

                    //path.setText("Select Path is "+uri);
                    // getfileName(uri);
                }
                else
                {
                    Toast.makeText(this, "File not Choose", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

/////////////////////////
//    private void addDataToFirestore(String titlestr, String descstr) {
//
//        CollectionReference dbCourses = firebaseFirestore.collection("FbNotes");
//        ModelClass courses = new ModelClass(titlestr, descstr);
//        dbCourses.add(courses).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//
//                Toast.makeText(AddingData_Activity.this, "Your Data has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//                Toast.makeText(AddingData_Activity.this, "Fail to add Data \n" + e, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//    }

//            private void sendDialogDataToActivity(String titleadd, String descadd)
//            {
//                Toast.makeText(this, titleadd, Toast.LENGTH_SHORT).show();
//                Toast.makeText(this, descadd, Toast.LENGTH_SHORT).show();
//           }
//        }


}