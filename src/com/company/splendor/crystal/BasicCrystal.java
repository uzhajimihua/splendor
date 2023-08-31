package com.company.splendor.crystal;

import com.company.splendor.other.Crystal;
import lombok.Data;

import java.util.Formatter;


@Data
public abstract class BasicCrystal implements BasicCrystalActions {
    protected int num;
    protected Crystal crystalColor;

    protected BasicCrystal(){
    }
    protected BasicCrystal(int num,Crystal color){
        this.num = num;
        this.crystalColor = color;
    }

    public void pickTwo() {
        num-=2;
    }
    public boolean canPickTwo() {
        return num>=4;
    }
    public void pickOne() {
        num--;
    }
    public boolean canPickOne() {
        return num>0;
    }
    public void returnCrystal(int number){num+=number;}
    public String showStatus(){
        return new Formatter(new StringBuilder()).format("%s 剩余%2d个, ",crystalColor,num).toString();
    }
}
