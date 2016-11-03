package com.projeto.milton.multimidia.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import static com.projeto.milton.multimidia.R.mipmap.ic_launcher;


/**
 * Created by Milton on 28/10/2016.
 */

public class ImagemView extends ImageView {
    private Drawable imagem;
    private EnemyView enemy;
    private int x;
    private int y;
    private int largura;
    private int altura;
    private int movimento = 200;

    public ImagemView(Context context, AttributeSet attrs){
        super(context, attrs);
        imagem = context.getDrawable(ic_launcher);
        largura = imagem.getIntrinsicWidth();
        altura = imagem.getIntrinsicHeight();
        x = 450;
        y = 600;
        enemy = new EnemyView(context);
        //setFocusable(true);
    }

    public ImagemView(Context context){
        super(context);
        imagem = context.getDrawable(ic_launcher);
        largura = imagem.getIntrinsicWidth();
        altura = imagem.getIntrinsicHeight();
        x = 300;
        y = 300;
        enemy = new EnemyView(context);
        //setFocusable(true);
    }

    public EnemyView getEnemy(){
        return this.enemy;
    }

    public void loadImagem(Drawable imagem, int largura, int altura, int movimento, int x, int y){
        //this.imagem = imagem;
        this.largura = largura;
        this.altura = altura;
        this.movimento = 25;
        this.x = 300;
        this.y = 300;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        imagem.setBounds(x, y, x+largura, y+altura);
        imagem.draw(canvas);
        enemy.draw(canvas);
        invalidate();
    }

    public boolean mover(String direcao){
        boolean moveu = false;
        switch (direcao){
            case "direita":
                this.x = this.x + movimento;
                moveu = true;
                break;
            case "esquerda":
                this.x = this.x - movimento;
                moveu = true;
                break;
            case "cima":
                this.y = this.y - movimento;
                moveu = true;
                break;
            case "baixo":
                this.y = this.y + movimento;
                moveu = true;
                break;
        }
        invalidate();
        return moveu;
    }

    public boolean moverEnemy(){
        boolean result;
        if(enemyWin()){
            result = false;
        }else{
            result = enemy.mover("baixo");
        }
        return  result;
    }

    public boolean enemyWin() {
        float enemyPosY = enemy.getMovimento() + enemy.getY();
        float enemyPosX = enemy.getX();
        if (enemyPosY >= y && enemyPosY <= y+altura) {
            if (enemyPosX >= x && enemyPosX <= x+largura)
                return true;
        }
        return false;
    }
}
