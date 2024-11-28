#!/bin/bash
clear
source ./conf/config_base.sh
source ./conf/config_linux_$cpu.conf

chmod +x ${kubectl_dir}/kubectl

rm -rf ${HOME}/.kube && mkdir -p ${HOME}/.kube

KUBE_CONFIG=${HOME}/.kube/config
# 设置集群参数，集群名称为 kubernetes
${kubectl_dir}/kubectl config set-cluster kubernetes \
  --certificate-authority=${tmp_cert_dir}/ca.pem \
  --embed-certs=true \
  --server=${kube_apiserver} \
  --kubeconfig=${KUBE_CONFIG}

# 设置客户端认证参数 用户名为 mshe 和证书中的cn一致
${kubectl_dir}/kubectl config set-credentials mshe \
  --client-certificate=${tmp_cert_dir}/kubectl.pem \
  --client-key=${tmp_cert_dir}/kubectl-key.pem \
  --embed-certs=true \
  --kubeconfig=${KUBE_CONFIG}

# 设置上下文 名称为 mshe:context(可自定义)
# 用户名为 mshe，需要和客户端认证的用户名保持一致
${kubectl_dir}/kubectl config set-context mshe-context \
  --cluster=kubernetes \
  --user=mshe \
  --kubeconfig=${KUBE_CONFIG}

# 设置使用的上下文为mshe:context(可以自定义)和上面的保持一致
${kubectl_dir}/kubectl config use-context mshe-context \
  --kubeconfig=${KUBE_CONFIG}

  
echo "*****kubectl配置完成*****"
echo
echo "${kubectl_dir}/kubectl get nodes"
echo
# 执行任意命令验证是否可以正常使用
${kubectl_dir}/kubectl get nodes

# ls ${HOME}/.kube
# cat ${HOME}/.kube/config