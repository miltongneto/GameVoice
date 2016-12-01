package com.projeto.milton.multimidia.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import static com.projeto.milton.multimidia.R.drawable.enemy2;

/**
 * Created by Milton on 01/12/2016.
 */

public class FastEnemyView extends EnemyView {
    public FastEnemyView(Context context) {
        super(context);
        initFastEnemy();
    }

    public FastEnemyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFastEnemy();
    }

    public FastEnemyView(Context context, PlayerView player) {
        super(context, player);
        initFastEnemy();
    }


    public void initFastEnemy(){
        Drawable imagem = getContext().getDrawable(enemy2);
        setImageDrawable(imagem);
        setX(400);
        setY(25);
        setGoDown(true);
        setMovimento(22);
        setLargura(120);
        setAltura(70);
    }
}
