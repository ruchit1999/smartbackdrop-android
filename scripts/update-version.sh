#!/bin/bash

# SmartBackdrop Version Update Script
# Usage: ./scripts/update-version.sh <new_version>

set -e

if [ $# -eq 0 ]; then
    echo "Usage: $0 <new_version>"
    echo "Example: $0 1.0.1"
    exit 1
fi

NEW_VERSION=$1
echo "Updating SmartBackdrop to version: $NEW_VERSION"

# Update version in build.gradle.kts files
echo "Updating build.gradle.kts files..."

# Core module
sed -i "s/version = \"[^\"]*\"/version = \"$NEW_VERSION\"/g" smartbackdrop-core/build.gradle.kts

# Compose module
sed -i "s/version = \"[^\"]*\"/version = \"$NEW_VERSION\"/g" smartbackdrop-compose/build.gradle.kts

# Views module
sed -i "s/version = \"[^\"]*\"/version = \"$NEW_VERSION\"/g" smartbackdrop-views/build.gradle.kts

# Update README.md
echo "Updating README.md..."
sed -i "s/implementation(\"com.github.smartbackdrop:smartbackdrop-compose:[^\"]*\")/implementation(\"com.github.smartbackdrop:smartbackdrop-compose:$NEW_VERSION\")/g" README.md
sed -i "s/implementation(\"com.github.smartbackdrop:smartbackdrop-views:[^\"]*\")/implementation(\"com.github.smartbackdrop:smartbackdrop-views:$NEW_VERSION\")/g" README.md
sed -i "s/implementation(\"com.github.smartbackdrop:smartbackdrop-core:[^\"]*\")/implementation(\"com.github.smartbackdrop:smartbackdrop-core:$NEW_VERSION\")/g" README.md

# Update QUICKSTART.md
echo "Updating QUICKSTART.md..."
sed -i "s/implementation(\"com.github.smartbackdrop:smartbackdrop-compose:[^\"]*\")/implementation(\"com.github.smartbackdrop:smartbackdrop-compose:$NEW_VERSION\")/g" QUICKSTART.md
sed -i "s/implementation(\"com.github.smartbackdrop:smartbackdrop-views:[^\"]*\")/implementation(\"com.github.smartbackdrop:smartbackdrop-views:$NEW_VERSION\")/g" QUICKSTART.md
sed -i "s/implementation(\"com.github.smartbackdrop:smartbackdrop-core:[^\"]*\")/implementation(\"com.github.smartbackdrop:smartbackdrop-core:$NEW_VERSION\")/g" QUICKSTART.md

# Update DEPENDENCY_USAGE.md
echo "Updating DEPENDENCY_USAGE.md..."
sed -i "s/implementation(\"com.github.smartbackdrop:smartbackdrop-compose:[^\"]*\")/implementation(\"com.github.smartbackdrop:smartbackdrop-compose:$NEW_VERSION\")/g" DEPENDENCY_USAGE.md
sed -i "s/implementation(\"com.github.smartbackdrop:smartbackdrop-views:[^\"]*\")/implementation(\"com.github.smartbackdrop:smartbackdrop-views:$NEW_VERSION\")/g" DEPENDENCY_USAGE.md
sed -i "s/implementation(\"com.github.smartbackdrop:smartbackdrop-core:[^\"]*\")/implementation(\"com.github.smartbackdrop:smartbackdrop-core:$NEW_VERSION\")/g" DEPENDENCY_USAGE.md

# Update CHANGELOG.md
echo "Updating CHANGELOG.md..."
sed -i "s/## \[Unreleased\]/## \[Unreleased\]\n\n## \[$NEW_VERSION\] - $(date +%Y-%m-%d)/g" CHANGELOG.md

echo "Version updated to $NEW_VERSION successfully!"
echo ""
echo "Next steps:"
echo "1. Review the changes: git diff"
echo "2. Commit the changes: git add . && git commit -m \"chore: bump version to $NEW_VERSION\""
echo "3. Create a tag: git tag v$NEW_VERSION"
echo "4. Push changes: git push origin main --tags"
echo ""
echo "JitPack will automatically build and publish the new version." 