package id.giansar.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    TextInputLayout tilPhone;
    TextInputLayout tilMessage;
    Button btnSendToWA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSendToWA = findViewById(R.id.btnSendToWA);
        btnSendToWA.setOnClickListener(btnSendToWAListener);

        boolean whatsAppIsAvailable = isPackageAvailable("com.whatsapp");
        TextView tvYesNo = findViewById(R.id.yesNo);
        tilPhone = findViewById(R.id.tilPhone);
        tilMessage = findViewById(R.id.tilMessage);
        if (whatsAppIsAvailable) {
            tvYesNo.setText("YES");
        } else {
            tilPhone.setEnabled(false);
            tilMessage.setEnabled(false);
            btnSendToWA.setEnabled(false);
        }
    }

    private View.OnClickListener btnSendToWAListener = view -> {
        String phoneNumber = tilPhone.getEditText().getText().toString();
        String message = tilMessage.getEditText().getText().toString();

//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("whatsapp://send?text=" + message + "&phone=" + phoneNumber));
//        startActivity(intent);

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.putExtra("jid", phoneNumber + "@s.whatsapp.net"); //phone number without "+" prefix
        sendIntent.setPackage("com.whatsapp");
        startActivity(sendIntent);
    };

    private boolean isPackageAvailable(String name) {
        try {
            getPackageManager().getPackageInfo(name, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }
}