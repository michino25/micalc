package michittio.ueh.micalc;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {

    String history = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        TextView txtHistory = findViewById(R.id.txt_history);
        history = getIntent().getStringExtra("result");
        if (!history.equals(""))
            txtHistory.setText(history);
    }
}