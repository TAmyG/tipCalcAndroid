package com.distinct.tamyg.tipcalc.activities;

import android.os.Bundle;
import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.distinct.tamyg.tipcalc.R;
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

    /**
     * Funcion que maneja el evento click al presionar sobre
     * el btnSubmit
     */
    private void handleClickSubmit(){

        String strInputTotal = inputBill.getText().toString().trim();
        if(!strInputTotal.isEmpty()){
            double total = Double.parseDouble(strInputTotal);
            int tipPercentage = getDefaultTipPercentage();
            double tip = total * (tipPercentage/100d);

            String strTip = String.format(getString(R.string.global_message_tip), tip);
            fragmentListener.action(strTip);
            txtTip.setVisibility(View.VISIBLE);
            txtTip.setText(strTip);
        }
    }

    public static int getDefaultTipPercentage() {
        return DEFAULT_TIP_PERCENTAGE;
    }

}
