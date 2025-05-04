# 🧾 YCA: Tax Calculation System

## 📌 Overview

**YCA (Your Calculation Assistant)** is a desktop-based income tax calculation system developed in **Java**. It features a **Swing GUI** that allows users to input their income details and calculates their tax based on the latest Indian tax slabs. The application also supports CSV export, ITR file generation using file handling, and full database integration using **MySQL**.

This project is built and managed using **Maven**, which handles dependencies and builds efficiently.

---

## 🎯 Features

- 💰 Calculates tax based on updated Indian slabs.
- 🖥️ Java Swing-based desktop UI for user interaction.
- 📄 Exports tax summaries to CSV using OpenCSV.
- 📑 Generates ITR files via file handling (Java FileWriter/BufferedWriter).
- 🗃️ Connects to MySQL database using JDBC for record storage and retrieval.

---

## 🛠️ Technologies Used

- **Java** (JDK 8+)
- **Swing** (GUI)
- **Maven** (Build Tool)
- **MySQL** (Database)
- **OpenCSV** (CSV export)
- **MySQL Connector/J** (Database connectivity)

---

## ⚙️ Why Maven?

Maven simplifies the entire development workflow by:

- Automatically downloading and managing third-party libraries.
- Providing a standard project structure and consistent build process.
- Handling compilation, testing, packaging, and deployment with simple commands.
- Allowing easy integration of new tools and plugins with minimal configuration.

---

## 📄 Purpose of `pom.xml`

The `pom.xml` file is the core of any Maven project. It serves the following purposes in the YCA project:

- Declares all external libraries (like MySQL Connector and OpenCSV) needed for the application.
- Specifies the Java version, project structure, and build configurations.
- Manages plugin configurations required for compiling and packaging the project.
- Ensures repeatable and portable builds across different environments without manually handling JAR files.

---

## 🖥️ Swing User Interface

The application’s front end is developed using **Java Swing**, providing a graphical interface where users can:

- Enter income and personal details.
- View calculated tax.
- Trigger export, ITR file generation, and database operations via buttons and forms.

---

## 🗃️ Database Integration

YCA connects to a **MySQL** database using JDBC. Tax data is securely stored and queried through SQL operations with proper exception handling and prepared statements to prevent SQL injection.

---

## 📂 File Handling

- **ITR File Generation:** Uses file I/O to generate formatted ITR files (`.txt` or `.itr`).
- **CSV Export:** Uses the OpenCSV library to export structured tax data for reporting or submission.

---

## 👨‍💻 Developed By

**Swamil Randive**  
MIT WPU Pune | Internship @ IIT Jodhpur  
Department of Computer Engineering Technology


