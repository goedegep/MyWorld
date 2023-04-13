rem Batch file to launch the MyWorld application
rem If this batch file is started by clicking on a file (Window 'file open' command) the working directory is the directory of that file.
rem So change the directory to the location of this batch file.
chdir /d %~dp0

start /min java.exe --module-path . -splash:MyWorldSplash.jpg  -m goedegep.myworld/goedegep.myworld.MyWorld %*
