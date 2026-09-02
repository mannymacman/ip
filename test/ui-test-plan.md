# UI Test Plan

Prerequisites: Java 25 and the Gradle wrapper must be available. Run from the project root.

## GUI launch banner

Aim: Verify that launching the JavaFX GUI displays Della's welcome banner in a dialog box.

Expected output: The first Della dialog contains `Hi! I'm Della :))` and `How can I help you?`.

## Case 1: Exit command

Aim: Verify that the chatbot returns the farewell message for `bye`.

Inputs: `bye`

Expected output: The output contains `Byee! Rest well!` and the process exits successfully.
