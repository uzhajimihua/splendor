package com.company.splendor.card;

import com.company.splendor.other.Crystal;
import lombok.Data;

import java.util.Map;

@Data
public class Noble {
    private Map<Crystal,Integer> map;
    private String name;
    private String owner;
    static final int point = 3;

    public Noble(){
        this.map = null;
        this.name = null;
        this.owner = null;
    }
    public Noble(Map<Crystal,Integer> map,String name){
        this.map = map;
        this.name = name;
        this.owner = null;
    }

    public boolean canInvite(int[] crystals){
        if(owner!=null) return false;
        for(Map.Entry<Crystal,Integer> e:map.entrySet()){
            if(crystals[e.getKey().ordinal()]<e.getValue()) return false;
        }
        return true;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("My name is ").append(name).append("!\n");
        for(Map.Entry<Crystal,Integer> e:map.entrySet()){
            sb.append(e.getKey()).append(":").append(e.getValue()).append("\n");
        }
        return sb.toString();
    }
}
