package aga.puzzle2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MenuPage extends Activity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView image = (ImageView) findViewById(R.id.menu_background);
        setContentView(R.layout.menu_page);


        //[New Game] button
        Button newgame_button = (Button) findViewById(R.id.newgame_button);
        newgame_button.setOnClickListener(new View.OnClickListener() {
                                              public void onClick(View v) {
                                                  Intent intent = new Intent(v.getContext(), NewGame.class);
                                                  startActivityForResult(intent, 0);
                                              }
                                          }
        );

        //[Resume] button
        Button resume_button = (Button) findViewById(R.id.resume_button);
        resume_button.setOnClickListener(new View.OnClickListener() {
                                              public void onClick(View v) {
                                                  Intent intent = new Intent(v.getContext(), Resume.class);
                                                  startActivityForResult(intent, 0);
                                              }
                                          }
        );

        //[Quit] button

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
