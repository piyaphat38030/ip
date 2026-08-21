---
name: test-ui
description: Runs Tem's command-line UI tests from test/ui-test-plan.md and reports the complete console transcript. Use after changing Tem commands, task display, or task behavior.
disable-model-invocation: true
---

# Test UI

Run the UI test plan after each code update that affects Tem.

1. Update `test/ui-test-plan.md` if the expected behavior has changed or a relevant case is missing.
2. Run:

   ```bash
   python3 .codex/skills/test-ui/scripts/run_ui_tests.py
   ```

3. Inspect the printed console input and output for every test case.
4. On the first failure, stop and report the expected and actual output; do not continue to later cases.
