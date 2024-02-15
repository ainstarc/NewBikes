#**Project Name: Identify New Bikes**

### Overview
- This Selenium automation testing project focuses on automating tasks related to the zigwheels.com Website. 
- The primary objectives includes: 
		- Searching of upcoming bikes manufactured by Honda with price less than 4Lakhs, showing all the Bike models availables in page along with price and Launch date. 
		- Search for the used cars in the Chennai Location, and capture all the popular model available in page.
		- Finally, 'Login' with google, give invalid account details & capture the error message. 
- This project uses various dependencies and libraries to facilitate automation. 
- The whole project is developed using page object model framework under which there are classes which describes the functionality of the program.

### Libraries and Dependencies
- **Maven Repository:** Maven 3.11.0
- **Selenium WebDriver:** 4.15.0
- **Apache POI:** 5.2.5
- **TestNG:** 7.9.0
- **Extent Report:** 5.0.9
- **Loggers:** 2.22.0

### Automation Flow
1. Navigate to the website.
2. Validate if website is accesible or not.
3. Validate presence of NewBikes dropdown.
4. Hover on NewBikes.
5. Validate if UpcomingBikes option is present or not, if present click on it.
6. Select manufacturer based on config.properties.
7. Match all bikes names with chosen option.
8. Get all bike details whose price are less than or equal to 4lakhs.
9. Validate presence of UsedCars dropdown.
10. Hover on UsedCars.
11. Check for city option availability based on config.properties.
12. If city is not found, click on Other Cities and search for that city.
13. Get all popular models list.
14. Validate Login/SignUp element, after clicking check for Google Button and click it.
15. Enter random-wrong email and get the error message.


### Libraries Explanation
- **Maven Repository:** Used for project management and dependency resolution.
- **Selenium WebDriver:** Automates browser interactions.
- **Apache POI:** Handles Excel operations for data-driven testing.
- **TestNG:** Facilitates test case organization and execution.
- **Extent Report:** Generates comprehensive test reports.
- **Loggers:** Facilitates logging for better debugging.

### Screenshots
Screenshots are captured at relevant steps for documentation and analysis.

### How to Run
1. **Open Eclipse IDE:**
   - Launch Eclipse IDE on your machine.

2. **Import Project:**
   - Select `File` -> `Import` from the menu.
   - Choose `Existing Maven Projects` and click `Next`.
   - Browse to the directory where you cloned the repository and select the project.

3. **Update Maven Project:**
   - Right-click on the project in the Project Explorer.
   - Choose `Maven` -> `Update Project`.
   - Click `OK` to update dependencies.

4. **Set Up Configuration:**
   - Open the `src/test/resources/config.properties` file.
   - Update any configuration parameters like browser type, URLs, etc., as needed.

5. **Run Test Suite:**
   - Locate the test suite file (`master.xml`)
   - Right-click on the file and choose `Run As` -> `TestNG Suite`.

6. **View Reports:**
   - After execution, open the `test-output` folder.
   - Find the TestNG file (`index.html`) for detailed test case reports.

### Reporting
1. **Locate Test Reports:**
   - After the execution, navigate to the `test-output` folder in the project directory.

2. **Open Extent Report:**
   - Inside the `extentReports` folder, find the Extent Report HTML file.

3. **View Test Reports:**
   - Open the ExtentReport file using any web browser.
   - This report provides a detailed overview of test executions, including passed and failed tests with screenshots, test duration, and any exceptions encountered.

4. **Screenshots and Logs:**
   - Extent Report may include screenshots captured during test execution and relevant logs for failed test cases.

5. **Analyze Results:**
   - Utilize the visual representation in the Extent Report to quickly identify test status and any issues encountered during the test run.

### Contributor
- **Shouvik Roy 2303529**

### Known Issues
- **Issue 1: Slow Performance on Large Data Sets**
  - Description: The automation script may experience performance issues when dealing with large data sets in the Upcoming Bikes section.
  - Workaround: Consider optimizing the script or running it on a machine with higher processing capabilities.

- **Issue 2: WebPage Blank Screen Issues**
  - Description: There might be occasional issues with the Webpage showing blank screen.
  - Workaround: Refresh the page or try using a different browser.

- **Issue 3: Google Button Inconsistency**
  - Description: The Google Button sometimes doesn't get clicked due to server coonectivity issues.
  - Workaround: Try re-executing the script, or wait for server connectivity fix.

- **Issue 4: Sign-In rejected**
  - Description: There might be occasional issues with google server rejecting sign-in.
  - Workaround: Try using different Gmail each time to test the site.


### Future Enhancements
- **Enhancement 1: Multi-Browser Support**
  - Description: Implement support for multiple browsers to enhance cross-browser compatibility.
  - Timeline: Targeting the next release.

- **Enhancement 2: Enhanced Logging**
  - Description: Introduce more detailed logging to aid in debugging and analysis of test results.
  - Timeline: Considered for the upcoming sprint.

- **Enhancement 3: Test Data Generation**
  - Description: Develop a module for generating test data dynamically to increase test coverage.
  - Timeline: Long-term goal for improved data-driven testing.

- **Enhancement 4: Parallel Execution**
  - Description: Enable parallel execution of tests to improve overall test suite efficiency.
  - Timeline: Investigating implementation feasibility.

- **Enhancement 5: Integration with CI/CD**
  - Description: Integrate the automation script with popular CI/CD platforms for continuous testing.
  - Timeline: Targeting a future release for streamlined workflows.


