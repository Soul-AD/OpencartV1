package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

/*
 valid data - login success - test passed - logout
 valid data - login failed - test failed  
  
 invalid data - login success - test failed - logout
 invalid data - login failed - test failed 
 */

public class TC003_LoginDDT extends BaseClass
{
	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class, groups="Datadriven")	// getting data provider from different package
	public void verify_loginDDT(String email, String pwd, String exp)
	{
		logger.info("**** Starting TC003_LoginDDT ****");
		
		try
		{
		//Home page
		HomePage hp=new HomePage(driver);
		hp.clickMyaccount();
		hp.clickLogin();
				
		//Login page
		LoginPage lp=new LoginPage(driver);
		lp.setEmailAddress(email);
		lp.setPassword(pwd);
		lp.clickLogin();
				
		//My account page
		MyAccountPage macc=new MyAccountPage(driver);
		boolean targetPage=macc.isMyAccountPageExists();	
		
		if(exp.equalsIgnoreCase("Valid"))
		{
			if(targetPage==true)
			{
				macc.clickLogout();
				Assert.assertTrue(true);
			}
			else
			{
				Assert.assertTrue(false);
			}
		}
		
		if(exp.equalsIgnoreCase("Invalid"))
		{
			if(targetPage==true)
			{
				macc.clickLogout();
				Assert.assertTrue(false);
			}
			else
			{
				Assert.assertTrue(true);
			}
		}
		
		}catch(Exception e)
		{
			Assert.fail();
		}
		
		logger.info("**** Finished TC003_LoginDDT ****");
		
	}
	
}
