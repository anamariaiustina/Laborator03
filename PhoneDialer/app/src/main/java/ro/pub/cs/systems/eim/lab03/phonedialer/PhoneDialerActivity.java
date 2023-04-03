package ro.pub.cs.systems.eim.lab03.phonedialer;

import android.Manifest;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class PhoneDialerActivity extends AppCompatActivity {

    private EditText phoneNumberEditText;
    private ImageButton backspaceImageButton;
    private ImageButton callImageButton;
    private ImageButton hangupImageButton;
    private Button commonButton;

    private BackspaceImageButtonClickListener backspaceImageButtonClickListener =
            new BackspaceImageButtonClickListener();
    private class BackspaceImageButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String phoneNumber = phoneNumberEditText.getText().toString();
            if (phoneNumber.length() > 0) {
                phoneNumber = phoneNumber.substring(0, phoneNumber.length()-1);
                phoneNumberEditText.setText(phoneNumber);
            }
        }
    }

    private CallImageButtonClickListener callImageButtonClickListener =
            new CallImageButtonClickListener();
    private class CallImageButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(PhoneDialerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        PhoneDialerActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        Constants.PERMISSION_REQUEST_CALL_PHONE);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumberEditText.getText().toString()));
                startActivity(intent);
            }
        }
    }

    private HangupImageButtonClickListener hangupImageButtonClickListener =
            new HangupImageButtonClickListener();
    private class HangupImageButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    private CommonButtonClickListener commonButtonClickListener = new CommonButtonClickListener();
    private class CommonButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            phoneNumberEditText.setText(phoneNumberEditText.getText().toString() +
                    ((Button) v).getText().toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_dialer);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        phoneNumberEditText = (EditText) findViewById(R.id.phone_number_edit_text);
        backspaceImageButton = (ImageButton) findViewById(R.id.backspace_image_button);
        backspaceImageButton.setOnClickListener(backspaceImageButtonClickListener);
        callImageButton = (ImageButton) findViewById(R.id.call_image_button);
        callImageButton.setOnClickListener(callImageButtonClickListener);
        hangupImageButton = (ImageButton) findViewById(R.id.hangup_image_button);
        hangupImageButton.setOnClickListener(hangupImageButtonClickListener);
        for (int i = 0; i < Constants.buttonIds.length; i++) {
            commonButton = (Button) findViewById(Constants.buttonIds[i]);
            commonButton.setOnClickListener(commonButtonClickListener);
        }
    }
}