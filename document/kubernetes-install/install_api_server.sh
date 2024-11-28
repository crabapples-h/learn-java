#!/bin/bash
clear
source ./conf/config_base.sh
source ./conf/config_linux_$cpu.conf
# 生成service文件
SERVICE_FILE_NAME=kube-api-server.service
echo "*****开始生成${SERVICE_FILE_NAME}文件*****"
cat > ./tmp/${SERVICE_FILE_NAME} <<EOF
[Unit]
Description=Kubernetes API Server
Documentation=https://github.com/kubernetes/kubernetes

[Service]
ExecStart=${kubernetes_workspace_dir}/kube-apiserver \
  --enable-admission-plugins=NamespaceLifecycle,NodeRestriction,LimitRanger,ServiceAccount,DefaultStorageClass,ResourceQuota \
  --anonymous-auth=false \
  --bind-address=%LOCAL_IP% \
  --advertise-address=%LOCAL_IP% \
  --secure-port=6443 \
  --authorization-mode=Node,RBAC \
  --runtime-config=api/all=true \
  --enable-bootstrap-token-auth \
  --service-cluster-ip-range=${service_cluster_ip_range} \
  --token-auth-file=${cert_dir}/token.csv \
  --service-node-port-range=${service_node_port_range} \
  --tls-cert-file=${cert_dir}/kube-apiserver.pem \
  --tls-private-key-file=${cert_dir}/kube-apiserver-key.pem \
  --client-ca-file=${cert_dir}/ca.pem \
  --kubelet-client-certificate=${cert_dir}/kube-apiserver.pem \
  --kubelet-client-key=${cert_dir}/kube-apiserver-key.pem \
  --service-account-key-file=${cert_dir}/ca-key.pem \
  --service-account-signing-key-file=${cert_dir}/ca-key.pem \
  --service-account-issuer=api \
  --etcd-cafile=${cert_dir}/ca.pem \
  --etcd-certfile=${cert_dir}/etcd.pem \
  --etcd-keyfile=${cert_dir}/etcd-key.pem \
  --etcd-servers=${etcd_node_ips} \
  --allow-privileged=true \
  --apiserver-count=3 \
  --audit-log-maxage=30 \
  --audit-log-maxbackup=3 \
  --audit-log-maxsize=100 \
  --audit-log-path=/var/log/kube-apiserver-audit.log \
  --event-ttl=1h \
  --v=4

[Install]
WantedBy=multi-user.target
EOF
echo "*****生成${SERVICE_FILE_NAME}文件完成*****"

# 拷贝api-server证书
for IP_ADDRESS in "${k8s_master_ips[@]}" ; 
do
  ssh root@${IP_ADDRESS} "rm -rf ${cert_dir}"
  ssh root@${IP_ADDRESS} "mkdir -p ${cert_dir}"
  scp ${tmp_cert_dir}/ca* root@${IP_ADDRESS}:${cert_dir}/
  scp ${tmp_cert_dir}/etcd* root@${IP_ADDRESS}:${cert_dir}/
  scp ${tmp_cert_dir}/kube-apiserver* root@${IP_ADDRESS}:${cert_dir}/
  scp ${tmp_cert_dir}/token.csv root@${IP_ADDRESS}:${cert_dir}/
done

# 字符串替换测试
# sed -i 's/%NODE_NAME%/测试/g' /opt/config/etcd.cfg
echo "*****开始复制数据到api-server节点*****"
for IP_ADDRESS in "${k8s_master_ips[@]}";
do
  # IP_ADDRESS=${etcd_ips[${index}]}
    # 远程拷贝文件
    # IP_ADDRESS=${api_server_ip_prefix}${i}
    echo "当前节点为：${IP_ADDRESS}"
    REMOTE_IP=$(ssh root@${IP_ADDRESS} "hostname -I | awk '{print \$1}' | cut -d '%' -f1")
    ssh root@${IP_ADDRESS} "systemctl stop ${SERVICE_FILE_NAME}"
    # 替换模板里的配置
    # ssh root@${IP_ADDRESS} "rm -rf ${kubernetes_workspace_dir}"
    ssh root@${IP_ADDRESS} "mkdir -p ${kubernetes_workspace_dir}"
    scp ./tmp/kubernetes/kube-apiserver root@${IP_ADDRESS}:${kubernetes_workspace_dir}/
    scp ./tmp/${SERVICE_FILE_NAME} root@${IP_ADDRESS}:/etc/systemd/system/${SERVICE_FILE_NAME}
    # rsync -ah --progress ./tmp/${SERVICE_FILE_NAME} root@${IP_ADDRESS}:/etc/systemd/system/${SERVICE_FILE_NAME}
    ssh root@${IP_ADDRESS} "sed -i 's/%LOCAL_IP%/${REMOTE_IP}/g' /etc/systemd/system/${SERVICE_FILE_NAME}"
    ssh root@${IP_ADDRESS} "systemctl daemon-reload"
    ssh root@${IP_ADDRESS} "systemctl enable ${SERVICE_FILE_NAME}"
    sleep 5
    ssh root@${IP_ADDRESS} "systemctl restart ${SERVICE_FILE_NAME}"
    echo "节点：${IP_ADDRESS}复制数据完成"
done
echo "*****复制数据到所有api-server节点完成*****"

echo "api-server部署完成，请到每个节点上执行"
echo
echo "systemctl restart ${SERVICE_FILE_NAME}"
echo
echo "systemctl status ${SERVICE_FILE_NAME}"
echo
echo "curl --insecure https://172.16.27.191:6443"
echo