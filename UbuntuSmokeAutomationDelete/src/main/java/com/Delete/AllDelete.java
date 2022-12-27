package com.Delete;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AllDelete {
	WebDriver driver;
	public AllDelete(WebDriver driver)
	{
		this.driver = driver;
	}
	public void navigatetoConnector()
	{
		System.out.println("inside navigate to connector");
		new WebDriverWait(driver, 120)
		.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='sidebar-options']/div[@data-header='Connectors']")));
		driver.findElement(By.xpath("//div[@class='sidebar-options']/div[@data-header='Connectors']")).click();
		//wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("add-text"))));
		
	}
	public void deleteConnector(String conn_name)
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		Boolean isPresent = driver.findElements(By.xpath("//*[text()='"+conn_name+"']")).size() > 0;
		if(isPresent)
		{
		new WebDriverWait(driver, Duration.ofSeconds(30))
		.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='"+conn_name+"']/ancestor-or-self::div[@class='table-row']/div/div[5]")));
		driver.findElement(By.xpath("//*[text()='"+conn_name+"']/ancestor-or-self::div[@class='table-row']/div/div[5]")).click();
		driver.findElement(By.xpath("//*[text()='"+conn_name+"']/ancestor-or-self::div[@class='table-row']/div/div[5]/div/div[4]")).click();
		driversleep();
		driver.findElement(By.xpath("//div[@class='event-button-click yes']")).click();
		System.out.println( conn_name+" deleted");
		}
		else
		{
			System.out.println(conn_name+" not found");
		}
	}
	public void deleteApp(String app_name)
	{
		driversleep();
		Boolean isPresent = driver.findElements(By.xpath("//*[text()='"+app_name+"']")).size() > 0;
		if(isPresent)
		{
		//WebElement element =driver.findElement(By.xpath("//*[@data-appname='"+app_name+"']/div/div[2]/div[2]/div/img"));
		//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		driver.findElement(By.xpath("//*[@data-appname='"+app_name+"']/descendant::div[contains(@class,'vertical menu')]")).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		driver.findElement(By.xpath("//*[@data-appname='"+app_name+"']/descendant::div[contains(@class,'delete-app')]")).click();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		driver.findElement(By.xpath("//div[contains(@class,'deleteApp')]/div[2]/div[1]")).click();
		
		System.out.println( app_name+" deleted");
		}
		else
		{
			System.out.println(app_name+" not found");
		}
	}
	
	
	public void driversleep()
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
	}
}
