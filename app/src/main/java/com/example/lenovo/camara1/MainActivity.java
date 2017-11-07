package com.example.lenovo.camara1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CAPTURE=1;
    ImageView result_photo;
    Intent j;
    Uri uriSavedImage;
    String namepath="Trabajo";
    String filenamed;
    //ImageView preview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button click = (Button)findViewById(R.id.Bcapture);
        result_photo = (ImageView)findViewById(R.id.imageView);

        if(!hasCamera())
        {
            click.setEnabled(false);
        }
    }

    public boolean hasCamera()
    {
       return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    public void launchCamera(View v)
    {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Intent j = i;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "ImagenesME/"+namepath);
        imagesFolder.mkdirs();
        filenamed = "IME_"+timeStamp+".jpg";
        File image=new File(imagesFolder, filenamed);
        Toast t = Toast.makeText(this, image.getPath(), Toast.LENGTH_LONG);
        uriSavedImage = Uri.fromFile(image);


        j.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        startActivityForResult(i, REQUEST_CAPTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CAPTURE && resultCode == RESULT_OK)
        {

            //Bundle extras = data.getExtras();
            //Bitmap photo = (Bitmap) extras.get("data");
            //result_photo.setImageBitmap(photo);
            Bitmap pic = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()+"/ImagenesME/"+namepath+"/"+filenamed);
            result_photo.setImageBitmap(pic);
            Toast.makeText(this, "Fotografia guardada", Toast.LENGTH_LONG).show();
        }
    }
    public void  ImagenOpen(View view){
        Intent imageviewer = new Intent(Intent.ACTION_VIEW, Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath()+"/ImagenesME/"+namepath+"/"+filenamed));
        startActivity(imageviewer);
    }
    public void  onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.radioButton:
                if (checked)
                     namepath = "Trabajo";
                break;
            case R.id.radioButton2:
                if (checked)
                    namepath ="Escuela";
                break;
            case R.id.radioButton3:
                namepath="Familia";
                break;
        }
    }
}
