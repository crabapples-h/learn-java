  taskkill /F /PID (netstat -ano | Select-String ":19093.*LISTENING").Line.Split()[-1]
