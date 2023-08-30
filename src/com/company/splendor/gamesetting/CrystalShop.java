package com.company.splendor.gamesetting;

import com.company.splendor.card.cards.Card;
import com.company.splendor.crystal.Gold;
import com.company.splendor.crystal.crystals.*;
import com.company.splendor.other.Crystal;
import com.company.splendor.player.BasicPlayer;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CrystalShop {
    //static private BasicCrystal[] crystals;
    static private int gold_num;
    static int crystal_nums;
    static private boolean isInit = false;

    static public void init(int player_num){
        if(isInit) return;
        crystal_nums = player_num==2?4:(player_num==3?5:7);
        WhiteCrystal.init(crystal_nums);
        BlackCrystal.init(crystal_nums);
        RedCrystal.init(crystal_nums);
        GreenCrystal.init(crystal_nums);
        BlueCrystal.init(crystal_nums);

        gold_num = 5;
        isInit = true;
    }

    static public int getGold_num(){
        return gold_num;
    }
    static public boolean canLent(){
        return gold_num>0;
    }
    static public void lent(){
        gold_num--;
    }
    static public void back(){
        gold_num++;
    }

    //是否至少可以还一张卡
    static public boolean canRepay(BasicPlayer player){
        List<Gold> golds = player.getGolds();
        for(Gold gold:golds){
            if(gold.canReturn(player)) return true;
        }
        return false;
    }
    //是否可以拿2个
    static public boolean canGetTwo(){
        return WhiteCrystal.canPickTwo()|BlackCrystal.canPickTwo()|RedCrystal.canPickTwo()|BlueCrystal.canPickTwo()|GreenCrystal.canPickTwo();
    }
    //是否可以拿3个
    static public boolean canGetThree(){
        int i=0;
        i+=(WhiteCrystal.canPickOne()?1:0);
        i+=(BlackCrystal.canPickOne()?1:0);
        i+=(RedCrystal.canPickOne()?1:0);
        i+=(BlueCrystal.canPickOne()?1:0);
        i+=(GreenCrystal.canPickOne()?1:0);
        return i>=3;
    }
    //检查单个宝石是否足够
    static public boolean canPick(Crystal crystal, int num){
        switch (crystal){
            case White:return num==1?WhiteCrystal.canPickOne():WhiteCrystal.canPickTwo();
            case Black:return num==1?BlackCrystal.canPickOne():BlackCrystal.canPickTwo();
            case Red:return num==1?RedCrystal.canPickOne():RedCrystal.canPickTwo();
            case Green:return num==1?GreenCrystal.canPickOne():GreenCrystal.canPickTwo();
            case Blue:return num==1?BlueCrystal.canPickOne():BlueCrystal.canPickTwo();
        }
        return false;
    }
    //拿走宝石
    static public void pick(int[] nums,BasicPlayer player){
        if(nums[0]!=0){
            if (nums[0] == 1) {
                WhiteCrystal.pickOne();
            } else {
                WhiteCrystal.pickTwo();
            }
        }
        if(nums[1]!=0){
            if (nums[1] == 1) {
                BlackCrystal.pickOne();
            } else {
                BlackCrystal.pickTwo();
            }
        }
        if(nums[2]!=0){
            if (nums[2] == 1) {
                RedCrystal.pickOne();
            } else {
                RedCrystal.pickTwo();
            }
        }
        if(nums[3]!=0){
            if (nums[3] == 1) {
                GreenCrystal.pickOne();
            } else {
                GreenCrystal.pickTwo();
            }
        }
        if(nums[4]!=0){
            if (nums[4] == 1) {
                BlueCrystal.pickOne();
            } else {
                BlueCrystal.pickTwo();
            }
        }
        for(int i=0;i<5;i++){
            player.getCrystals()[i]+=nums[i];
        }
    }

    //购买发展卡
    static public void payForCard(Card card){
        for(Map.Entry<Crystal,Integer> e:card.getSolution().entrySet()){
            switch (e.getKey()){
                case White:WhiteCrystal.returnCrystal(e.getValue());break;
                case Black:BlackCrystal.returnCrystal(e.getValue());break;
                case Red:RedCrystal.returnCrystal(e.getValue());break;
                case Green:GreenCrystal.returnCrystal(e.getValue());break;
                case Blue:BlackCrystal.returnCrystal(e.getValue());break;
            }
        }
    }
    //偿还赎金
    static public void payForGold(BasicPlayer player, int pos, Scanner scanner){
        Gold gold = player.repay(pos);
        Crystal owe = gold.Return(player);
        Card card = gold.getCard();
        if(owe==null){
            System.out.println("您想将黄金用于代替(不属于其中任何一个会随机选一个)：");
            for(Crystal crystal:card.getSolution().keySet())
                System.out.print(crystal.ordinal()+" "+crystal+" ");
            int i = scanner.nextInt();
            if(i>=0&&i<5){
                if(card.getSolution().containsKey(Crystal.values()[i])) owe = Crystal.values()[i];
                else owe = card.getSolution().keySet().iterator().next();
            }
            else owe = card.getSolution().keySet().iterator().next();
        }
        card.getSolution().put(owe,card.getSolution().get(owe)-1);
        payForCard(card);
        back();
    }
    //返还过多宝石
    static public void payForCrystal(Crystal crystal){
        switch (crystal){
            case White:WhiteCrystal.returnCrystal(1);break;
            case Black:BlackCrystal.returnCrystal(1);break;
            case Red:RedCrystal.returnCrystal(1);break;
            case Green:GreenCrystal.returnCrystal(1);break;
            case Blue:BlackCrystal.returnCrystal(1);break;
        }
    }

    static public void showStatus(){
        System.out.println("目前的状况：\n");
        DrawCards.showCards();
        for(Crystal crystal:Crystal.values()){
            switch (crystal){
                case White:WhiteCrystal.showStatus();break;
                case Black:BlackCrystal.showStatus();break;
                case Red:RedCrystal.showStatus();break;
                case Green:GreenCrystal.showStatus();break;
                case Blue:BlackCrystal.showStatus();break;
            }
        }
        System.out.println("Gold remains "+gold_num);
    }
}
