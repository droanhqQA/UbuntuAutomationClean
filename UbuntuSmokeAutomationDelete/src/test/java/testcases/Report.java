package testcases;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import com.Delete.AllDelete;
import com.Login.UserLogin;
import com.Delete.TakeScreenshots;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Report {
	AllDelete allDelete;
	ChromeDriver driver;
	FileInputStream fs;
	static Logger log;
	@BeforeMethod
	public void setUp() throws IOException {
		Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		System.out.println(currentTimestamp);

		String u_name = "brijesh@studio.com";
		String u_pass = "qwerty";
		String con_name, con_string, con_uname, con_upass, con_db;
		// System.out.println(detailDAO.connectionName()+"\n"+detailDAO.connectionString());
		String os=System.getProperty("os.name").toLowerCase();
		System.out.println("OS name: "+os);
		String driver_type = os.contains("windows") ? "/chromedriver.exe" :"/var/lib/jenkins/driver/chromedriver";
		
		if(os.contains("windows"))
				{
			final URL resource = Report.class.getResource(driver_type);
			System.setProperty("webdriver.chrome.driver", resource.getFile());
				}
		else
			System.setProperty("webdriver.chrome.driver", driver_type);
		
		//System.setProperty("webdriver.chrome.driver", (driver_path.toString().substring("file:/".length(),driver_path.toString().length())));

				ChromeOptions options = new ChromeOptions();
					options.addArguments("headless");
						options.addArguments("window-size=1500,800");
						options.addArguments("incognito");
						driver = new ChromeDriver(options);

		//driver = new ChromeDriver();
		UserLogin user = new UserLogin(driver);
		log = Logger.getLogger(Report.class);
		driver.get("https://ubuntu.onprem.dronahq.com");
		driver.manage().window().maximize();
		user.login(u_name, u_pass);
		log.info("Login Successfull");
		allDelete = new AllDelete(driver);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		
		
	}

	@Test
	public void DeleteAll() {
		///driver.findElement(By.xpath("//div[@class='sidebar-options']/div[@data-header='Apps']")).click();//go to App
		allDelete.deleteApp("AutoApp");
		log.info("App Deleted");
		driver.navigate().refresh();
		allDelete.deleteApp("ExportAuto");
		log.info("App Deleted");
		driver.navigate().refresh();
		allDelete.deleteApp("ImportApp");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		allDelete.navigatetoConnector();
		
		allDelete.deleteConnector("AutoTestMongo");
//		log.info("Mongo Deleted");
		allDelete.deleteConnector("AutoTestPostgre");
		log.info("Postgre Deleted");
		allDelete.deleteConnector("AutoApi");
		log.info("Rest Api Deleted");
		allDelete.deleteConnector("AutoGraphQL");
		log.info("GraphQL Deleted");
		allDelete.deleteConnector("AutoApiOAuth1");
		log.info("AutoApiOAuth1 Deleted");
		allDelete.deleteConnector("AutoApiOAuth2");
		log.info("AutoApiOAuth2 Deleted");
//		allDelete.deleteGoogleSheet();
//		log.info("Google Sheet Deleted");
		
		
	}
	//@Ignore

	@AfterMethod
	public void tearOff(ITestResult result)
	{
		
		try {
			if(ITestResult.FAILURE==result.getStatus())
			{
			new TakeScreenshots().takeScreenShot(result.getName(),"DelteAll",driver);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		driver.quit();
	}
}
