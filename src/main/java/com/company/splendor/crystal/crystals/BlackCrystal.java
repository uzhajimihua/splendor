package com.company.splendor.crystal.crystals;

import com.company.splendor.crystal.BasicCrystal;
import com.company.splendor.other.Crystal;
import lombok.Getter;

@Getter
public class BlackCrystal extends BasicCrystal {
    public BlackCrystal(){
        super();
    }

    public BlackCrystal(int crystal_nums) {
        super(crystal_nums,Crystal.Black);
    }
}
