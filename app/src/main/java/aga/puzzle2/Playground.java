package aga.puzzle2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Playground extends Activity {

    int SCORE = 500;
    int MOVES = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playground);

        // private String selectedImagePath;
        TextView nickname_tip = (TextView) findViewById(R.id.name);
        //This gets the elements from the New game page and sets it in the Playground page.
        // Note: This will set the number of splits to 9 by default, if the user does not select the radio button.
        final int splits = Integer.parseInt(getIntent().getStringExtra("numberOfSplits"));
        nickname_tip.setText(getIntent().getStringExtra("name")+", welcome!");
        final ArrayList<Bitmap> numberOfSplits = splitImage(splits);
        Bitmap lastImagePiece = numberOfSplits.get(numberOfSplits.size() - 1);
        //numberOfSplits.remove(numberOfSplits.size()-1);

        //Shuffeling
        lastImagePiece.eraseColor(android.graphics.Color.BLACK);
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        final Bitmap bmp = Bitmap.createBitmap(lastImagePiece.getWidth(), lastImagePiece.getHeight(), conf);
        numberOfSplits.set(numberOfSplits.size() - 1, bmp);
        Collections.shuffle(numberOfSplits);

        final GridView grid = (GridView) findViewById(R.id.gridview);
        final ImageAdapter SIA = new ImageAdapter(this, numberOfSplits);
        int changedPosition = 0;
        grid.setAdapter(SIA);
        grid.setNumColumns((int) Math.sqrt(numberOfSplits.size()));
        grid.setHorizontalSpacing(0);
        grid.setVerticalSpacing(0);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                int up = position - 3;
                int down = position + 3;
                int left = position - 1;
                int right = position + 1;
                ImageAdapter ia = (ImageAdapter) parent.getAdapter();
                ImageView imageView = (ImageView) v;

                if (up >= 0 && ia.getItem(up).equals(bmp)) {

                    //       ((ImageView) parent.getItemAtPosition(up)).setImageResource(SIA.getItemViewType(position));
                    imageView.setImageResource(SIA.getItemViewType(up));

                } else if (down <= (splits - 1) && ia.getItem(down).equals(bmp)) {

//                    ((ImageView) parent.getItemAtPosition(down)).setImageResource(SIA.getItemViewType(position));
                    imageView.setImageResource(SIA.getItemViewType(down));

                } else if (left >= 0 && ia.getItem(left).equals(bmp)) {

//                    ((ImageView) parent.getItemAtPosition(left)).setImageResource(SIA.getItemViewType(position));
                    imageView.setImageResource(SIA.getItemViewType(left));

                } else if (right <= (splits - 1) && ia.getItem(right).equals(bmp)) {

//                    ((ImageView) parent.getItemAtPosition(right)).setImageResource(SIA.getItemViewType(position));
                    imageView.setImageResource(SIA.getItemViewType(right));

                }
                Toast.makeText(getApplicationContext(),
                        ((position + 1) + " " + (position - 1) + " " + position),
                        Toast.LENGTH_LONG).show();
            }
        });

        Button submit = (Button) findViewById(R.id.finish_button);
        submit.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {
                                          Intent intent = new Intent(v.getContext(), FinishGame.class);
                                          ArrayList validationList = new ArrayList();
                                          Collections.copy(numberOfSplits, validationList);
                                          Collections.sort(validationList);
                                          if (numberOfSplits.equals(validationList))
                                          startActivityForResult(intent, 0);
                                          else {
                                              AlertDialog.Builder alert = new AlertDialog.Builder(Playground.this);
                                              alert.setTitle("Unfortunately!");
                                              alert.setMessage("Splits are not in the correct order.");
                                              alert.setPositiveButton("OK", null);
                                              alert.show();
                                          }
                                      }
                                  }
        );

    }
    private ArrayList<Bitmap> splitImage(int NumberOfSplits) {

        //For the number of rows and columns of the grid to be displayed
        int rows,cols;
        //For height and width of the small image chunks
        int chunkHeight,chunkWidth;

        //To store all the small image chunks in bitmap format in this list
        ArrayList<Bitmap> chunkedImages = new ArrayList<Bitmap>(NumberOfSplits);

        //Getting the scaled bitmap of the source image
        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.default_photo);
        Bitmap bitmap = drawable.getBitmap();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        rows = cols = (int) Math.sqrt(NumberOfSplits);
        chunkHeight = bitmap.getHeight()/rows;
        chunkWidth = bitmap.getWidth()/cols;
        //xCoord and yCoord are the pixel positions of the image chunks
        int yCoord = 0;
        for(int x=0; x<rows; x++){
            int xCoord = 0;
            for(int y=0; y<cols; y++){
                chunkedImages.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight));
                xCoord += chunkWidth;
            }
            yCoord += chunkHeight;
        }
        return chunkedImages;


    }

    //    public moves OnClinck(grid){
//        MOVES ++;
//    }
    public void calculateScore(int MOVES){

        SCORE = SCORE -(MOVES *10);
    }



}