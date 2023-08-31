package com.company.splendor.card.cards;

import com.company.splendor.card.BasicCard;
import com.company.splendor.other.Crystal;

import java.util.Map;

public class Card extends BasicCard {
    public Card(int difficulty, int points, Crystal reward, Map<Crystal, Integer> solution){
        super(difficulty,points,reward,solution);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("my point:").append(points).append("\n");
        sb.append("my reward:").append(reward).append("\n");
        sb.append("my requirements:\n");
        for(Map.Entry<Crystal,Integer> e:solution.entrySet()){
            sb.append("\t").append(e.getKey()).append(":").append(e.getValue()).append("\n");
        }
        return sb.toString();
    }
}
