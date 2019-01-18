package com.yanzhikai.androidopengldemo;

import android.content.Context;
import android.content.Intent;

public class ActivityJumpUtil {
    public static void startActivity(Context context, Class target){
        Intent intent = new Intent(context, target);
        context.startActivity(intent);
    }
}
