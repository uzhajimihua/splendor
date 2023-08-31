package com.company.splendor.crystal.crystals;

import com.company.splendor.crystal.BasicCrystal;
import com.company.splendor.other.Crystal;
import lombok.Getter;

@Getter
public class GreenCrystal extends BasicCrystal {
    public GreenCrystal(){
        super();
    }

    public GreenCrystal(int crystal_nums) {
        super(crystal_nums,Crystal.Green);
    }
}
