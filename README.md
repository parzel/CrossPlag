# CrossPlag

CrossPlag is an open-source & cross-platform document similarity checker for doc, docx and pdf files. It is written in Kotlin and using [TornadoFX]( https://github.com/edvin/tornadofx) for the GUI.

Note that this tool does only calculate how similar files are to each other and does not check against any online files or databases.

You can get the standalone jar file here.

If you want to help with development, you are happily invited to do so (but beware its messy). Below are some steps which can help you set up the project.

Feel free to fork and modify this project or issue a pull request.

-------

# Development Instructions

To set up do the following:

1) Install openjdk 8 and openjfx (or similar)
2) Install Intellij IDEA
3) git clone https://github.com/parzel/crossplag-kotlin.git
4) Open project in IDEA 
5) Import gradle project (there should be a popup in the left corner)
6) Edit configurations
7) Add gradle
8) Choose crossplag-kotlin as project
9) In the task field either put 'run' or 'build' (for building and running and only building it respectively)