package com.company.splendor.crystal;

import com.company.splendor.other.Crystal;
import lombok.Data;

//todo 宝石工厂类 严格单例模式
@Data
public abstract class BasicCrystal implements BasicCrystalActions {
    static protected int num;
    static protected Crystal color;
    //防止多次初始化
    static protected boolean flag = false;

    protected BasicCrystal(){
        num=0;
        color=null;
    }
    static protected void init(int num,Crystal color){
        if(flag) return;
        BasicCrystal.num = num;
        BasicCrystal.color = color;
        BasicCrystal.flag = true;
    }

    public static void pickTwo() {
        num-=2;
    }
    public static boolean canPickTwo() {
        return num>=4;
    }
    public static void pickOne() {
        num--;
    }
    public static boolean canPickOne() {
        return num>0;
    }

    public static void returnCrystal(int number){num+=number;}

    public static void showStatus(){
        System.out.println(color+" still remains "+num);
    }
}
