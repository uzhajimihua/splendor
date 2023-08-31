package com.company.splendor.crystal;

import com.company.splendor.crystal.crystals.*;
import com.company.splendor.gamesetting.ActionSequence;
import com.company.splendor.other.Crystal;

public class CrystalFactory {
    private final WhiteCrystal whiteCrystal;
    private final BlackCrystal blackCrystal;
    private final RedCrystal redCrystal;
    private final GreenCrystal greenCrystal;
    private final BlueCrystal blueCrystal;

    private static CrystalFactory crystalFactory;

    private CrystalFactory(int player_num){
        int crystal_num = player_num==2?4:(player_num==3?5:7);
        whiteCrystal = new WhiteCrystal(crystal_num);
        blackCrystal = new BlackCrystal(crystal_num);
        redCrystal = new RedCrystal(crystal_num);
        greenCrystal = new GreenCrystal(crystal_num);
        blueCrystal = new BlueCrystal(crystal_num);
    }

    public static CrystalFactory getCrystalFactory() {
        if(crystalFactory == null){
            crystalFactory = new CrystalFactory(ActionSequence.getSequence().getPlayerNum());
        }
        return crystalFactory;
    }

    //获取对应颜色宝石实例
    public BasicCrystal getCrystal(Crystal crystal){
        switch (crystal){
            case White:return whiteCrystal;
            case Black:return blackCrystal;
            case Red:return redCrystal;
            case Green:return greenCrystal;
            case Blue:return blueCrystal;
            default:return null;
        }
    }
}

