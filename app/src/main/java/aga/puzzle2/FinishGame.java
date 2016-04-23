package aga.puzzle2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinishGame extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playground);
        Button startgame = (Button) findViewById(R.id.startgame_button);
        Button validate = (Button) findViewById(R.id.finish_button);
        // private String selectedImagePath;
        TextView nicelydone = (TextView) findViewById(R.id.nicely_done);
        TextView nickname_tip = (TextView) findViewById(R.id.name);
        //This gets the elements from the New game page and sets it in the Playground page. Note: This will set the number of splits to 9 by default, if the user does not select the radio button.
        nickname_tip.setText(getIntent().getStringExtra("name"));

        //[New Game] button
        Button newgame_button = (Button) findViewById(R.id.newgame_button);
        newgame_button.setOnClickListener(new View.OnClickListener() {
                                              public void onClick(View v) {
                                                  Intent intent = new Intent(v.getContext(), NewGame.class);
                                                  startActivityForResult(intent, 0);
                                              }
                                          }
        );

        //Quit game button
        Button quit_button = (Button) findViewById(R.id.quit_button);
        quit_button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        System.exit(0);
                    }
                }
        );
    }
}
