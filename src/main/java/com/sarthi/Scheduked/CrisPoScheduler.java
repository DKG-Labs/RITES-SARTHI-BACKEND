package com.sarthi.Scheduked;

import com.sarthi.service.Impl.CrisPoAsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CrisPoScheduler {
    @Autowired
    private CrisPoAsyncService asyncService;

    @Scheduled(cron = "0 2 15 * * ?")
    public void runDailyPoSync() {
        System.out.println("Scheduler triggered");
      //  asyncService.syncPos(LocalDate.now().minusDays(1));

        asyncService.syncPos("2019-11-23");

        asyncService.syncMa("2019-06-19");


    }


}
