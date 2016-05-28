package com.android.library.thirdpay;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import com.android.library.R;
import com.android.library.ui.base.BaseSubActivity;
import com.android.library.ui.utils.ActivityUtils;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class DonateActivity extends BaseSubActivity implements View.OnClickListener{
    
    public static final int DONATE_REQUEST_CODE = 1000;
    public static final int DONATE_MAX_VALUE = 20000;
    
    public static final String EXTRA_DONATE_PAY = "donate_pay";
    public static final String EXTRA_DONATE_MESSAGE = "donate_message";
    
    private TextView mMaxPayPips;
    private TextView mDonateHeader;
    private EditText mDonateBody;
    private TextView mDonateFooter;
    private TextView mDonateMessageHeader;
    private EditText mDonateMessageBody;
    private TextView mDonateMessageFooter;
    private TextView mDonateShowValue;
    private Button mCommitButton;
    private ListView mDonateListView;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_layout_ui_donate);
        setTitle(R.string.pay_donate_title);
        initUI();
    }

    
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.lib_id_pay_donate_submit){
            Intent intent = new Intent(activity, PayActivity.class);
            Bundle bundle = new Bundle();
            double parsed = Double.parseDouble(mDonateBody.getText().toString());
            bundle.putDouble(EXTRA_DONATE_PAY, parsed);
            String message = mDonateMessageBody.getText().toString();
            if(message == null || message.length() == 0){
            	bundle.putString(EXTRA_DONATE_MESSAGE, getString(R.string.pay_donate_message_hint));
            } else {
            	bundle.putString(EXTRA_DONATE_MESSAGE, message);
            }
            intent.putExtras(bundle);
            ActivityUtils.startActivityForResult(activity, intent, DONATE_REQUEST_CODE);
            DonateActivity.this.finish();
        }
    }

    private void initUI(){
    	mMaxPayPips = (TextView) findViewById(R.id.lib_id_pay_donate_max_tips);
    	View payDonateView = findViewById(R.id.pay_donate_layout);
    	View payMessageView = findViewById(R.id.pay_message_layout);
    	mDonateHeader = (TextView) payDonateView.findViewById(R.id.lib_id_edittext_header);
    	mDonateBody = (EditText) payDonateView.findViewById(R.id.lib_id_edittext_body);
    	mDonateFooter = (TextView) payDonateView.findViewById(R.id.lib_id_edittext_footer);
    	
    	mDonateMessageHeader = (TextView) payMessageView.findViewById(R.id.lib_id_edittext_header);
    	mDonateMessageBody = (EditText) payMessageView.findViewById(R.id.lib_id_edittext_body);
    	mDonateMessageFooter = (TextView) payMessageView.findViewById(R.id.lib_id_edittext_footer);
    	
    	mDonateShowValue = (TextView) findViewById(R.id.lib_id_pay_donate_show_value);
    	mCommitButton = (Button) findViewById(R.id.lib_id_pay_donate_submit);
    	mCommitButton.setOnClickListener(this);
        mDonateListView = (ListView) findViewById(R.id.lib_id_pay_donate_list);
        
        mDonateHeader.setText(R.string.pay_donate_header);
        mDonateBody.setHint(R.string.pay_donate_hint);
        mDonateBody.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        mDonateFooter.setText(R.string.pay_donate_footer);
        
        mDonateMessageHeader.setText(R.string.pay_donate_message_header);
        mDonateMessageBody.setHint(R.string.pay_donate_message_hint);
        mDonateMessageBody.setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        mDonateMessageFooter.setText("");
        
        mCommitButton.setEnabled(false);
        
    	mDonateBody.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            	if (s.toString().contains(".")) {
					if (s.length() - 1 - s.toString().indexOf(".") > 2) {
						s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
						mDonateBody.setText(s);
						mDonateBody.setSelection(s.length());
					}
				}
				if (s.toString().trim().substring(0).equals(".")) {
					s = "0" + s;
					mDonateBody.setText(s);
					mDonateBody.setSelection(2);
				}

				if (s.toString().startsWith("0")
						&& s.toString().trim().length() > 1) {
					if (!s.toString().substring(1, 2).equals(".")) {
						mDonateBody.setText(s.subSequence(0, 1));
						mDonateBody.setSelection(1);
						mDonateShowValue.setText("");
						mMaxPayPips.setVisibility(View.INVISIBLE);
						return;
					}
				}
            }
            
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
            }
            
            @Override
            public void afterTextChanged(Editable s) {
            	String replaceable = String.format("[%s, \\s.]", NumberFormat.getCurrencyInstance(Locale.CHINA).getCurrency().getSymbol(Locale.CHINA));
                String cleanString = mDonateBody.getText().toString().replaceAll(replaceable, "");
                if (cleanString.equals("") || new BigDecimal(cleanString).toString().equals("0")) {
                	mMaxPayPips.setVisibility(View.INVISIBLE);
                	mCommitButton.setEnabled(false);
                	mDonateShowValue.setText("");
                } else {
                	double parsed = Double.parseDouble(mDonateBody.getText().toString());
                	String yuan = NumberFormat.getCurrencyInstance(Locale.CHINA).getCurrency().getSymbol(Locale.CHINA);
                	mDonateShowValue.setText(yuan + String.format("%.2f", parsed));
                	if(parsed>DONATE_MAX_VALUE){
                		mMaxPayPips.setVisibility(View.VISIBLE);
                		mMaxPayPips.setText(getString(R.string.pay_donate_max_value, DONATE_MAX_VALUE));
                		mCommitButton.setEnabled(false);
                		mDonateHeader.setTextColor(Color.RED);
                        mDonateBody.setTextColor(Color.RED);
                        mDonateFooter.setTextColor(Color.RED);
                	} else {
                		mMaxPayPips.setVisibility(View.INVISIBLE);
                		mCommitButton.setEnabled(true);
                		mDonateHeader.setTextColor(Color.BLACK);
                        mDonateBody.setTextColor(Color.BLACK);
                        mDonateFooter.setTextColor(Color.BLACK);
                	}
                }
            }
        });
       
    }
}
