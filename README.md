# Airlite Backend

## Environment Setup

### Java Environment
This application requires Java to be properly set up. The JAVA_HOME environment variable must be configured correctly.

```bash
# Add this to your ~/.zshrc or ~/.bash_profile
export JAVA_HOME=/opt/homebrew/Cellar/openjdk/23.0.2/libexec/openjdk.jdk/Contents/Home
```

### Auth0 Configuration
This application uses Auth0 for authentication. You need to set the following environment variables:

```bash
# Add these to your ~/.zshrc or ~/.bash_profile with your actual Auth0 values
export AUTH0_CLIENT_ID=your_actual_client_id
export AUTH0_CLIENT_SECRET=your_actual_client_secret
```

Alternatively, you can create a `.env` file in the project root with these variables.

### Running the Application
Once the environment is set up, you can run the application with:

```bash
mvn spring-boot:run
``` 