# Git Hooks

This directory contains Git hooks for the project. These hooks help maintain code quality by running checks before commits.

## Setup

To use these hooks, run the following command in your repository:

```bash
git config core.hooksPath .githooks
```

## Available Hooks

### pre-commit
Runs a clean build before each commit to ensure the code is in a working state. The commit will be aborted if the build fails.

## Manual Setup

If you prefer to set up the hooks manually, you can copy the contents of this directory to your `.git/hooks` directory:

```bash
cp -r .githooks/* .git/hooks/
chmod +x .git/hooks/*
``` 