package com.projeto.milton.multimidia.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import util.ResultEnum;

/**
 * Created by Milton on 06/11/2016.
 */

public class GameView extends View {
    private PlayerView player;
    private List<EnemyView> enemies;
    private List<BonusView> bonus;

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
        bonus = new ArrayList<BonusView>();
        enemies.add(new EnemyView(getContext(),player));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        player.draw(canvas);
        for(EnemyView enemy : enemies){
            enemy.draw(canvas);
        }
        for(BonusView b : bonus){
            b.draw(canvas);
        }
        invalidate();
    }

    public int play(){

        for(EnemyView enemy : enemies){
            if(enemy.process() == ResultEnum.PERDEU) return ResultEnum.PERDEU;
        }
        for(BonusView b : bonus){
            int result = b.process();
            if(result != ResultEnum.CONTINUAR){
                bonus.remove(b);
                return result;
            }

        }
        return ResultEnum.CONTINUAR;
    }

    public void addEnemy(EnemyView enemy){
        enemies.add(enemy);
    }

    public void addBonus(BonusView b){
        bonus.add(b);
    }

    public PlayerView getPlayer() {
        return player;
    }

    public List<EnemyView> getEnemies(){
        return  this.enemies;
    }

}
