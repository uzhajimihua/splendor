package com.company.splendor.crystal.crystals;

import com.company.splendor.crystal.BasicCrystal;
import com.company.splendor.other.Crystal;
import lombok.Getter;

@Getter
public class RedCrystal extends BasicCrystal {
    public RedCrystal(){
        super();
    }

    public RedCrystal(int crystal_nums) {
        super(crystal_nums,Crystal.Red);
    }
}
