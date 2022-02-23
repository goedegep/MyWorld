rem Batch file to launch the MyWorld application

echo off
set args=--module-path . --add-modules goedegep.myworld --add-exports=javafx.base/com.sun.javafx.event=org.controlsfx.controls goedegep.myworld.MyWorld

if "%1"=="" (
  java.exe %args%
) else (
  java.exe %args% -a %1
)
