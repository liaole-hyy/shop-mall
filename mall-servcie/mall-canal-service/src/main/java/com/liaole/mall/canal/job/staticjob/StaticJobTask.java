package com.liaole.mall.canal.job.staticjob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import org.springframework.stereotype.Component;


//@ElasticSimpleJob(jobName = "${elaticjob.zookeeper.namespace}",
//                shardingTotalCount = 1,
//                cron = "0/5 * * * * ? *"
//)
//@Component
public class StaticJobTask implements SimpleJob {

    /**
     *  执行作业
     * @param shardingContext
     */
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("StaticJobTask--execute");
    }
}
