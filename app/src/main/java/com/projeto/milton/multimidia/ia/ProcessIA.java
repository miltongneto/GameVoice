package com.projeto.milton.multimidia.ia;

import android.content.Context;
import android.os.Debug;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

import com.projeto.milton.multimidia.view.BonusView;
import com.projeto.milton.multimidia.view.EnemyView;
import com.projeto.milton.multimidia.view.FastEnemyView;
import com.projeto.milton.multimidia.view.GameView;

import java.util.Random;

import util.ResultEnum;

/**
 * Created by Milton on 07/11/2016.
 */

public class ProcessIA {
    private Context context;
    private GameView game;
    private int NIVEL;
    private long XP;
    private long COUNT_TIME;
    private long TIME_BONUS;
    private int height;
    private int width;
    private int resultGame;
    private boolean isDown;

    public ProcessIA(Context context, GameView game, int height, int width){
        this.context = context;
        this.game = game;
        this.NIVEL = 1;
        this.COUNT_TIME = 0;
        this.XP = 0;
        this.TIME_BONUS = 0;
        this.height = height;
        this.width = width;
        this.isDown = true;
        Log.i("Largura ", width + "");
    }

    public int runGame(){
        resultGame = game.play();
        if(resultGame == ResultEnum.CONTINUAR) {
            sendBonus();
            switch (NIVEL) {
                case 1:
                    iaNivelUm();
                    break;
                case 2:
                    iaNivelDois();
                    break;
                case 3:
                    iaNivelTres();
                    break;
                case 4:
                    iaNivelQuatro();
                    break;
            }
        }else if(resultGame == ResultEnum.BONUS_XP){
            this.XP += 300;
        }

        return resultGame;
    }

    public void iaNivelUm(){
        COUNT_TIME += 1;
        XP += 1;

        if(COUNT_TIME >= 50){
            EnemyView enemy = new EnemyView(context,game.getPlayer());
            int posX = (int)(Math.random()*width);
            enemy.setX(posX);
            game.addEnemy(enemy);
            COUNT_TIME = 0;
        }else if(XP >= 150){
            NIVEL = 2;
            COUNT_TIME = 0;
        }
    }

    public void iaNivelDois(){
        COUNT_TIME += 1;
        XP += 2;

        if(COUNT_TIME >= 35){
            createSmartEnemy();

            COUNT_TIME = 0;
        }else if(XP >= 600){
            NIVEL = 3;
            COUNT_TIME = 0;
        }
    }

    public void iaNivelTres(){
        COUNT_TIME += 1;
        XP += 3;

        for(EnemyView enemy : game.getEnemies()){
            int distance = (int) (game.getPlayer().getY() - enemy.getY());
            if(distance > 200 && distance < 260){
                int side = (int) (game.getPlayer().getX() - enemy.getX());
                if(side > 0 && side > 10){
                    resultGame = ResultEnum.LIE_LEFT;
                }else if(side < 0 && side < 10){
                    resultGame = ResultEnum.LIE_RIGHT;
                }
            }
        }

        if(COUNT_TIME > 35) {
            createSmartEnemy();
            COUNT_TIME = 0;
        }else if(XP >= 1000){
            NIVEL = 4;
            COUNT_TIME = 0;
        }
    }

    public void iaNivelQuatro(){
        COUNT_TIME += 1;
        XP += 4;

        if(isDown) swap();

        if(COUNT_TIME >= 35){
            createSmartEnemy();

            COUNT_TIME = 0;
        }else if(XP >= 4000){
            NIVEL = 5;
            COUNT_TIME = 0;
        }
    }

    public void createSmartEnemy(){

        float playerPosX = game.getPlayer().getX();
        int min = (int) (playerPosX - 30);
        //int max = (int) (playerPosX + 30);

        int enemyPos = min +(int) (Math.random()*60);

        Random random = new Random();
        double escolha = Math.random();

        EnemyView enemy;
        if(escolha <= 0.66){
            enemy = new EnemyView(context,game.getPlayer());
            enemy.setMovimento(15);
        }else{
            enemy = new FastEnemyView(context,game.getPlayer());
        }
        enemy.setX(enemyPos);

        if(!isDown){
            enemy.setGoDown(false);
            enemy.setY(1000);
        }
        game.addEnemy(enemy);
    }

    public void sendBonus(){
        TIME_BONUS ++;
        if(TIME_BONUS >= 150){
            BonusView bonus = new BonusView(context,game.getPlayer());
            bonus.setX((int)(Math.random()*width));
            game.addBonus(bonus);

            TIME_BONUS = 0;
        }
    }

    public void swap(){
        isDown = false;
        game.getPlayer().goTop();
    }
    public long getXp(){
        return this.XP;
    }
}
