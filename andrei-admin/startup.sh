APPNAME=dockerdemo
docker build -t andrei-admin .
docker run -d  --name andrei-admin -p 8080:8080 andrei-admin
