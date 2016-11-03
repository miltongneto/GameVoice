package com.projeto.milton.multimidia.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import static android.R.drawable.ic_notification_overlay;

/**
 * Created by Milton on 02/11/2016.
 */

public class EnemyView extends ImageView {
    private int altura;
    private int largura;
    private int x;
    private int y;
    private int movimento;

    public EnemyView(Context context) {
        super(context);
        Drawable imagem = context.getDrawable(ic_notification_overlay);
        setImageDrawable(imagem);
        largura = imagem.getIntrinsicWidth();
        altura = imagem.getIntrinsicHeight();
        x = 450;
        y = 25;
        movimento = 10;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getDrawable().setBounds(x, y, x + largura, y + altura);
        getDrawable().draw(canvas);
    }

    public boolean mover(String direcao) {
        boolean moveu = false;
        this.y = this.y + movimento;
        moveu = true;

        invalidate();
        return moveu;
    }

    public int getMovimento(){
        return this.movimento;
    }

    public int getAltura() {
        return altura;
    }

    public int getLargura() {
        return largura;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }
}