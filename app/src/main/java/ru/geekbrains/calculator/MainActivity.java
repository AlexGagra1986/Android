package ru.geekbrains.calculator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Calculator calculator;
    private TextView text;
    private final static String keyCounters = "Counters";

    private ConstraintLayout layoutMain;
    private Activity activity;
    private Button button;
    private SharedPreferences sharedPreferences;



    //создание меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    // переход на другую активити в меню
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.sitting:
                Intent intent = new Intent(MainActivity.this, Sittings.class);
                startActivity(intent);
                return true;
            case R.id.exit:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //     Сохранение данных
    @Override
    public void onSaveInstanceState(@NonNull Bundle instanceState) {
        super.onSaveInstanceState(instanceState);

        instanceState.putParcelable(keyCounters, calculator);
    }

    // Восстановление данных
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle instanceState) {
        super.onRestoreInstanceState(instanceState);
        calculator = instanceState.getParcelable(keyCounters);
        setTextCounters();
    }

    // Отображение данных на экране
    private void setTextCounters(){
        setTextCounter(text, Integer.parseInt(calculator.getText()));

    }

    private void setTextCounter(TextView textCounter, int counter){
        textCounter.setText(String.format(Locale.getDefault(), "%d", counter));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode());


        int[] numbers = new int[]{
                R.id.zero,
                R.id.btn1,
                R.id.btn2,
                R.id.btn3,
                R.id.btn4,
                R.id.btn5,
                R.id.btn6,
                R.id.btn7,
                R.id.btn8,
                R.id.btn9
        };

        int[] actions = new int[]{
                R.id.plus,
                R.id.minus,
                R.id.multiply,
                R.id.division,
                R.id.equals,
                R.id.prosent
        };


        text = findViewById(R.id.text);
        calculator = new Calculator();


        View.OnClickListener numberButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator.onNumPressed(view.getId());
                text.setText(calculator.getText());
            }
        };


        View.OnClickListener actionButtonOnclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator.onActionPressed(view.getId());
                text.setText(calculator.getText());
            }
        };


        for (int i = 0; i < numbers.length; i++) {
            findViewById(numbers[i]).setOnClickListener(numberButtonClickListener);
        }

        for (int j = 0; j < actions.length; j++) {
            findViewById(actions[j]).setOnClickListener(actionButtonOnclickListener);
        }
        findViewById(R.id.ac).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator.reset();
                text.setText(calculator.getText());
            }
        });


//        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                saveNightModeState(true);
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                saveNightModeState(false);
//            }
//            recreate();
//        });
//
//
//    }
//
//
//    private void saveNightModeState(boolean nightMode) {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean(KEY_NIGHT_MODE, nightMode).apply();
    }

}
