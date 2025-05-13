package com.example.proyecto3b;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class RotatedSurfaceView extends SurfaceView {
    private Matrix matrix;
    private int rotationAngle = 0;

    public RotatedSurfaceView(Context context) {
        super(context);
        init();
    }

    public RotatedSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        matrix = new Matrix();
    }

    // Establecer el ángulo de rotación
    public void setRotationAngle(int angle) {
        rotationAngle = angle;
        invalidate(); // Redibujar la vista
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Aplicamos la rotación al canvas
        matrix.setRotate(rotationAngle, getWidth() / 2f, getHeight() / 2f);
        canvas.setMatrix(matrix);
        super.onDraw(canvas);
    }
}