package com.joskoding.beshoot;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    PCD pcd=new PCD();
    Bitmap bitmap,tmpbmp,bmpdefault;
    ImageButton btngray,btnbinner,btnnegative,btnbrightness,btnsharpen,btnedge,btnblur,btnidentity,btnemboss,btnedgeenhance,btngaussian,btncancel,btndefault,btnflip,btnflipvertical;
    Button btnpopupmenu;
    SeekBar sbpopupmenu;
    ImageView ivshow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        checkUpdate();
        //Untuk Kamera
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Untuk Cameranya, Menggunakan Catch Jika Nanti Terdapat Kesalahan
                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 0);
                }catch (Exception e){

                }
            }
        });
    }

    //Untuk Inisialisasi Komponen
    private void init() {
        ivshow=(ImageView)findViewById(R.id.ivshow);
        btngray=(ImageButton)findViewById(R.id.btngray);
        btnbinner=(ImageButton)findViewById(R.id.btnbinner);
        btnnegative=(ImageButton)findViewById(R.id.btnnegative);
        btnbrightness=(ImageButton)findViewById(R.id.btnbrightness);
        btnedgeenhance=(ImageButton)findViewById(R.id.btnedgeenhancefilter);
        btnedge=(ImageButton)findViewById(R.id.btnedge);
        btnblur=(ImageButton)findViewById(R.id.btnblur);
        btnemboss=(ImageButton)findViewById(R.id.btnemboss);
        btnidentity=(ImageButton)findViewById(R.id.btnidentity);
        btngaussian=(ImageButton)findViewById(R.id.btngaussian);
        btncancel=(ImageButton)findViewById(R.id.btncancel);
        btndefault=(ImageButton)findViewById(R.id.btndefault);
        btnsharpen=(ImageButton)findViewById(R.id.btnsharpen);
        btnflip=(ImageButton)findViewById(R.id.btnflip);
        btnflipvertical=(ImageButton)findViewById(R.id.btnflipvertical);
    }
    //Untuk Check Update, Cancel, Default
    private void checkUpdate(){
        BitmapDrawable bitmapDrawable=(BitmapDrawable)ivshow.getDrawable();
        bitmap=bitmapDrawable.getBitmap();
        tmpbmp=bitmap;
    }

    public void cancelEffect(View view){
        try{
            ivshow.setImageBitmap(tmpbmp);
            checkUpdate();
            Toast.makeText(MainActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }
    }
    public void defaultEffect(View view){
        try{
            ivshow.setImageBitmap(bmpdefault);
            checkUpdate();
            Toast.makeText(MainActivity.this, "Default Images", Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }
    }
    //Fungsi Pemanggilan efek-efek
    // Untuk Gray
    public void gray(View view){
        try{
            checkUpdate();
            ivshow.setImageBitmap(pcd.toGrayscale(bitmap));
            Toast.makeText(MainActivity.this, "Grayscale Added Successfully", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            
        }
    }
    // Untuk Binner
    public void binner(View view){
        try {
            checkUpdate();
            ivshow.setImageBitmap(pcd.toBinner(bitmap));
            Toast.makeText(MainActivity.this, "Binner Added Successfully", Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }
    }
    //Untuk Negative
    public void negative(View view){
        try{
            checkUpdate();
            ivshow.setImageBitmap(pcd.toNegative(bitmap));
            Toast.makeText(MainActivity.this, "Negative Added Successfully", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            
        }
    }
    //Untuk Vertical Horizontal
    public void flip(View view){
        try{
            checkUpdate();
            ivshow.setImageBitmap(pcd.toFlip(bitmap,2));
            Toast.makeText(MainActivity.this, "Flip Horizontal Successfully", Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }
    }
    public void flipvertical(View view){
        try{
            checkUpdate();
            ivshow.setImageBitmap(pcd.toFlip(bitmap,1));
            Toast.makeText(MainActivity.this, "Flip Vertical Successfully", Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }
    }
    //Untuk Sharpen Filter
    public void sharpen(View view){
        try{
            checkUpdate();
            ivshow.setImageBitmap(pcd.toSharpen(bitmap));
            Toast.makeText(MainActivity.this, "Sharpen Added Successfully", Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }
    }
    //Untuk Blur Filter
    public void blur(View view){
        try{
            checkUpdate();
            ivshow.setImageBitmap(pcd.toBlur(bitmap));
            Toast.makeText(MainActivity.this, "Blur Added Successfully", Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }
    }
    //Untuk Edge Filter
    public void edge(View view){
        try{
            checkUpdate();
            ivshow.setImageBitmap(pcd.toEdgeFilter(bitmap));
            Toast.makeText(MainActivity.this, "Edge Filter Added Successfully", Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }
    }
    //Untuk Identity
    public void identity(View view){
        try{
            checkUpdate();
            ivshow.setImageBitmap(pcd.toIdentityFilter(bitmap));
            Toast.makeText(MainActivity.this, "Identity Filter Added Successfully", Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }
    }
    //Untuk Edge Enhance Filter
    public void edgeenhance(View view){
        try{
            checkUpdate();
            ivshow.setImageBitmap(pcd.toEdgeEnhanceFilter(bitmap));
            Toast.makeText(MainActivity.this, "Edge Enhance Added Successfully", Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }
    }
    // Untuk Emboss
    public void emboss(View view){
        try{
            checkUpdate();
            ivshow.setImageBitmap(pcd.toEmboss(bitmap));
            Toast.makeText(MainActivity.this, "Emboss Added Successfully", Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }
    }
    // Untuk Gaussian
    public void gaussian(View view){
        try{
            checkUpdate();
            ivshow.setImageBitmap(pcd.toGaussian(bitmap));
            Toast.makeText(MainActivity.this, "Gaussian Added Successfully", Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }
    }
    public void brightness(View view){
        checkUpdate();
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Configure Information");
        alert.setMessage("Change and Seek For Brightness");
        LinearLayout linear=new LinearLayout(this);
        linear.setOrientation(LinearLayout.VERTICAL);
        TextView text=new TextView(this);
        text.setPadding(10, 10, 10, 10);
        final SeekBar seek=new SeekBar(this);
        linear.addView(seek);
        linear.addView(text);
        alert.setView(linear);
        final Bitmap[] temp = new Bitmap[1];
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int brightvalue;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                brightvalue = progress;
                temp[0] =pcd.toBrightness(bitmap, brightvalue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                checkUpdate();
            }
        });
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ivshow.setImageBitmap(temp[0]);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getApplicationContext(), "Brightness Canceled", Toast.LENGTH_LONG).show();
                alert.create().dismiss();
            }
        });
        alert.show();
    }
    public void contrast(View view){
        checkUpdate();
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Configure Information");
        alert.setMessage("Change and Seek For Contrast Image");
        LinearLayout linear=new LinearLayout(this);
        linear.setOrientation(LinearLayout.VERTICAL);
        TextView text=new TextView(this);
        text.setPadding(10, 10, 10, 10);
        final SeekBar seek=new SeekBar(this);
        linear.addView(seek);
        linear.addView(text);
        alert.setView(linear);
        final Bitmap[] temp = new Bitmap[1];
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int contrastValue;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                contrastValue = progress;
                temp[0] =pcd.toContrast(bitmap, contrastValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                checkUpdate();
            }
        });
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ivshow.setImageBitmap(temp[0]);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getApplicationContext(), "Rotate Canceled", Toast.LENGTH_LONG).show();
                alert.create().dismiss();
            }
        });
        alert.show();
    }
    public void rotate(View view){
        checkUpdate();
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Configure Information");
        alert.setMessage("Change and Seek For Rotate Image");
        LinearLayout linear=new LinearLayout(this);
        linear.setOrientation(LinearLayout.VERTICAL);
        TextView text=new TextView(this);
        text.setPadding(10, 10, 10, 10);
        final SeekBar seek=new SeekBar(this);
        linear.addView(seek);
        linear.addView(text);
        alert.setView(linear);
        final Bitmap[] temp = new Bitmap[1];
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int rotateValue;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rotateValue = (int)(progress*3.6);
                temp[0] =pcd.toRotate(bitmap, rotateValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                checkUpdate();
            }
        });
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ivshow.setImageBitmap(temp[0]);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getApplicationContext(), "Rotate Canceled", Toast.LENGTH_LONG).show();
                alert.create().dismiss();
            }
        });
        alert.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            Bitmap bp=(Bitmap)data.getExtras().get("data");
            ivshow.setImageBitmap(bp);
            // Membuat Default
            bmpdefault=bp;
        }catch (Exception e){
            
        }
        // Ini Untuk Choose Gambar
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null && data.getData() != null){
            Uri uri=data.getData();
            try{
                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ivshow.setImageBitmap(bitmap);
                // Membuat Defualt
                bmpdefault=bitmap;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemAboutUs:
                break;
            case R.id.itemChoose:
                Intent chooseImage=new Intent();
                chooseImage.setType("image/*");
                chooseImage.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(chooseImage,"Select Picture"),PICK_IMAGE_REQUEST);
                break;
            case R.id.itemSaveImages:
                try{
                    checkUpdate();
                    MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "PCD","Gambar PCD Disimpan");
                    Toast.makeText(MainActivity.this, "Save Picture, Success", Toast.LENGTH_SHORT).show();
                }catch (Exception e){

                }

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
