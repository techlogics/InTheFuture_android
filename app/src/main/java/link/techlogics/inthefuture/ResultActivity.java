package link.techlogics.inthefuture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by hima on 14/09/21.
 */
public class ResultActivity extends Activity {

    private Button btnRetry, btnMain;
    private TextView resultText;
    private int clearCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent i = getIntent();
        clearCount = i.getIntExtra("clearCount", 0);

        if (clearCount == 0) {
            resultText = (TextView) findViewById(R.id.result_text);
            resultText.setText("残念！");
        } else {
            resultText = (TextView) findViewById(R.id.result_text);
            resultText.setText(clearCount + "回成功！");
        }




        btnMain = (Button) findViewById(R.id.btn_main);
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnRetry = (Button) findViewById(R.id.btn_retry);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
