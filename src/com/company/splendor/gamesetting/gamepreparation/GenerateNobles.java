package com.company.splendor.gamesetting.gamepreparation;

import com.company.splendor.card.Noble;
import com.company.splendor.other.Crystal;

import java.util.*;

/**
 * 生成10个贵族并抽取
 */
public class GenerateNobles {
    private static final List<Noble> nobleList;

    public static Noble[] generate(int player_num){
        Random random = new Random();
        Noble[] nobles = new Noble[player_num];
        boolean[] visited = new boolean[10];
        for(int i=0,j=random.nextInt(10);i<player_num;j=random.nextInt(10)){
            if(!visited[j]) {
                nobles[i] = nobleList.get(j);
                visited[j] = true;
                i++;
            }
        }
        return nobles;
    }

    static {
        nobleList = new ArrayList<>(10);

        Map<Crystal,Integer> map = new HashMap<>();
        map.put(Crystal.Blue,4);
        map.put(Crystal.White,4);
        Noble n = new Noble(map,"Noble1");
        nobleList.add(n);

        map = new HashMap<>();
        map.put(Crystal.Red,4);
        map.put(Crystal.Green,4);
        n = new Noble(map,"Noble2");
        nobleList.add(n);

        map = new HashMap<>();
        map.put(Crystal.Black,4);
        map.put(Crystal.White,4);
        n = new Noble(map,"Noble3");
        nobleList.add(n);

        map = new HashMap<>();
        map.put(Crystal.Blue,4);
        map.put(Crystal.Green,4);
        n = new Noble(map,"Noble4");
        nobleList.add(n);

        map = new HashMap<>();
        map.put(Crystal.Red,4);
        map.put(Crystal.Black,4);
        n = new Noble(map,"Noble5");
        nobleList.add(n);

        map = new HashMap<>();
        map.put(Crystal.Green,3);
        map.put(Crystal.Blue,3);
        map.put(Crystal.White,3);
        n = new Noble(map,"Noble6");
        nobleList.add(n);

        map = new HashMap<>();
        map.put(Crystal.Black,3);
        map.put(Crystal.Blue,3);
        map.put(Crystal.White,3);
        n = new Noble(map,"Noble7");
        nobleList.add(n);

        map = new HashMap<>();
        map.put(Crystal.Green,3);
        map.put(Crystal.Blue,3);
        map.put(Crystal.Red,3);
        n = new Noble(map,"Noble8");
        nobleList.add(n);

        map = new HashMap<>();
        map.put(Crystal.Black,3);
        map.put(Crystal.Red,3);
        map.put(Crystal.White,3);
        n = new Noble(map,"Noble9");
        nobleList.add(n);

        map = new HashMap<>();
        map.put(Crystal.Black,3);
        map.put(Crystal.Red,3);
        map.put(Crystal.Green,3);
        n = new Noble(map,"Noble10");
        nobleList.add(n);
    }
}
