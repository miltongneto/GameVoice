package com.projeto.milton.multimidia.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import util.ResultEnum;
import static android.R.drawable.ic_notification_overlay;
import static com.projeto.milton.multimidia.R.drawable.enemy1;

/**
 * Created by Milton on 02/11/2016.
 */

public class EnemyView extends GameObjectView{
    private int altura;
    private int largura;
    private int movimento;
    private PlayerView player;
    private boolean goDown;

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
        Drawable imagem = getContext().getDrawable(enemy1);
        setImageDrawable(imagem);
        largura = 100; //imagem.getIntrinsicWidth();
        altura = 100; //imagem.getIntrinsicHeight();
        setX(400);
        setY(25);
        goDown = true;
        movimento = 10;
    }


    public int process() {
        if(enemyWin())
            return ResultEnum.PERDEU;

        return ResultEnum.CONTINUAR;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int x = (int) getX();
        int y = (int) getY();
        super.onDraw(canvas);
        getDrawable().setBounds(x, y, x + largura, y + altura);
        getDrawable().draw(canvas);
    }

    public boolean enemyWin() {
        int x = (int) getX();
        int y = (int) getY();

        if(goDown)  mover("baixo");
        else mover("cima");

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

        switch (direcao){
            case "cima":
                setY(y - movimento);
                moveu = true;
                break;
            case "baixo":
                setY(y + movimento);
                moveu = true;
                break;
        }
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

    public void setMovimento(int movimento){
        this.movimento = movimento;
    }

    public void setPlayer(PlayerView player) {
        this.player = player;
    }

    public boolean isGoDown() {
        return goDown;
    }

    public void setGoDown(boolean goDown) {
        this.goDown = goDown;
    }
}