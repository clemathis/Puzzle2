package aga.puzzle2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class NewGame extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_game);

        // Write your nickname
        EditText nickname_tip = (EditText) findViewById(R.id.nickname_tip);






        //PLAY GAME button
        //Zmienic mu intent, bo bedzie otwieral to samo okno
        Button newgame_button = (Button) findViewById(R.id.newplay_button);
        newgame_button.setOnClickListener(new View.OnClickListener() {
                                              public void onClick(View v) {
                                                  Intent intent = new Intent(v.getContext(), NewGame.class);
                                                  startActivityForResult(intent, 0);
                                              }
                                          }
        );
    }


}
