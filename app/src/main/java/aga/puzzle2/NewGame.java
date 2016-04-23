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
    }
}