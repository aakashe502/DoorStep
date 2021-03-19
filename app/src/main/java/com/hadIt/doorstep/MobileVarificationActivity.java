package com.hadIt.doorstep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;

public class MobileVarificationActivity extends AppCompatActivity {
    CountryCodePicker ccp;
    EditText mobile;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_varification);

        mobile=findViewById(R.id.mobile);
        ccp=findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(mobile);
        b1=findViewById(R.id.b1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MobileVarificationActivity.this, ManageOtp.class);
                intent.putExtra("mobile",ccp.getFullNumberWithPlus().trim());
                startActivity(intent);
            }
        });
    }
}