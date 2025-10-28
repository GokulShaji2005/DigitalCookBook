

## Project Title

DigitalCookBook — Desktop Recipe Manager (Java Swing)

## Description

DigitalCookBook is a small, modular Java desktop application that helps cooks, chefs and viewers manage recipes locally. It provides a Swing-based user interface for signing up and signing in users, adding and editing recipes, categorizing recipes, and marking favourites. The app uses JDBC for persistence and is organized with a clear package layout for `dao`, `model`, `services`, and `ui` layers.

This repository contains the full source code under `src/` and is structured so it can be built and run with a standard Java toolchain (JDK 11+ recommended).

## Features

- User authentication (Sign up / Login / Logout)
- Add, edit, view and delete recipes
- Recipe categorization and search by title/category
- Mark and view favourite recipes
- Admin dashboard for managing users and viewers
- Clean Swing UI separated into logical panels (auth, cook, viewer, admin)
- Simple JDBC-based persistence (see `dao/dbConnection/DBConnection.java`)

## Installation

Prerequisites:

- Java JDK 11 or later installed and available on your PATH. The project uses the Java module system and Swing.

Steps:

1. Clone or download this repository.
2. From the project root open a terminal/command prompt.
3. Compile the sources. From the `src` folder you can compile with javac or from the project root; examples below assume you are at the repository root and have a JDK installed.

Compile (from project root):

```bash
javac -d out --module-source-path src $(find src -name "*.java")
```

This places compiled classes under `out/` using the module source path. On Windows with Git Bash you may need to adjust the `find` command or provide a list of files.

## Usage

Run the application using the Java module system. The project's module name is `digitalCookBook` (see `src/module-info.java`). The following runs the main class located in `main.Main`:

```bash
java --module-path out -m digitalCookBook/main.Main
```

If you prefer a simpler classpath run (for development), compile without modules and run the `Main` class directly. Example:

```bash
# compile (non-modular)
javac -d classes $(find src -name "*.java")
# run
java -cp classes main.Main
```

Notes:

- The app expects a local JDBC connection handled by `dao/dbConnection/DBConnection.java`. Configure any connection strings or embedded database files there if needed.
- If you plan to package the app as a runnable JAR, create a manifest with the `Main-Class: main.Main` entry and include all compiled classes and resource files.

## Contributing

Contributions are welcome. Suggested workflow:

1. Fork the repository.
2. Create a feature branch (git checkout -b feature/your-feature).
3. Make small, focused commits.
4. Run and manually test the app locally.
5. Open a pull request describing the change.

When contributing, please:

- Follow the existing package structure and naming conventions.
- Add unit tests where appropriate (this project is Swing-heavy and may require integration tests).
- Keep UI changes incremental and documented.

## License

This project is provided under the MIT License. You can copy, modify and distribute the code. Replace this block with a different license if you prefer.

---

If you'd like, I can:
- Add a simple build script (Ant, Maven or Gradle) and a runnable JAR target.
- Create a quick start script for Windows (batch or PowerShell) that compiles and runs the app.

Let me know which you'd prefer.
