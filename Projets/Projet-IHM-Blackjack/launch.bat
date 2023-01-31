@echo off
title Launch
call setenv-cmd.bat
SET /p nom=Nom du programme 
call java -jar %nom%.jar


