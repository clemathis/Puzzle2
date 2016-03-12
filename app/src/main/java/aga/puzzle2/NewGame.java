package aga.puzzle2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class  NewGame extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView image = (ImageView) findViewById(R.id.menu_background);
        setContentView(R.layout.new_game);

    }
}
