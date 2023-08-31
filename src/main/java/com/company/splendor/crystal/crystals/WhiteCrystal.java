package com.company.splendor.crystal.crystals;

import com.company.splendor.crystal.BasicCrystal;
import com.company.splendor.other.Crystal;
import lombok.Getter;

@Getter
public class WhiteCrystal extends BasicCrystal {
    public WhiteCrystal(){
        super();
    }

    public WhiteCrystal(int crystal_nums) {
        super(crystal_nums,Crystal.White);
    }
}
