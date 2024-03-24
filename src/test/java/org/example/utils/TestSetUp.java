package org.example.utils;

import java.io.IOException;

import org.example.pageObjects.PageObjectManager;


public class TestSetUp {

    public PageObjectManager pageObjectManager;
    public BaseTest baseTest;

    public TestSetUp() throws IOException {

        baseTest = new BaseTest();
        pageObjectManager = new PageObjectManager(baseTest.WebDriverManager());

    }
}
