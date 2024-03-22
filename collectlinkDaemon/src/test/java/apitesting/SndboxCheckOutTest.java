package apitesting;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import endpoints.EmailGenration;
import genricLibraries.BaseClass;
import genricLibraries.UtilitiesPath;
import io.restassured.response.Response;
import payload.SandBoxCollectLinkApiPayload;
import pom.CheckOutPage;

public class SndboxCheckOutTest extends BaseClass{
	
//	for netBanking collect
	@Test(enabled = true)
	public void netBankingTest() throws Exception {

		Response response = SandBoxCollectLinkApiPayload.CollectPayload(property);
		//System.out.println(response.getBody().asString());

		String collectLink = response.path("data");
		property.writeToProperties("sandBoxUrl", collectLink, UtilitiesPath.PROPERTIES_PATH);

		driver = web.launchBrowser(property.readData("browser"));
		web.maximizeBrowser();
		web.navigateToApp(property.readData("sandBoxUrl"));
		web.waitUntilElementFound(Long.parseLong(property.readData("time")));

		int i = Integer.parseInt(property.readData("bankCount"));

		checkOut = new CheckOutPage(driver);

		SoftAssert asserts = new SoftAssert();

		checkOut.clickOnMoreBanks();
		Thread.sleep(2000);

//		checking banks server
		String bankDownList = property.readData("sandBoxserverDown");
		List<WebElement> allBankList = checkOut.allBanks();
		while (i <= allBankList.size()-1) {
			String bankName = allBankList.get(i).getText();

			allBankList.get(i).click();
			
//			Thread.sleep(5000);
//		    checkOut.forSuccess();
//		    checkOut.submitPayment();
//		    
			Thread.sleep(10000);
//			
//			WebElement element = checkOut.getConfirmMessage();
//
//			web.explicitWait(Long.parseLong(property.readData("time")), element);
//
//			String confirmMessage = element.getText();

			// for aditya birla bank
			if (i == 0) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						// if bank server is working this will execute
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for airtel bank
			if (i == 1) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						// if bank server is working this will execute
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for allahabad bank
			if (i == 2) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for andhra bank
			if (i == 3) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for axis bank
			if (i == 4) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for bank of baroda bank
			if (i == 5) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for bank of india bank
			if (i == 6) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for bank of maharashtra bank
			if (i == 7) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for canara bank
			if (i == 8) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for catholic syrian bank
			if (i == 9) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for central bank of india
			if (i == 10) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for citibank
			if (i == 11) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for city union bank
			if (i == 12) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for corporation bank
			if (i == 13) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for cosmos bank
			if (i == 14) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for dcb bank
			if (i == 15) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for dena bank
			if (i == 16) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for deutsche bank
			if (i == 17) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for federal bank (need to review)
			if (i == 18) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for hdfc bank
			if (i == 19) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for icici bank
			if (i == 20) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for idbi bank (need to review)
			if (i == 21) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for idfc bank
			if (i == 22) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for indian bank
			if (i == 23) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for indian overseas bank
			if (i == 24) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for indsulsind bank
			if (i == 25) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for j & k bank
			if (i == 26) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for janata sahakari bank
			if (i == 27) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for karnataka bank
			if (i == 28) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for karur vysya bank
			if (i == 29) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for kotak mahindra bank
			if (i == 30) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for lakshmi vilas bank (need to review)
			if (i == 31) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for oriental bank of commerce
			if (i == 32) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for p retail
			if (i == 33) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for pubnjab and sind bank
			if (i == 34) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for RBL bank
			if (i == 35) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for saraswat bank
			if (i == 36) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for sbi bank
			if (i == 37) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for south indian bank
			if (i == 38) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for syndicate bank
			if (i == 39) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for Tamilnad mercantile bank
			if (i == 40) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for uco bank
			if (i == 41) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for union bank
			if (i == 42) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for united bank of india
			if (i == 43) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for vijaya bank
			if (i == 44) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for yes bank
			if (i == 45) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for paytm bank
			if (i == 46) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for Standard charter bank
			if (i == 47) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for bandhan bank
			if (i == 48) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for bank of bahrain and kuwait
			if (i == 49) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for dbs bank ltd
			if (i == 50) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for dhanlaxmi bank
			if (i == 51) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for punjab and maharashtra co-operative bank limited (net banking is not
			// there)
			if (i == 52) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for shamrao vithal co-op. Bank ltd
			if (i == 53) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for suryoday smal finance bank ltd (need to review)
			if (i == 54) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for the bharat co-op bank.ltd (need to review)
			if (i == 55) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for the nainital bank (need to review)
			if (i == 56) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}

			// for ujjivan small finance (need to review)
			if (i == 57) {
					if (driver.getTitle().contains("Sandbox Payment Decision Page")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ",";
					}
			}
			i++;
			property.writeToProperties("bankCount", String.valueOf(i), UtilitiesPath.PROPERTIES_PATH);
			property.writeToProperties("sandBoxserverDown", bankDownList, UtilitiesPath.PROPERTIES_PATH);
			break;

		}

		// To quit the browser once after one bank check
		asserts.assertAll();
		driver.quit();

		if (i == 58) {
			// to stop the execution once all banks done
			System.out.println("finsh");
			property.writeToProperties("bankCount", String.valueOf(0), UtilitiesPath.PROPERTIES_PATH);
			property.writeToProperties("sandBoxserverDown", "", UtilitiesPath.PROPERTIES_PATH);
			if(!(bankDownList.equals(""))){
				String message = """
						Hi,

						The below mentioned netBanking is down in sandbox.
						            \nBank List:-\n""";
			String split[] = bankDownList.split(",");
			for(int bankIndex=0;bankIndex<split.length;bankIndex++) {
				message += (bankIndex+1)+". "+split[bankIndex].trim()+"\n";
			}
			    message +=	"\n\n Thanks & Regards,\nGuruprasad v";
				EmailGenration a1 = new EmailGenration();
				a1.sendMail("Sandbox netBanking server down", message);
			}
		} else {
			// to re-call the method to check for all banks
			netBankingTest();
		}
	}

//	@Test
	public void schedular() throws Exception {

		int count = 1;
		while (count <= 4) {
			netBankingTest();
//			count++;
			if (count < 4) {
				TimeUnit.HOURS.sleep(24);
			} else {
				break;
			}
		}
	}

}
