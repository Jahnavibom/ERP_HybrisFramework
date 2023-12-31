package driverFactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;




public class DriverScript {
	public static WebDriver driver;
	String inputpath = "./FileInput/Controller.xlsx";
	String outputpath = "./FileOutput/HybridResults.xlsx";
	String TestCases ="MasterTestCases";
	ExtentReports report;
	ExtentTest logger;
	
	public void startTest( ) throws Throwable
	{
		String Module_status = "";
		// create object for excelfile util class 
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	
		// iterate all rows in  Testcases sheet
		for(int i=1;i<=xl.rowCount(TestCases);i++)
		{
			// read execution_ status cell
			 String Execution_status = xl.getCellData(TestCases, i, 2);
			 if(Execution_status.equalsIgnoreCase("Y"))
			 {
				 // store all sheets into TCmodule
				 String TCModule = xl.getCellData(TestCases, i, 1);
				//define path to generate html report
				report = new ExtentReports("./target/Reports/"+TCModule+FunctionLibrary.generateDate()+".html");
				logger= report.startTest(TCModule);
				 // iterate all rows in TCmodule
				 for(int j=1; j<=xl.rowCount(TCModule);j++)
				 {
					 // read cell from TCmodule sheet
					 String Description= xl.getCellData(TCModule, j, 0);
					 String Object_Type= xl.getCellData(TCModule, j, 1);
					 String Locator_Type= xl.getCellData(TCModule, j, 2);
					 String Locator_Value= xl.getCellData(TCModule, j, 3);
					 String Test_Data= xl.getCellData(TCModule, j, 4);
					 try 
					 {
						if(Object_Type.equalsIgnoreCase("startBrowser"))
						{
							driver = FunctionLibrary.startBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("openUrl"))
						{
							FunctionLibrary.openUrl(driver);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("waitForElement"))
						{
							FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("typeAction"))
						{
							FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("clickAction"))
						{
							FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("validateTitle"))
						{
							FunctionLibrary.validateTitle(driver, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("closeBrowser"))
						{
							FunctionLibrary.closeBrowser(driver);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("mouseclick"))
						{
							FunctionLibrary.mouseclick(driver);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("categoryTable"))
						{
							FunctionLibrary.categoryTable(driver, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("dropDownAction"))
						{
							FunctionLibrary.dropDownAction(driver, Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("captureStock"))
						{
							FunctionLibrary.captureStock(driver, Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("stockTable"))
						{
							FunctionLibrary.stockTable(driver);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("captureSupplier"))
						{
							FunctionLibrary.captureSupplier(driver, Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("supplierTable"))
						{
							FunctionLibrary.supplierTable(driver);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("captureCustomer"))
						{
							FunctionLibrary.captureCustomer(driver, Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("customerTable"))
						{
							FunctionLibrary.customerTable(driver);
							logger.log(LogStatus.INFO, Description);
						}
						// Write as pass into status cell of TCModule
						xl.setCellData(TCModule, j, 5, "pass", outputpath);
						logger.log(LogStatus.PASS, Description);
						Module_status="true";
						
					 }catch (Exception e) 
					 {
						System.out.println(e.getMessage());
						// write as Fail into status cell of TCmodule
						xl.setCellData(TCModule, j, 5, "Fail", outputpath);
						logger.log(LogStatus.FAIL, Description);
						Module_status="False";
					 }
					  if(Module_status.equalsIgnoreCase("True"))
					  {
						  //Write as pass into testcases sheet
						  xl.setCellData(TestCases, i, 3,"pass", outputpath);
					  }
					  if(Module_status.equalsIgnoreCase("False"))
					  {
						  //write as fail into testcases sheet
						  xl.setCellData(TestCases, i, 3,"Fail", outputpath);
					  }
					  report.endTest(logger);
					  report.flush();
					 
				 }
			 }
			 else
			 {
				 //write as blocked into status cell  in Testcasesheet for Flag N
				 xl.setCellData(TestCases, i, 3, "Blocked", outputpath);
			 }
			
		}
		
	}

}
