package controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import model.ModelTests;
import view.ViewTests;

@RunWith(Suite.class)
@SuiteClasses({ ControllerTests.class, ModelTests.class, ViewTests.class })
public class AllTests {

}
