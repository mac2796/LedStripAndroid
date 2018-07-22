package xyz.mateusztarnowski.homecontrol;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;

public class MainActivity extends AppCompatActivity {
    private LedStripController ledStripController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ledStripController = new LedStripController(getApplicationContext(), "http://192.168.0.76");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item);
        addLedStripsOnNetworkToArrayAdapter(arrayAdapter);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Button buttonOff = (Button) findViewById(R.id.button_off);
        buttonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ledStripController.turnOff();
            }
        });

        final Button buttonWakeup = (Button) findViewById(R.id.button_wakeup);
        buttonWakeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LightSequenceGenerator.generateColorfulSequence(ledStripController).start();
            }
        });

        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setMax(8);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                ledStripController.changeBrightness(seekBar.getProgress());
            }
        });

        ColorPickerView colorPicker = (ColorPickerView) findViewById(R.id.color_picker);
        colorPicker.addOnColorSelectedListener(new OnColorSelectedListener() {
            @Override
            public void onColorSelected(int i) {
                changeLightStripColorTo(i);
            }
        });
    }

    private void addLedStripsOnNetworkToArrayAdapter(ArrayAdapter adapter) {
        new FindLedStripsTask().execute(adapter);
    }

    private void changeLightStripColorTo(int color) {
        int r = (int)(Color.red(color) / 2.55);
        int g = (int)(Color.green(color) / 2.55);
        int b = (int)(Color.blue(color) / 2.55);
        ledStripController.changeColor(r, g, b);
    }
}
