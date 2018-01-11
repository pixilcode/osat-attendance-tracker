package controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import model.ModelTests;

@RunWith(Suite.class)
@SuiteClasses({ ControllerTests.class, ModelTests.class })
public class AllTests {

}
