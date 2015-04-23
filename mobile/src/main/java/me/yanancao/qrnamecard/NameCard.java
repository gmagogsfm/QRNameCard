package me.yanancao.qrnamecard;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;


public class NameCard extends ActionBarActivity {

    public String TAG = "yanan_debug";

    public final void updateQR(View view){
        EditText firstNameTextField = (EditText) findViewById(R.id.firstName);
        EditText lastNameTextField = (EditText) findViewById(R.id.lastName);
        EditText emailTextField = (EditText) findViewById(R.id.email);
        EditText phoneNumberTextField = (EditText) findViewById(R.id.phoneNumber);

        Log.d(TAG,firstNameTextField.getText().toString());
        Log.d(TAG,lastNameTextField.getText().toString());
        Log.d(TAG,emailTextField.getText().toString());
        Log.d(TAG,phoneNumberTextField.getText().toString());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_name_card);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_name_card, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
