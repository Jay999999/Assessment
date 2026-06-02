# Assessment

---

## Tech Stack
| Tool | Version |
|------|---------|
| Java | 21 |
| Selenium WebDriver | 4.44.0 |
| TestNG | 7.11.0 |
| Maven | Latest |
| ChromeDriver | 148 |
| Browser | Chrome 148 |

---

## How to Set Up the Environment

### Prerequisites
- Java 21 installed → [Download](https://www.oracle.com/java/technologies/downloads/)
- Maven installed → [Download](https://maven.apache.org/download.cgi)
- Chrome Browser installed
- Eclipse IDE (or IntelliJ)

### Steps
1. **Clone the repository**
```cmd
   git clone https://github.com/YourUsername/CartAutomation.git
```

2. **Open in Eclipse**
   - File → Import → Maven → Existing Maven Project
   - Browse to cloned folder → Finish

3. **Install dependencies**
```cmd
   mvn clean install -DskipTests
```

4. **Verify Chrome and ChromeDriver match**
   - Open Chrome → Settings → About Chrome → note version
   - Selenium 4 manages ChromeDriver automatically via Selenium Manager

---

## How to Run the Test

### Option 1 — Run via Maven
```cmd
mvn test
```

### Option 2 — Run via Eclipse
- Right-click `CartTest.java` → **Run As → TestNG Test**

### Option 3 — Run via TestNG XML
- Right-click `testng.xml` → **Run As → TestNG Suite**

### Expected Output
Selected Product: Noir jacket
On product page: https://sauce-demo.myshopify.com/.../noir-jacket
Button found: Add to Cart
Clicked Add to Cart
Item confirmed added to cart!
On cart page: https://sauce-demo.myshopify.com/cart
Product in Cart: Noir jacket - S / Blue
PASSED: tests.CartTest.verifyCartProductName

---

## Test Design Decisions

### Page Object Model (POM)
The project follows POM design pattern separating:
- **BaseClass** — driver initialization and teardown only
- **ViewPage** — all actions related to product selection
- **CartPage** — all actions related to cart (add, open, verify)
- **CartTest** — only calls page methods, zero Selenium code

### Why `Actions` class for clicking?
The product link on the homepage had overlapping elements causing 
`ElementNotInteractableException` with normal `.click()`. 
`Actions.moveToElement().click()` simulates real mouse movement 
and resolves this reliably without JavaScript.

### Why `textToBePresentInElementLocated` after Add to Cart?
Instead of `Thread.sleep()`, waiting for the cart header to change 
from `(0)` to `(1)` is an event-driven wait — faster and more 
reliable as it confirms the item is actually added before navigating.

### Why `Assert.assertTrue(contains)` instead of `assertEquals`?
The cart displays the full product text including variant 
e.g. `"Noir jacket - S / Blue"` while the homepage shows only 
`"Noir jacket"`. Using `contains()` handles this mismatch cleanly 
without brittle string manipulation.

### Why `driver.get("/cart")` instead of clicking My Cart?
The "My Cart" header link opens a slide-out dropdown (href="#") 
and does not navigate to the cart page. Direct navigation via 
`driver.get()` is more reliable.

---

## Assumptions Made
- The site is publicly accessible without login
- Chrome browser is installed on the machine
- Only one item is added to cart per test run
- Product variant (size/color) may differ per run but product 
  title remains the same
- Cart is session-based — same browser session is maintained 
  throughout the test

---

## Challenges Faced

| Challenge | Solution |
|-----------|----------|
| `ElementNotInteractableException` on product click | Used `Actions.moveToElement().click()` |
| `button[type='submit']` not found | Changed to `@value='Add to Cart'` XPath |
| `NullPointerException` — driver was null in page constructors | Called `super.setup()` first in `@BeforeMethod` |
| `NoSuchSessionException` — browser closed mid-test | Removed `driver.quit()` from test, kept only in `@AfterMethod` |
| My Cart link not navigating to cart page | Used `driver.get("/cart")` for direct navigation |
| Cart product name includes variant (`- S / Blue`) | Used `contains()` assertion instead of `assertEquals` |
| Product not added before cart opened | Added `WebDriverWait` for cart count to change to `(1)` |

---

## What I Would Improve With More Time

1. **Data Driven Testing** — use `@DataProvider` to test 
   multiple products dynamically instead of hardcoding product name

2. **Cross Browser Testing** — extend `BaseClass` to support 
   Firefox and Edge via a config parameter

3. **Extent Reports** — add HTML reporting for better test 
   visibility and screenshots on failure

4. **Config File** — move URL, timeout values, and product names 
   to a `config.properties` file

5. **Screenshot on Failure** — capture and attach screenshot 
   automatically when a test fails using `ITestListener`

6. **Dynamic Product Selection** — instead of hardcoding 
   `"Noir jacket"`, pass product name as a parameter so any 
   product can be tested

7. **Parallel Execution** — configure TestNG for parallel 
   test execution across multiple browsers simultaneously

