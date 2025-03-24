# Test-Automation-FrameWork

## Hybrid Test Automation Framework

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/your-repo)
[![Java Version](https://img.shields.io/badge/Java-Corretto%2023-orange)](https://aws.amazon.com/corretto/)
[![Gradle](https://img.shields.io/badge/Gradle-8.11-blue)](https://gradle.org/)

### 🌟 Quick Start

#### Prerequisites
- **Java**: Amazon Corretto 23.0.1 (aarch64)
- **Gradle**: 8.11

#### Setup Guide 🛠️

1. **Clone the Repository**
   ```bash
   git clone git@github.com:akhilesh-halekote/Test-Automation-FrameWork-WebApps.git
   cd Test-Automation-FrameWork-WebApps

2. **Open in IntelliJ IDEA**
- Launch IntelliJ IDEA
- Open the Test-Automation-FrameWork-WebApps folder
- Wait for project build to complete

3. **Build the Project**
    ```bash
    ./gradlew build
4. **Run Tests**
    ```bash
    ./gradlew test
#### 📝 *Coding Conventions*
##### Naming Standards:
- camelCase for methods & variables
- PascalCase for class names
- lowercase for package names
#### 🧩 *Code Quality*
- Keep methods concise and task-specific
- Provide meaningful names to methods, classes & variables
#### 📂 *Project Structure*
- `src/main/java`: Contains the main codebase
- `src/test/java`: Contains the test cases
- `build.gradle`: Gradle build configuration file
#### 📚 *Dependencies*
- *Lombok*: Provides boilerplate code generation (e.g., getters, setters).
- *Selenium Java*: Automates browser interactions for testing.
- *TestNG*: Provides a testing framework for Java.
- *SLF4J No-Op*: Disables logging by providing a no-operation logger.
- *OkHttp*: Provides an HTTP client for making REST calls.
- *Jackson Databind*: Serializes and deserializes JSON to/from Java objects.
#### 🚀 *Features*
- *Browser Automation*: Uses Selenium WebDriver for browser interactions.
- *Cross-Browser Testing*: Supports Chrome, Firefox, Edge, and Safari.
- *Logging*: Uses SLF4J for logging.
- *HTTP Client*: Uses OkHttp for making REST calls.
- *JSON Handling*: Uses Jackson for JSON serialization/deserialization.
- *Parallel Test Execution*: Supports running tests in parallel.
- *Data-Driven Testing*: Supports parameterized tests using TestNG.
- *Screenshot Capture*: Captures screenshots on test failure.
- *Customizable Reports*: Generates detailed test reports.

#### 📄 License
This project is licensed under the MIT License - see the LICENSE file for details.


#### 📞 Support
For support, please contact [akhilesh-halekote](mailto:akhileshhalekotemohan@gmail.com).
 
#### Test Reports
- Test reports are generated in the build/reports/tests/test directory. 
- Open the index.html file to view the test results.


#### 🧩 Contributing
Contributions are welcome! Please read the CONTRIBUTING file for guidelines on how to contribute to this project.

#### References
- [Selenium WebDriver](https://www.selenium.dev/documentation/en/webdriver/)
- [Page Factory Pattern](https://www.browserstack.com/guide/page-object-model-in-selenium#:~:text=Page%20Factory%20is%20a%20class,used%20to%20initialize%20web%20elements/)