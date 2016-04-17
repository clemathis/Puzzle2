package aga.puzzle2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.FileNotFoundException;
import java.io.IOException;

public class NewGame extends Activity {

    private static final int SELECTED_PICTURE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_game);

        // Write your nickname
        final EditText nickname_tip = (EditText) findViewById(R.id.nickname_tip);
        // Choose nr of pieces
        final RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);

        //Use a default photo
        Button default_button = (Button) findViewById(R.id.default_button);
        default_button.setOnClickListener(new View.OnClickListener() {
                                              public void onClick(View v) {
                                                  Intent intent = new Intent(v.getContext(), Playground.class);
                                                  //startActivityForResult(intent, 0);
                                                  intent.putExtra("name", nickname_tip.getText().toString());
                                                  RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
                                                  String radiovalue = "9";
                                                  if (rg.getCheckedRadioButtonId() != -1)
                                                      radiovalue = ((RadioButton) findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                                                  intent.putExtra("numberOfSplits", radiovalue);
                                                  intent.putExtra("flag", "0");
                                                  startActivity(intent);
                                              }
                                          }
        );
        //Choose a photo from gallery
        Button upload_button = (Button) findViewById(R.id.upload_button);
        upload_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //  Intent intent = new Intent(v.getContext(),Playground.class);
                intent.setClass(v.getContext(), Playground.class);
                intent.putExtra("name", nickname_tip.getText().toString());
                intent.setClass(getBaseContext(), Playground.class);
                RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
                String radiovalue = "9";
                if (rg.getCheckedRadioButtonId() != -1)
                    radiovalue = ((RadioButton) findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                intent.putExtra("numberOfSplits", radiovalue);
                intent.putExtra("flag", "1");
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), 1);
                Log.v("RESULT", "Somewhere here!!!!!!!!!!!!!!!!");


            }

        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        Log.v("RESULT", "******************Ta-Da-Da*****************"+getPath(uri));

        switch (requestCode) {
            case SELECTED_PICTURE:
                if (resultCode == RESULT_OK) {
                    Uri uri1 = data.getData();
                    Log.v("RESULT", "******************Ta-Da-Da*****************"+getPath(uri));
                    final String[] projection = {MediaStore.Images.Thumbnails.DATA,MediaStore.Images.Thumbnails.IMAGE_ID};
                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                    Drawable d = new BitmapDrawable(yourSelectedImage);

                    data.putExtra("inageURI", yourSelectedImage);

                }
                break;

            default:
                Log.v("RESULT", "******************Nothing*****************");

                break;
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