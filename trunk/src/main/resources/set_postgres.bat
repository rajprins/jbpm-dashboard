@Echo off
cls

echo Setting PostgreSQL configuration files...

copy /Y hibernate.cfg.postgres.xml hibernate.cfg.xml
copy /Y jbpmDB.postgres.properties jbpmDB.properties

echo Done.
pause