package com.projeto.milton.multimidia.ia;

import android.content.Context;

import com.projeto.milton.multimidia.view.EnemyView;
import com.projeto.milton.multimidia.view.GameView;

/**
 * Created by Milton on 07/11/2016.
 */

public class ProcessIA {
    private Context context;
    private GameView game;
    private int NIVEL;
    private long XP;
    private long COUNT_TIME;

    public ProcessIA(Context context, GameView game){
        this.context = context;
        this.game = game;
        this.NIVEL = 1;
        this.COUNT_TIME = 0;
        this.XP = 0;
    }

    public boolean runGame(){
        if(game.play()) {
            switch (NIVEL) {
                case 1:
                    iaNivelUm();
                    break;
                case 2:
                    return false;
            }
            return  true;
        }

        return false;
    }

    public void iaNivelUm(){
        COUNT_TIME += 1;
        XP += 1;

        if(COUNT_TIME > 60){
            EnemyView e = new EnemyView(context,game.getPlayer());
            int posX = (int)(Math.random()*1000);
            e.setX(posX);
            game.addEnemy(e);
            COUNT_TIME = 0;
        }else if(XP >= 400){
            NIVEL = 2;
            COUNT_TIME = 0;
        }
    }
}
