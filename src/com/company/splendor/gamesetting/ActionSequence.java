package com.company.splendor.gamesetting;

import com.company.splendor.other.Crystal;
import com.company.splendor.player.BasicPlayer;
import com.company.splendor.player.realplayer.Ai;
import com.company.splendor.player.realplayer.Gamer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ActionSequence {
    static private BasicPlayer[] players;
    static private int cur_player=0;
    static private boolean isInit = false;

    static public void initPlayers(int player_num) throws InterruptedException {
        if(isInit) return;
        Random random = new Random();
        players = new BasicPlayer[player_num];
        players[random.nextInt(player_num)] = new Gamer("您");
        for(int i=0;i<player_num;i++){
            if(players[i]!=null) continue;
            players[i]=new Ai("player"+(i+1));
        }
        prepare();
        isInit = true;
    }
    static public void prepare() throws InterruptedException {
        if(isInit) return;
        for(BasicPlayer player:players){
            System.out.println(player.getPlayerName()+" 准备就绪！");
            Thread.sleep(1000);
        }
        System.out.println("游戏开始！");
    }

    static public BasicPlayer getCurrentPlayer(){
        int cur = cur_player;
        cur_player = (cur_player+1)%(players.length);
        return players[cur];
    }

    static public void aiAction(Ai ai){
        Random random = new Random();
        System.out.println("现在是"+ai.getPlayerName()+"的回合");
        String availableActions = preCheck(ai).toString();
        //随机权重
        List<Integer> actions = new ArrayList<>();
        for(int i=1;i<6;i++) if(availableActions.indexOf(i+'0')!=-1) actions.add(i);
        //平均概率被选中
        switch (actions.get(random.nextInt(actions.size()))){
            case 1:ai.BuyACard();break;
            case 2:ai.borrow();break;
            case 3:ai.repay();break;
            case 4:ai.pick(3);break;
            case 5:ai.pick(2);break;
            default:System.out.println(ai.getPlayerName()+"不知所措！");
        }
        ai.crystalCheck();
    }
    static public boolean gamerAction(Gamer gamer, Scanner scanner){
        System.out.println("现在是您的回合：");
        CrystalShop.showStatus();
        for(BasicPlayer player:players){
            player.showStatus();
        }
        //gamer.showStatus();
        System.out.println(preCheck(gamer));
        int difficulty,pos;
        Crystal crystal=null;
        int[] nums = new int[5];
        switch (scanner.nextInt()){
            case 0:System.out.println("拜拜！");return true;
            case 1: System.out.println("您想买的发展卡是(难度序号,顺序序号)：");
                    difficulty = scanner.nextInt();pos= scanner.nextInt();
                    while(gamer.canBuyACard(difficulty,pos)){
                        System.out.println("买不起！重新挑一张！");
                        difficulty = scanner.nextInt();
                        pos = scanner.nextInt();
                    }
                    CrystalShop.payForCard(gamer.buyACard(difficulty,pos));
                    break;
            case 2: System.out.println("您想贷款的发展卡是(难度序号,顺序序号)：");
                    difficulty = scanner.nextInt();pos= scanner.nextInt();
                    while(difficulty<1||difficulty>3||pos<1||pos>4){
                        System.out.println("错误的卡牌！重新挑一张！");
                        difficulty = scanner.nextInt();
                        pos = scanner.nextInt();
                    }
                    gamer.borrow(difficulty,pos);
                    CrystalShop.lent();
                    break;
            case 3: System.out.println("您想赎回的发展卡是(难度序号,顺序序号)：");
                    pos= scanner.nextInt();
                    while((pos<1||pos>3)||(!gamer.getGolds().get(pos).canReturn(gamer))){
                        System.out.println("错误的卡牌！重新挑一张！");
                        pos = scanner.nextInt();
                    }
                    CrystalShop.payForGold(gamer,pos,scanner);
                    break;
            case 4: System.out.println("您想拿的宝石颜色是(可以拿三种颜色各一个宝石)：");
                    List<Crystal> list = new ArrayList<>(3);
                    while(list.size()<3){
                        try{
                            crystal = Crystal.valueOf(scanner.next());
                            if(!CrystalShop.canPick(crystal,1)){
                                System.out.println("这种宝石不能再拿了！重新选一个：");
                            }
                            list.add(crystal);
                        }catch (IllegalArgumentException e){
                            System.out.println("没有这种宝石！重新选一个：");
                        }
                    }
                    for(Crystal crystal1:list){
                        nums[crystal1.ordinal()] = 1;
                    }
                    CrystalShop.pick(nums,gamer);
                    break;
            case 5: System.out.println("您想拿的宝石颜色是(可以拿一种颜色的两个宝石，不足4个的宝石不能选择)：");
                    while(crystal==null){
                        try{
                            crystal = Crystal.valueOf(scanner.next());
                            if(!CrystalShop.canPick(crystal,2)){
                                System.out.println("这种宝石不能再拿了！重新选一个：");
                                crystal = null;
                            }
                        }catch (IllegalArgumentException e){
                            System.out.println("没有这种宝石！重新选一个：");
                            crystal = null;
                        }
                    }
                    nums[crystal.ordinal()] = 2;
                    CrystalShop.pick(nums,gamer);
                    break;
            default:
        }
        //宝石数量检查
        if(gamer.crystalCheck()>0){
            System.out.println("宝石不能超过10个，你需要归还"+gamer.crystalCheck()+"个宝石！");
            System.out.println("您现在持有的宝石有：");
            for(Crystal crystal1:Crystal.values())
                System.out.print(crystal1+":"+gamer.getCrystals()[crystal1.ordinal()]+" ");
            System.out.println("请输入你想归还的宝石(每次还一个，输入宝石名字)：");
            while(gamer.crystalCheck()>0){
                try{
                    crystal = Crystal.valueOf(scanner.next());
                    if(gamer.getCrystals()[crystal.ordinal()]-1<0){
                        System.out.println("你的"+crystal+"宝石数量为0！");
                    }else{
                        gamer.getCrystals()[crystal.ordinal()]--;
                        CrystalShop.payForCrystal(crystal);
                    }
                }
                catch (IllegalArgumentException e){
                    System.out.println("你没有这种宝石,gold也不是宝石");
                }
            }
        }
        return false;
    }

    static private StringBuilder preCheck(BasicPlayer player){
        StringBuilder sb = new StringBuilder("您想要:0 退出游戏,");
        if(DrawCards.canChoose(player)) sb.append("1 购买发展卡");
        if(CrystalShop.canLent()&&player.getGolds().size()<3) sb.append("2 贷款发展卡,");
        if(CrystalShop.canRepay(player)) sb.append("3 还款发展卡,");
        if(CrystalShop.canGetThree()) sb.append("4 拿三种宝石,");
        if(CrystalShop.canGetTwo()) sb.append("5 拿一种宝石,");
        return sb.append("目前不支持反悔,输错了就空过哦");
    }
}
