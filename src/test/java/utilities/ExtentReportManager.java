package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener
{

	public ExtentSparkReporter sparkReporter;	//UI of the report
	public ExtentReports extent;		//populate common info on the report
	public ExtentTest test;		//creating test case entries in report and update status of test methods
	
	String repName;
	private WebDriver driver;
	
	public void onStart(ITestContext testContext) {
		
		/*SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt=new Date();
		String currentdatetimestamp=df.format(dt);
		*/
		
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());	//time stamp
		
		repName = "Test-Report-" + timeStamp + ".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);		//specify location of report
		
		sparkReporter.config().setDocumentTitle("opencart Automation Report");		//Title of report
		sparkReporter.config().setReportName("opencart Functional Testing"); 		//name of the report
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		
		extent.setSystemInfo("Application", "Opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		
		String os = testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		
		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty())
		{
			extent.setSystemInfo("Groups", includedGroups.toString());
		}
		
	}
	
	
	public void onTestSuccess(ITestResult result) {
		
		test=extent.createTest(result.getTestClass().getName());	//create new entry in report
		test.assignCategory(result.getMethod().getGroups());		//to display groups in report
		test.log(Status.PASS, result.getName()+ " got successfully executed");	//update status		p/f/s
		
	}
	
	
	public void onTestFailure(ITestResult result) {
		
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());		//to display groups in report
		
		test.log(Status.FAIL, result.getName()+ " got failed");
		test.log(Status.INFO, result.getThrowable().getMessage());			// will give error message
		
		try
		{
			String imgPath = new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		} 
		catch(IOException e1)
		{
			e1.printStackTrace();
		}
		
	}
	
	
	public void onTestSkipped(ITestResult result) {
	
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.SKIP, result.getName()+ " got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
		
	}
	
	
	public void onFinish(ITestContext testContext) {

		extent.flush();		//add all information to report
		
		String pathOfExtentReport = System.getProperty("user.dir"+ "\\reports\\" +repName);
		File extentReport = new File(pathOfExtentReport);
		
		try
		{
			Desktop.getDesktop().browse(extentReport.toURI());		//will open report in browser
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	
		/*
		 * //for sending report via email try { URL url = new
		 * URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName); //
		 * Create the email message ImageHtmlEmail email = new ImageHtmlEmail();
		 * email.setDataSourceResolver (new DataSourceUrlResolver (url));
		 * email.setHostName("smtp.googlemail.com"); email.setSmtpPort (465);
		 * email.setAuthenticator (new DefaultAuthenticator ("akshaytest@gmail.com",
		 * "password")); email.setSSLOnConnect(true);
		 * email.setFrom("akshaytest@gmail.com"); //Sender
		 * email.setSubject("Test Results");
		 * email.setMsg("Please find Attached Report....");
		 * email.addTo("ak.busyqa@gmail.com"); //Receiver email.attach(url,
		 * "extent report", "please check report..."); email.send(); // send the email }
		 * catch (Exception e) { e.printStackTrace(); }
		 */
	}
	
}
