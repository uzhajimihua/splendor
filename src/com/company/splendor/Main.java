package com.company.splendor;

import com.company.splendor.gamesetting.ActionSequence;
import com.company.splendor.gamesetting.CrystalShop;
import com.company.splendor.gamesetting.DrawCards;
import com.company.splendor.gamesetting.PresentNobles;
import com.company.splendor.player.BasicPlayer;
import com.company.splendor.player.realplayer.Ai;
import com.company.splendor.player.realplayer.Gamer;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws InterruptedException {
	    // write your code here
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入游戏人数(2-4人):");
        int players;
        for(players= scanner.nextInt();players<2||players>4;players=scanner.nextInt()) System.out.println("错误的游戏人数！请重新输入游戏人数(2-4人):");
        //设置宝石
        CrystalShop.init(players);
        //准备机器人和玩家
        ActionSequence.initPlayers(players);
        //抽卡
        DrawCards.showCards();
        //抽取贵族
        PresentNobles.initNobles(players);

        while(true){
            BasicPlayer player = ActionSequence.getCurrentPlayer();
            if(player instanceof Ai){
                ActionSequence.aiAction((Ai)player);
            } else if (player instanceof Gamer){
                if(ActionSequence.gamerAction((Gamer) player, scanner)){
                    System.out.println("玩家退出，游戏结束！");
                    break;
                }
            }
            player.canInviteNobles(PresentNobles.getNobles());
            if(player.isWinner()) {
                System.out.println("游戏结束！"+player.getPlayerName()+"获得胜利！");
                break;
            }
        }
    }
}
