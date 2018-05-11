package com.example.vadim.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends Activity
        implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private float mTextSize = 20;

    private EditText mEdit;

    private final static String FILENAME = "file.txt";

    private boolean flagBold = false;

    private static final int IDM_OPEN = 101;

    private static final int IDM_SAVE = 102;

    private static final int IDM_EXIT = 103;

    private final Colors[] mColors = Colors.values();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEdit = findViewById(R.id.edit_text);

        final Button buttonR = findViewById(R.id.button_r);
        final Button buttonB = findViewById(R.id.button_b);
        final Button buttonI = findViewById(R.id.button_i);
        final Button buttonPlus = findViewById(R.id.button_plus);
        final Button buttonMinus = findViewById(R.id.button_minus);

        buttonR.setOnClickListener(this);
        buttonB.setOnClickListener(this);
        buttonI.setOnClickListener(this);
        buttonPlus.setOnClickListener(this);
        buttonMinus.setOnClickListener(this);

        final Spinner spin = findViewById(R.id.Spinner01);
        spin.setOnItemSelectedListener(this);

        final ArrayAdapter<Colors> arrayAdapter = new ArrayAdapter<Colors>(
                this, android.R.layout.simple_spinner_item, mColors) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                switch (position) {
                    case 0 :
                        setItemOfAdapter(tv, Colors.BLACK);
                        break;
                    case 1 :
                        setItemOfAdapter(tv, Colors.BLUE);
                        break;
                    case 2 :
                        setItemOfAdapter(tv, Colors.YELLOW);
                        break;
                    case 3 :
                        setItemOfAdapter(tv, Colors.RED);
                        break;
                    case 4 :
                        setItemOfAdapter(tv, Colors.BROWN);
                        break;
                    case 5 :
                        setItemOfAdapter(tv, Colors.GREEN);
                        break;
                    case 6 :
                        setItemOfAdapter(tv, Colors.PINK);
                        break;
                }
                return view;
            }
        };

        spin.setPrompt("Выбрать цвет");
        arrayAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(arrayAdapter);

        TextView contextMenu = findViewById(R.id.view1);
        contextMenu.setTextSize(20);
        registerForContextMenu(contextMenu);
    }

    private void setItemOfAdapter(TextView textView, Colors color) {
        textView.setBackgroundColor(color.getCodeColor());
        textView.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_r: {
                mEdit.setTypeface(null, Typeface.NORMAL);
                flagBold = false;
                break;
            }
            case R.id.button_b: {
                if (flagBold) {
                    mEdit.setTypeface(null, Typeface.BOLD_ITALIC);
                } else {
                    mEdit.setTypeface(null, Typeface.BOLD);
                }
                break;
            }
            case R.id.button_i: {
                mEdit.setTypeface(null, Typeface.ITALIC);
                flagBold = true;
                break;
            }
            case R.id.button_plus: {
                if (mTextSize <= 24) {
                    mTextSize+=1;
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Уведомление: шрифт не может быть больше 27", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                mEdit.setTextSize(mTextSize);
                break;
            }
            case R.id.button_minus: {
                if (mTextSize >= 14) {
                    mTextSize -= 1;
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Уведомление: шрифт не может быть меньше 14", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                mEdit.setTextSize(mTextSize);
                break;
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, IDM_OPEN, Menu.NONE, "Open");
        menu.add(Menu.NONE, IDM_SAVE, Menu.NONE, "Save");
        menu.add(Menu.NONE, IDM_EXIT, Menu.NONE, "Exit");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        CharSequence message;
        switch (item.getItemId()) {
            case IDM_OPEN:
                try {
                    openFile();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),
                            "Exception: Такого файла не существует", Toast.LENGTH_LONG)
                            .show();
                }
                break;
            case IDM_SAVE:
                try {
                    saveFile();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),
                            "Exception: не удается сохранить файл" + e.toString(), Toast.LENGTH_LONG)
                            .show();
                }
                break;
            case IDM_EXIT:
                finish();
                System.exit(0);
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    @Override
    public void onItemSelected(
            AdapterView<?> parent, View v, int position, long id) {
        switch (position) {
            case 0 : mEdit.setTextColor(Colors.BLACK.getCodeColor());
                break;
            case 1 : mEdit.setTextColor(Colors.BLUE.getCodeColor());
                break;
            case 2 : mEdit.setTextColor(Colors.YELLOW.getCodeColor());
                break;
            case 3 : mEdit.setTextColor(Colors.RED.getCodeColor());
                break;
            case 4 : mEdit.setTextColor(Colors.BROWN.getCodeColor());
                break;
            case 5 : mEdit.setTextColor(Colors.GREEN.getCodeColor());
                break;
            case 6 : mEdit.setTextColor(Colors.PINK.getCodeColor());
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        mEdit.setText("");
    }

    private void openFile() throws IOException {
        InputStream inputStream = openFileInput(FILENAME);
        if (inputStream != null) {
            InputStreamReader sr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(sr);
            String str;
            StringBuilder builder = new StringBuilder();
            while ((str = reader.readLine()) != null) {
                builder.append(str).append("\n");
            }
            inputStream.close();
            mEdit.setText(builder.toString());
        }
    }

    private void saveFile() throws IOException {
        OutputStreamWriter outputStreamWriter =
                new OutputStreamWriter(openFileOutput(FILENAME, 0));

        outputStreamWriter.write(mEdit.getText().toString());
        outputStreamWriter.close();
    }

}
