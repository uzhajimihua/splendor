package com.company.splendor.player.realplayer;

import com.company.splendor.card.cards.Card;
import com.company.splendor.gamesetting.CrystalShop;
import com.company.splendor.other.Crystal;
import com.company.splendor.player.BasicPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ai extends BasicPlayer {
    private final Random random = new Random();

    public Ai(String playerName) {
        super(playerName);
    }

    public void BuyACard() throws InterruptedException {
        List<Integer> canBuyList = new ArrayList<>(12);
        for(int i=1;i<5;i++){
            for(int j=1;j<4;j++){
                if(super.canBuyACard(j,i)) canBuyList.add((j-1)*4+i-1);
            }
        }
        int target = canBuyList.get(random.nextInt(canBuyList.size()));
        Card card = super.buyACard(target/4+1,target%4);
        System.out.println(playerName+"购买了一张发展卡！获得了"+card.getPoints()+"分，现在"+playerName+"总共有"+points+"分！");
        Thread.sleep(1000);
    }

    public void borrow() throws InterruptedException {
        int difficulty = random.nextInt(3)+1;
        int pos = random.nextInt(4)+1;
        super.borrow(difficulty,pos);
        System.out.println(playerName+"贷款了一张发展卡！现在还有"+ CrystalShop.getCrystalShop().getGold_num()+"块金矿！");
        Thread.sleep(1000);
    }

    public void repay() throws InterruptedException {
        for(int i=random.nextInt();;i=random.nextInt(golds.size())){
            if(golds.get(i).canReturn(this)){
                CrystalShop.getCrystalShop().payForGold(this,i,null);
                System.out.println(playerName+"还款了一张发展卡！现在有"+ CrystalShop.getCrystalShop().getGold_num()+"块金矿！");
                break;
            }
        }
        Thread.sleep(1000);
    }

    public void pick(int num) throws InterruptedException {
        int[] crystal_num = new int[5];
        if(num<3){
            for(int i=random.nextInt(5);;i=random.nextInt(5)){
                if(CrystalShop.getCrystalShop().canPick(Crystal.values()[i],num)){
                    crystal_num[i]=2;
                    break;
                }
            }
        }
        else {
            for(int i=0,j=random.nextInt(5);i<3;j=random.nextInt(5)){
                if(crystal_num[j]<1&&CrystalShop.getCrystalShop().canPick(Crystal.values()[j],1)) {
                    crystal_num[j]=1;i++;
                }
            }
        }
        CrystalShop.getCrystalShop().pick(crystal_num,this);
        System.out.println(playerName+"拿走了"+num+"个宝石！");
        Thread.sleep(1000);
    }

    @Override
    public int crystalCheck() throws InterruptedException {
        int sum=-10;
        for(int i:crystals) sum+=i;
        if(sum>0){
            System.out.print(playerName+"拥有了超过10个宝石！");
            for(int i=sum,j=random.nextInt(5);i>0;j=random.nextInt(5)){
                if(crystals[j]>0){
                    crystals[j]--;CrystalShop.getCrystalShop().payForCrystal(Crystal.values()[j]);i--;
                }
            }
            System.out.println(playerName+"归还了一些宝石！");
        }
        Thread.sleep(1000);
        return sum;
    }

}
