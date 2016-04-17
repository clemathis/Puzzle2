package aga.puzzle2;


import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Playground extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playground);
        // private String selectedImagePath;
        TextView nickname_tip = (TextView) findViewById(R.id.name);
        //This gets the elements from the New game page and sets it in the Playground page. Note: This will set the number of splits to 9 by default, if the user does not select the radio button.
        nickname_tip.setText(getIntent().getStringExtra("name") + " " + getIntent().getStringExtra("numberOfSplits")  + " "+getIntent().getStringExtra("flag"));
        if((getIntent().getStringExtra("flag")).equals("1")) {

//            Uri targetUri = Uri.parse(getIntent().getStringExtra("imageURI"));
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), targetUri);
//
            ImageView imageView = (ImageView) findViewById(R.id.targetImage);
            Log.v("RESULT", "******************Ta-Da*****************"+getIntent().getStringExtra("imageURI"));

//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        }
        else{
            ImageView imageView = (ImageView) findViewById(R.id.targetImage);
            imageView.setImageResource(R.drawable.default_photo);
        }
    }
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }



}
