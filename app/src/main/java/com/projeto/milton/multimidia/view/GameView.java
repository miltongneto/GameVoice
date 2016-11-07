package com.projeto.milton.multimidia.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Milton on 06/11/2016.
 */

public class GameView extends View {
    private PlayerView player;
    private List<EnemyView> enemies;

    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        player = new PlayerView(getContext());
        enemies = new ArrayList<EnemyView>();
        enemies.add(new EnemyView(getContext(),player));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        player.draw(canvas);
        for(EnemyView enemy : enemies){
            enemy.draw(canvas);
        }
        invalidate();
    }

    public boolean play(){

        if(!player.process()) return false;

        for(EnemyView enemy : enemies){
            if(!enemy.process()) return false;
        }
        return true;
    }

    public PlayerView getPlayer() {
        return player;
    }

    public void addEnemy(EnemyView enemy){
        enemies.add(enemy);
    }
}
