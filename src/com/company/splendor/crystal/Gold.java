package com.company.splendor.crystal;

import com.company.splendor.card.cards.Card;
import com.company.splendor.other.Crystal;
import com.company.splendor.player.BasicPlayer;
import lombok.Data;

import java.util.Map;

@Data
public class Gold {
    private Card card;

    public Gold(Card card){
        this.card = card;
    }

    public boolean canReturn(BasicPlayer player){
        int owe = 0;
        for(Map.Entry<Crystal,Integer> e:card.getSolution().entrySet()){
            owe+=(e.getValue()-player.getCrystals()[e.getKey().ordinal()]-player.getDiscounts()[e.getKey().ordinal()]);
        }
        return owe <= 1;
    }

    public Crystal Return(BasicPlayer player){
        for(Map.Entry<Crystal,Integer> e:card.getSolution().entrySet()){
            if(e.getValue()-player.getCrystals()[e.getKey().ordinal()]-player.getDiscounts()[e.getKey().ordinal()]>0)
                return e.getKey();
        }
        return null;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
