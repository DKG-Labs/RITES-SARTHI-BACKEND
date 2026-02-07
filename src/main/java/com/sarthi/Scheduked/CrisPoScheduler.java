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

    @Scheduled(cron = "0 12 23 * * ?")
    public void runDailyPoSync() {
        System.out.println("Scheduler triggered");
      //  asyncService.syncPos(LocalDate.now().minusDays(1));

      //   asyncService.syncPos("2025-12-23");

       //  asyncService.syncMa("2019-06-19");

       //  asyncService.syncAmendedPo("2019-06-13");

       //  asyncService.syncPoCancellations("2019-06-13");

    }

}
