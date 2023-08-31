package com.company.splendor.player.realplayer;

import com.company.splendor.card.cards.Card;
import com.company.splendor.crystal.crystals.Gold;
import com.company.splendor.player.BasicPlayer;

public class Gamer extends BasicPlayer {
    public Gamer(String playerName){
        super(playerName);
    }

    @Override
    public Card buyACard(int difficulty, int pos){
        Card card = super.buyACard(difficulty,pos);
        System.out.println("您购买了一张发展卡！获得了"+card.getPoints()+"分！获得了一个"+card.getReward()+"折扣奖励！");
        return card;
    }

    @Override
    public void borrow(int difficulty, int pos){
        super.borrow(difficulty,pos);
        System.out.print("您贷款了一张发展卡！");
        if((3-golds.size())>0) System.out.println("您还可以贷款"+(3-golds.size())+"张发展卡！");
        else System.out.println("您不可以再贷款发展卡了！");
    }

    @Override
    public Gold repay(int pos){
        Gold gold = super.repay(pos);
        System.out.print("您偿还了一张发展卡！现在，您可以贷款"+(3-golds.size())+"张贷款卡！");
        return gold;
    }
}
