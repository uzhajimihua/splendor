package com.company.splendor.gamesetting;

import com.company.splendor.card.Noble;
import com.company.splendor.gamesetting.gamepreparation.GenerateNobles;
import com.company.splendor.other.Crystal;
import lombok.Data;

import java.util.Formatter;
import java.util.Map;

@Data
public class PresentNobles {
    static private Noble[] nobles;

    static public void initNobles(int player_num){
        nobles = GenerateNobles.generate(player_num+1);
        System.out.println("前来观看的贵族有:");
        System.out.println(showNoble());
    }

    static public Noble[] getNobles(){
        return nobles;
    }

    static public void showNobles(){
        System.out.println("仍然在观望的贵族有:");
        System.out.println(showNoble());

    }
    static private StringBuilder showNoble(){
        StringBuilder sb = new StringBuilder();
        Formatter fm = new Formatter(sb);
        for(Noble n:nobles){
            if(n.getOwner()==null) fm.format("%-"+(n.getMap().entrySet().size()*8-1)+"s ",n.getName()+":");
        }
        sb.append("\n");
        for(Noble n:nobles){
            if(n.getOwner()!=null) continue;
            for(Map.Entry<Crystal,Integer> e:n.getMap().entrySet())
                fm.format("%-5s:%d,",e.getKey(),e.getValue());
            sb.deleteCharAt(sb.length()-1).append(" ");
        }
        return sb.deleteCharAt(sb.length()-1).append("\n");
    }
}
