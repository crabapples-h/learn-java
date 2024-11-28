#!/bin/bash
clear
source ./conf/config_base.sh
echo "当前cpu架构: $cpu,5秒后开始下载"
sleep 5
source ./conf/config_linux_$cpu.conf
export https_proxy=$proxy_url
export http_proxy=$proxy_url
export all_proxy=$proxy_url
function download_file() {
  # 判断文件是否存在.如果不存在则重新下载
  if [ ! -f "$1" ]; then
    curl -L $2 -o $1
    # 检查下载是否成功
    if [ $? -ne 0 ]; then
      echo "文件下载失败！"
      exit 1
    fi
  else
    echo "文件已存在，无需下载。"
  fi
}

# rm -rf ./tmp
# 判断目录是否存在,如若不存在就新建
if [ ! -d "./tmp" ]; then
  mkdir -p ./tmp
fi

echo "下载etcd:$etcd_download_url" 
download_file ./tmp/${etcd_file_name}.tar.gz ${etcd_download_url}
echo "etcd下载完成"

echo "下载Kubernetes:$kubernetes_download_url"
download_file ./tmp/${kubernetes_file_name}.tar.gz ${kubernetes_download_url}
echo "Kubernetes下载完成"


echo "下载cri-docker:$cri_docker_download_url"
download_file ./tmp/${cri_docker_file_name} ${cri_docker_download_url}
echo "cri-docker下载完成"


rm -rf ./tmp/etcd && mkdir -p ./tmp/etcd
rm -rf ./tmp/kubernetes-server && mkdir -p ./tmp/kubernetes-server
rm -rf ./tmp/kubernetes && mkdir -p ./tmp/kubernetes
rm -rf ./tmp/cri-docker && mkdir -p ./tmp/cri-docker


tar -zxvf ./tmp/${etcd_file_name}.tar.gz -C ./tmp/etcd --strip-components=1
tar -zxvf ./tmp/${kubernetes_file_name}.tar.gz -C ./tmp/kubernetes-server --strip-components=1
tar -zxvf ./tmp/${cri_docker_file_name} -C ./tmp/cri-docker --strip-components=1

cp ./tmp/kubernetes-server/server/bin/kube-apiserver ./tmp/kubernetes/kube-apiserver
cp ./tmp/kubernetes-server/server/bin/kube-proxy ./tmp/kubernetes/kube-proxy
cp ./tmp/kubernetes-server/server/bin/kube-controller-manager ./tmp/kubernetes/kube-controller-manager
cp ./tmp/kubernetes-server/server/bin/kube-scheduler ./tmp/kubernetes/kube-scheduler
cp ./tmp/kubernetes-server/server/bin/kubectl ./tmp/kubernetes/kubectl
cp ./tmp/kubernetes-server/server/bin/kubelet ./tmp/kubernetes/kubelet
echo "文件下载完成"
exit 0

