package com.company.splendor;

import com.company.splendor.gamesetting.ActionSequence;
import com.company.splendor.gamesetting.CrystalShop;
import com.company.splendor.gamesetting.DrawCards;
import com.company.splendor.gamesetting.PresentNobles;
import com.company.splendor.player.BasicPlayer;
import com.company.splendor.player.realplayer.Ai;
import com.company.splendor.player.realplayer.Gamer;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
	    // write your code here
        Scanner scanner = new Scanner(System.in);
        int player_num=0;

        while(true){
            while(player_num == 0){
                try{
                    System.out.println("请输入游戏人数(2-4人),退出请关掉:");
                    player_num =scanner.nextInt();
                    if(player_num <2|| player_num >4){
                        System.out.println("错误的游戏人数！请重新输入游戏人数(2-4人):");
                        player_num=0;
                    }
                }catch (InputMismatchException e){
                    System.out.println("请输入数字！");
                    player_num=-1;
                }finally {
                    if(scanner.hasNextLine()) scanner.nextLine();
                }
            }

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
            System.out.println("任意键退出！");
            System.in.read();
            break;
        }
    }
}
