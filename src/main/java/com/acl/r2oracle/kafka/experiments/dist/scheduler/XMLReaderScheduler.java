package com.acl.r2oracle.kafka.experiments.dist.scheduler;

import com.acl.r2oracle.kafka.experiments.core.facade.Result;
import com.acl.r2oracle.kafka.experiments.facade.r2.ProcessorFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduled tasks for the experiments executions requests.
 *
 * @author alejandro
 * @since 0.1.0
 */
@Component
public class XMLReaderScheduler {
    private static final Logger logger = LoggerFactory.getLogger(XMLReaderScheduler.class);

    private final ProcessorFacade facade;

    public XMLReaderScheduler(ProcessorFacade facade) {
        this.facade = facade;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void executeExperiment() {
        this.facade.execute()
                .doOnNext(this::process)
                .subscribe();
    }

    private void process(Result<Void> result) {
        result.ifSuccess(
                ignored -> logger.debug("Scheduled task executed successfully"),
                errors -> logger.error("Errors detected when trying to execute a scheduled task ({})", errors));
    }

}
