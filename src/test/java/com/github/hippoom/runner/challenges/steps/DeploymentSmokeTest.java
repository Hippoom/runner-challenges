package com.github.hippoom.runner.challenges.steps;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com/github/hippoom/runner/challenges/steps")
@ConfigurationParameter(key = Constants.FEATURES_PROPERTY_NAME,
    value = "src/test/resources/features")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME,
    value = "com.github.hippoom.runner.challenges.steps")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME,
    value = "pretty,html:build/cucumber-reports/cucumber-pretty.html,"
        + "json:build/cucumber-reports/CucumberTestReport.json")
public class DeploymentSmokeTest {
}
