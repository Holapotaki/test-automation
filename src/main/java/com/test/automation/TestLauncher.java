package com.test.automation;

import com.test.automation.tests.ApiTests;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.PrintWriter;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

public class TestLauncher {

    private static void runAllTestsAndPrintSummary() {
        System.out.println("Running tests!");

        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectClass(ApiTests.class))
                .build();
        Launcher launcher = LauncherFactory.create();
        launcher.discover(request);
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        TestExecutionSummary summary = listener.getSummary();
        summary.printTo(new PrintWriter(System.out));
    }

    public static void main(String[] args) {

        runAllTestsAndPrintSummary();
    }
}
