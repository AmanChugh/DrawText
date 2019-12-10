package com.ammyaman.drawtext;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    private File saveImage(Bitmap originalBitmap) {
        File myDir = getDataDir();
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);

            // NEWLY ADDED CODE STARTS HERE [
            Canvas canvas = new Canvas(originalBitmap);

            Paint paint = new Paint();
            paint.setColor(Color.WHITE); // Text Color
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(200); // Text Size
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)); // Text Overlapping Pattern
            // some more settings...

            canvas.drawBitmap(originalBitmap, 0, 0, paint);
            int xPos = (canvas.getWidth() / 2);
            int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
            //((textPaint.descent() + textPaint.ascent()) / 2) is the distance from the baseline to the center.

            TextPaint mTextPaint = new TextPaint();
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            mTextPaint.setColor(Color.WHITE); // Text Color
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            mTextPaint.setTextSize(150); // Text Size

            float scale = getResources().getDisplayMetrics().density;
            int textWidth = canvas.getWidth() - (int) (60 * scale);

            mTextPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            String textToPrint = "Winning doesn’t always mean being first. Winning means you’re doing better than you’ve done before.\n\nBonnie Blair";
            StaticLayout mTextLayout = new StaticLayout(textToPrint, mTextPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);


            float textX = originalBitmap.getWidth() / 2;
            float textY = (originalBitmap.getHeight() / 2) - (mTextLayout.getHeight() / 2);
            canvas.save();
            canvas.translate(textX, textY);
            mTextLayout.draw(canvas);
            canvas.restore();

//            canvas.drawText("Hello Testing Hello Testing Hello Testing Hello Testing Hello Testing", xPos, yPos, paint);

//            canvas.drawText("Testing...", 10, 10, paint);
            // NEWLY ADDED CODE ENDS HERE ]

            originalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }

    public void onClickMe(View view) {
        Bitmap mIcon = BitmapFactory.decodeResource(getResources(), R.drawable.hill);
        Bitmap mutableBitmap = mIcon.copy(Bitmap.Config.ARGB_8888, true);
        File filePAth = saveImage(mutableBitmap);

        showImage(filePAth);
    }

    private void showImage(File filePAth) {
        ImageView iv = findViewById(R.id.iv);
        Bitmap myBitmap = BitmapFactory.decodeFile(filePAth.getAbsolutePath());

        iv.setImageBitmap(myBitmap);
    }
}
