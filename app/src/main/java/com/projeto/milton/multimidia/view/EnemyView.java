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

public class EnemyView extends GameObjectView{
    private int altura;
    private int largura;
    private int x;
    private int y;
    private int movimento;
    private PlayerView player;

    public EnemyView(Context context) {
        super(context);
        init();
    }

    public EnemyView(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }
    public EnemyView(Context context, PlayerView player) {
        super(context);
        this.player = player;
        init();
    }


    public void init() {
        Drawable imagem = getContext().getDrawable(ic_notification_overlay);
        setImageDrawable(imagem);
        largura = imagem.getIntrinsicWidth();
        altura = imagem.getIntrinsicHeight();
        x = 400;
        y = 25;
        movimento = 15;
    }


    public boolean process() {
        if(enemyWin())
            return false;

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getDrawable().setBounds(x, y, x + largura, y + altura);
        getDrawable().draw(canvas);
    }

    public boolean enemyWin() {
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

    public PlayerView getPlayer() {
        return player;
    }

    public void setPlayer(PlayerView player) {
        this.player = player;
    }
}