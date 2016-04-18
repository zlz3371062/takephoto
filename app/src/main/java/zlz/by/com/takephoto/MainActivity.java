package zlz.by.com.takephoto;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URI;

public class MainActivity extends Activity {

    private TextView take,updata,get;
    private ImageView img;
    private   File dir,file ,file2;
    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       take = (TextView) findViewById(R.id.tokephoto);
        get = (TextView) findViewById(R.id.get);
        get.setOnClickListener(new onlick());
        take.setOnClickListener(new onlick());
        updata = (TextView) findViewById(R.id.updataphoto);
        updata.setOnClickListener(new onlick());
        img = (ImageView) findViewById(R.id.img);
        img.setOnClickListener(new onlick());
        dir = Environment.getExternalStorageDirectory();
        path = dir.getPath()+ "/zlz/";

        file = new File(path);
        if(!file.exists())
            file.mkdir();


        file2 = new File(path, "temp.jpg");
        file2.delete();
    }


       private class  onlick implements View.OnClickListener{


           @Override
          public void onClick(View v) {
          switch (v.getId()) {
              case  R.id.tokephoto:

              takephoto();
                  break;
              case  R.id.get:
                    get();
                  break;
              case  R.id.img:
                  break;
          }
           }
        }

    private void takephoto(){
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        startActivityForResult(intent, 0);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file2));
        startActivityForResult(intent, 0);

    }

//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 0 && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            img.setImageBitmap(imageBitmap);
//        }
//    }
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    Log.e("zlz", requestCode + "");
    if (requestCode == 0 ) {
        setPic();
    }
    else  if(requestCode == 1)
    {
        try {

            Bitmap map = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
            FileOutputStream out = new FileOutputStream(file2);
            map.compress(Bitmap.CompressFormat.JPEG,50,out);
            Uri uri = Uri.fromFile(file2);
            startImage(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }






    }else if( requestCode == 2){
         Bundle zlz = data.getExtras();
         Bitmap bitmap = zlz.getParcelable("data");
         img.setImageBitmap(bitmap);



    }




}

    private void setPic() {
        // Get the dimensions of the View
//        int targetW = img.getWidth();
//        int targetH = img.getHeight();

        // Get the dimensions of the bitmap


        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file2.getPath(), bmOptions);
//        int photoW = bmOptions.outWidth;
//        int photoH = bmOptions.outHeight;
//
//        // Determine how much to scale down the image
//        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
//
//        // Decode the image file into a Bitmap sized to fill the View
//        bmOptions.inJustDecodeBounds = false;
//
//        bmOptions.inSampleSize = scaleFactor;
//        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(file2.getPath(), bmOptions);
        Uri uri = Uri.fromFile(file2);
        startImage(uri);
    }
  private void  upload(){



  }

  private void get(){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        i.putExtra("return-data", true);
        startActivityForResult(i,1);

        }

   private void  startImage(Uri uri){
        Intent  intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY",1);
        intent.putExtra("outputX",150);
        intent.putExtra("outputY",150);
        intent.putExtra("return-data",true);
        startActivityForResult(intent,2);

   }



}
