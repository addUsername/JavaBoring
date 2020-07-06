
@if not %ERRORLEVEL% == 0 (goto error_conda)

@set path=%1
@set champions=%2
@set threshold=%3
@set second_inicial=%4
@set frame_step=%5
@set frame_stop=%6
@set json_path=%7

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
