package com.company.splendor.gamesetting;

import com.company.splendor.card.cards.Card;
import com.company.splendor.gamesetting.gamepreparation.GenerateCards;
import com.company.splendor.other.Crystal;
import com.company.splendor.player.BasicPlayer;
import lombok.Data;

import java.util.Map;
import java.util.Random;

@Data
public class DrawCards {
    //摆放的牌
    private static Card[] difficulty3;
    private static Card[] difficulty2;
    private static Card[] difficulty1;
    //剩余张数
    private static int[] nums;
    //牌堆
    private static Card[] cards3;
    private static Card[] cards2;
    private static Card[] cards1;
    //随机种子
    private static Random random;

    static {
        //生成卡牌
        cards3 = GenerateCards.generate3();
        cards2 = GenerateCards.generate2();
        cards1 = GenerateCards.generate1();

        difficulty3 = new Card[4];
        difficulty2 = new Card[4];
        difficulty1 = new Card[4];

        nums = new int[]{0,40,30,20};
        random = new Random();

        //每种难度翻4张牌
        for(int i=3;i>=0;i--){
            setACard(difficulty3,i,cards3,3);
            setACard(difficulty2,i,cards2,2);
            setACard(difficulty1,i,cards1,1);
        }
    }

    static private void setACard(Card[] difficulty,int pos,Card[] cards,int diff){
        int i;
        if(nums[diff]<=0){
            System.out.println("difficulty "+diff+" has no more new cards!");
            return;
        }
        for(i=random.nextInt(cards.length);cards[i]==null;i=random.nextInt(cards.length));
        difficulty[pos]=cards[i];
        cards[i]=null;
        nums[diff]--;
    }

    //difficulty 123 pos012
    static public Card chooseACard(int diff,int pos,boolean takeaway){
        Card card = null;
        switch (diff){
            case 3: card = difficulty3[pos];if(takeaway) setACard(difficulty3,pos,cards3,diff);break;
            case 2: card = difficulty2[pos];if(takeaway) setACard(difficulty2,pos,cards2,diff);break;
            case 1: card = difficulty1[pos];if(takeaway) setACard(difficulty1,pos,cards1,diff);break;
            default:
        }
        return  card;
    }
    //生成发展卡数组情况字符串
    static private StringBuilder showCard(Card[] difficulty){
        StringBuilder sb = new StringBuilder();
        for(Card card:difficulty){
            sb.append(card.getReward()).append("\t").append(card.getPoints()).append("\n");
            sb.append("requires:");
            for(Map.Entry<Crystal,Integer> e:card.getSolution().entrySet())
                sb.append(e.getKey()).append(":").append(e.getValue()).append(",");
            sb.delete(sb.length()-1,sb.length()).append("\n");
        }
        return sb;
    }
    //显示翻开的发展卡情况
    static public void showCards(){
        System.out.println("difficulty 3:");
        System.out.print(showCard(difficulty3));
        System.out.println("difficulty 2:");
        System.out.print(showCard(difficulty2));
        System.out.println("difficulty 1:");
        System.out.println(showCard(difficulty1));
    }

    //判断玩家是否可购买其中一张发展卡
    public static boolean canChoose(BasicPlayer player){
        for(Card card:difficulty3){
            if(player.canBuyACard(card)) return true;
        }
        for(Card card:difficulty2){
            if(player.canBuyACard(card)) return true;
        }
        for(Card card:difficulty1){
            if(player.canBuyACard(card)) return true;
        }
        return false;
    }
}
