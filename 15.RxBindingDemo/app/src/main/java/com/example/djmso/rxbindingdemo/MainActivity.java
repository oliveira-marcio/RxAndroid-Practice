package com.example.djmso.rxbindingdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private EditText inputText;
    private TextView viewText;
    private Button clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.etInputField);
        viewText = findViewById(R.id.tvInput);
        clearButton = findViewById(R.id.btnClear);

        // All boilerplate below can be replace with RxBinding! ;-)

//        inputText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                viewText.setText(charSequence);
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        clearButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                inputText.setText("");
//                viewText.setText(" ");
//
//            }
//        });

        Disposable disposable1 = RxTextView.textChanges(inputText)
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {
                       viewText.setText(charSequence);
                    }
                });

        Disposable disposable2 = RxView.clicks(clearButton)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        inputText.setText("");
                        viewText.setText(" ");
                    }
                });

    }
}

