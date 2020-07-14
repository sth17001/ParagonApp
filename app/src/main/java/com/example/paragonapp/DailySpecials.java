package com.example.paragonapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;


public class DailySpecials extends AppCompatActivity {
    //BUTTONS
Button dailyButton;
Button weeklyButton;
Button editDailySpecial;
Button editWeeklySpecial;
Button uploadButton;
Boolean isAdmin;

    //PHOTOVIEW is similar to image but is able to zoom
ImageView dailyImage, logoImage, weeklyImage;
String generatedFilePath;
LinearLayout dailyLayout;
PhotoView paragonweekly;
private ImageView mImageView;
private ProgressBar weeklyProgressBar;
private Uri weeklyImageUrl;
private StorageReference weeklyStorageRef;
private DatabaseReference weeklyDataBaseRef;
private EditText weeklyEditTextFileName;
private StorageTask mUploadTask;

private static final int PICK_IMAGE_REQUEST  = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_specials);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            isAdmin = extras.getBoolean("isAdmin");
        }
        //Uploading for fireBase
        weeklyStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        weeklyDataBaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        weeklyStorageRef.child("weekly.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                final Uri url = uri;
                generatedFilePath = url.getPath();
                System.out.println(generatedFilePath + "=================================================================");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });




        weeklyImage = (ImageView) findViewById(R.id.weeklyPic);
        dailyImage = (ImageView) findViewById(R.id.dailyPic);
        

        Glide.with(DailySpecials.this)
                .load("https://lh5.googleusercontent.com/K7h8DlaT_wqzGcTFucNludBe0KkilIvc92hrKv4ptFLIt8RpBslwMY8Lj6n2u3xzA3gBo_9DYACX74rOJvmoV7WS1-lbnZgcquFdJvZW3zD3HAbY_KCA24mOzGRoqu7cYCADuoY5")
                .into(weeklyImage);

        logoImage = findViewById(R.id.logo);

        //Buttons for Displaying Daily/Weekly specials
        dailyButton = (Button)findViewById(R.id.dailyButton);
        weeklyButton = (Button)findViewById(R.id.weeklyButton);

        // Buttons for Editing Daily/Weekly Specials
        editDailySpecial = (Button)findViewById(R.id.editDailySpecial);
        editWeeklySpecial = (Button)findViewById(R.id.editWeeklySpecial);

        

        uploadButton = (Button) findViewById(R.id.uploadButton);
        if(isAdmin == true){
            uploadButton.setVisibility(View.VISIBLE);
            editWeeklySpecial.setVisibility(View.GONE);
            editDailySpecial.setVisibility(View.VISIBLE);
            logoImage.setVisibility(View.GONE);
        } else {
            uploadButton.setVisibility(View.GONE);
            editWeeklySpecial.setVisibility(View.GONE);
            editDailySpecial.setVisibility(View.GONE);
        }


        //LinearLayout
        dailyLayout = findViewById(R.id.dailyLayout);

        // Images for daily and weekly specials
        dailyImage = (ImageView) findViewById(R.id.dailyPic);
        paragonweekly = (PhotoView) findViewById(R.id.weeklyPic);

        //photoStorage = addListenerForSingleValueEvent(New Value)

        // Sets the Daily Special Photo visible and Weekly to Gone to show the Daily Special (Current)
        dailyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            dailyLayout.setVisibility(View.VISIBLE);
            paragonweekly.setVisibility(View.GONE);
                if(isAdmin == true){
                    uploadButton.setVisibility(View.VISIBLE);
                    editWeeklySpecial.setVisibility(View.GONE);
                    editDailySpecial.setVisibility(View.VISIBLE);
                    logoImage.setVisibility(View.GONE);
                } else {
                    uploadButton.setVisibility(View.GONE);
                    editWeeklySpecial.setVisibility(View.GONE);
                    editDailySpecial.setVisibility(View.GONE);
                    logoImage.setVisibility(View.VISIBLE);
                }
                // Code here executes on main thread after user presses button
            }
        });
        //
        weeklyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                dailyLayout.setVisibility(View.GONE);
                paragonweekly.setVisibility(View.VISIBLE);
                if(isAdmin == true){
                    uploadButton.setVisibility(View.VISIBLE);
                    editWeeklySpecial.setVisibility(View.VISIBLE);
                    editDailySpecial.setVisibility(View.GONE);
                    logoImage.setVisibility(View.GONE);
                } else {
                    uploadButton.setVisibility(View.GONE);
                    editWeeklySpecial.setVisibility(View.GONE);
                    editDailySpecial.setVisibility(View.GONE);
                    logoImage.setVisibility(View.VISIBLE);
                }
                // Code here executes on main thread after user presses button
            }
        });
        // Button for selecting the image for the Weekly special
        editWeeklySpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFileChooser();
                //uploadFile();  add this in later

            }
        });

        // Button for uploading the Weekly Special image
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(DailySpecials.this, "Uploading Image", Toast.LENGTH_LONG).show();
                } else {
                    Fileuploader();
                }
            }

        });

    }

    private String getExtension(Uri uri){

        ContentResolver cR = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cR.getType(uri));
    }
    // This is the better one that we use
    private void Fileuploader() {
        if(weeklyImageUrl != null) {
            final StorageReference Ref = weeklyStorageRef.child("weekly" + "." + getExtension(weeklyImageUrl));
            mUploadTask = Ref.putFile(weeklyImageUrl)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(DailySpecials.this, "Upload Complete", Toast.LENGTH_LONG).show();

                            //Download a file
                            File localFile = null;
                            try {
                                localFile = File.createTempFile("images", "jpg");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Ref.getFile(localFile)
                                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                            // Successfully downloaded data to local file
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle failed download
                                    // ...
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });
        }
        else{
                Toast.makeText(this, "No File Selected", Toast.LENGTH_SHORT).show();
        }
    }

    //When this is called then it allows the user to select a image
    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            weeklyImageUrl = data.getData(); // Get the data
            paragonweekly.setImageURI(weeklyImageUrl); // Set the image to the data that was gotten
           // Picasso.with(this).load(weeklyImageUrl).into(paragonweekly);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    // We dont use this one anymore we made a better one
    private void uploadFile()
    {
        if(weeklyImageUrl != null){
            StorageReference fileReference = weeklyStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(weeklyImageUrl));

            mUploadTask = fileReference.putFile(weeklyImageUrl)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    weeklyProgressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(DailySpecials.this, "Upload succesful", Toast.LENGTH_LONG).show();
                            PhotoUpload upload = new PhotoUpload(weeklyEditTextFileName.getText().toString().trim(),
                                    taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                            String weeklyUploadId = weeklyDataBaseRef.push().getKey();
                            weeklyDataBaseRef.child(weeklyUploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(DailySpecials.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            weeklyProgressBar.setProgress((int) progress);
                        }
                    });
        } else{
            Toast.makeText(this, "No File Selected", Toast.LENGTH_SHORT).show();
        }
    }

}