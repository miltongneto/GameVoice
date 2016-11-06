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
        boolean result;
        result = player.process();
        if(!result) return result;

        for(EnemyView enemy : enemies){
            result = enemy.process();
            if(!result) return result;
        }
        return result;
    }

    public PlayerView getPlayer() {
        return player;
    }
}
