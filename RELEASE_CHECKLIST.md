# Release Checklist

This checklist ensures a smooth release process for SmartBackdrop Android SDK.

## 🚀 Pre-Release Checklist

### Code Quality
- [ ] All tests pass: `./gradlew test`
- [ ] Code style check: `./gradlew ktlintCheck`
- [ ] Build all modules: `./gradlew build`
- [ ] Sample app builds and runs: `./gradlew :sample-app:assembleDebug`
- [ ] No critical bugs in open issues
- [ ] All planned features for this release are complete

### Documentation
- [ ] README.md is up to date
- [ ] QUICKSTART.md is current
- [ ] DEPENDENCY_USAGE.md is accurate
- [ ] CHANGELOG.md has all changes documented
- [ ] API documentation is complete
- [ ] Sample app demonstrates all features

### Version Management
- [ ] Update version numbers in all modules
- [ ] Update dependency references in documentation
- [ ] Update CHANGELOG.md with release date
- [ ] Ensure version consistency across all files

## 📦 Release Process

### 1. Update Version
```bash
# Use the version update script
./scripts/update-version.sh 1.0.1
```

### 2. Review Changes
```bash
# Review all changes
git diff
```

### 3. Commit Changes
```bash
# Add all changes
git add .

# Commit with conventional commit message
git commit -m "chore: bump version to 1.0.1

- Update version numbers in all modules
- Update documentation references
- Update CHANGELOG.md"
```

### 4. Create Tag
```bash
# Create annotated tag
git tag -a v1.0.1 -m "Release version 1.0.1"
```

### 5. Push Changes
```bash
# Push to main branch
git push origin main

# Push tags
git push origin --tags
```

## 🔄 Automated Processes

### GitHub Actions
- [ ] CI/CD pipeline runs successfully
- [ ] Tests pass on all platforms
- [ ] Build artifacts are generated
- [ ] Security scans complete without issues

### JitPack Integration
- [ ] JitPack automatically builds the new version
- [ ] All modules are published successfully
- [ ] Dependencies are available for download
- [ ] Version appears on JitPack website

### GitHub Release
- [ ] GitHub Actions creates release automatically
- [ ] Release notes are generated
- [ ] Sample APK is uploaded as asset
- [ ] Release is marked as latest

## 📋 Post-Release Tasks

### Verification
- [ ] Test dependency resolution in a new project
- [ ] Verify all modules are available on JitPack
- [ ] Check that documentation links work
- [ ] Confirm sample app works with new version

### Communication
- [ ] Update GitHub repository description if needed
- [ ] Post release announcement on social media
- [ ] Update any external documentation sites
- [ ] Respond to community questions

### Monitoring
- [ ] Monitor for any issues with the new release
- [ ] Check GitHub Issues for new bug reports
- [ ] Monitor JitPack build logs for any failures
- [ ] Track download statistics

## 🎯 Release Types

### Patch Release (1.0.1)
- Bug fixes only
- No breaking changes
- Minimal testing required
- Can be released quickly

### Minor Release (1.1.0)
- New features added
- Backward compatible
- Comprehensive testing required
- Update documentation

### Major Release (2.0.0)
- Breaking changes
- Major new features
- Extensive testing required
- Migration guide needed

## 📊 Release Metrics

Track these metrics for each release:

- **Downloads**: Number of downloads from JitPack
- **Issues**: Number of new issues reported
- **Stars**: GitHub repository stars
- **Forks**: GitHub repository forks
- **Contributors**: New contributors

## 🔧 Troubleshooting

### Common Issues

#### JitPack Build Fails
- Check JitPack build logs
- Verify `jitpack.yml` configuration
- Ensure all dependencies are available
- Check for syntax errors in build files

#### Version Not Available
- Wait for JitPack to complete build (usually 5-10 minutes)
- Check JitPack website for build status
- Verify tag was pushed correctly
- Check for any build errors

#### Documentation Out of Sync
- Update all documentation files
- Check for hardcoded version numbers
- Verify all links work correctly
- Test installation instructions

## 📞 Support

For release-related issues:

- **JitPack Issues**: [JitPack Support](https://jitpack.io/docs/ANDROID.html)
- **GitHub Actions**: Check Actions tab in repository
- **Community**: GitHub Discussions or Issues

## 🔗 Useful Commands

```bash
# Check current version
grep -r "version = " */build.gradle.kts

# Test build locally
./gradlew clean build

# Run all tests
./gradlew test

# Check for outdated dependencies
./gradlew dependencyUpdates

# Build sample app
./gradlew :sample-app:assembleDebug

# Create release tag
git tag -a v1.0.1 -m "Release version 1.0.1"

# Push release
git push origin main --tags
```

---

**Happy releasing!** 🎉 