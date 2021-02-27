#!/bin/bash
echo "stopping springboot application...."
pid=`ps -ef | grep springboot.jar | grep -v grep | awk '{print $2}'`
if ["$pid" == ""]
    then
        echo "springboot is already stop!"
else
    echo "kill -9 的pid:" + $pid
    kill -9 $pid
fi

echo "remove old springboot.jar ..."
# 这里如果你配置的ssh传输节点不在同一目录下，你需要将当然需要部署的旧版本jar替换成新jar...
# 由于我配置的ssh传输目录在当前目录下，所以当前jar包就是新jar包，不需要进行删除替换。
# rm /home/jenkins/HelloWorld/helloboot-0.0.1-SNAPSHOT.jar
# cp /root/.jenkins/jobs/HelloWorld/workspace/target/helloboot-0.0.1-SNAPSHOT.jar /home/jenkins/HelloWorld
sleep 2s
echo "running springboot.jar...."
java -jar springboot.jar &
echo "running springboot finish"
