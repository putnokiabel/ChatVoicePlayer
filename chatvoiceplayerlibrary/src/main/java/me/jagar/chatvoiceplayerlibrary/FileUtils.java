package me.jagar.chatvoiceplayerlibrary;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileUtils {

    public static void updateVisualizer(final Context context, final File file, final PlayerVisualizerSeekbar playerVisualizerSeekbar){
        new AsyncTask<Void, Void, byte[]>() {
            @Override
            protected byte[] doInBackground(Void... voids) {
                return fileToBytes(file);
            }

            @Override
            protected void onPostExecute(final byte[] bytes) {
                super.onPostExecute(bytes);

                new Handler(context.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        playerVisualizerSeekbar.setBytes(bytes);
                        playerVisualizerSeekbar.invalidate();
                    }
                });
            }
        }.execute();
    }
    public static byte[] fileToBytes(File file) {
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
