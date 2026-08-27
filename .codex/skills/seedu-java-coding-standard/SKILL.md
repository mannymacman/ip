---
name: seedu-java-coding-standard
description: Apply the SE-EDU intermediate Java coding standard to this Della project when creating, editing, or reviewing Java code.
---

# SE-EDU Java Coding Standard

Apply this skill to all Java production code and Java tests in this repository. Use the
[SE-EDU Java coding standard (basic + intermediate)](https://se-education.org/guides/conventions/java/intermediate.html)
as the authority. For topics it does not cover, follow the Google Java Style Guide as
the SE-EDU guide directs.

## Required conventions

- Use lowercase package names, PascalCase nouns for types, camelCase verbs for methods,
  and camelCase variables. Name booleans with `is`, `has`, `can`, `should`, or similar;
  use plural names for collections. Test methods may use
  `featureUnderTest_testScenario_expectedBehavior()`.
- Indent with four spaces; use K&R braces; include braces for every loop and conditional
  body. Keep lines within 120 characters, aiming for 110 or fewer, and wrap at
  readability-preserving locations.
- Keep imports explicit, minimal, and consistently ordered: static imports, Java imports,
  then non-Java imports, with a blank line between groups.
- Declare variables in the smallest practical scope and initialise them at declaration when
  a valid initial value exists. Do not expose mutable class state through public fields.
- Separate logical units in a block with one blank line. Write comments in clear American
  English without local slang; remove comments that merely repeat the code.
- Add descriptive Javadocs to public classes and public methods, except obvious getters and
  setters, exact Javadoc-preserving overrides, and test code. Start method summaries with
  a third-person verb such as “Returns”, “Adds”, or “Displays”; use complete, punctuated
  tag descriptions and a blank line before tags.

## Before finishing

Review modified Java files for the conventions above. Run the relevant Gradle verification
task with Java 25; run `javadoc` as well when Javadocs were changed.
