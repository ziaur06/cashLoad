package com.dous.cashload.cucumber.stepdefs;

import com.dous.cashload.CashloadApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = CashloadApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
