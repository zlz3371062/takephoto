package zlz.by.com.takephoto;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

public class MainActivity extends Activity {

    private TextView take,updata;
    private ImageView img;
    private   File file ,file2;
    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       take = (TextView) findViewById(R.id.tokephoto);
        take.setOnClickListener(new onlick());
        updata = (TextView) findViewById(R.id.updataphoto);
        updata.setOnClickListener(new onlick());
        img = (ImageView) findViewById(R.id.img);
        File dir = Environment.getExternalStorageDirectory();
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
          takephoto();
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
    Log.e("zlz",requestCode + "");
    if (requestCode == 0 ) {
        setPic();
    }
}

    private void setPic() {
        // Get the dimensions of the View
        int targetW = img.getWidth();
        int targetH = img.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file2.getPath(), bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;

        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(file2.getPath(), bmOptions);
        if(bitmap == null){

            Log.e("zlz", "zkon");

        }else {
            img.setImageBitmap(bitmap);
        }
    }


}
