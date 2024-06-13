# Scan-Login

## Overview
Scan-Login is a demo project showcasing WeChat scan login functionality using Spring Boot. It allows users to authenticate by scanning a QR code with their WeChat app.

## Features
- Generate QR code for WeChat login
- Handle WeChat login callbacks
- Simple Spring Boot application setup

## Prerequisites
- Java 8 or higher
- Maven
- WeChat developer account

## Installation

1. **Clone the repository**
    ```bash
    git clone https://github.com/cosmopolitan033/scan-login.git
    cd scan-login
    ```

2. **Build the project**
    ```bash
    mvn clean install
    ```

3. **Run the application**
    ```bash
    mvn spring-boot:run
    ```

## Configuration

1. **Update `application.properties`**
    Configure your WeChat app credentials in `src/main/resources/application.properties`:
    ```properties
    wechat.appId=YOUR_APP_ID
    wechat.appSecret=YOUR_APP_SECRET
    wechat.callbackUrl=YOUR_CALLBACK_URL
    ```

## Usage

1. **Access the application**
    Open your browser and go to `http://localhost:8080`.

2. **Generate QR Code**
    Click the button to generate a QR code for WeChat login.

3. **Scan the QR Code**
    Use your WeChat app to scan the QR code.

4. **Handle Callback**
    After scanning, WeChat will redirect to the callback URL configured.

## Project Structure

- `src/main/java`: Contains the Java source files
    - `controller`: Handles web requests
    - `service`: Contains business logic
    - `config`: Configuration classes
- `src/main/resources`: Contains application configuration files
- `pom.xml`: Maven configuration file

## License

This project is licensed under the MIT License. See the [LICENSE](./LICENSE) file for details.

## Contributing

1. Fork the repository.
2. Create your feature branch: `git checkout -b my-new-feature`.
3. Commit your changes: `git commit -am 'Add some feature'`.
4. Push to the branch: `git push origin my-new-feature`.
5. Submit a pull request.

## Acknowledgments

- Inspired by the need for a simple demo of WeChat login integration with Spring Boot.
