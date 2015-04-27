package me.yanancao.qrnamecard;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
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


        String qr_input = firstNameTextField.getText().toString();
        QRCodeWriter writer = new QRCodeWriter();
        try{
            BitMatrix codeBitMatrix = writer.encode(qr_input, BarcodeFormat.QR_CODE, 800, 800);
            Bitmap codeBitMap = toBitmap(codeBitMatrix);

            ImageView qrcode = (ImageView) findViewById(R.id.qrCode);

            qrcode.setImageBitmap(codeBitMap);
        }
        catch (WriterException e){
            Log.d(TAG,"exception from qr code writer");
        }

    }

    private Bitmap toBitmap(BitMatrix matrix){
        int height = matrix.getHeight();
        int width = matrix.getWidth();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                bmp.setPixel(x, y, matrix.get(x,y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
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
