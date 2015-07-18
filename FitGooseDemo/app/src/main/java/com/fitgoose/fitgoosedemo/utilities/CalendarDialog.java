package com.fitgoose.fitgoosedemo.utilities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fitgoose.fitgoosedemo.MyDate;
import com.fitgoose.fitgoosedemo.R;
import com.fitgoose.fitgoosedemo.plan_tab.DailyNativeCard;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import it.gmariotti.cardslib.library.utils.BitmapUtils;
import it.gmariotti.cardslib.library.view.CardViewNative;


public class CalendarDialog extends Dialog {
    private DailyNativeCard mCard;
    private Context mContext;

    public CalendarDialog(Context context, MyDate date) {
        super(context);
        mContext = context;
        mCard = new DailyNativeCard(context,date);
        mCard.init();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_daily_card);

        //card view
        CardViewNative cardView = (CardViewNative) findViewById(R.id.calendar_daily_card);
        cardView.setCard(mCard);
        final Bitmap bitmap = cardView.createBitmap();

        //button return
        Button buttonReturn = (Button) findViewById(R.id.calendar_daily_card_return);
        buttonReturn.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                CalendarDialog.this.dismiss();
            }
        });

        //button save
        Button buttonSave = (Button) findViewById(R.id.calendar_daily_card_save);
        buttonSave.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v) {

                File photoFile=null;

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                File photoStorage = mContext.getFilesDir();
                if (photoStorage!=null){
                    photoFile = new File(photoStorage, (System.currentTimeMillis()) + ".jpg");
                    try {
                        //f.createNewFile();
                        FileOutputStream fo = new FileOutputStream(photoFile);
                        fo.write(bytes.toByteArray());
                        fo.flush();
                        fo.close();
                    } catch (IOException e) {
                        Log.e("FG Save File", "Error saving image ", e);
                    }
                }

                //BitmapUtils.createFileFromBitmap(bitmap);
                Toast.makeText(mContext, "Save image success.", Toast.LENGTH_SHORT).show();
                CalendarDialog.this.dismiss();
            }
        });
    }
}

