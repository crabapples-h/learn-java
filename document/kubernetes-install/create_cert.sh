#/bin/bash
clear
source ./conf/config_base.sh
echo "当前cpu架构: $cpu"
echo "当前操作系统: $os"
# 如果cpu是arm
if [ "mac" == "$os" ]; then
  source ./conf/config_mac.conf
else 
  source ./conf/config_linux_$cpu.conf
fi
function printLog() {
  echo "********************$1********************"
}

function create(){
  printLog "开始生成[$1]证书"
  ${cfssl_workspace_dir}/cfssl gencert --loglevel=${log_level} \
    --ca=${tmp_cert_dir}/ca.pem \
    --ca-key=${tmp_cert_dir}/ca-key.pem \
    --config=${cfssl_config_dir}/config.json \
    --profile=$1 \
    ${cfssl_config_dir}/$1-csr.json | \
    ${cfssl_workspace_dir}/cfssljson -bare ${tmp_cert_dir}/$1 -
  rm ${tmp_cert_dir}/$1.csr
  echo ${tmp_cert_dir}/$1.pem
  echo ${tmp_cert_dir}/$1-key.pem
  printLog "生成[$1]证书完成"
  echo
}

# rm -rf ${tmp_cert_dir} && mkdir -p ${tmp_cert_dir}
echo "证书路径:${cert_dir}"



printLog "开始生成CA证书"
${cfssl_workspace_dir}/cfssl gencert --loglevel=${log_level} \
  --initca ${cfssl_config_dir}/ca-csr.json | \
  ${cfssl_workspace_dir}/cfssljson -bare ${tmp_cert_dir}/ca -
rm ${tmp_cert_dir}/ca.csr
printLog "生成CA证书完成"
echo

create etcd
create kube-apiserver
# create kubectl
# create kube-controller-manager
# create kube-scheduler
# create kube-proxy

printLog "Bootstrapping token"
# 创建 TLS Bootstrapping Token 
export BOOTSTRAP_TOKEN=$(head -c 16 /dev/urandom | od -An -t x | tr -d ' ')
cat > ${tmp_cert_dir}/token.csv << EOF
${BOOTSTRAP_TOKEN},kubelet-bootstrap,10001,"system:kubelet-bootstrap"
EOF
echo $BOOTSTRAP_TOKEN
printLog "生成token.csv文件完成"

# 生成token.csv文件的内容为 
# 50d3cb9c149d944b125fdcc9cae8ac19,kubelet-bootstrap,10001,"system:kubelet-bootstrap"
# 其格式为：token，用户名，xxx，角色分组

ls -lah $tmp_cert_dir

# 拷贝ETCD证书
# for IP_ADDRESS in "${etcd_install_ips[@]}"; 
# do
#   echo "当前节点为:${IP_ADDRESS}"
#   ssh root@${IP_ADDRESS} "rm -rf ${cert_dir}"
#   ssh root@${IP_ADDRESS} "mkdir -p ${cert_dir}"
#   scp ${tmp_cert_dir}/ca* root@${IP_ADDRESS}:${cert_dir}/
#   scp ${tmp_cert_dir}/etcd* root@${IP_ADDRESS}:${cert_dir}/
# done

# 拷贝api-server证书
# for IP_ADDRESS in "${k8s_master_ips[@]}" ; 
# do
#   ssh root@${IP_ADDRESS} "rm -rf ${cert_dir}"
#   ssh root@${IP_ADDRESS} "mkdir -p ${cert_dir}"
#   scp ${tmp_cert_dir}/ca* root@${IP_ADDRESS}:${cert_dir}/
#   scp ${tmp_cert_dir}/etcd* root@${IP_ADDRESS}:${cert_dir}/
#   scp ${tmp_cert_dir}/kube-apiserver* root@${IP_ADDRESS}:${cert_dir}/
#   scp ${tmp_cert_dir}/token.csv root@${IP_ADDRESS}:${cert_dir}/
# done

# # 拷贝controller-manager证书
# for IP_ADDRESS in "${k8s_master_ips[@]}" ; 
# do
#   scp ${tmp_cert_dir}/kube-controller* root@${IP_ADDRESS}:${cert_dir}/
# done

# # 拷贝scheduler证书
# for IP_ADDRESS in "${k8s_master_ips[@]}" ; 
# do
#   scp ${tmp_cert_dir}/kube-scheduler* root@${IP_ADDRESS}:${cert_dir}/
# done

# # 拷贝proxy证书
# for IP_ADDRESS in "${k8s_master_ips[@]}" ; 
# do
#   scp ${tmp_cert_dir}/kube-proxy* root@${IP_ADDRESS}:${cert_dir}/
# done
