
@if not %ERRORLEVEL% == 0 (goto error_activate)
@set path_txt=%1
@echo creando entorno virtual
call conda create --name myenv --file %path_txt%
@if not %ERRORLEVEL% == 0 (goto error_create)
@echo entorno creado
exit

:error_activate
@echo ERROR trying to activate environment, add anaconda3 to PATH
pause
exit

:error_create
@echo ERROR trying to create environment, do it by hand pls
pause
exit
