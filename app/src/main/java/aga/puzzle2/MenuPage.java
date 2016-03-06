package aga.puzzle2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class MenuPage extends Activity {

    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView image = (ImageView) findViewById(R.id.menu_background);
        setContentView(R.layout.menu_page);
    }

}
