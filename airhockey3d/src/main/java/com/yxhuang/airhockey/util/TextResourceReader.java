package com.yxhuang.airhockey.util;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by yxhuang
 * Date: 2017/11/20
 * Description:
 */

public class TextResourceReader {

    public static String readTextFilerFromResource(Context context, String  resourcePath){
        StringBuilder builder = new StringBuilder();

        try {
            InputStream inputStream = context.getAssets().open(resourcePath);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null){
                builder.append(nextLine);
                builder.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Resources.NotFoundException e){
            e.printStackTrace();
        }

        return builder.toString();
    }
}
