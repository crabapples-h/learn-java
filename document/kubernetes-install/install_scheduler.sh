#!bin/bash
clear
source ./conf/config_base.sh
source ./conf/config_linux_$cpu.conf
TMP_CERT_DIR=./tmp/cert

echo "*****开始生成kubeconfig文件*****"
# 设置集群参数，集群名称为 kubernetes
KUBE_CONFIG_NAME=kube-scheduler.kubeconfig
KUBE_CONFIG_DIR=./tmp/${KUBE_CONFIG_NAME}

${kubectl_dir}/kubectl config set-cluster kubernetes \
  --certificate-authority=${TMP_CERT_DIR}/ca.pem \
  --embed-certs=true \
  --server=${kube_apiserver} \
  --kubeconfig=${KUBE_CONFIG_DIR}

# 设置客户端认证参数 用户名为 mshe-kube-scheduler(可自定义)
${kubectl_dir}/kubectl config set-credentials mshe-kube-scheduler \
  --client-certificate=${TMP_CERT_DIR}/kube-scheduler.pem \
  --client-key=${TMP_CERT_DIR}/kube-scheduler-key.pem \
  --embed-certs=true \
  --kubeconfig=${KUBE_CONFIG_DIR}

# 设置上下文 名称为 mshe:context(可自定义)
# 用户名为 mshe-kube-scheduler ，需要和客户端认证的用户名保持一致
${kubectl_dir}/kubectl config set-context mshe:context \
  --cluster=kubernetes \
  --user=mshe-kube-scheduler \
  --kubeconfig=${KUBE_CONFIG_DIR}

# 设置使用的上下文为mshe:context(可以自定义)和上面的保持一致
${kubectl_dir}/kubectl config use-context mshe:context \
  --kubeconfig=${KUBE_CONFIG_DIR}
echo "kubeconfig文件生成完成"


# 生成service文件
SERVICE_FILE_NAME=kube-scheduler.service
echo "*****开始生成${SERVICE_FILE_NAME}文件*****"
cat > ./tmp/${SERVICE_FILE_NAME} <<EOF
[Unit]
Description=Kubernetes Scheduler
Documentation=https://github.com/kubernetes/kubernetes
[Service]
ExecStart=${kubernetes_workspace_dir}/kube-scheduler \
  --kubeconfig=/opt/kubernetes/${KUBE_CONFIG_NAME} \
  --leader-elect=true \
  --v=4
Restart=on-failure
RestartSec=5
[Install]
WantedBy=multi-user.target
EOF
echo "*****生成${SERVICE_FILE_NAME}文件完成*****"

# 字符串替换测试
# sed -i 's/%NODE_NAME%/测试/g' /opt/config/etcd.cfg
echo "*****开始复制数据到scheduler节点*****"
for IP_ADDRESS in "${k8s_master_ips[@]}";
do
    # 远程拷贝文件
    echo "当前节点为：${IP_ADDRESS}"
    REMOTE_IP=$(ssh root@${IP_ADDRESS} "hostname -I | awk '{print \$1}' | cut -d '%' -f1")
    ssh root@${IP_ADDRESS} "systemctl stop ${SERVICE_FILE_NAME}"
    # 远程替换模板里的配置
    # ssh root@${IP_ADDRESS} "rm -rf ${kubernetes_workspace_dir}"
    ssh root@${IP_ADDRESS} "mkdir -p ${kubernetes_workspace_dir}"
    scp ./tmp/kubernetes/kube-scheduler root@${IP_ADDRESS}:${kubernetes_workspace_dir}/
    scp ./tmp/${KUBE_CONFIG_NAME}  root@${IP_ADDRESS}:${kubernetes_workspace_dir}/${KUBE_CONFIG_NAME} 
    scp ./tmp/${SERVICE_FILE_NAME} root@${IP_ADDRESS}:/etc/systemd/system/${SERVICE_FILE_NAME}
    ssh root@${IP_ADDRESS} "sed -i 's/%LOCAL_IP%/${REMOTE_IP}/g' /etc/systemd/system/${SERVICE_FILE_NAME}"
    ssh root@${IP_ADDRESS} "systemctl daemon-reload"
    ssh root@${IP_ADDRESS} "systemctl enable ${SERVICE_FILE_NAME}"
    # sleep 5
    ssh root@${IP_ADDRESS} "systemctl restart ${SERVICE_FILE_NAME}"
    echo "节点：${IP_ADDRESS}复制数据完成"
done

echo "*****复制数据到所有scheduler节点完成*****"

echo "scheduler部署完成，请到每个节点上执行"
echo
echo "systemctl restart ${SERVICE_FILE_NAME}"
echo
echo "systemctl status ${SERVICE_FILE_NAME}"
echo
echo "${kubectl_dir}/kubectl get nodes"
echo
${kubectl_dir}/kubectl get nodes