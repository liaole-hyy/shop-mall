package com.liaole.mall.util;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;

import java.util.ArrayList;
import java.util.List;

/**
 * google 的布隆过滤器测试
 * 使用场景 1.海量数据判断值是否存在，效率极高，本身不存数据 安全高
 * 去重
 * 缺点
 * 1.不能再100%准确率场景
 * 2.只支持查询和插入不支持删除
 */
public class BloomFilterTest {

    private static int size = 1000000 ;

    //Google的布隆过滤器
    //private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(),size);
    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(),size,0.001);
    public static void main(String[] args) {
        //放一百万个key到布隆过滤器中
        for(int i=0;i<size;i++){
            bloomFilter.put(i);
        }

        List<Integer> list = new ArrayList<Integer>(1000);
        for(int i=size+10000;i<size+20000;i++){
            if(bloomFilter.mightContain(i)){
                list.add(i);
            }
        }
        System.out.println("误判的数量:"+list.size());
    }
}
