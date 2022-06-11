package utils;

import org.apache.commons.logging.impl.SLF4JLog;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestListener extends TestListenerAdapter {
    private static final Logger log = LoggerFactory.getLogger(SLF4JLog.class);

    @Override
    public void onTestFailure(ITestResult tr) {
        log.error(tr.getName() + " -- Test case failed\n");
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        log.warn(tr.getName() + " -- Test case skipped\n");
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        log.info(tr.getName() + " -- Test case success\n");
    }
}
