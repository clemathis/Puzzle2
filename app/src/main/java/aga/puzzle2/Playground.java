package aga.puzzle2;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.GridView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Playground extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playground);
        Button startgame = (Button) findViewById(R.id.startgame_button);
        Button validate = (Button) findViewById(R.id.finish_button);
        // private String selectedImagePath;
        TextView nickname_tip = (TextView) findViewById(R.id.name);
        //This gets the elements from the New game page and sets it in the Playground page. Note: This will set the number of splits to 9 by default, if the user does not select the radio button.
        nickname_tip.setText(getIntent().getStringExtra("name"));
        if ((getIntent().getStringExtra("flag")).equals("1")) {

//            Uri targetUri = Uri.parse(getIntent().getStringExtra("imageURI"));
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), targetUri);
//
            ImageView imageView = (ImageView) findViewById(R.id.targetImage);
            Log.v("RESULT", "******************Ta-Da*****************" + getIntent().getStringExtra("imageURI"));

//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        } else {
            ImageView imageView = (ImageView) findViewById(R.id.targetImage);
            imageView.setImageResource(R.drawable.default_photo);
        }
    }

    //Splitting the image

    public void OnClick(View view) {
        int NumberOfSplits = Integer.parseInt((getIntent().getStringExtra("numberOfSplits").replaceAll("[\\D]", "")));
        //Getting the source image to split
        ImageView image = (ImageView) findViewById(R.id.targetImage);
        //invoking method to split the source image
        splitImage(image, NumberOfSplits);
    }

    private void splitImage(ImageView image, int NumberOfSplits) {

        //For the number of rows and columns of the grid to be displayed
        int rows, cols;
        //For height and width of the small image chunks
        int chunkHeight, chunkWidth;

        //To store all the small image chunks in bitmap format in this list
        final ArrayList<Bitmap> chunkedImages = new ArrayList<Bitmap>(NumberOfSplits);

        //Getting the scaled bitmap of the source image
        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        rows = cols = (int) Math.sqrt(NumberOfSplits);
        chunkHeight = bitmap.getHeight() / rows;
        chunkWidth = bitmap.getWidth() / cols;
        //xCoord and yCoord are the pixel positions of the image chunks
        int yCoord = 0;
        for (int x = 0; x < rows; x++) {
            int xCoord = 0;
            for (int y = 0; y < cols; y++) {
                chunkedImages.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight));
                xCoord += chunkWidth;
            }
            yCoord += chunkHeight;
        }

        /* Now the chunkedImages has all the small image chunks in the form of Bitmap class.
         * You can do what ever you want with this chunkedImages as per your requirement.
         * I pass it to a new Activity to show all small chunks in a grid for demo.
         * You can get the source code of this activity from my Google Drive Account.
         */

        //Start a new activity to show these chunks into a grid
        Button startgame = (Button) findViewById(R.id.startgame_button);
        startgame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Playground.this,Playground.class);
                intent.putParcelableArrayListExtra("image chunks", chunkedImages);
                //but how to display it in the place of Image View id targetImage?
                startActivity(intent);
            }


        });
    }
}