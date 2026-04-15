# Repository Guidelines

## Project Structure & Module Organization
This is a JavaFX application following a classic **3-layer architecture** with a clear separation of concerns:
- **`be` (Business Entities)**: Plain Java objects (POJOs) representing core data (e.g., `Event`, `Ticket`, `User`, `Voucher`).
- **`bll` (Business Logic Layer)**: Handles application logic and orchestration. Uses a `BLLFacade` to provide a unified interface to the GUI. Includes specialized logic like `TicketLogic` for QR/PDF generation.
- **`dal` (Data Access Layer)**: Manages data persistence. Interfaces (e.g., `IUserDAO`) define the contract, with implementations in `mock` or future database-backed DAOs.
- **`gui`**: Contains JavaFX controllers and FXML views. Note that `MainController` currently contains some internal models and logic that are being transitioned to the BLL.

## Build, Test, and Development Commands
The project uses Maven with a provided wrapper.
- **Run the application**: `./mvnw javafx:run`
- **Compile the project**: `./mvnw clean compile`
- **Run all tests**: `./mvnw test`
- **Run a specific test**: `./mvnw -Dtest=TicketLogicTest test`

## Coding Style & Naming Conventions
- **Java**: Follow standard Java naming conventions (PascalCase for classes, camelCase for methods and variables).
- **UI**: Use FXML for layout definitions (`src/main/resources/.../view-name.fxml`) and CSS for styling (`style.css`).
- **Architecture**: Always access logic through the `BLLFacade`. Do not instantiate DAOs directly in the GUI layer.
- **Dependencies**: Key libraries include `OpenJFX` for UI, `ZXing` for QR/Barcodes, and `PDFBox` for ticket generation.

## Testing Guidelines
- **Framework**: JUnit 5 (Jupiter).
- **Location**: Tests are located in `src/test/java`.
- **Patterns**: Aim to test business logic in the `bll` package. Use mocks or the provided `mock` DAL implementations for isolation.

## Commit & Pull Request Guidelines
As there are no existing commits, follow these standard practices:
- Use descriptive, imperative summary lines (e.g., "Add PDF generation for tickets").
- Separate the subject from the body with a blank line.
- Reference issues if applicable.
