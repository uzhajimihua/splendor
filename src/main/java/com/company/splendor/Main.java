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
        int player_num;

        while(true){
            System.out.println("请输入游戏人数(2-4人):");
            for(player_num = scanner.nextInt(); player_num <2|| player_num >4; player_num =scanner.nextInt()) System.out.println("错误的游戏人数！请重新输入游戏人数(2-4人):");
            //要先准备机器人和玩家
            ActionSequence.setSequence(player_num);
            //设置宝石
            CrystalShop.init();
            //抽卡
            DrawCards.showCards();
            //抽取贵族
            PresentNobles.initNobles(player_num);

            while(true){
                BasicPlayer player = ActionSequence.getSequence().getCurrentPlayer();
                if(player instanceof Ai){
                    ActionSequence.getSequence().aiAction((Ai)player);
                } else if (player instanceof Gamer){
                    if(ActionSequence.getSequence().gamerAction((Gamer) player, scanner)){
                        System.out.println("玩家退出，游戏结束！输入0结束程序，输入其他开始新的一局游戏！");
                        break;
                    }
                }
                player.canInviteNobles(PresentNobles.getNobles());
                if(player.isWinner()) {
                    System.out.println("游戏结束！"+player.getPlayerName()+"获得胜利！输入0结束程序，输入其他开始新的一局游戏！");
                    break;
                }
            }
            //退出程序
            try {
                if(scanner.nextInt()==0) break;
            }catch (IllegalArgumentException ignored){
            }
            finally {
                if(scanner.hasNextLine()) scanner.hasNextLine();
            }
        }
    }
}
