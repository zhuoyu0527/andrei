APPNAME=dockerdemo
docker ps -a | grep andrei-admin | awk '{print $1 }'|xargs docker stop
docker ps -a | grep andrei-admin | awk '{print $1 }'|xargs docker rm
docker images|grep andrei-admin | awk '{print $3 }'|xargs docker rmi
