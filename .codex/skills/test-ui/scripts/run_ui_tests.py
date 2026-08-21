#!/usr/bin/env python3
"""Runs the console scenarios recorded in the project's UI test plan."""

import re
import shutil
import subprocess
import sys
import tempfile
from pathlib import Path

ROOT = Path(__file__).resolve().parents[4]
PLAN = ROOT / "test" / "ui-test-plan.md"
CASE_PATTERN = re.compile(
    r"^### (?P<name>.+?)\n.*?^Input:\n```text\n(?P<input>.*?)\n```\n"
    r"\nExpected output:\n```text\n(?P<expected>.*?)\n```",
    re.MULTILINE | re.DOTALL,
)


def load_cases():
    """Reads named UI test cases and their expected transcripts from the plan."""
    cases = list(CASE_PATTERN.finditer(PLAN.read_text()))
    if not cases:
        raise ValueError("No test cases were found in test/ui-test-plan.md.")
    return cases


def compile_program(classes_dir):
    """Compiles every Java source file into the supplied temporary directory."""
    sources = sorted((ROOT / "src" / "main" / "java").glob("*.java"))
    subprocess.run(["javac", "-d", str(classes_dir), *map(str, sources)], check=True)


def main():
    """Compiles Tem and executes every planned console scenario."""
    try:
        cases = load_cases()
    except ValueError as error:
        print(f"Test plan error: {error}")
        return 1

    classes_dir = Path(tempfile.mkdtemp(prefix="tem-ui-tests-"))
    try:
        compile_program(classes_dir)
        for index, case in enumerate(cases, start=1):
            console_input = case["input"] + "\n"
            expected = case["expected"] + "\n"
            completed = subprocess.run(
                ["java", "-cp", str(classes_dir), "Tem"],
                input=console_input,
                text=True,
                capture_output=True,
                check=False,
            )
            actual = completed.stdout
            print(f"\nTest {index}: {case['name']}")
            print("Console input:")
            print(console_input, end="")
            print("Console output:")
            print(actual, end="")
            if completed.returncode != 0 or actual != expected:
                print("\nFAILED: expected output:")
                print(expected, end="")
                print("Actual output:")
                print(actual, end="")
                return 1
        print(f"\nPASS: {len(cases)} UI test case(s) passed.")
        return 0
    finally:
        shutil.rmtree(classes_dir)


if __name__ == "__main__":
    sys.exit(main())
