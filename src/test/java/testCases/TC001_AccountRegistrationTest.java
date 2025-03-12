package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass
{

	
	@Test(groups={"Regression","Master"})
	void verify_account_registration()
	{
		logger.info("**** Starting TC001_AccountRegistrationTest ****");
		
		try
		{
		HomePage hp=new HomePage(driver);
		hp.clickMyaccount();
		logger.info("Clicked on my account link...");
		
		hp.clickRegister();
		logger.info("Clicked on register link...");
		
		AccountRegistrationPage regpage=new AccountRegistrationPage(driver);
		
		logger.info("Providing customer details...");
		
		regpage.txtFirstname(randomString().toUpperCase());
		regpage.txtLastname(randomString().toUpperCase());
		regpage.txtEmail(randomString()+"@gmail.com");			//randomly generated the email
		regpage.txtTelephone(randomNumber());
		
		String password=randomAlphaNumeric();
		
		regpage.txtPassword(password);
		regpage.txtConfirmPassword(password);
		
		regpage.chkPolicy();
		regpage.btnContinue();
		
		logger.info("Validating expected message...");
		String confmsg=regpage.getConfirmationMsg();
		if(confmsg.equals("Your Account Has Been Created!!"))
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test Failed..");
			logger.debug("Debug logs..");
			Assert.assertTrue(false);
		}
		
		//Assert.assertEquals(confmsg, "Your Account Has Been Created!!");
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		
		logger.info("**** Finished TC001_AccountRegistrationTest ****");
	}
	
	
}
