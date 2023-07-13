package com.roydon.community.listener;

import android.graphics.Matrix;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    private ImageView imageView;
    private Matrix matrix = new Matrix();
    private float scaleFactor = 1.0f;

    public ScaleListener(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        scaleFactor *= detector.getScaleFactor();
        scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 10.0f));
        matrix.setScale(scaleFactor, scaleFactor);
        imageView.setImageMatrix(matrix);
        return true;
    }
}
