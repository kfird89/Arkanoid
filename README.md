# Arkanoid

A robust, object-oriented Java application that recreates the classic arcade game Arkanoid, featuring dynamic physics, custom levels, and smooth animations.

## Overview
Arkanoid provides an interactive gaming experience where the player controls a paddle to bounce a ball and destroy blocks on the screen. The system is built entirely from scratch using Object-Oriented Programming (OOP) principles, carefully managing the relationships between graphical objects, geometry-based physics calculations, and the game's rendering loop.

## Key Features
- **Dynamic Collision Detection**: Accurately calculates intersection points between the ball, paddle, and blocks. It dynamically adjusts the ball's trajectory based on real-time physics, including region-specific hit angles on the paddle.
- **Level Management**: Features multiple unique levels with varying difficulty, distinct block arrangements, custom backgrounds, and different ball speeds.
- **Smooth Animation Loop**: Utilizes a continuous game loop to smoothly render graphics and update the state of all on-screen elements (Sprites) at a consistent frame rate.
- **Progress Tracking**: Includes real-time management of the player's score and remaining lives, with dynamic updates presented on a clean graphical interface.
- **Modular Architecture**: Cleanly separates game logic, graphical elements, and physical boundaries (Collidables) using interfaces and solid design patterns.

## Getting Started

### Prerequisites
- Java Development Kit (JDK) version 8 or higher installed on your system.

### Compilation
Create a bin directory and compile the Java source files:
```bash
   mkdir bin
   javac -d bin src/*.java
```
Run the game by:
```bash
java -cp ".;bin;biuoop-1.4.jar" Ass5Game```
