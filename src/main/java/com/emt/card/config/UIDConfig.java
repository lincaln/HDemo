package com.emt.card.config;

import com.baidu.fsg.uid.impl.DefaultUidGenerator;
import com.baidu.fsg.uid.utils.NetUtils;
import com.baidu.fsg.uid.worker.WorkerIdAssigner;
import com.baidu.fsg.uid.worker.WorkerNodeType;
import com.emt.card.db.entity.WorkerNode;
import com.emt.card.db.repository.WorkerNodeRepository;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UIDConfig {

    @Bean(name = "uidGenerator")
    public DefaultUidGenerator defaultUidGenerator() {
        DefaultUidGenerator defaultUidGenerator = new DefaultUidGenerator();
        defaultUidGenerator.setWorkerIdAssigner(workerIdAssigner());
        defaultUidGenerator.setTimeBits(29);
        defaultUidGenerator.setWorkerBits(21);
        defaultUidGenerator.setSeqBits(13);
        defaultUidGenerator.setEpochStr("2019-01-01");
        return defaultUidGenerator;
    }

    @Bean
    public WorkerIdAssigner workerIdAssigner() {
        return new DisposableWorkerIdAssigner();
    }

    public static class DisposableWorkerIdAssigner implements WorkerIdAssigner {

        @Autowired
        private WorkerNodeRepository workerNodeRepository;

        @Override
        public long assignWorkerId() {
            // build worker node entity
            WorkerNode workerNodeEntity = buildWorkerNode();
            // add worker node for new (ignore the same IP + PORT)
            workerNodeRepository.save(workerNodeEntity);

            return workerNodeEntity.getId();
        }

        private WorkerNode buildWorkerNode() {
            WorkerNode workerNode = new WorkerNode();
            workerNode.setType(WorkerNodeType.ACTUAL.value());
            workerNode.setHostName(NetUtils.getLocalAddress());
            workerNode.setPort(System.currentTimeMillis() + "-" + RandomUtils.nextInt(100000));
            return workerNode;
        }
    }

}
