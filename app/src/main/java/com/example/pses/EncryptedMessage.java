package com.example.pses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileOutputStream;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class EncryptedMessage extends AppCompatActivity {


    private ImageView qrCode, back;
    private EditText Message;
    private Button generateBtn, send;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypted_message);

        qrCode = findViewById(R.id.iv_qr);
        Message = findViewById(R.id.et_message);
        generateBtn = findViewById(R.id.GenerateQRbtn);
        send = findViewById(R.id.btn_sendMessage);
        back = findViewById(R.id.iv_back);

        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateBtn.setVisibility(View.INVISIBLE);
                generateBtn.setClickable(false);
                send.setVisibility(View.VISIBLE);
                send.setClickable(true);
                back.setVisibility(View.VISIBLE);
                back.setClickable(true);


                if (TextUtils.isEmpty(Message.getText().toString())){
                    Toast.makeText(EncryptedMessage.this, "Enter some text to generate QR", Toast.LENGTH_SHORT).show();
                } else {
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

                    Display display = manager.getDefaultDisplay();

                    Point point = new Point();
                    display.getSize(point);

                    int width = point.x;
                    int height = point.y;

                    int dimension = width < height ? width : height;
                    dimension = dimension * 3 / 4;

                    qrgEncoder = new QRGEncoder(Message.getText().toString(), null, QRGContents.Type.TEXT, dimension);
                    try {
                        bitmap = qrgEncoder.encodeAsBitmap();
                        qrCode.setImageBitmap(bitmap);

                    } catch (WriterException e) {
                        Log.e("Tag", e.toString());

                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateBtn.setVisibility(View.VISIBLE);
                generateBtn.setClickable(true);
                send.setVisibility(View.INVISIBLE);
                send.setClickable(false);
                back.setVisibility(View.INVISIBLE);
                back.setClickable(false);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean installed = appInstalledorNot("com.whatsapp");
                if (installed){
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) qrCode.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    shareImageandText(bitmap);
                } else {
                    Toast.makeText(EncryptedMessage.this, "There isn't whatsapp on ur device", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean appInstalledorNot(String uri) {
        PackageManager packageManager = getPackageManager();
        boolean appInstalled;

        try {
            packageManager.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            appInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            appInstalled = false;
        }
           return appInstalled;
    }

    private void shareImageandText(Bitmap bitmap) {
        Uri uri = getmageToShare(bitmap);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/png");
        intent.setPackage("com.whatsapp");
        startActivity(intent);
    }

    private Uri getmageToShare(Bitmap bitmap) {
        File imagefolder = new File(getCacheDir(), "images");
        Uri uri = null;
        try {
            imagefolder.mkdirs();
            File file = new File(imagefolder, "image.png");
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
            uri = FileProvider.getUriForFile(this, "com.example.pses.fileprovider", file);
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return uri;
    }
}