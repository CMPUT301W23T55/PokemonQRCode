package com.example.pokemonqrcode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.view.Display;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanIntentResult;
import com.journeyapps.barcodescanner.ScanOptions;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

public class ScannedCodeTests {

    @Test
    public void testNameGeneration() throws NoSuchAlgorithmException {
        byte[] array = {1, -128, 43, -50, 32, 127, 120};
        ScannedCode scan = new ScannedCode(array);
        scan.createName();
        System.out.println(scan.getName());
        assertNotEquals(null, scan.getName());
    }
    @Test
    public void testImageGeneration() throws NoSuchAlgorithmException {
        byte[] array = {-2, 45, -128, 127, -60, 20, 126};
        ScannedCode scan = new ScannedCode(array);
        scan.createImage();
        System.out.println(scan.getPicture());
        assertNotEquals(null, scan.getPicture());
    }
}
