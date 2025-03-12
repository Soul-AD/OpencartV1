package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage
{

	public AccountRegistrationPage(WebDriver driver)
	{
		super(driver);
	}
	
	@FindBy(xpath="//input[@id='input-firstname']")	WebElement txtFirstname;

	@FindBy(xpath="//input[@id='input-lastname']") WebElement txtLastname;
	
	@FindBy(xpath="//input[@id='input-email']") WebElement txtEmail;
	
	@FindBy(xpath="//input[@id='input-telephone']")	WebElement txtTelephone;

	@FindBy(xpath="//input[@id='input-password']") WebElement txtPassword;
	
	@FindBy(xpath="//input[@id='input-confirm']") WebElement txtConfirmPassword;

	@FindBy(xpath="//input[@name='agree']") WebElement chkPolicy;
	
	@FindBy(xpath="//input[@value='Continue']") WebElement btnContinue;

	@FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']") WebElement msgConfirmation;
	
	
	public void txtFirstname(String fname)
	{
		txtFirstname.sendKeys(fname);
	}
	
	
	public void txtLastname(String lname)
	{
		txtLastname.sendKeys(lname);
	}
	
	public void txtEmail(String email)
	{
		txtEmail.sendKeys(email);
	}
	
	public void txtTelephone(String tel)
	{
		txtTelephone.sendKeys(tel);
	}
	
	public void txtPassword(String pwd)
	{
		txtPassword.sendKeys(pwd);
	}
	
	public void txtConfirmPassword(String cpwd)
	{
		txtConfirmPassword.sendKeys(cpwd);
	}
	
	public void chkPolicy()
	{
		chkPolicy.click();
	}
	
	public void btnContinue()
	{
		btnContinue.click();
	}
	
	
	public String getConfirmationMsg()
	{
		try {
			return(msgConfirmation.getText());
			}
		catch(Exception e)
			{
			return(e.getMessage());
			}
	}
	
	
	
}
