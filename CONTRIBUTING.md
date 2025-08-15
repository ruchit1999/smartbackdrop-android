# Contributing to SmartBackdrop Android SDK

Thank you for your interest in contributing to SmartBackdrop! This document provides guidelines and information for contributors.

## 🤝 How to Contribute

### Types of Contributions

We welcome various types of contributions:

- **Bug Reports**: Report bugs and issues you encounter
- **Feature Requests**: Suggest new features or improvements
- **Code Contributions**: Submit pull requests with code changes
- **Documentation**: Improve or add documentation
- **Examples**: Create sample apps or usage examples
- **Testing**: Add tests or improve test coverage

### Before You Start

1. **Check Existing Issues**: Search existing issues to avoid duplicates
2. **Read Documentation**: Familiarize yourself with the project structure
3. **Set Up Development Environment**: Follow the setup instructions below

## 🛠️ Development Setup

### Prerequisites

- Android Studio Arctic Fox or later
- JDK 17 or later
- Android SDK API 21+
- Git

### Getting Started

1. **Fork the Repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/smartbackdrop-android.git
   cd smartbackdrop-android
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory and open it

3. **Sync and Build**
   ```bash
   ./gradlew build
   ```

4. **Run the Sample App**
   ```bash
   ./gradlew :sample-app:installDebug
   ```

### Project Structure

```
smartbackdrop-android/
├── smartbackdrop-core/          # Core algorithms and engine
├── smartbackdrop-compose/       # Jetpack Compose integration
├── smartbackdrop-views/         # Traditional Android Views
├── sample-app/                  # Demo application
├── gradle/                      # Gradle configuration
└── docs/                        # Documentation
```

## 📝 Code Style Guidelines

### Kotlin Conventions

- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable and function names
- Prefer `val` over `var` when possible
- Use expression bodies for simple functions

### Code Formatting

- Use 4 spaces for indentation
- Maximum line length: 120 characters
- Use trailing commas for multi-line collections
- Add blank lines between logical sections

### Documentation

- Add KDoc comments for all public APIs
- Include `@param`, `@return`, and `@throws` tags where applicable
- Write clear, concise descriptions
- Include usage examples for complex APIs

Example:
```kotlin
/**
 * Creates a backdrop specification from an image.
 *
 * @param bitmap The source image bitmap
 * @param config Configuration for backdrop generation
 * @return A [BackdropSpec] containing the generated backdrop and foreground colors
 * @throws IllegalArgumentException if bitmap is null or empty
 */
suspend fun buildSpec(bitmap: Bitmap, config: BackdropConfig): BackdropSpec
```

### Testing

- Write unit tests for new functionality
- Aim for at least 80% code coverage
- Use descriptive test names
- Test both success and failure cases

Example:
```kotlin
@Test
fun `should create gradient backdrop from edge analysis`() {
    // Given
    val bitmap = createTestBitmap()
    val config = BackdropConfig(strategy = Strategy.EdgeGradientThenBlur)
    
    // When
    val spec = engine.buildSpec(bitmap, config)
    
    // Then
    assertThat(spec.background).isInstanceOf(GradientBackdrop::class.java)
}
```

## 🚀 Making Changes

### Creating a Feature Branch

```bash
git checkout -b feature/your-feature-name
```

### Commit Guidelines

Use conventional commit messages:

- `feat:` New features
- `fix:` Bug fixes
- `docs:` Documentation changes
- `style:` Code style changes (formatting, etc.)
- `refactor:` Code refactoring
- `test:` Adding or updating tests
- `chore:` Maintenance tasks

Example:
```
feat: add support for custom backdrop strategies

- Add BackdropStrategy interface
- Implement factory pattern for strategy creation
- Add unit tests for new functionality
- Update documentation with usage examples
```

### Testing Your Changes

1. **Run Unit Tests**
   ```bash
   ./gradlew test
   ```

2. **Run Sample App**
   ```bash
   ./gradlew :sample-app:installDebug
   ```

3. **Check Code Style**
   ```bash
   ./gradlew ktlintCheck
   ```

4. **Build All Modules**
   ```bash
   ./gradlew build
   ```

## 📤 Submitting Changes

### Pull Request Process

1. **Create a Pull Request**
   - Go to your fork on GitHub
   - Click "New Pull Request"
   - Select the main branch as the target

2. **Fill Out the Template**
   - Describe the changes you made
   - Link any related issues
   - Include screenshots for UI changes
   - List any breaking changes

3. **Review Process**
   - Maintainers will review your code
   - Address any feedback or requested changes
   - Ensure all CI checks pass

### Pull Request Template

```markdown
## Description
Brief description of the changes made.

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Testing
- [ ] Unit tests pass
- [ ] Sample app works correctly
- [ ] Manual testing completed

## Screenshots (if applicable)
Add screenshots for UI changes.

## Checklist
- [ ] Code follows project style guidelines
- [ ] Self-review completed
- [ ] Documentation updated
- [ ] Tests added/updated
```

## 🐛 Reporting Issues

### Bug Report Template

```markdown
## Bug Description
Clear description of the bug.

## Steps to Reproduce
1. Step 1
2. Step 2
3. Step 3

## Expected Behavior
What you expected to happen.

## Actual Behavior
What actually happened.

## Environment
- Android Version: [e.g., API 30]
- Device: [e.g., Pixel 5]
- SmartBackdrop Version: [e.g., 1.0.0]

## Additional Information
Any other relevant information, logs, or screenshots.
```

## 🎯 Feature Requests

### Feature Request Template

```markdown
## Feature Description
Clear description of the requested feature.

## Use Case
Explain why this feature would be useful.

## Proposed Implementation
Optional: Suggest how this could be implemented.

## Alternatives Considered
Optional: Describe any alternatives you've considered.
```

## 📋 Code of Conduct

### Our Standards

- Be respectful and inclusive
- Use welcoming and inclusive language
- Be collaborative and constructive
- Focus on what is best for the community
- Show empathy towards other community members

### Enforcement

- Unacceptable behavior will not be tolerated
- Maintainers will take appropriate action
- Violations may result in temporary or permanent ban

## 🏆 Recognition

Contributors will be recognized in:

- GitHub contributors list
- Release notes
- Project documentation
- Community acknowledgments

## 📞 Getting Help

If you need help with contributing:

- **GitHub Issues**: For bug reports and feature requests
- **GitHub Discussions**: For questions and general discussion
- **Email**: support@smartbackdrop.dev

## 📄 License

By contributing to SmartBackdrop, you agree that your contributions will be licensed under the MIT License.

---

Thank you for contributing to SmartBackdrop! 🎉
