package com.company.splendor.gamesetting;

import com.company.splendor.card.cards.Card;
import com.company.splendor.crystal.BasicCrystal;
import com.company.splendor.crystal.CrystalFactory;
import com.company.splendor.crystal.crystals.Gold;
import com.company.splendor.other.Crystal;
import com.company.splendor.player.BasicPlayer;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Data
public class CrystalShop {
    private int gold_num;
    private final CrystalFactory crystalFactory;

    private static CrystalShop crystalShop;

    private CrystalShop(){
        gold_num = 5;
        crystalFactory = CrystalFactory.getCrystalFactory();
    }

    static public void init(){
        crystalShop = new CrystalShop();
    }

    static public CrystalShop getCrystalShop(){
        return crystalShop;
    }

    //拿宝石相关
    //是否可以拿2个
    public boolean canGetTwo(){
        int i=0;
        for(Crystal crystal:Crystal.values()){
            i+=(crystalFactory.getCrystal(crystal).canPickTwo()?1:0);
        }
        return i>=1;
    }
    //是否可以拿3个
    public boolean canGetThree(){
        int i=0;
        for(Crystal crystal:Crystal.values()){
            i+=(crystalFactory.getCrystal(crystal).canPickOne()?1:0);
        }
        return i>=3;
    }
    //检查单个宝石是否足够
    public boolean canPick(Crystal crystal, int num){
        BasicCrystal basicCrystal = crystalFactory.getCrystal(crystal);
        return num==1?basicCrystal.canPickOne():basicCrystal.canPickTwo();
    }
    //拿走宝石
    public void pick(int[] nums,BasicPlayer player){
        for(int i=0;i<5;i++){
            switch (nums[i]){
                case 1:crystalFactory.getCrystal(Crystal.values()[i]).pickOne();break;
                case 2:crystalFactory.getCrystal(Crystal.values()[i]).pickTwo();break;
                default:continue;
            }
            player.getCrystals()[i]+=nums[i];
        }
    }

    //购买发展卡
    public void payForCard(Card card){
        for(Map.Entry<Crystal,Integer> e:card.getSolution().entrySet()){
            crystalFactory.getCrystal(e.getKey()).returnCrystal(e.getValue());
        }
    }

    //金块相关
    public int getGold_num(){
        return gold_num;
    }
    public boolean canLent(){
        return gold_num>0;
    }
    public void lent(){
        gold_num--;
    }
    public void back(){
        gold_num++;
    }

    //是否至少可以还一张卡
    public boolean canRepay(BasicPlayer player){
        List<Gold> golds = player.getGolds();
        for(Gold gold:golds){
            if(gold.canReturn(player)) return true;
        }
        return false;
    }

    //偿还赎金
    public void payForGold(BasicPlayer player, int pos, Scanner scanner){
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
    public void payForCrystal(Crystal crystal){
        crystalFactory.getCrystal(crystal).returnCrystal(1);
    }

    public void showStatus(){
        System.out.println("目前的状况:");
        DrawCards.showCards();
        StringBuilder sb = new StringBuilder();
        for(Crystal crystal:Crystal.values()){
            sb.append(crystalFactory.getCrystal(crystal).showStatus());
        }
        sb.deleteCharAt(sb.length()-2).append(String.format("%nGold  remains%2d%n",gold_num));
        System.out.println(sb);
    }
}
