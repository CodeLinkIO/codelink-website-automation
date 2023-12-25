# CODELINK WEBSITE AUTOMATION
## About
This repository contains testware application to verify web-based UI for website https://codelink.io/.

## Prerequisites
- Java 8 or higher
- Maven 3.6.3 or higher
- Selenium 4.16 or higher
- Web browser of choice (Chrome, Firefox, Edge, etc.)
## Getting Started
1. Clone the repository to your local machine.
```
   git clone https://github.com/CodeLinkIO/codelink-website-automation.git
```
2. Navigate to the project directory.
```
   cd codelink-website-automation
```
3. Run the following command to install the dependencies:
```
mvn clean install
```
4. Run the following command to execute the tests:
```
mvn test
```
## Authorizing the Gmail Service for First-Time Use
Before using the Gmail functionality in this application, you'll need to authorize it with your Gmail account.

### Follow these steps:

1. Add Credentials File:

- Place a JSON file containing your Gmail API credentials in the following path:
```
- /src/main/resources/credentials
```
- This file is typically named `credentials.json`.
2. Run the `main()` Method:
- To initiate the authorization process, run the `main()` method in the `Gmail` class.
You can do this directly in your IDE or using a command-line tool like Maven.
3. Grant Permissions:
- A browser window will open, prompting you to log in to your Gmail account and grant the application access to your Gmail data.
- Carefully review the permissions and click "Allow" if you agree.
4. Authorization Completion:
- Once you've authorized, the application will complete the authorization process.
- You can now use the Gmail functionalities in your test scripts.
### Example Code:
```
/*
* run main() to get authorize (1st time)
* @email = "qa@codelink.io"
* @email_title = "Testing"
* @email_body = "this is the body"
* @email_link = "google.com"
* */
public static void main(String[] args) throws IOException, GeneralSecurityException {
    String body = getEmailBody("qa@codelink.io", "Testing");
    System.out.println("Email Body: " + body);
    String link = getEmailLink("qa@codelink.io", "Testing");
    System.out.println("Link(s) in email: " + link);
    System.out.println("Total mail(s):" + getTotalCountOfMails());
    System.out.println("Is mail exist: " + isMailExist("Testing"));
}
```
### Additional Notes:
- Credentials File: Ensure the credentials file is in the correct format and contains valid API keys.
- Subsequent Runs: After successful authorization, you won't need to repeat this process unless you change credentials or revoke access.
## Reporting

- Test results are generated in HTML format using __Extent Report__, a comprehensive reporting library for test automation.
- The generated report provides detailed information about test execution, including:
  - Overall test results (pass/fail/skip)
  - Individual test case results
  - Logs and stack traces for errors
  - Customizable charts and graphs
- The report can be found in the reports/extentreport.html directory.