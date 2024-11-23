# Twisk  
Twisk is a queue simulation tool that allows users to design and test their own workflows. Built using Java for the GUI (JavaFX), it leverages C for client simulation through multithreading and connects the two via JNI.  

## Features
- Design custom workflows with "Activities" and "Counters."
- Configure entry/exit points for clients in the simulation.
- Choose between Uniform, Gaussian, and Exponential distributions for client arrival rates.
- Save and load simulation worlds as `.json` files.
- Import pre-made worlds and modify them.
- Real-time client simulation powered by multithreading in C.

## How it Works
Twisk operates as a hybrid system:
- **Java**: Provides the graphical interface and handles the main application logic.  
- **C**: Manages the client simulation using multithreading for efficient performance.  
- **JNI**: Bridges Java and C, enabling seamless integration of the two languages.  

This architecture allows Twisk to simulate fifty of clients in real-time while offering a user-friendly design interface.

## Resources
- [JavaFX Documentation](https://openjfx.io/)
- [JNI Documentation](https://docs.oracle.com/javase/8/docs/technotes/guides/jni/)
- [Example JSON File for Twisk](https://github.com/sbnh1/Twisk/blob/main/src/main/resources/import/monde1.json)

