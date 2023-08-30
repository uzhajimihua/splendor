package com.company.splendor.card;

import com.company.splendor.other.Crystal;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@ToString
public abstract class BasicCard {
    protected int difficulty;
    protected int points;
    protected Crystal reward;
    protected Map<Crystal,Integer> solution;

    private BasicCard(){}
    protected BasicCard(int difficulty, int points, Crystal reward, Map<Crystal, Integer> solution) {
        this.difficulty = difficulty;
        this.points = points;
        this.reward = reward;
        this.solution = solution;
    }

//    @Override
//    public String toString(){
//        StringBuffer sb = new StringBuffer();
//        sb.append("my point:").append(points).append("\n");
//        sb.append("my reward:").append(reward).append("\n");
//        sb.append("my requirements:\n");
//        for(Map.Entry<Crystal,Integer> e:solution.entrySet()){
//            sb.append(e.getKey()).append(":").append(e.getValue()).append("\n");
//        }
//        return sb.toString();
//    }
}
