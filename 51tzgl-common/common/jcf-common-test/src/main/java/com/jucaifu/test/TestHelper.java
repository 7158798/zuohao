package com.jucaifu.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jucaifu.common.log.LOG;

/**
 * TestHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/20.
 */
public class TestHelper {

    /**
     * Execute concurrency.
     *
     * @param threadCount the thread count
     * @param runCount    the run count
     * @param runnable    the runnable
     */
    public static void executeConcurrency(int threadCount, int runCount, Runnable runnable) {

        ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);

        if (!threadPool.isShutdown()) {

            threadPool.execute(new Runnable() {

                @Override
                public void run() {

                    LOG.dTag(TestHelper.class, "threadCount:" + threadCount + "  runCount:" + runCount);

                    for (int i = 1; i <= runCount; i++) {
                        runnable.run();
                        LOG.dTag(TestHelper.class, Thread.currentThread().getName() + ":" + i);
                    }
                }
            });
        }else{
            LOG.dTag("","线程池启动失败");
        }
    }
}
