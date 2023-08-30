package com.company.splendor.gamesetting.gamepreparation;

import com.company.splendor.card.cards.Card;
import com.company.splendor.other.Crystal;

import java.util.HashMap;
import java.util.Map;

public class GenerateCards {
    private static final Crystal[] crystals=Crystal.values();
    private static int cur;
    private static Map<Crystal,Integer> map;

    //3级牌 20张 4种*5张
    public static Card[] generate3(){
        Card[] cards = new Card[20];

        //5分牌 5张 本色3+顺色7 奖励本色
        for(cur=0;cur<5;cur++){
            map = new HashMap<>();
            map.put(crystals[cur],3);
            map.put(crystals[(cur+1)%5],7);
            cards[cur]=new Card(3,5,crystals[cur],map);
        }

        //4分牌 5张 顺色7 奖励本色
        assert cur==5;
        for(;cur<10;cur++){
            map = new HashMap<>();
            map.put(crystals[(cur+1)%5],7);
            cards[cur]=new Card(3,4,crystals[cur%5],map);
        }

        //4分牌 5张 本色 3 顺色 6 对色 3 奖励本色
        assert cur==10;
        for(;cur<15;cur++){
            map = new HashMap<>();
            map.put(crystals[cur%5], 3);
            map.put(crystals[(cur+1)%5], 6);
            map.put(crystals[(cur+2)%5], 3);
            cards[cur]=new Card(3,4,crystals[cur%5],map);
        }

        //3分牌 5张 除本色外4色 对色5其他3 奖励本色
        assert cur==15;
        for(;cur<20;cur++){
            map = new HashMap<>();
            map.put(crystals[(cur+1)%5], 3);
            map.put(crystals[(cur+2)%5], 5);
            map.put(crystals[(cur+3)%5], 3);
            map.put(crystals[(cur+4)%5], 3);
            cards[cur]=new Card(3,3,crystals[cur%5],map);
        }

        return cards;
    }

    //2级牌 30张 6种*5张
    public static Card[] generate2(){
        Card[] cards = new Card[30];

        //3分牌 5张 本色6 奖励本色
        for(cur=0;cur<5;cur++){
            map = new HashMap<>();
            map.put(crystals[cur],6);
            cards[cur]=new Card(2,3,crystals[cur],map);
        }

        //2分牌 5张 35组合
        //白黑 顺3顺对5
        assert cur==5;
        for(;cur<7;cur++){
            map = new HashMap<>();
            map.put(crystals[(cur+1)%5],3);
            map.put(crystals[(cur+2)%5],5);
            cards[cur]=new Card(2,2,crystals[cur%5],map);
        }

        //红 逆3逆对5
        assert cur==7;
        map = new HashMap<>();
        map.put(crystals[(cur-1)%5],3);
        map.put(crystals[(cur-2)%5],5);
        cards[cur]=new Card(2,2,crystals[cur%5],map);
        cur++;

        //绿蓝 本3顺5
        assert cur==8;
        for(;cur<10;cur++){
            map = new HashMap<>();
            map.put(crystals[(cur)%5],3);
            map.put(crystals[(cur+1)%5],5);
            cards[cur]=new Card(2,2,crystals[cur%5],map);
        }

        //2分牌 5张 单色5 奖励本色
        //白 顺对5
        assert cur==10;
        map = new HashMap<>();
        map.put(crystals[(cur+2)%5],5);
        cards[cur]=new Card(2,2,crystals[cur%5],map);
        cur++;
        //黑红 逆5
        assert cur==11;
        for(;cur<13;cur++){
            map = new HashMap<>();
            map.put(crystals[(cur-1)%5],5);
            cards[cur]=new Card(2,2,crystals[cur%5],map);
        }
        //绿蓝 本5
        assert cur==13;
        for(;cur<15;cur++){
            map = new HashMap<>();
            map.put(crystals[(cur)%5],5);
            cards[cur]=new Card(2,2,crystals[cur%5],map);
        }

        //2分牌 5张 顺2 顺对4 逆对2 奖励本色
        assert cur==15;
        for(;cur<20;cur++){
            map = new HashMap<>();
            map.put(crystals[(cur+1)%5], 2);
            map.put(crystals[(cur+2)%5], 4);
            map.put(crystals[(cur-2)%5], 2);
            cards[cur]=new Card(2,2,crystals[cur%5],map);
        }

        //1分牌 5张 2本色 3顺对 3逆
        assert cur==20;
        for(;cur<25;cur++){
            map = new HashMap<>();
            map.put(crystals[(cur)%5], 2);
            map.put(crystals[(cur+2)%5], 3);
            map.put(crystals[(cur-1)%5], 3);
            cards[cur]=new Card(2,2,crystals[cur%5],map);
        }

        //1分牌 5张 322组合
        //白  2顺临 2顺对 3逆对
        assert cur==25;
        map = new HashMap<>();
        map.put(crystals[(cur+1)%5], 2);
        map.put(crystals[(cur+2)%5], 2);
        map.put(crystals[(cur-2)%5], 3);
        cards[cur]=new Card(2,1,crystals[cur%5],map);
        cur++;
        //黑  2顺对 3逆临 2逆对
        assert cur==26;
        map = new HashMap<>();
        map.put(crystals[(cur+1)%5], 2);
        map.put(crystals[(cur-1)%5], 3);
        map.put(crystals[(cur-2)%5], 2);
        cards[cur]=new Card(2,1,crystals[cur%5],map);
        cur++;
        //红 2本色 3逆临 2逆对
        assert cur==27;
        map = new HashMap<>();
        map.put(crystals[(cur)%5], 2);
        map.put(crystals[(cur-1)%5], 3);
        map.put(crystals[(cur-2)%5], 2);
        cards[cur]=new Card(2,1,crystals[cur%5],map);
        cur++;
        //绿 3顺临 2顺对 2逆对
        assert cur==28;
        map = new HashMap<>();
        map.put(crystals[(cur+1)%5], 3);
        map.put(crystals[(cur+2)%5], 2);
        map.put(crystals[(cur-2)%5], 2);
        cards[cur]=new Card(2,1,crystals[cur%5],map);
        cur++;
        //蓝 2本色 2逆临 3逆对
        assert cur==29;
        map = new HashMap<>();
        map.put(crystals[(cur)%5], 2);
        map.put(crystals[(cur-1)%5], 2);
        map.put(crystals[(cur-2)%5], 3);
        cards[cur]=new Card(2,1,crystals[cur%5],map);

        return cards;
    }

    //1级牌 40张 8种*5张
    public static Card[] generate1(){
        Card[] cards = new Card[40];

        //1分牌 5张 4逆对 奖励本色
        for(cur=0;cur<5;cur++){
            map = new HashMap<>();
            map.put(crystals[(cur+3)%5],4);
            cards[cur]=new Card(1,1,crystals[cur],map);
        }

        //0分 5张 3色 311 奖励本色
        //白 3本色 1顺临 1逆临
        assert cur==5;
        map = new HashMap<>();
        map.put(crystals[(cur)%5], 3);
        map.put(crystals[(cur+1)%5], 1);
        map.put(crystals[(cur-1)%5], 1);
        cards[cur]=new Card(1,0,crystals[cur%5],map);
        cur++;
        //黑 1本色 3顺临 1顺对
        assert cur==6;
        map = new HashMap<>();
        map.put(crystals[(cur)%5], 1);
        map.put(crystals[(cur+1)%5], 3);
        map.put(crystals[(cur+2)%5], 1);
        cards[cur]=new Card(1,0,crystals[cur%5],map);
        cur++;
        //红 1本色 3逆临 1逆对
        assert cur==7;
        map = new HashMap<>();
        map.put(crystals[(cur)%5], 1);
        map.put(crystals[(cur-1)%5], 3);
        map.put(crystals[(cur-2)%5], 1);
        cards[cur]=new Card(1,0,crystals[cur%5],map);
        cur++;
        //绿 1本色 3顺临 1顺对
        assert cur==8;
        map = new HashMap<>();
        map.put(crystals[(cur)%5], 1);
        map.put(crystals[(cur+1)%5], 3);
        map.put(crystals[(cur+2)%5], 1);
        cards[cur]=new Card(1,0,crystals[cur%5],map);
        cur++;
        //蓝  1本色 3逆临 1逆对
        assert cur==9;
        map = new HashMap<>();
        map.put(crystals[(cur)%5], 1);
        map.put(crystals[(cur-1)%5], 3);
        map.put(crystals[(cur-2)%5], 1);
        cards[cur]=new Card(1,0,crystals[cur%5],map);
        cur++;

        //1顺临 2逆临 2逆对
        assert cur==10;
        for(;cur<15;cur++){
            map = new HashMap<>();
            map.put(crystals[(cur+1)%5],1);
            map.put(crystals[(cur-1)%5],2);
            map.put(crystals[(cur-2)%5],2);
            cards[cur]=new Card(1,0,crystals[cur%5],map);
        }

        //1顺临 1顺对 1逆临 2逆对
        assert cur==15;
        for(;cur<20;cur++){
            map = new HashMap<>();
            map.put(crystals[(cur+1)%5],1);
            map.put(crystals[(cur+2)%5],1);
            map.put(crystals[(cur-1)%5],1);
            map.put(crystals[(cur-2)%5],2);
            cards[cur]=new Card(1,0,crystals[cur%5],map);
        }

        //0分 22
        //白 2顺临 2逆临
        //绿 2顺临 2逆临
        for(;cur<24;cur+=3){
            map = new HashMap<>();
            map.put(crystals[(cur+1)%5],2);
            map.put(crystals[(cur-1)%5],2);
            cards[cur]=new Card(1,0,crystals[cur%5],map);
        }
        //红 2本色 2逆对
        map = new HashMap<>();
        map.put(crystals[(22)%5],2);
        map.put(crystals[(22-2)%5],2);
        cards[22]=new Card(1,0,crystals[22%5],map);
        //黑 2顺对 2逆临
        //蓝 2顺对 2逆临
        for(cur=21;cur<25;cur+=3){
            map = new HashMap<>();
            map.put(crystals[(cur+2)%5],2);
            map.put(crystals[(cur-1)%5],2);
            cards[cur]=new Card(1,0,crystals[cur%5],map);
        }

        //0分 4色除本色 奖励本色
        for(cur=25;cur<30;cur++){
            map = new HashMap<>();
            map.put(crystals[(cur+1)%5],1);
            map.put(crystals[(cur-1)%5],1);
            map.put(crystals[(cur+2)%5],1);
            map.put(crystals[(cur-2)%5],1);
            cards[cur]=new Card(1,0,crystals[cur%5],map);
        }

        //0分 单色3 奖励本色
        //白 3逆临
        //绿 3逆临
        for(;cur<34;cur+=3){
            map = new HashMap<>();
            map.put(crystals[(cur-1)%5],3);
            cards[cur]=new Card(1,0,crystals[cur%5],map);
        }
        //红 3逆对
        map = new HashMap<>();
        map.put(crystals[(32-2)%5],3);
        cards[32]=new Card(1,0,crystals[30%5],map);
        //黑 3顺对
        //蓝 3顺对
        for(cur=31;cur<35;cur+=3){
            map = new HashMap<>();
            map.put(crystals[(cur+2)%5],3);
            cards[cur]=new Card(1,0,crystals[cur%5],map);
        }

        //1顺临 2顺对
        for(cur=35;cur<40;cur++){
            map = new HashMap<>();
            map.put(crystals[(cur+1)%5],1);
            map.put(crystals[(cur+2)%5],2);
            cards[cur]=new Card(1,0,crystals[cur%5],map);
        }

        return cards;
    }
}