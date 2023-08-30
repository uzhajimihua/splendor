package com.company.splendor.player;

import com.company.splendor.card.Noble;
import com.company.splendor.card.cards.Card;
import com.company.splendor.crystal.Gold;
import com.company.splendor.gamesetting.CrystalShop;
import com.company.splendor.gamesetting.DrawCards;
import com.company.splendor.other.Crystal;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public abstract class BasicPlayer implements BasicActions{
    protected int[] crystals;
    protected int[] discounts;
    protected List<Gold> golds;
    protected int points;
    protected List<Noble> visitedNobles;
    protected String playerName;

    private BasicPlayer(){
        crystals = new int[5];
        discounts = new int[5];
        golds = new ArrayList<>(3);
        points = 0;
        visitedNobles = new ArrayList<>(5);
        this.playerName = "";
    }
    protected BasicPlayer(String playerName){
        crystals = new int[5];
        discounts = new int[5];
        golds = new ArrayList<>(3);
        points = 0;
        visitedNobles = new ArrayList<>(5);
        this.playerName = playerName;
    }

    @Override
    public void showStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append(playerName).append(" 拥有:");
        for(Crystal c:Crystal.values()){
            sb.append(c).append(":").append(crystals[c.ordinal()]).append(" ");
        }
        sb.append("\n").append(playerName).append(" 可抵扣:");
        for(Crystal c:Crystal.values()){
            sb.append(c).append(":").append(discounts[c.ordinal()]).append(" ");
        }
        sb.append("\n");
        if(!golds.isEmpty()){
            sb.append("贷款中的发展卡有：\n");
            for(Gold g:golds){
                sb.append(showCard(g.getCard()));
            }
        }
        sb.append(playerName).append("拥有 ").append(points).append(" 分\n");
        System.out.println(sb);
    }
    @Override
    public void canInviteNobles(Noble[] nobles){
        for(Noble n:nobles){
            if(n.canInvite(crystals)){
                visitedNobles.add(n);
                n.setOwner(playerName);
                points+=3;
                System.out.println(n.getName()+" is invited by "+playerName+"!");
                break;
            }
        }
    }

    public int crystalCheck(){
        int sum = 0;
        for(int i:crystals){
            sum+=i;
        }
        return sum-10;
    }

    @Override
    public boolean isWinner(){
        return points>=15;
    }

    @Override
    public boolean canBuyACard(Card card){
        if(card == null) return false;
        for(Map.Entry<Crystal,Integer> e:card.getSolution().entrySet()){
            if(discounts[e.getKey().ordinal()]-e.getValue()<0)
                if(crystals[e.getKey().ordinal()]+(discounts[e.getKey().ordinal()]-e.getValue())<0)
                    return false;
        }
        return true;
    }

    public boolean canBuyACard(int difficulty,int pos){
        //越界处理
        if(difficulty>3||difficulty<1||pos>4||pos<1) return false;
        return canBuyACard(DrawCards.chooseACard(difficulty,pos-1,false));
    }

    @Override
    public Card buyACard(int difficulty,int pos){
        Card card = DrawCards.chooseACard(difficulty,pos,true);
        for(Map.Entry<Crystal,Integer> e:card.getSolution().entrySet()){
            if(discounts[e.getKey().ordinal()]-e.getValue()<0)
                crystals[e.getKey().ordinal()]+=(discounts[e.getKey().ordinal()]-e.getValue());
        }
        points += card.getPoints();
        discounts[card.getReward().ordinal()]++;
        return card;
    }

    @Override
    public void borrow(int difficulty, int pos){
        Card card = DrawCards.chooseACard(difficulty,pos-1,true);
        Gold gold = new Gold(card);
        golds.add(gold);
        CrystalShop.lent();
    }
    @Override
    public Gold repay(int pos){
        Gold gold = golds.remove(pos);
        points+=gold.getCard().getPoints();
        discounts[gold.getCard().getReward().ordinal()]++;
        return gold;
    }

    private StringBuilder showCard(Card card){
        StringBuilder sb = new StringBuilder();
        sb.append(card.getReward()).append("\t").append(card.getPoints()).append("\n");
        sb.append("requires:");
        for(Map.Entry<Crystal,Integer> e:card.getSolution().entrySet())
            sb.append(e.getKey()).append(":").append(e.getValue()).append(",");
        sb.delete(sb.length()-1,sb.length()).append("\n");
        return sb;
    }
}
