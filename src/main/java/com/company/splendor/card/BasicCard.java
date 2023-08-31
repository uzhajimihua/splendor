package com.company.splendor.card;

import com.company.splendor.other.Crystal;
import lombok.Data;

import java.util.Map;

@Data
public abstract class BasicCard {
    protected int difficulty;
    protected int points;
    protected Crystal reward;
    protected Map<Crystal,Integer> solution;

    protected BasicCard(int difficulty, int points, Crystal reward, Map<Crystal, Integer> solution) {
        this.difficulty = difficulty;
        this.points = points;
        this.reward = reward;
        this.solution = solution;
    }
}
