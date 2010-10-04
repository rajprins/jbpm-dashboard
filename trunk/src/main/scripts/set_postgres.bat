@Echo off
cls

echo Setting PostgreSQL configuration files...

copy /Y hibernate.cfg.postgres.xml ../resources/hibernate.cfg.xml
copy /Y jbpmDB.postgres.properties ../resources/jbpmDB.properties

echo Done.
pause