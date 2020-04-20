# MVVM-Coroutines
This projcet is a sample following MVVM architecture. 
Inspired by the Google io [talk](https://www.youtube.com/watch?v=PZBg5DIzNww) about modular application, this project is modularized by layer.

This application allows creating users. each user has some tasks fetched from https://jsonplaceholder.typicode.com/todos api based on his ID.

Retrofit is used for the network layer.

Room for database layer.

Repository allows to fetch from appropriate data source and return data back to the VM.

The VM notify the UI.

Navigation of JetPack to navigate through different UIs.
