@Echo off
cls

echo Setting HypersonicDB configuration files...

copy /Y hibernate.cfg.hsqldb.xml ../resources/hibernate.cfg.xml
copy /Y jbpmDB.hsqldb.properties ../resources/jbpmDB.properties

echo Done.
pause