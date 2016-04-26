package com.joskoding.beshoot;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.renderscript.Script;
import android.renderscript.ScriptIntrinsicConvolve3x3;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Raka Adi Nugroho on 27/03/2016.
 */
public class PCD {
    public static final int FLIP_VERTICAL=1;
    public static final int FLIP_HORIZONTAL=2;
    public Bitmap toGrayscale(Bitmap bitmap){
        Bitmap bmOut=Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        int A, R, G, B;
        int pixel;
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();
        for (int x=0; x<width; ++x){
            for (int y=0; y<height; ++y){
                pixel=bitmap.getPixel(x,y);
                A= Color.alpha(pixel);
                R=Color.red(pixel);
                G=Color.green(pixel);
                B=Color.blue(pixel);
                R=G=B=(int)(R+G+B)/3;
                bmOut.setPixel(x,y, Color.argb(A, R, G, B));
            }
        }
        return bmOut;
    }
    public Bitmap toBinner(Bitmap bitmap){
        bitmap=toGrayscale(bitmap);
        Bitmap bmOut=Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        int A, R, G, B, CenterValue;
        int pixel;
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();
        for (int x=0; x<width; ++x){
            for (int y=0; y<height; ++y){
                pixel=bitmap.getPixel(x, y);
                A= Color.alpha(pixel);
                R=Color.red(pixel);
                G=Color.green(pixel);
                B=Color.blue(pixel);
                CenterValue=(int)(R+G+B)/3;
                if(CenterValue >= 128){
                    R=G=B=(int)255;
                }else{
                    R=G=B=(int)0;
                }
                bmOut.setPixel(x,y, Color.argb(A, R, G, B));
            }
        }
        return bmOut;
    }
    public Bitmap toNegative(Bitmap bitmap){
        Bitmap bmOut=Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        int A, R, G, B;
        int pixel;
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();
        for (int x=0; x<width; ++x){
            for (int y=0; y<height; ++y){
                pixel=bitmap.getPixel(x, y);
                A= Color.alpha(pixel);
                R=255 - Color.red(pixel);
                G=255 - Color.green(pixel);
                B=255 - Color.blue(pixel);
                bmOut.setPixel(x,y, Color.argb(A, R, G, B));
            }
        }
        return bmOut;
    }
    public Bitmap toRotate(Bitmap bitmap, float degree){
        Matrix matrix=new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
    public Bitmap toContrast(Bitmap bitmap, double value){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, bitmap.getConfig());
        int A, R, G, B;
        int pixel;
        double contrast=Math.pow((100+value)/100, 2);
        for (int x=0;x <width; ++x){
            for (int y=0; y<height; ++y){
                pixel=bitmap.getPixel(x,y);
                A=Color.alpha(pixel);
                R = Color.red(pixel);
                R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(R < 0) { R = 0; }
                else if(R > 255) { R = 255; }

                G = Color.red(pixel);
                G = (int)(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(G < 0) { G = 0; }
                else if(G > 255) { G = 255; }

                B = Color.red(pixel);
                B = (int)(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(B < 0) { B = 0; }
                else if(B > 255) { B = 255; }
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
        return bmOut;
    }
    public Bitmap toBrightness(Bitmap bitmap, int value){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, bitmap.getConfig());
        int A, R, G, B;
        int pixel;
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                pixel = bitmap.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);

                R += value;
                if(R > 255) { R = 255; }
                else if(R < 0) { R = 0; }

                G += value;
                if(G > 255) { G = 255; }
                else if(G < 0) { G = 0; }

                B += value;
                if(B > 255) { B = 255; }
                else if(B < 0) { B = 0; }
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
        return bmOut;
    }
    public static Bitmap toFlip(Bitmap bitmap, int type){
        Matrix matrix=new Matrix();
        if (type == FLIP_VERTICAL){
            matrix.preScale(1.0f, -1.0f);
        }
        else if (type == FLIP_HORIZONTAL){
            matrix.preScale(-1.0f, 1.0f);
        }else{
            return null;
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
    public Bitmap toSharpen(Bitmap bitmap){
        double[][] SharpenConfig=new double[][]{
                {0, -1, 0},
                {-1, 5, -1},
                {0, -1, 0}
        };
        ConvolutionMatrix convolutionMatrix=new ConvolutionMatrix(3);
        convolutionMatrix.applyConfig(SharpenConfig);
        convolutionMatrix.Factor =1;
        convolutionMatrix.Offset =127;

        return ConvolutionMatrix.computeConvolution3x3(bitmap, convolutionMatrix);
    }
    public Bitmap toBlur(Bitmap bitmap){
        double[][] BlurConfig=new double[][]{
                {1f/9f, 1f/9f, 1f/9f},
                {1f/9f, 1f/9f, 1f/9f},
                {1f/9f, 1f/9f, 1f/9f}
        };
        ConvolutionMatrix convolutionMatrix=new ConvolutionMatrix(3);
        convolutionMatrix.applyConfig(BlurConfig);
        convolutionMatrix.Factor =1;
        convolutionMatrix.Offset =127;

        return ConvolutionMatrix.computeConvolution3x3(bitmap, convolutionMatrix);
    }
    public Bitmap toEdgeFilter(Bitmap bitmap){
        double[][] EdgeFilterConfig=new double[][]{
                {-1, -1, -1},
                {-1, 8, -1},
                {-1, -1, -1}
        };
        ConvolutionMatrix convolutionMatrix=new ConvolutionMatrix(3);
        convolutionMatrix.applyConfig(EdgeFilterConfig);
        convolutionMatrix.Factor =1;
        convolutionMatrix.Offset =127;

        return ConvolutionMatrix.computeConvolution3x3(bitmap, convolutionMatrix);
    }
    public Bitmap toIdentityFilter(Bitmap bitmap){
        double[][] IdentityFilterConfig=new double[][]{
                {0, 0, 0},
                {0, -1, 0},
                {0, 0, 0}
        };
        ConvolutionMatrix convolutionMatrix=new ConvolutionMatrix(3);
        convolutionMatrix.applyConfig(IdentityFilterConfig);
        convolutionMatrix.Factor =1;
        convolutionMatrix.Offset =127;

        return ConvolutionMatrix.computeConvolution3x3(bitmap, convolutionMatrix);
    }
    public Bitmap toEdgeEnhanceFilter(Bitmap bitmap){
        double[][] EdgeEnhanceFilterConfig=new double[][]{
                {0, 0, 0},
                {-1, -1, 0},
                {0, 0, 0}
        };
        ConvolutionMatrix convolutionMatrix=new ConvolutionMatrix(3);
        convolutionMatrix.applyConfig(EdgeEnhanceFilterConfig);
        convolutionMatrix.Factor =1;
        convolutionMatrix.Offset =127;

        return ConvolutionMatrix.computeConvolution3x3(bitmap, convolutionMatrix);
    }
    public Bitmap toEmboss(Bitmap bitmap){
        double[][] EmbossConfig=new double[][]{
                {-1, -1, 0},
                {-1, 0, 1},
                {0, 1, 1}
        };
        ConvolutionMatrix convolutionMatrix=new ConvolutionMatrix(3);
        convolutionMatrix.applyConfig(EmbossConfig);
        convolutionMatrix.Factor=1;
        convolutionMatrix.Offset=127;

        return ConvolutionMatrix.computeConvolution3x3(bitmap, convolutionMatrix);
    }
    public Bitmap toGaussian(Bitmap bitmap){
        double[][] GaussianConfig=new double[][]{
                {1f/16f, 1f/8f, 1f/16f},
                {1f/8f, 1f/4f, 1f/8f},
                {1f/16f, 1f/8f, 1f/16f}
        };
        ConvolutionMatrix convolutionMatrix=new ConvolutionMatrix(3);
        convolutionMatrix.applyConfig(GaussianConfig);
        convolutionMatrix.Factor=1;
        convolutionMatrix.Offset=127;

        return ConvolutionMatrix.computeConvolution3x3(bitmap, convolutionMatrix);
    }
    // Simpan Gambar
    public static final String insertImage(ContentResolver cr,
                                           Bitmap source,
                                           String title,
                                           String description) {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, title);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, title);
        values.put(MediaStore.Images.Media.DESCRIPTION, description);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());

        Uri url = null;
        String stringUrl = null;    /* value to be returned */

        try {
            url = cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            if (source != null) {
                OutputStream imageOut = cr.openOutputStream(url);
                try {
                    source.compress(Bitmap.CompressFormat.JPEG, 50, imageOut);
                } finally {
                    imageOut.close();
                }

                long id = ContentUris.parseId(url);
                // Wait until MINI_KIND thumbnail is generated.
                Bitmap miniThumb = MediaStore.Images.Thumbnails.getThumbnail(cr, id, MediaStore.Images.Thumbnails.MINI_KIND, null);
                // This is for backward compatibility.
                storeThumbnail(cr, miniThumb, id, 50F, 50F, MediaStore.Images.Thumbnails.MICRO_KIND);
            } else {
                cr.delete(url, null, null);
                url = null;
            }
        } catch (Exception e) {
            if (url != null) {
                cr.delete(url, null, null);
                url = null;
            }
        }

        if (url != null) {
            stringUrl = url.toString();
        }

        return stringUrl;
    }

    /**
     * A copy of the Android internals StoreThumbnail method, it used with the insertImage to
     * populate the android.provider.MediaStore.Images.Media#insertImage with all the correct
     * meta data. The StoreThumbnail method is private so it must be duplicated here.
     * @see android.provider.MediaStore.Images.Media (StoreThumbnail private method)
     */
    private static final Bitmap storeThumbnail(
            ContentResolver cr,
            Bitmap source,
            long id,
            float width,
            float height,
            int kind) {

        // create the matrix to scale it
        Matrix matrix = new Matrix();

        float scaleX = width / source.getWidth();
        float scaleY = height / source.getHeight();

        matrix.setScale(scaleX, scaleY);

        Bitmap thumb = Bitmap.createBitmap(source, 0, 0,
                source.getWidth(),
                source.getHeight(), matrix,
                true
        );

        ContentValues values = new ContentValues(4);
        values.put(MediaStore.Images.Thumbnails.KIND,kind);
        values.put(MediaStore.Images.Thumbnails.IMAGE_ID,(int)id);
        values.put(MediaStore.Images.Thumbnails.HEIGHT,thumb.getHeight());
        values.put(MediaStore.Images.Thumbnails.WIDTH,thumb.getWidth());

        Uri url = cr.insert(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, values);

        try {
            OutputStream thumbOut = cr.openOutputStream(url);
            thumb.compress(Bitmap.CompressFormat.JPEG, 100, thumbOut);
            thumbOut.close();
            return thumb;
        } catch (FileNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
    }
    private void faceDetection(){

    }
}
