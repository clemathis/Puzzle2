package aga.puzzle2;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Playground extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playground);
        // private String selectedImagePath;
        TextView nickname_tip = (TextView) findViewById(R.id.name);
        //This gets the elements from the New game page and sets it in the Playground page.
        // Note: This will set the number of splits to 9 by default, if the user does not select the radio button.
        nickname_tip.setText(getIntent().getStringExtra("name")+", welcome!");
        ArrayList<Bitmap> numberOfSplits = splitImage(Integer.parseInt(getIntent().getStringExtra("numberOfSplits")));
        Bitmap lastImagePiece = numberOfSplits.get(numberOfSplits.size());

        FileOutputStream fileOutputStream = null;
        try {

            // create a File object for the parent directory
            File lastPiece = new File("/sdcard/Capture/");
            // have the object build the directory structure, if needed.
            lastPiece.mkdirs();

            //Capture is folder name and file name with date and time
            fileOutputStream = new FileOutputStream(String.format(
                    "/sdcard/Capture/%d.jpg",
                    System.currentTimeMillis()));

            // Here we Resize the Image ...
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            lastImagePiece.compress(Bitmap.CompressFormat.JPEG, 100,
                    byteArrayOutputStream); // bm is the bitmap object
            byte[] bsResized = byteArrayOutputStream.toByteArray();

            fileOutputStream.write(bsResized);
            fileOutputStream.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

        //Removing the last piece
        numberOfSplits.remove(numberOfSplits.size());


        /*
	* 1. Bitmap lastImagePiece = numberOfSplits.get(numberOfSplits.size());
	* 2. Remove the last piece : numberOfSplits.remove(numberOfSplits.seize());
	* 3. Opening the FileOutputStream fos : file name : lastImage.jpeg
	* 4. Writing the Bitmap lastImagePiece to fos
	* 5. fos.flush();
	* 6. fos.close();
	* 7. ArrayList<Bitmap> shuffledImages = shuffle(numberOfSplits);
	*/


        GridView grid = (GridView) findViewById(R.id.gridview);
        grid.setAdapter(new ImageAdapter(this, numberOfSplits));
        grid.setNumColumns((int) Math.sqrt(numberOfSplits.size()));

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

}