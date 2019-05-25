package com.github.dukekan.cubadocker.results;

import com.github.dockerjava.api.model.Statistics;
import com.github.dockerjava.core.async.ResultCallbackTemplate;

import java.util.concurrent.CountDownLatch;

/**
 * @author d.kuznetsov
 * @version $Id$
 */
public class StatsLogsResultCallback extends ResultCallbackTemplate<StatsLogsResultCallback, Statistics> {

    private Statistics statistics;
    private CountDownLatch countDownLatch;

    public  StatsLogsResultCallback(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public  void onNext(Statistics statistics) {
        if (statistics != null) {
            this.statistics = statistics;
            this.onComplete();
        }
        this.countDownLatch.countDown();
    }

    public  Statistics getStatistics() {
        return this.statistics;
    }
}
