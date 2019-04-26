# CrossPlag

CrossPlag is an open-source & cross-platform document similarity checker for doc, docx and pdf files. It is written in Kotlin and using [TornadoFX]( https://github.com/edvin/tornadofx) for its GUI.

This tool uses the Levenshtein distance to match sentences from one document to another. By this it can detect similar texts disregarding replacements, restructuring and insertions.

Note that this tool does only calculate how similar files are to each other and does not check against any online files or databases.

You can get the standalone jar file [here](https://github.com/parzel/CrossPlag/raw/master/jar/CrossPlag-all.jar).

If you want to help with development, you are happily invited to do so (but beware its messy). Below are some steps which can help you set up the project.

Feel free to fork and modify this project or issue a pull request.

-------

# Development Instructions

To set up do the following:

1) Install openjdk 8 and openjfx (or similar)
2) Install Intellij IDEA
3) Clone https://github.com/parzel/CrossPlag
4) Open project in IDEA 
5) Edit configurations
6) Add gradle
7) Choose CrossPlag as project
8) In the task field either put 'run' or 'build' (for running and or building it respectively)
9) Run your configuration