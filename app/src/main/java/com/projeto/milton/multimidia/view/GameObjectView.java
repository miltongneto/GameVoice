package com.projeto.milton.multimidia.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Milton on 06/11/2016.
 */

public abstract class GameObjectView extends ImageView {
    private int x;
    private int y;

    public GameObjectView(Context context) {
        super(context);
    }
    public GameObjectView(Context context, int x, int y) {
        super(context);
        this.x = x;
        this.y = y;
        init();
    }

    public GameObjectView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public abstract void init();

    public abstract int process();

    @Override
    public float getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public float getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
