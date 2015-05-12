package me.yanancao.qrnamecard;

import android.content.Context;
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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.io.text.VCardReader;
import ezvcard.property.Email;
import ezvcard.property.StructuredName;
import ezvcard.property.Telephone;


public class NameCard extends ActionBarActivity {

    public String TAG = "yanan_debug";
    private final int QR_CODE_WIDTH = 800;
    private final int QR_CODE_HEIGHT = 800;
    private final String QR_FILE_NAME = "qr_file.vcf";

    public final void updateQR(View view){

        EditText firstNameTextField = (EditText) findViewById(R.id.firstName);
        EditText lastNameTextField = (EditText) findViewById(R.id.lastName);
        EditText emailTextField = (EditText) findViewById(R.id.email);
        EditText phoneNumberTextField = (EditText) findViewById(R.id.phoneNumber);

        Log.d(TAG,firstNameTextField.getText().toString());
        Log.d(TAG,lastNameTextField.getText().toString());
        Log.d(TAG,emailTextField.getText().toString());
        Log.d(TAG,phoneNumberTextField.getText().toString());

        VCard card = new VCard();

        StructuredName n = new StructuredName();
        n.setFamily(lastNameTextField.getText().toString());
        n.setGiven(firstNameTextField.getText().toString());

        card.setStructuredName(n);
        card.addEmail(emailTextField.getText().toString());

        Telephone tele = new Telephone(phoneNumberTextField.getText().toString());
        card.addTelephoneNumber(tele);

        String qr_input = Ezvcard.write(card).version(VCardVersion.V4_0).go();
        QRCodeWriter writer = new QRCodeWriter();
        try{
            BitMatrix codeBitMatrix = writer.encode(qr_input, BarcodeFormat.QR_CODE, QR_CODE_HEIGHT, QR_CODE_WIDTH);
            Bitmap codeBitMap = toBitmap(codeBitMatrix);

            ImageView qrcode = (ImageView) findViewById(R.id.qrCode);

            qrcode.setImageBitmap(codeBitMap);

            saveQRFile(card);
        }
        catch (WriterException e){
            Log.d(TAG,"exception from qr code writer");
            e.printStackTrace();
        }
    }

    private final void saveQRFile(VCard card){

        String filename = QR_FILE_NAME;
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            Ezvcard.write(card).go(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private final void restoreSavedQR() {
        InputStream in = null;
        try {
            in = openFileInput(QR_FILE_NAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        VCardReader reader = new VCardReader(in);

        VCard card = null;
        try {
            while ((card = reader.readNext()) != null){
                EditText firstNameTextField = (EditText) findViewById(R.id.firstName);
                EditText lastNameTextField = (EditText) findViewById(R.id.lastName);
                EditText emailTextField = (EditText) findViewById(R.id.email);
                EditText phoneNumberTextField = (EditText) findViewById(R.id.phoneNumber);

                firstNameTextField.setText(card.getStructuredName().getGiven());
                lastNameTextField.setText(card.getStructuredName().getFamily());
                for(Email email:card.getEmails()){
                    emailTextField.setText(email.getValue());
                }
                for(Telephone tele: card.getTelephoneNumbers()){
                    phoneNumberTextField.setText(tele.getText());
                }

                Log.d("restoreSavedQR",firstNameTextField.getText().toString());
                Log.d("restoreSavedQR",lastNameTextField.getText().toString());
                Log.d("restoreSavedQR",emailTextField.getText().toString());
                Log.d("restoreSavedQR",phoneNumberTextField.getText().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final Bitmap toBitmap(BitMatrix matrix){
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

        restoreSavedQR();
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
