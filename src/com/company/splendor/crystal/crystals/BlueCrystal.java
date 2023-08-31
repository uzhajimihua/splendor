package com.company.splendor.crystal.crystals;

import com.company.splendor.crystal.BasicCrystal;
import com.company.splendor.other.Crystal;
import lombok.Getter;

@Getter
public class BlueCrystal extends BasicCrystal {
    public BlueCrystal(){
        super();
    }

    public BlueCrystal(int crystal_nums) {
        super(crystal_nums,Crystal.Blue);
    }
}
