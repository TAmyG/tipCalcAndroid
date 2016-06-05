package com.distinct.tamyg.tipcalc.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.distinct.tamyg.tipcalc.R;
import com.distinct.tamyg.tipcalc.TipCalcApp;
import com.distinct.tamyg.tipcalc.fragments.TipHistoryListFragment;
import com.distinct.tamyg.tipcalc.fragments.TipHistoryListFragmentListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.inputBill)
    EditText inputBill;
    @Bind(R.id.btnSubmit)
    Button btnSubmit;
    @Bind(R.id.btnIncrease)
    Button btnIncrease;
    @Bind(R.id.btnDecrease)
    Button btnDecrease;
    @Bind(R.id.inputPercentage)
    EditText inputPercentage;
    @Bind(R.id.btnClear)
    Button btnClear;
    @Bind(R.id.txtTip)
    TextView txtTip;

    private TipHistoryListFragmentListener fragmentListener;
    private final static  int TIP_STATE_CHANGE = 1;
    private final static  int DEFAULT_TIP_PERCENTAGE = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        TipHistoryListFragment fragment = (TipHistoryListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentList);
        fragment.setRetainInstance(true);

        fragmentListener = (TipHistoryListFragmentListener)fragment;
    }

    @OnClick({R.id.btnSubmit, R.id.btnIncrease, R.id.btnDecrease, R.id.btnClear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                handleClickSubmit();
                break;
            case R.id.btnIncrease:
                break;
            case R.id.btnDecrease:
                break;
            case R.id.btnClear:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_about){
            about();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Utiliza el String definido en TipCalcApp para abrir la direccion
     * establecida utilizando un intent para el navegador
     */
    private void about() {
        TipCalcApp app = (TipCalcApp)getApplication();
        String strUrl = app.getAboutUrl();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(strUrl));
        startActivity(intent);
    }

    /**
     * Funcion que maneja el evento click al presionar sobre
     * el btnSubmit
     */
    private void handleClickSubmit(){
        hideKeyboard();

        String strInputTotal = inputBill.getText().toString().trim();
        if(!strInputTotal.isEmpty()){
            double total = Double.parseDouble(strInputTotal);
            int tipPercentage = getDefaultTipPercentage();
            double tip = total * (tipPercentage/100d);

            String strTip = String.format(getString(R.string.global_message_tip), tip);
            fragmentListener.action(strTip);
            txtTip.setVisibility(View.VISIBLE);
            txtTip.setText(strTip);
            inputPercentage.setText("% "+getDefaultTipPercentage());
        }
    }

    /**
     * Metodo para ocultar el teclado cuando se requiera
     */
    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        try{
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);

        }catch (NullPointerException npe){
            Log.e(getLocalClassName(), Log.getStackTraceString(npe));
        }
    }

    public static int getDefaultTipPercentage() {
        return DEFAULT_TIP_PERCENTAGE;
    }

}
