package com.eleventures.snakesandladders.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;

import com.eleventures.snakesandladders.R;

public class RollingDiceAutoDismissProgress extends AutoDismissDialog{
    public RollingDiceAutoDismissProgress(Context context, int timer, @Nullable OnShowListener showListener, @Nullable OnDismissListener dismissListener) {
        super(context, timer, showListener, dismissListener);
        setContentView(R.layout.layout_rolling_dice_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater myLayout = LayoutInflater.from(getContext());
        final View dialogView = myLayout.inflate(R.layout.layout_rolling_dice_dialog, null);
        setContentView(dialogView);
    }
}
