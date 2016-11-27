package com.projeto.milton.multimidia.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import util.ResultEnum;

import static android.R.drawable.presence_online;

/**
 * Created by Milton on 25/11/2016.
 */

public class BonusView extends GameObjectView {
    private int altura;
    private int largura;
    private int movimento;
    private PlayerView player;

    public BonusView(Context context) {
        super(context);
        init();
    }

    public BonusView(Context context, int x, int y) {
        super(context, x, y);
        init();
    }

    public BonusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public BonusView(Context context, PlayerView player) {
        super(context);
        this.player = player;
        init();
    }

    @Override
    public void init() {
        Drawable imagem = getContext().getDrawable(presence_online);
        setImageDrawable(imagem);
        largura = imagem.getIntrinsicWidth();
        altura = imagem.getIntrinsicHeight();
        setY(25);
        movimento = 20;
    }

    @Override
    public int process() {
        if(takeBonus()){
            return ResultEnum.BONUS_XP;
        }
        return ResultEnum.CONTINUAR;
    }


    public boolean takeBonus() {
        int x = (int) getX();
        int y = (int) getY();
        mover("baixo");
        float playerPosY = player.getY();
        float playerPosX = player.getX();
        if (y >= playerPosY && y <= playerPosY + player.getAltura()) {
            if (x >= playerPosX && x+largura <= playerPosX + player.getLargura())
                return true;
        }
        return false;
    }

    public boolean mover(String direcao) {
        boolean moveu = false;
        int y = (int) getY();
        setY(y + movimento);
        moveu = true;

        invalidate();
        return moveu;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int x = (int) getX();
        int y = (int) getY();
        super.onDraw(canvas);
        getDrawable().setBounds(x, y, x + largura, y + altura);
        getDrawable().draw(canvas);
    }
}
