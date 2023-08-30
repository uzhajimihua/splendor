package com.company.splendor.crystal.crystals;

import com.company.splendor.crystal.BasicCrystal;
import com.company.splendor.other.Crystal;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GreenCrystal extends BasicCrystal {
    public GreenCrystal(){
        super();
    }

    public static void init(int crystal_nums) {
        init(crystal_nums,Crystal.Green);
    }
}
