@if not %ERRORLEVEL% == 0 (goto error_path)
@echo Activando entorno virtual en conda
call activate 
@if not %ERRORLEVEL% == 0 (goto error_conda)

@set rootpath=%1
@set videopath=%2
@set title=%3
@echo Running python





@if not %ERRORLEVEL% == 0 (goto error_python)
exit

:error_path
@echo ERROR trying to add Anaconda3 towinPATH, do it by hand pls
exit

:error_conda
@echo ERROR with conda, try reinstalling, hope it helps..
exit

:error_python
@echo PYTHON COMMAND FAILED succesfully, lel..
exit
