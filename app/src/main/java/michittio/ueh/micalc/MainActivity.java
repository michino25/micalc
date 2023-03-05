package michittio.ueh.micalc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String history = "";
    TextView tempTv, solutionTv;
    Button historyBtn;
    boolean calculating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        tempTv = findViewById(R.id.temp_tv);
        solutionTv = findViewById(R.id.solution_tv);

        Intent intent = new Intent(this, HistoryActivity.class);

        assignId(R.id.button_c);
        assignId(R.id.button_open_bracket);
        assignId(R.id.button_close_bracket);
        assignId(R.id.button_divide);
        assignId(R.id.button_multiply);
        assignId(R.id.button_plus);
        assignId(R.id.button_minus);
        assignId(R.id.button_equals);
        assignId(R.id.button_0);
        assignId(R.id.button_1);
        assignId(R.id.button_2);
        assignId(R.id.button_3);
        assignId(R.id.button_4);
        assignId(R.id.button_5);
        assignId(R.id.button_6);
        assignId(R.id.button_7);
        assignId(R.id.button_8);
        assignId(R.id.button_9);
        assignId(R.id.button_ac);
        assignId(R.id.button_dot);

        //      History Handler
        historyBtn = findViewById(R.id.button_history);
        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("result", history);
                startActivity(intent);
            }
        });

    }

    void assignId(int id) {
        MaterialButton btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();

        if (calculating && !buttonText.equals("=") && !buttonText.equals("AC") && !buttonText.equals("⌫")) {
            calculating = false;
            solutionTv.setText("0");
            tempTv.setText("");
        }

        if (buttonText.equals("AC")) {
            solutionTv.setText("0");
            tempTv.setText("");
            calculating = false;
            return;
        }

        if (buttonText.equals("=")) {
            if (!calculating && !tempTv.getText().toString().equals("")) {

                history += solutionTv.getText().toString() + " = " + tempTv.getText().toString() + "\n";

                solutionTv.setText(tempTv.getText());
                tempTv.setText("");
                calculating = true;
            }
            return;
        }

        String dataView = solutionTv.getText().toString();

        if (buttonText.equals("⌫")) {
            if (calculating) {
                solutionTv.setText("0");
                tempTv.setText("");
                calculating = false;
                return;
            } else if (dataView.charAt(dataView.length() - 1) == ' ')
                dataView = dataView.substring(0, dataView.length() - 3);
            else
                dataView = dataView.substring(0, dataView.length() - 1);

            if (dataView.length() == 0)
                dataView = "0";
        } else {
            if (dataView.equals("0"))
                dataView = buttonText;
            else if (dataView.charAt(dataView.length() - 1) == ' '
                    && buttonText.charAt(buttonText.length() - 1) == ' ')
                return;
            else
                dataView = dataView + buttonText;
        }

        solutionTv.setText(dataView);

        String dataCalc = solutionTv.getText().toString();
        dataCalc = dataCalc.replaceAll("–", "-");
        dataCalc = dataCalc.replaceAll("×", "*");
        dataCalc = dataCalc.replaceAll("÷", "/");
        String finalResult = getResult(dataCalc);

        if (!finalResult.equals("Err")) {
            tempTv.setText(finalResult);
        }


    }

    // Calc Function
    String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Err";
        }
    }

}