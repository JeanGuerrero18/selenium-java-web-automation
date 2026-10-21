# Agent instructions

## Language

- Comments are written in **neutral Spanish** (no voseo): `//` and `/* */` comments, Javadoc, and comments in
  `pom.xml`, TestNG suites, `logback-test.xml`, `config.properties`, `.gitignore` and GitHub workflows.
  This overrides the global "comments in English" rule for this project.
- `@Test(description = ...)` strings are also in neutral Spanish: they are shown in the Extent report and must
  match the Python project's test docstrings case by case.
- Identifiers, log messages, `step(...)` texts, assertion messages and commit messages stay in English.
- Keep technical terms that are normally used untranslated (driver, page object, frame, badge, timeout, CI).

## Lint

- `mvn checkstyle:check` must report 0 violations (rules in `config/checkstyle/checkstyle.xml`, 110 columns).
  CI runs it as a separate job before the UI tests.
- Checkstyle is pinned to 12.x: 13.0.0+ needs Java 21 and this project targets Java 17.
