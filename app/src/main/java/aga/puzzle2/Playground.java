package aga.puzzle2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Playground extends Activity {

    int SCORE = 500;
    int MOVES = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playground);
        final LinearLayout thisLayout = new LinearLayout(this);
        String number_of_score = Integer.toString(SCORE);
        final TextView nr_of_score = (TextView) findViewById(R.id.nr_of_scores);
        nr_of_score.setText(number_of_score);
        // private String selectedImagePath;
        TextView nickname_tip = (TextView) findViewById(R.id.name);
        //This gets the elements from the New game page and sets it in the Playground page.
        // Note: This will set the number of splits to 9 by default, if the user does not select the radio button.
        final int splits = Integer.parseInt(getIntent().getStringExtra("numberOfSplits"));
        nickname_tip.setText(getIntent().getStringExtra("name")+", welcome!");
        final ArrayList<Bitmap> numberOfSplits = splitImage(splits);
        Bitmap lastImagePiece = numberOfSplits.get(numberOfSplits.size() - 1);

        Bitmap.Config conf = Bitmap.Config.RGB_565; // see other conf types
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

            @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                ImageAdapter ia = (ImageAdapter) parent.getAdapter();
                ImageView imageView = (ImageView) v;
                if ((position - 3) >= 0 && numberOfSplits.get((position - 3)).sameAs(bmp)) {
                    Log.v("Executed ------->", "up");
                    ImageView ivUp = (ImageView) parent.getChildAt(position - 3);
                    ivUp.setImageBitmap(numberOfSplits.get(position));
                    imageView.setImageBitmap(numberOfSplits.get(position-3));
//                    ia.notifyDataSetChanged();


                } else if ((position + 3) <= (splits - 1) && numberOfSplits.get((position + 3)).sameAs(bmp)) {
                    Log.v("Executed ------->", "down");
                    ImageView ivUp = (ImageView) parent.getChildAt(position + 3);
                    ivUp.setImageBitmap(numberOfSplits.get(position));
                    imageView.setImageBitmap(numberOfSplits.get(position + 3));
//                    ia.notifyDataSetChanged();

                } else if ((position - 1) >= 0 && numberOfSplits.get((position - 1)).sameAs(bmp)) {
                    Log.v("Executed ------->", "left");
                    ImageView ivUp = (ImageView) parent.getChildAt(position - 1);
                    ivUp.setImageBitmap(numberOfSplits.get(position));
                    imageView.setImageBitmap(numberOfSplits.get(position - 1));
//                    ia.notifyDataSetChanged();

                } else if ((position + 1) <= (splits - 1) && numberOfSplits.get((position + 1)).sameAs(bmp)) {
                    Log.v("Executed ------->","right");
                    ImageView ivUp = (ImageView) parent.getChildAt(position + 1);
                    ivUp.setImageBitmap(numberOfSplits.get(position));
                    imageView.setImageBitmap(numberOfSplits.get(position + 1));
//                    ia.notifyDataSetChanged();

                }
            }
        });

        Button submit = (Button) findViewById(R.id.finish_button);
        submit.setOnClickListener(new View.OnClickListener() {
                                      @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
                                      public void onClick(View v) {
                                          Intent intent = new Intent(v.getContext(), FinishGame.class);
                                          ArrayList<Bitmap> validationList = splitImage(splits);;

                                          boolean flag = true;
                                          for(int i=0;i<numberOfSplits.size() -1 ;i++) {
                                              if(validationList.get(i).sameAs(numberOfSplits.get(i)))
                                                  continue;
                                              else {
                                                  flag = false;
                                                  break;
                                              }

                                          }
                                          if(flag == true) {
                                              intent.putExtra("final_score", nr_of_score.getText().toString());
                                              startActivityForResult(intent, 0);


                                          }
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

        int rows,cols;
        int chunkHeight,chunkWidth;

        ArrayList<Bitmap> chunkedImages = new ArrayList<Bitmap>(NumberOfSplits);

        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.default_photo);
        Bitmap bitmap = drawable.getBitmap();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        rows = cols = (int) Math.sqrt(NumberOfSplits);
        chunkHeight = bitmap.getHeight()/rows;
        chunkWidth = bitmap.getWidth()/cols;
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

    public boolean equals(Bitmap bitmap1, Bitmap bitmap2) {
        ByteBuffer buffer1 = ByteBuffer.allocate(bitmap1.getHeight() * bitmap1.getRowBytes());
        bitmap1.copyPixelsToBuffer(buffer1);

        ByteBuffer buffer2 = ByteBuffer.allocate(bitmap2.getHeight() * bitmap2.getRowBytes());
        bitmap2.copyPixelsToBuffer(buffer2);

        return Arrays.equals(buffer1.array(), buffer2.array());
    }


}


