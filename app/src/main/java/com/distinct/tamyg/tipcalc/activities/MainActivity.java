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
import android.widget.EditText;
import android.widget.TextView;

import com.distinct.tamyg.tipcalc.R;
import com.distinct.tamyg.tipcalc.TipCalcApp;
import com.distinct.tamyg.tipcalc.fragments.TipHistoryListFragment;
import com.distinct.tamyg.tipcalc.fragments.TipHistoryListFragmentListener;
import com.distinct.tamyg.tipcalc.models.TipRecord;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.inputBill)
    EditText inputBill;
    @Bind(R.id.inputPercentage)
    EditText inputPercentage;
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
                handleClickIncrease();
                break;
            case R.id.btnDecrease:
                handleClickDecrease();
                break;
            case R.id.btnClear:
                handleClickClear();
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

    public int getDefaultTipPercentage() {
        int tipPercentage = DEFAULT_TIP_PERCENTAGE;
        String strInputTipPercentage = inputPercentage.getText().toString().trim();

        if(strInputTipPercentage.isEmpty())
            inputPercentage.setText(String.valueOf(tipPercentage));
        else
            tipPercentage = Integer.parseInt(strInputTipPercentage);

        return tipPercentage;
    }

    private void handleTipChange(int tipStateChange) {
        int tipPercentage = getDefaultTipPercentage();
        tipPercentage += tipStateChange;

        if(tipPercentage > 0)
            inputPercentage.setText(String.valueOf(tipPercentage));
    }
    /**Funcionalidad para botones----------------------------*/
    /*********************************************************/
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
            TipRecord tipRecord = new TipRecord();

            tipRecord.setBill(total);
            tipRecord.setTipPercentage(tipPercentage);
            tipRecord.setTimeStamp(new Date());

            String strTip = String.format(getString(R.string.global_message_tip),
                                    tipRecord.getTip());
            fragmentListener.addToList(tipRecord);
            txtTip.setVisibility(View.VISIBLE);
            txtTip.setText(strTip);
            inputPercentage.setText(String.valueOf(getDefaultTipPercentage()));
        }
    }

    /**
     * Funcion que maneja el evento click al presionar sobre
     * el btnClear
     */
    private void handleClickClear() {
        fragmentListener.clearList();
    }
    /**
     * Funcion que maneja el evento click al presionar sobre
     * el btnIncrease
     */
    private void handleClickIncrease() {
        hideKeyboard();
        handleTipChange(TIP_STATE_CHANGE);
    }
    /**
     * Funcion que maneja el evento click al presionar sobre
     * el btnDecrease
     */
    private void handleClickDecrease() {
        hideKeyboard();
        handleTipChange(-TIP_STATE_CHANGE);
    }
}
