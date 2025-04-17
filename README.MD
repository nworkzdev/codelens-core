# Project: CodeLens (Visual Debugger)

## Description:

CodeLens is a visual debugging tool designed to help developers understand the execution flow of their Java code. It works by intercepting method calls at runtime and then visualizing those calls as a graph. This graph shows which methods were called, in what order, and how long they took to execute. In addition to tracking method calls, CodeLens also keeps track of the values of variables and arguments throughout the entire call chain.

## Purpose:

The primary purpose of CodeLens is to make debugging and code analysis easier and more intuitive. Instead of stepping through code line by line in a traditional debugger, developers can use CodeLens to:

* **Quickly grasp the overall structure of a program's execution.** See the big picture of how methods interact.

* **Identify performance bottlenecks.** Spot methods that are called frequently or take a long time to execute.

* **Understand complex codebases.** Visualize the call flow in unfamiliar or complicated code.

* **Debug more efficiently.** Pinpoint the source of a bug by seeing the exact sequence of method calls and how variable values change.

* **Track variable changes:** See how the values of variables and arguments change throughout the method call chain.