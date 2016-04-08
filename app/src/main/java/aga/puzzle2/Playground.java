package aga.puzzle2;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Playground extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playground);
        TextView nickname_tip = (TextView) findViewById(R.id.name);
        //This gets the elements from the New game page and sets it in the Playground page. Note: This will set the number of splits to 9 by default, if the user does not select the radio button.
        nickname_tip.setText(getIntent().getStringExtra("name") + " " + getIntent().getStringExtra("numberOfSplits"));
    }

}
