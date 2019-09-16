package com.huazai.avl;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class AVLTest {
    private static Random random = new Random();
    private static final int MAX1 = 16;
    private static final int MAX2 = 25565;

    @org.junit.Test
    public void testAVLMapPut() {
        AVLMap<Integer, String> map = new AVLMap<>();
        for (int i = 0; i < MAX1; i++) {
            int randomInt = random.nextInt(MAX1);
            map.put(randomInt, randomInt + "");
        }

        Iterator<AVLEntry<Integer, String>> iterator = map.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next() + " ");
        }
        System.out.println(map.size());
    }


    @Test
    public void testAVLMapPutCompareTreeMapPut() {
        AVLMap<Integer, String> avlMap = new AVLMap<>();
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        for (int i = 0; i < MAX2; i++) {
            int randomInt = random.nextInt(MAX2);
            avlMap.put(randomInt, randomInt + "");
            treeMap.put(randomInt, randomInt + "");
        }

        Iterator<AVLEntry<Integer, String>> iterator = avlMap.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next().getKey() + " ");
        }

        System.out.println();

        Iterator<Map.Entry<Integer, String>> iterator1 = treeMap.entrySet().iterator();
        while (iterator1.hasNext()) {
            System.out.print(iterator1.next().getKey() + " ");
        }

        System.out.println();

        Assert.assertTrue(avlMap.size() == treeMap.size());
    }

    @Test
    public void testAVLMapGetEntry() {
        AVLMap<Integer, String> avlMap = new AVLMap<>();
        List<Integer> keyList = new ArrayList<>();
        for (int i = 0; i < MAX1; i++) {
            int randomInt = random.nextInt(MAX1);
            avlMap.put(randomInt, randomInt + "");
            keyList.add(randomInt);
        }

        Iterator<AVLEntry<Integer, String>> iterator = avlMap.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next().getKey() + " ");
        }

        System.out.println("\n" + keyList.toString());

        for (Integer integer : keyList) {
            System.out.println(avlMap.containsKey(integer));
        }

        // 测试不存在的
        System.out.println(avlMap.containsKey(-1));
    }

    public static void main(String[] args) {
        AVLMap<Integer, String> map=new AVLMap<Integer, String>();
        int[] array={6,2,7,1,4,8,3,5};
//        int[] array={6,4,8,3,5,7,9,8,7,5,4,3,4,5,7,89,86};
        for(int key:array){
            map.put(key, key+"");
        }

        map.remove(3);

        map.levelOrder();
        /*Iterator<AVLEntry<Integer, String>> it=map.iterator();
        while(it.hasNext()){
            System.out.print(it.next().getKey()+" ");
        }
        System.out.println();*/
    }
}
