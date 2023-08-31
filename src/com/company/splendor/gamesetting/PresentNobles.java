package com.company.splendor.gamesetting;

import com.company.splendor.card.Noble;
import com.company.splendor.gamesetting.gamepreparation.GenerateNobles;
import com.company.splendor.other.Crystal;
import lombok.Data;

import java.util.Map;

@Data
public class PresentNobles {
    static private Noble[] nobles;

    static public void initNobles(int player_num){
        nobles = GenerateNobles.generate(player_num+1);
        System.out.println("前来观看的贵族有:");
        for(Noble noble:nobles)
            System.out.println(showNoble(noble));
    }

    static public Noble[] getNobles(){
        return nobles;
    }

    static public void showNobles(){
        System.out.println("仍然在观望的贵族有:");
        for(Noble noble:nobles){
            if(noble.getOwner()==null) System.out.println(showNoble(noble));
        }
    }
    static private StringBuilder showNoble(Noble noble){
        StringBuilder sb = new StringBuilder();
        sb.append(noble.getName()).append("希望您有:\n");
        for(Map.Entry<Crystal,Integer> e:noble.getMap().entrySet())
            sb.append(e.getKey()).append(":").append(e.getValue()).append(",");
        return sb.delete(sb.length()-1,sb.length());
    }
}
