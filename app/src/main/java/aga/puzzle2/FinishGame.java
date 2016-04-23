package aga.puzzle2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class FinishGame extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playground);
//        Button startgame = (Button) findViewById(R.id.startgame_button);
//        Button validate = (Button) findViewById(R.id.finish_button);
        // private String selectedImagePath;
        TextView nicelydone = (TextView) findViewById(R.id.nicely_done);
        TextView nickname_tip = (TextView) findViewById(R.id.name);
        //This gets the elements from the New game page and sets it in the Playground page. Note: This will set the number of splits to 9 by default, if the user does not select the radio button.
        nickname_tip.setText(getIntent().getStringExtra("name"));
    }
}
