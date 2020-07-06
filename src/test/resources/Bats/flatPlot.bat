@if not %ERRORLEVEL% == 0 (goto error_conda)

@set path=%1
@set champions=%2
@set title=%3
@echo Running python





@if not %ERRORLEVEL% == 0 (goto error_python)
exit

:error_conda
@echo ERROR with conda, try reinstalling, hope it helps..
exit

:error_python
@echo PYTHON COMMAND FAILED succesfully, lel..
exit
