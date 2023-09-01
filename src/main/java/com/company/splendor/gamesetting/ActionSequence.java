package com.company.splendor.gamesetting;

import com.company.splendor.other.Crystal;
import com.company.splendor.player.BasicPlayer;
import com.company.splendor.player.realplayer.Ai;
import com.company.splendor.player.realplayer.Gamer;
import lombok.Data;

import java.util.*;

@Data
public class ActionSequence {
    private BasicPlayer[] players;
    private int cur_player;
    private int playerNum;

    static private final ActionSequence sequence = new ActionSequence();

    private ActionSequence(){
        cur_player = 0;
        players = null;
        playerNum = 0;
    }

    static public ActionSequence getSequence(){
        return sequence;
    }
    //初始化私有实例
    static public void setSequence(int playerNum) throws InterruptedException {
        sequence.setPlayerNum(playerNum);
        sequence.setPlayers(new BasicPlayer[playerNum]);
        sequence.initPlayers();
    }

    public void initPlayers() throws InterruptedException {
        Random random = new Random();
        players[random.nextInt(playerNum)] = new Gamer("您");
        for(int i=0;i<playerNum;i++){
            if(players[i]!=null) continue;
            players[i]=new Ai("player"+(i+1));
        }
        prepare();
    }
    public void prepare() throws InterruptedException {
        for(BasicPlayer player:players){
            System.out.println(player.getPlayerName()+" 准备就绪！");
            Thread.sleep(1000);
        }
        System.out.println("游戏开始！");
    }

    public BasicPlayer getCurrentPlayer(){
        int cur = cur_player;
        cur_player = (cur_player+1)%(players.length);
        return players[cur];
    }

    public void aiAction(Ai ai) throws InterruptedException {
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
    public boolean gamerAction(Gamer gamer, Scanner scanner) throws InterruptedException {
        System.out.println("现在是您的回合：");
        CrystalShop.getCrystalShop().showStatus();
        PresentNobles.showNobles();
        for(BasicPlayer player:players){
            player.showStatus();
        }
        System.out.println(preCheck(gamer));
        int difficulty = 0,pos=0;
        Crystal crystal=null;
        int[] nums = new int[5];
        int action = -1;
        while(action==-1){
            try{
                action = scanner.nextInt();
            }catch (InputMismatchException e){
                System.out.println("错误的输入，请输入数字:");
                action = -1;
            }finally {
                if(scanner.hasNextLine()) scanner.nextLine();
            }

        }

        switch (action){
            case 0:System.out.println("拜拜！");return true;
            case 1: System.out.println("您想买的发展卡是(难度序号,顺序序号)：");
                    while(difficulty==0||pos==0){
                        try{
                            while(difficulty==0||pos==0){
                                if(difficulty==0) difficulty = scanner.nextInt();
                                if(pos==0) pos = scanner.nextInt();
                                if (!gamer.canBuyACard(difficulty,pos)){
                                    System.out.println("买不起！重新挑一张！");
                                    difficulty = 0;
                                    pos = 0;
                                }
                            }
                        }catch (InputMismatchException e){
                            if(difficulty==0) System.out.println("第一个参数有问题，请重新输入！");
                            if(pos==0)  System.out.println("第二个参数有问题，请重新输入！");
                        }
                        finally {
                            if (scanner.hasNextLine()) scanner.nextLine();
                        }
                    }
                    CrystalShop.getCrystalShop().payForCard(gamer.buyACard(difficulty,pos));
                    break;
            case 2: System.out.println("您想贷款的发展卡是(难度序号,顺序序号):");
                    while (difficulty==0||pos==0){
                        try{
                            if(difficulty==0) difficulty = scanner.nextInt();
                            if(pos==0) pos = scanner.nextInt();
                            if(difficulty<1||difficulty>3||pos<1||pos>4){
                                System.out.println("错误的卡牌！重新挑一张！");
                                difficulty = 0;
                                pos = 0;
                            }
                        }catch (InputMismatchException e){
                            if(difficulty==0) System.out.println("第一个参数有问题，请重新输入！");
                            if(pos==0)  System.out.println("第二个参数有问题，请重新输入！");
                        }
                        finally {
                            if (scanner.hasNextLine()) scanner.nextLine();
                        }
                    }
                    gamer.borrow(difficulty,pos);
                    break;
            case 3: System.out.println("您想赎回的发展卡是(序号):");
                    while(pos==0){
                        try{
                            pos= scanner.nextInt();
                            if((pos<1||pos>gamer.getGolds().size())||(!gamer.getGolds().get(pos-1).canReturn(gamer))){
                                System.out.println("不能赎回的卡牌！请重新输入！");
                                pos = 0;
                            }
                        }catch (InputMismatchException e){
                            System.out.println("错误的输入！请重新输入：");
                        }
                        finally {
                            if (scanner.hasNextLine()) scanner.nextLine();
                        }
                    }
                    CrystalShop.getCrystalShop().payForGold(gamer,pos,scanner);
                    break;
            case 4: System.out.println("您想拿的宝石颜色是(可以拿三种颜色各一个宝石)：");
                    List<Crystal> list = new ArrayList<>(3);
                    while(list.size()<3){
                        try{
                            while(list.size()<3){
                                crystal = Crystal.valueOf(upClassOfFirst(scanner.next()));
                                if((!CrystalShop.getCrystalShop().canPick(crystal,1))||list.contains(crystal)){
                                    System.out.println(crystal+"不能拿了！重新选一个：");
                                }
                                else list.add(crystal);
                            }
                        }catch (IllegalArgumentException e){
                            System.out.println("没有第"+list.size()+1+"种宝石！重新选"+(3-list.size())+"个：");
                        }catch (InputMismatchException e){
                            System.out.println("输入有错误！请重新从第"+list.size()+1+"个开始重新输入！");
                        }
                        finally {
                            if (scanner.hasNextLine()) scanner.nextLine();
                        }
                    }
                    for(Crystal crystal1:list){
                        nums[crystal1.ordinal()] = 1;
                    }
                    CrystalShop.getCrystalShop().pick(nums,gamer);
                    break;
            case 5: System.out.println("您想拿的宝石颜色是(可以拿一种颜色的两个宝石，不足4个的宝石不能选择)：");
                    while(crystal==null){
                        try{
                            crystal = Crystal.valueOf(upClassOfFirst(scanner.next()));
                            if(!CrystalShop.getCrystalShop().canPick(crystal,2)){
                                System.out.println("这种宝石不能再拿了！重新选一个:");
                                crystal = null;
                            }
                        }catch (IllegalArgumentException e){
                            System.out.println("没有这种宝石！重新选一个:");
                        }catch (InputMismatchException e){
                            System.out.println("输入有错误！请重新输入:");
                        }
                        finally {
                            if (scanner.hasNextLine()) scanner.nextLine();
                        }
                    }
                    nums[crystal.ordinal()] = 2;
                    CrystalShop.getCrystalShop().pick(nums,gamer);
                    break;
            default:System.out.println("输错了哈，你空过了！");
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
                        CrystalShop.getCrystalShop().payForCrystal(crystal);
                    }
                }
                catch (IllegalArgumentException e){
                    System.out.println("你没有这种宝石,gold也不是宝石");
                }
            }
        }
        return false;
    }

    private StringBuilder preCheck(BasicPlayer player){
        StringBuilder sb = new StringBuilder("您想要:0 退出游戏,");
        if(DrawCards.canChoose(player)) sb.append("1 购买发展卡");
        if(CrystalShop.getCrystalShop().canLent()&&player.getGolds().size()<3) sb.append("2 贷款发展卡,");
        if(CrystalShop.getCrystalShop().canRepay(player)) sb.append("3 还款发展卡,");
        if(CrystalShop.getCrystalShop().canGetThree()) sb.append("4 拿三种宝石,");
        if(CrystalShop.getCrystalShop().canGetTwo()) sb.append("5 拿一种宝石,");
        return sb.append("目前不支持反悔,输错了就空过哦");
    }

    //首字母大写工具方法
    private static String upClassOfFirst(String str){
        char[] cs = str.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }
}
