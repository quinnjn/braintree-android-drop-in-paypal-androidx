package com.example.qneumiiller.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DropInRequest dropInRequest = new DropInRequest()
                .tokenizationKey("sandbox_tmxhyf7d_dcpspy2brwdjr3qn");
        startActivityForResult(dropInRequest.getIntent(this), 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
            String paymentMethodNonce = result.getPaymentMethodNonce().getNonce();
            Toast.makeText(this, "Paid via " + result.getPaymentMethodNonce().getTypeLabel(), Toast.LENGTH_SHORT).show();
        } else if (resultCode == Activity.RESULT_CANCELED) {
            // canceled
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
        } else {
            // an error occurred, checked the returned exception
            Exception exception = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
            Toast.makeText(this, "Error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
