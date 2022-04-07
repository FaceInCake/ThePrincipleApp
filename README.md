[![Android](https://github.com/FaceInCake/ThePrincipleApp/actions/workflows/Android.yml/badge.svg?branch=main)](https://github.com/FaceInCake/ThePrincipleApp/actions/workflows/Android.yml)

# ThePrincipleApp

Android application for the Comp4200 course, class of Winter 2022, group 7.
This application has a multitude of features for managing and organizing your school life.
Classes, exams, assignments, oh my!

## Table of Contents
1. [Development](#development)
   1. [GitHub](#github)
   1. [Android Studio](#android-studio)
1. [Contributing](#contributing)
1. [Testing](#testing)
   1.  [Database Tests](#database-tests)
3. [Running](#running)
4. [Help](#help)

## Development

### GitHub

[GitHub-Projects](https://github.com/FaceInCake/ThePrincipleApp/projects) and Issues is used to keep track of past, present, and future tasks.
It follows an automated Kanban style with reviewing. More can be read in the [Contributing](#contributing) section

### Android Studio

[Android-Studio](https://developer.android.com/studio) (version 2021.1.1 or newer) is used for development.
You may use the Git integration if you want, however it is not necessary.
To initialize, you can just clone the repo wherever you want, and then open that new directory with Android Studio.
This project will be programed with Java. Whom'st'd'

## Contributing

Pick an [Issue](https://github.com/FaceInCake/ThePrincipleApp/issues) you wish to solve, then assign yourself to it.
Or, write a comment on it, stating your intentions.
You should always make a new branch/fork for any set of commits you wish to submit.
You can then push freely to this new branch.

When your changes are done and the program is stable, you can PR into main.
Be sure to check out the [Testing] section to help verify your program is stable.
You must create a Pull Reqeust (PR), when merging into main.
Remember to link the [Issue](https://github.com/FaceInCake/ThePrincipleApp/issues) you're solving in the PR.

## Testing

JUnit is the goto for unit testing, as you may guess.
Any Java class that doesn't interact with the Android system should just be a JUnit test.
You can find these tests in the [*(test)*](https://github.com/FaceInCake/ThePrincipleApp/tree/main/app/src/test/java/com/example/theprincipleapp) directory of the main module.

But if your Java class does interact with the Android system, you should use an Instrumented Test.
These tests open an emulator to run the tests with.
These can be found in the [*(androidTest)*](https://github.com/FaceInCake/ThePrincipleApp/tree/main/app/src/androidTest/java/com/example/theprincipleapp) directory of the main module.

There exists two build configs for running all instrumented tests, and running all junit tests.

### Database Tests
- subclass DBTest
  - gives you, static UserDatabase : udb
  - gives you, static Context : appCon
- import static org.junit.Assert.*;
- Create a @Before func : prepoluate the UserDatabase
  - Feel free to use the static methods for creating example objects
  - ex, CourseTest.exampleCourse() -> Course
  - Oh, and make one for your class if you can (keep it trivial)
- create any number of @Test func : a test
You can check out any other Test class and copy from it aswell

***There's a glitch with Instrumented tests, I don't have a fix for it yet. If you can figure it out I'll love you forever.***

Every public method should have near 100% coverage.
Else, you should strive for >90% test coverage.
There's no need to test getters and simple setters, that's stupid, don't do that.
I will find you and eat your keyboard if I find you doing that.

You can follow the ExampleTest class for how to make one.

In Android Studio, you can go to any of these file individually, if you want to run a certain class or method. There's also a way to run all tests that illudes me, that's easy and requires no setup.

## Running

If you're using Android Studio, you can use the built-in emulator to run the app.

***However the heck you publish an app.***

## Help

You can make a GitHub Issue if you encounter any problems.
Please be verbose and give steps to reproduce any problems you have.
faceincake@gmail.com
