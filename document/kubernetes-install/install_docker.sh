#!/bin/bash
clear
source ./conf/config_base.sh
source ./conf/config_linux_$cpu.conf
# 生成daemon.json文件
DOCKER_DAEMON=daemon.json
echo "*****开始生成${DOCKER_DAEMON}文件*****"
cat > ./tmp/${DOCKER_DAEMON} << EOF
{
  "exec-opts": ["native.cgroupdriver=systemd"],
  "registry-mirrors": ["https://3086t6m8.mirror.aliyuncs.com"],
  "insecure-registries": ["harbor.crabapples.cn"]
}
EOF
echo "*****生成${DOCKER_DAEMON}文件完成*****"

# 字符串替换测试
echo "*****开始在worker节点安装docker*****"
APT_SOURCE_FILE=/etc/apt/sources.list
APT_SOURCE_SOURCE_CONTENT=archive.ubuntu.com
APT_SOURCE_TARGET_CONTENT=mirrors.aliyun.com
# sed -i 's/${APT_SOURCE_SOURCE_CONTENT}/${APT_SOURCE_TARGET_CONTENT}/g' ${APT_SOURCE_FILE}
# echo sed -i 's/${APT_SOURCE_SOURCE_CONTENT}/${APT_SOURCE_TARGET_CONTENT}/g' ${APT_SOURCE_FILE}

for IP_ADDRESS in "${k8s_worker_ips[@]}";
do
    # 远程拷贝文件
    REMOTE_IP=$(ssh root@${IP_ADDRESS} "hostname -I | awk '{print \$1}' | cut -d '%' -f1")
    echo "当前节点为：${IP_ADDRESS},远程主机IP为：${REMOTE_IP}"

    # ssh root@${IP_ADDRESS} "sed -i 's/${APT_SOURCE_SOURCE_CONTENT}/${APT_SOURCE_TARGET_CONTENT}/g' ${APT_SOURCE_FILE}"
    ssh root@${IP_ADDRESS} "sudo apt-get update"
    ssh root@${IP_ADDRESS} "sudo apt-get -y install apt-transport-https"
    ssh root@${IP_ADDRESS} "sudo apt-get -y install ca-certificates"
    ssh root@${IP_ADDRESS} "sudo apt-get -y install curl"
    ssh root@${IP_ADDRESS} "sudo apt-get -y install software-properties-common"
    ssh root@${IP_ADDRESS} "curl -fsSL https://mirrors.aliyun.com/docker-ce/linux/ubuntu/gpg | sudo apt-key add -"
    ssh root@${IP_ADDRESS} 'sudo add-apt-repository "deb [arch=amd64] https://mirrors.aliyun.com/docker-ce/linux/ubuntu $(lsb_release -cs) stable"'
    ssh root@${IP_ADDRESS} "sudo apt-get update"
    ssh root@${IP_ADDRESS} "sudo apt-get -y install docker-ce"
    ssh root@${IP_ADDRESS} "mkdir -p /etc/docker/"
    scp  ./tmp/${DOCKER_DAEMON} root@${IP_ADDRESS}:/etc/docker/${DOCKER_DAEMON}
    ssh root@${IP_ADDRESS} "systemctl daemon-reload"
    ssh root@${IP_ADDRESS} "systemctl restart docker"
    scp  ./tmp/cri-dockerd root@${IP_ADDRESS}:/opt/cri-dockerd
    ssh root@${IP_ADDRESS} "chmod +x /opt/cri-dockerd/cri-dockerd"
    ssh root@${IP_ADDRESS} "systemctl daemon-reload"
    ssh root@${IP_ADDRESS} "systemctl enable cri-docker.service"

    echo "节点：${IP_ADDRESS}安装docker完成"
done
echo "*****所有worker节点安装docker完成*****"