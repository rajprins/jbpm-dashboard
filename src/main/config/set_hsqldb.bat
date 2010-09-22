@Echo off
cls

echo Setting HypersonicDB configuration files...

copy /Y hibernate.cfg.hsqldb.xml hibernate.cfg.xml
copy /Y jbpmDB.hsqldb.properties jbpmDB.properties

echo Done.
pause