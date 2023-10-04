package apitesting;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import endpoints.EmailGenration;
import genricLibraries.BaseClass;
import genricLibraries.UtilitiesPath;
import io.restassured.response.Response;
import payload.CollectLinkPayloadApi;
import pom.CheckOutPage;

public class CheckOutTest extends BaseClass {

	// for upi collect mode
//	@Test(enabled = false)
	public void upiTest() throws InterruptedException {

		Response response = CollectLinkPayloadApi.CreateUser(property);
		System.out.println(response.getBody().asString());

		String collectLink = response.path("data");
		property.writeToProperties("url", collectLink, UtilitiesPath.PROPERTIES_PATH);

		driver = web.launchBrowser(property.readData("browser"));
		web.maximizeBrowser();
		web.navigateToApp(property.readData("url"));
		web.waitUntilElementFound(Long.parseLong(property.readData("time")));

		checkOut = new CheckOutPage(driver);

		SoftAssert asserts = new SoftAssert();

		checkOut.clickOnAddUpi();
		checkOut.sendUpiID(excel.readDataFromExcel("checkout", 0, 1));
		checkOut.clickConfirm();

		Thread.sleep(20000);
		WebElement element = checkOut.getConfirmMessage();

		web.explicitWait(Long.parseLong(property.readData("time")), element);

		String confirmMessage = element.getText();
		asserts.assertTrue(confirmMessage.contains("Awesome! Your payment is successful"));

		asserts.assertAll();
		driver.close();
	}

	// for credit card collect mode
//	@Test(enabled = false)
	public void creditCardTest() throws InterruptedException {

		Response response = CollectLinkPayloadApi.CreateUser(property);
		System.out.println(response.getBody().asString());

		String collectLink = response.path("data");
		property.writeToProperties("url", collectLink, UtilitiesPath.PROPERTIES_PATH);

		driver = web.launchBrowser(property.readData("browser"));
		web.maximizeBrowser();
		web.navigateToApp(property.readData("url"));
		web.waitUntilElementFound(Long.parseLong(property.readData("time")));

		checkOut = new CheckOutPage(driver);

		SoftAssert asserts = new SoftAssert();

		checkOut.payButtonThroughCredit();
		Map<String, String> creditCardDetails = excel.readDataFromExcel("credit");
		System.out.println(creditCardDetails);
		checkOut.sendCreditCardNum(creditCardDetails.get("num"));
		checkOut.sendNameOnCreditCard(creditCardDetails.get("name"));
		checkOut.sendCreditCardValidTill(creditCardDetails.get("valid"));
		checkOut.sendCreditCardCvvNum(creditCardDetails.get("cvv"));
		checkOut.clickOnCreditPay();

		Thread.sleep(5000);
		checkOut.forSuccess();
		checkOut.submitPayment();

		Thread.sleep(5000);
		WebElement element = checkOut.getConfirmMessage();

		web.explicitWait(Long.parseLong(property.readData("time")), element);

		String confirmMessage = element.getText();
		asserts.assertTrue(confirmMessage.contains("Awesome! Your payment is successful"));

		asserts.assertAll();
		driver.close();
	}

	// for debit card collect mode
//	@Test(enabled = false)
	public void debitCardTest() throws InterruptedException {

		Response response = CollectLinkPayloadApi.CreateUser(property);
		System.out.println(response.getBody().asString());

		String collectLink = response.path("data");
		property.writeToProperties("url", collectLink, UtilitiesPath.PROPERTIES_PATH);

		driver = web.launchBrowser(property.readData("browser"));
		web.maximizeBrowser();
		web.navigateToApp(property.readData("url"));
		web.waitUntilElementFound(Long.parseLong(property.readData("time")));

		checkOut = new CheckOutPage(driver);

		SoftAssert asserts = new SoftAssert();

		checkOut.payButtonThroughDebit();
		Map<String, String> debitCardDetails = excel.readDataFromExcel("debit");
		System.out.println(debitCardDetails);
		checkOut.sendDebitCardNum(debitCardDetails.get("num"));
		checkOut.sendNameOnDebitCard(debitCardDetails.get("name"));
		checkOut.sendDebitCardValidTill(debitCardDetails.get("valid"));
		checkOut.sendDebitCardCvvNum(debitCardDetails.get("cvv"));
		checkOut.clickOnDebitPay();

		Thread.sleep(5000);
		checkOut.forSuccess();
		checkOut.submitPayment();

		Thread.sleep(5000);
		WebElement element = checkOut.getConfirmMessage();

		web.explicitWait(Long.parseLong(property.readData("time")), element);

		String confirmMessage = element.getText();
		asserts.assertTrue(confirmMessage.contains("Awesome! Your payment is successful"));

		asserts.assertAll();
		driver.close();
	}

//	for netBanking collect
//	@Test(enabled = true)
	public void netBankingTest() throws Exception {

		Response response = CollectLinkPayloadApi.CreateUser(property);
		//System.out.println(response.getBody().asString());

		String collectLink = response.path("data");
		property.writeToProperties("url", collectLink, UtilitiesPath.PROPERTIES_PATH);

		driver = web.launchBrowser(property.readData("browser"));
		web.maximizeBrowser();
		web.navigateToApp(property.readData("url"));
		web.waitUntilElementFound(Long.parseLong(property.readData("time")));

		int i = Integer.parseInt(property.readData("i"));

		checkOut = new CheckOutPage(driver);

		SoftAssert asserts = new SoftAssert();

		checkOut.clickOnMoreBanks();
		Thread.sleep(2000);

//		checking banks server
		String bankDownList = property.readData("serverDown");
		List<WebElement> allBankList = checkOut.allBanks();
		while (i <= 57) {
			String bankName = allBankList.get(i).getText();

			allBankList.get(i).click();
			Thread.sleep(20000);

			// for aditya birla bank
			if (i == 0) {
					if (driver.getTitle().contains("Aditya Birla")) {
						// if bank server is working this will execute
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for airtel bank
			if (i == 1) {
					if (driver.getTitle().contains("Airtel Payments")||driver.getCurrentUrl().contains("airtelbank")) {
						// if bank server is working this will execute
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for allahabad bank
			if (i == 2) {
					if (driver.getTitle().contains("INDIAN BANK")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for andhra bank
			if (i == 3) {
					if (driver.getTitle().contains("PayUbiz")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for axis bank
			if (i == 4) {
					if (driver.getTitle().contains("Axis")||driver.getCurrentUrl().contains("axisbank")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for bank of baroda bank
			if (i == 5) {
					if (driver.getTitle().contains("Bank of Baroda")||driver.getCurrentUrl().contains("bobibanking")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for bank of india bank
			if (i == 6) {
					if (driver.getTitle().contains("Bank of India")||driver.getCurrentUrl().contains("bankofindia")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for bank of maharashtra bank
			if (i == 7) {
					if (driver.getTitle().contains("Bank of Maharashtra")||driver.getCurrentUrl().contains("mahaconnect")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for canara bank
			if (i == 8) {
					if (driver.getTitle().contains("Canara Bank")||driver.getCurrentUrl().contains("canarabank")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for catholic syrian bank
			if (i == 9) {
					if (driver.getTitle().contains("CSB NetBanking")||driver.getCurrentUrl().contains("csbnet")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for central bank of india
			if (i == 10) {
					if (driver.getTitle().contains("Central Bank")||driver.getCurrentUrl().contains("centralbank")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for citibank
			if (i == 11) {
					if (driver.getTitle().contains("Citi")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for city union bank
			if (i == 12) {
					if (driver.getTitle().contains("CITY UNION BANK")||driver.getCurrentUrl().contains("onlinecub")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for corporation bank
			if (i == 13) {
					if (driver.getTitle().contains("Union Bank")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for cosmos bank
			if (i == 14) {
					if (driver.getTitle().contains("COSMOS")||driver.getCurrentUrl().contains("cosmosbank")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for dcb bank
			if (i == 15) {
					if (driver.getTitle().contains("DCB")||driver.getCurrentUrl().contains("dcbbank")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for dena bank
			if (i == 16) {
					if (driver.getTitle().contains("Bank of Baroda")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for deutsche bank
			if (i == 17) {
					if (driver.getTitle().contains("DEUTSCHE BANK")||driver.getCurrentUrl().contains("deutschebank")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for federal bank (need to review)
			if (i == 18) {
					if (driver.getTitle().contains("fednetbank")||driver.getCurrentUrl().contains("fednetbank")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for hdfc bank
			if (i == 19) {
					if (driver.getTitle().contains("HDFC Bank")||driver.getCurrentUrl().contains("hdfcbank")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for icici bank
			if (i == 20) {
					if (driver.getTitle().contains("Log in to Internet Banking")||driver.getCurrentUrl().contains("icicibank")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for idbi bank (need to review)
			if (i == 21) {
					if (driver.getTitle().contains("IDBI")||driver.getCurrentUrl().contains("idbibank")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for idfc bank
			if (i == 22) {
					if (driver.getTitle().contains("Enterprise Auth")||driver.getCurrentUrl().contains("idfcfirstbank")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for indian bank
			if (i == 23) {
					if (driver.getTitle().contains("INDIAN BANK")||driver.getCurrentUrl().contains("indianbank")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for indian overseas bank
			if (i == 24) {
					if (driver.getTitle().contains("TPPEntry")||driver.getCurrentUrl().contains("iobnet")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for indsulsind bank
			if (i == 25) {
					if (driver.getTitle().contains("Indusind Bank")||driver.getCurrentUrl().contains("indusind")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for j & k bank
			if (i == 26) {
					if (driver.getTitle().contains("J&K Bank")||driver.getCurrentUrl().contains("jkbankonline")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for janata sahakari bank
			if (i == 27) {
					if (driver.getTitle().contains("Janata Sahakari")||driver.getCurrentUrl().contains("jsbnet")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for karnataka bank
			if (i == 28) {
					if (driver.getTitle().contains("MoneyClick")||driver.getCurrentUrl().contains("ktkbank")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for karur vysya bank
			if (i == 29) {
					if (driver.getTitle().contains("kvbin")||driver.getCurrentUrl().contains("kvbin")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for kotak mahindra bank
			if (i == 30) {
					if (driver.getTitle().contains("Kotak Mahindra Bank")||driver.getCurrentUrl().contains("kotak")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for lakshmi vilas bank (need to review)
			if (i == 31) {
					if (driver.getTitle().contains("DBS")||driver.getCurrentUrl().contains("lvbankonline")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for oriental bank of commerce
			if (i == 32) {
					if (driver.getTitle().contains("PNB")||driver.getCurrentUrl().contains("pnbibanking")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for p retail
			if (i == 33) {
					if (driver.getTitle().contains("PNB")||driver.getCurrentUrl().contains("pnbibanking")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for pubnjab and sind bank
			if (i == 34) {
					if (driver.getTitle().contains("Punjab & Sind")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for RBL bank
			if (i == 35) {
					if (driver.getTitle().contains("RBL Bank")||driver.getCurrentUrl().contains("rblbank")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for saraswat bank
			if (i == 36) {
					if (driver.getTitle().contains("Saraswat Bank")||driver.getCurrentUrl().contains("saraswatbank")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for sbi bank
			if (i == 37) {
					if (driver.getTitle().contains("STATE BANK OF INDIA")||driver.getCurrentUrl().contains("onlinesbi")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for south indian bank
			if (i == 38) {
					if (driver.getTitle().contains("South Indian Bank")||driver.getCurrentUrl().contains("southindianbank")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for syndicate bank
			if (i == 39) {
					if (driver.getTitle().contains("Canara Bank")||driver.getCurrentUrl().contains("canarabank")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for Tamilnad mercantile bank
			if (i == 40) {
					if (driver.getTitle().contains("Tamilnad Mercantile Bank")||driver.getCurrentUrl().contains("tmbnet")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for uco bank
			if (i == 41) {
					if (driver.getTitle().contains("Uco Bank")||driver.getCurrentUrl().contains("ucoebanking")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for union bank
			if (i == 42) {
					if (driver.getTitle().contains("Union Bank")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for united bank of india
			if (i == 43) {
					if (driver.getTitle().contains("PNB")||driver.getCurrentUrl().contains("pnbibanking")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for vijaya bank
			if (i == 44) {
					if (driver.getTitle().contains("Bank of Baroda")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for yes bank
			if (i == 45) {
					if (driver.getTitle().contains("YES Bank")||driver.getCurrentUrl().contains("yesbank")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for paytm bank
			if (i == 46) {
					if (driver.getTitle().contains("Paytm")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for Standard charter bank
			if (i == 47) {
					if (driver.getTitle().contains("Paytm Payments")||driver.getCurrentUrl().contains("paytmbank")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for bandhan bank
			if (i == 48) {
					if (driver.getTitle().contains("Bandhan")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for bank of bahrain and kuwait
			if (i == 49) {
					if (driver.getTitle().contains("BAHRAIN & KUWAIT")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for dbs bank ltd
			if (i == 50) {
					if (driver.getTitle().contains("DBS")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for dhanlaxmi bank
			if (i == 51) {
					if (driver.getTitle().contains("Dhanlaxmi Bank")||driver.getCurrentUrl().contains("dhanbank")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for punjab and maharashtra co-operative bank limited (net banking is not
			// there)
			if (i == 52) {
					if (driver.getTitle().contains("Aditya Birla")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for shamrao vithal co-op. Bank ltd
			if (i == 53) {
					if (driver.getTitle().contains("SVC")||driver.getCurrentUrl().contains("svcbank")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for suryoday smal finance bank ltd (need to review)
			if (i == 54) {
					if (driver.getTitle().contains("Internet Banking")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for the bharat co-op bank.ltd (need to review)
			if (i == 55) {
					if (driver.getTitle().contains("Bharat")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for the nainital bank (need to review)
			if (i == 56) {
					if (driver.getTitle().contains("Nainital Bank")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}

			// for ujjivan small finance (need to review)
			if (i == 57) {
					if (driver.getTitle().contains("Personal Net Banking")) {
						//System.out.println(bankName + ": done");
					} else {
						bankDownList += bankName + ", ";
					}
			}
			i++;
			property.writeToProperties("i", String.valueOf(i), UtilitiesPath.PROPERTIES_PATH);
			property.writeToProperties("serverDown", bankDownList, UtilitiesPath.PROPERTIES_PATH);
			break;

		}

		// To quit the browser once after one bank check
		asserts.assertAll();
		driver.quit();

		if (i == 58) {
			// to stop the execution once all banks done
			System.out.println("finsh");
			property.writeToProperties("i", String.valueOf(0), UtilitiesPath.PROPERTIES_PATH);
			property.writeToProperties("serverDown", "Bank List: ", UtilitiesPath.PROPERTIES_PATH);
			if(!(bankDownList.equals("Bank List: "))){
				String message = """
						Hi,

						   The below mentioned netBanking is down.
						            \n""" + bankDownList + """
						   \n\nThanks & Regards,
						Guruprasad v
								""";
				EmailGenration a1 = new EmailGenration();
				a1.sendMail("Collectlink bank server down", message);
			}
		} else {
			// to re-call the method to check for all banks
			netBankingTest();
		}
	}

	public void specifiedNetBanking() {

	}

	@Test
	public void schedular() throws Exception {

		int count = 1;
		while (count <= 4) {
			netBankingTest();
//			count++;
			if (count < 4) {
				TimeUnit.HOURS.sleep(2);
			} else {
				break;
			}
		}
	}

}
