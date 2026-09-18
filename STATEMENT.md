# Project Statement: Enterprise Employee Manager V1.0

## Problem Statement

Small businesses and individual department managers frequently need a straightforward, localized method to manage employee records and generate administrative reports. However, many existing solutions are either overly complex requiring expensive cloud subscriptions, dedicated servers, and steep learning curves or too rudimentary, such as flat spreadsheets that lack automated data validation and processing capabilities. There is a need for a lightweight, reliable, and self contained system that securely logs employee data and executes administrative tasks without requiring a complex IT infrastructure.

## Scope of Project

This project encompasses the development of a localized, Command Line Interface (CLI) application built in Java. The scope is specifically focused on foundational human resources tasks: capturing new employee data (name and base salary), validating the input, persisting the data to a serverless local database (SQLite), and generating text based administrative reports.

**Out of Scope:**

* Graphical User Interfaces (GUI) or web based frontends.
* Complex payroll calculations (e.g., tax deductions, hourly timesheets).
* Networked or cloud based database deployments.
* Advanced CRUD operations (Update and Delete are excluded from V1.0).

## Target Users

* **Small Business Owners / Micro enterprise HR:** Users who need a fast, offline, and reliable tool to log basic employee information without purchasing enterprise software.
* **Department Managers:** Individuals looking for a zero configuration desktop tool to keep track of their internal team roster and compensation.
* **Developers & IT Students:** Individuals seeking a clean reference architecture demonstrating how to combine Java Object Oriented Programming (OOP), JDBC database connections, and multithreading in a localized CLI environment.

## High Level Features

* **Interactive Command Line Interface:** A persistent, menu driven console that guides the user through available operations and safely handles session termination.
* **Zero Configuration Persistence (SQLite):** Automatically provisions a local .db file on the first run, allowing users to save and retain employee records permanently without setting up a database server.
* **Asynchronous Reporting (Multithreading):** System reports are generated and written to local text files on a background worker thread. This non blocking architecture ensures the main terminal menu remains instantly responsive to the user, even during simulated heavy I/O operations.
* **Robust Input Validation & Error Handling:** Built in exception handling (e.g., NumberFormatException) safely intercepts invalid user inputs, such as typing alphabetical characters into financial fields, preventing application crashes.
* **Extensible Object Oriented Architecture:** Core data models are built using abstract classes, ensuring a standardized contract that can easily be expanded in future versions to include diverse employee classifications (e.g., part time, contractors).
