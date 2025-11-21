java -jar -Dserver.port=8858 \
  -Dcsp.sentinel.dashboard.server=127.0.0.1:8858 \
  -Dproject.name=sentinel-dashboard \
  -Dsentinel.dashboard.auth.username=crabapples \
  -Dsentinel.dashboard.auth.password=crabapples \
  -Dauth.enabled=true \
  -Dcsp.sentinel.log.dir=./logs/csp \
  -Dnacos.config.server-addr=http://172.16.8.70:8848 \
  -Dnacos.config.namespace=namespace-sentinel \
  -Dnacos.config.groupId=DEFAULT_GROUP \
  -Dnacos.config.username= \
  -Dnacos.config.password= \
  ./sentinel-dashboard-1.8.9.jar
