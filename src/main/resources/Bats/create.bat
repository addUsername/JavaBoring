
@echo creando entorno virtual
call conda create -n app2 python=3.8 anaconda -y
@if not %ERRORLEVEL% == 0 (goto error_create)
call activate
@echo instalando librerias
call conda install numpy -y
call pip install opencv-python -y
call conda install matplotlib -y
@if not %ERRORLEVEL% == 0 (goto error_library)
exit

:error_create
@echo ERROR trying to create environment, do it by hand pls
exit

:error_library
@echo ERROR installing libraries, prob opencv..
exit
