# Enterprise Employee Manager (CLI)

A lightweight, console based employee management system built in Java. I developed this project to demonstrate how to integrate core Java concepts specifically object oriented design, multithreading, and exception handling with persistent database storage without overcomplicating the architecture.

Instead of relying on heavy frameworks or requiring a dedicated database server, this application uses a self contained SQLite file. You can run it directly from your environment, save employee records, and trigger background reporting tasks without freezing the main terminal interface.

---

##  1. Environment Setup (Prerequisites)

Before running the application, ensure your local machine meets the following requirements:

* **Java Development Kit (JDK):** You must have JDK 17 or higher installed. You can verify your installation by opening a terminal and running:
```bash
java -version

```


* **IDE or Text Editor:** Visual Studio Code (with the "Extension Pack for Java" installed), IntelliJ IDEA, or Eclipse is recommended. You can also run this strictly via the command line.

---

##  2. Dependency Installation

Because Java does not have native, built in support for SQLite, the project requires a standalone JDBC (Java Database Connectivity) driver to translate Java commands into SQLite operations.

1. Navigate to the Maven Central Repository in your web browser.
2. Search for **`sqlite-jdbc`** (look for the package maintained by `org.xerial`).
3. Go to the **Versions** tab, select the latest version (e.g., `3.53.4.0`), and download the compiled **`.jar`** file (do not download the source code zip).
4. Create a folder named `lib` inside your project directory and move the downloaded `.jar` file into it.

---

##  3. Configuration

How you link the JDBC dependency depends on how you plan to evaluate the project.

**Option A: Visual Studio Code Configuration**

1. Open your project folder in VS Code.
2. Expand the **Java Projects** panel in the bottom left sidebar.
3. Scroll down to **Referenced Libraries** and click the **+** (Add) icon.
4. Select the `sqlite-jdbc-x.x.x.jar` file you downloaded.

**Option B: IntelliJ IDEA Configuration**

1. Go to `File` > `Project Structure` > `Modules`.
2. Click the **Dependencies** tab.
3. Click the **+** sign, select `JARs or directories`, and point it to your downloaded `.jar` file.

**Option C: Command Line (CLI) Configuration**
No IDE configuration is needed. You will manually pass the `.jar` file to the compiler in the execution step below.

---

##  4. Execution

**Running via IDE (VS Code / IntelliJ / Eclipse):**
Open the `EMSApplication.java` file and click the **Run** button (usually located just above the `public static void main` method or in the top right corner).

**Running via Command Line:**
Open your terminal, navigate to the root directory of the project, and compile the Java files while pointing to the SQLite driver in your `lib` folder:

*Windows:*

```bash
javac -cp ".;lib/sqlite-jdbc-3.53.4.0.jar" *.java
java -cp ".;lib/sqlite-jdbc-3.53.4.0.jar" EMSApplication

```

*Mac/Linux:*

```bash
javac -cp ".:lib/sqlite-jdbc-3.53.4.0.jar" *.java
java -cp ".:lib/sqlite-jdbc-3.53.4.0.jar" EMSApplication

```

*(Note: Replace `3.53.4.0` with the exact version number you downloaded).*

---

##  5. Using the Application

Upon successful execution, the terminal will display the Enterprise Employee Manager main menu.

* **Initial Run:** The system will automatically execute `CREATE TABLE IF NOT EXISTS` and generate a local database file named `ems.db` in your root folder.
* **Registering Employees (Option 1):** Enter a name and a numeric salary. The system will persist this to the database. If you enter text for the salary, the system's `NumberFormatException` handling will catch it and keep the application running.
* **Running Reports (Option 2):** The application will spawn a separate background thread (simulating a 2 second heavy data extraction) to generate an `employee_report.txt` file. You will immediately regain control of the menu while the thread runs in the background.

---

##  Architecture & Code Structure

* `Employee.java` (Abstract): The base model defining the contract for all employee types.
* `FullTimeEmployee.java`: A concrete implementation extending the base model to include bonus calculations.
* `DatabaseManager.java`: Handles the JDBC connection, table initialization, and SQL `INSERT` commands.
* `ReportGenerator.java`: Implements the `Runnable` interface to handle heavy text file exports on a separate worker thread.
* `EMSApplication.java`: The main loop and interactive console menu.

---

##  Troubleshooting

**"No suitable driver found for jdbc:sqlite:ems.db"**
This occurs when the Java compiler cannot find the SQLite `.jar` file. If using an IDE, double check that the file is actively linked in your project settings. If using the CLI, ensure your `-cp` (classpath) flag points to the exact, correct path and spelling of the `.jar` file.

**Terminal Warnings about Native Access (JDK 21+)**
If you are running a newer Java version, you might see console warnings stating: `A restricted method in java.lang.System has been called`. This is a strict security warning triggered because the SQLite driver interacts natively with your operating system. It will not break the program. To suppress it in VS Code, add `"vmArgs": "--enable-native-access=ALL-UNNAMED"` to your `launch.json` file. For CLI execution, add the flag right after `java`:
`java --enable-native-access=ALL-UNNAMED -cp ...`
