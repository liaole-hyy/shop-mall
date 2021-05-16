package com.liaole.mall.canal.job.dynamic;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

public class DynamicJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println(shardingContext);
        System.out.println("dynamic task ---------------execute");
    }
}
