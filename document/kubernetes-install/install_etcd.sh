#!/bin/bash
clear
source ./conf/config_base.sh
source ./conf/config_linux_$cpu.conf
# 集群IP列表
CLUSTER_LIST=""
for index in "${!etcd_install_ips[@]}";
do
  IP_ADDRESS=${etcd_install_ips[${index}]}
  CLUSTER_LIST="etcd-0$(($index+1))=https://${IP_ADDRESS}:2380,$CLUSTER_LIST"
done
CLUSTER_LIST="${CLUSTER_LIST%?}"
# 生成service文件
SERVICE_FILE_NAME=etcd.service
echo "*****开始生成${SERVICE_FILE_NAME}文件*****"
cat > ./tmp/${SERVICE_FILE_NAME} << EOF
[Unit]
Description=Etcd Server
After=network.target
After=network-online.target
Wants=network-online.target

[Service]
Type=notify
ExecStart=${etcd_workspace_dir}/etcd \
  --name="%NODE_NAME%" \
  --data-dir="${etcd_data_dir}" \
  --listen-client-urls="https://%LOCAL_IP%:2379" \
  --advertise-client-urls="https://%LOCAL_IP%:2379" \
  --listen-peer-urls="https://%LOCAL_IP%:2380" \
  --initial-advertise-peer-urls="https://%LOCAL_IP%:2380" \
  --initial-cluster="$CLUSTER_LIST" \
  --initial-cluster-token="etcd-cluster" \
  --initial-cluster-state=new \
  --cert-file=${cert_dir}/etcd.pem \
  --key-file=${cert_dir}/etcd-key.pem \
  --peer-cert-file=${cert_dir}/etcd.pem \
  --peer-key-file=${cert_dir}/etcd-key.pem \
  --peer-trusted-ca-file=${cert_dir}/ca.pem \
  --trusted-ca-file=${cert_dir}/ca.pem \
  --peer-client-cert-auth=true 

#Restart=on-failure
LimitNOFILE=65536
[Install]
WantedBy=multi-user.target

# systemctl daemon-reload
# systemctl enable ${SERVICE_FILE_NAME}
# systemctl restart ${SERVICE_FILE_NAME}
EOF
echo "*****生成${SERVICE_FILE_NAME}文件完成*****"

# 拷贝ETCD证书
echo "*****开始复制证书到etcd节点*****"
for IP_ADDRESS in "${etcd_install_ips[@]}"; 
do
  echo "当前节点为:${IP_ADDRESS}"
  ssh root@${IP_ADDRESS} "rm -rf ${cert_dir} && mkdir -p ${cert_dir}"
  scp ${tmp_cert_dir}/ca* root@${IP_ADDRESS}:${cert_dir}/
  scp ${tmp_cert_dir}/etcd* root@${IP_ADDRESS}:${cert_dir}/
done
echo "*****复制证书到etcd节点完成*****"

# 字符串替换测试
# sed -i 's/%NODE_NAME%/测试/g' /opt/config/etcd.cfg
echo "*****开始复制数据到etcd节点*****"
for index in "${!etcd_install_ips[@]}";
do
    IP_ADDRESS=${etcd_install_ips[${index}]}
    # 远程拷贝文件
    echo "当前节点为：${IP_ADDRESS}"
    REMOTE_IP=$(ssh root@${IP_ADDRESS} "hostname -I | awk '{print \$1}' | cut -d '%' -f1")
    ssh root@${IP_ADDRESS} "systemctl stop ${SERVICE_FILE_NAME}"
    ssh root@${IP_ADDRESS} "rm -rf ${etcd_workspace_dir}"
    scp -r ./tmp/etcd root@${IP_ADDRESS}:${etcd_workspace_dir}
    scp  ./tmp/${SERVICE_FILE_NAME} root@${IP_ADDRESS}:/etc/systemd/system/${SERVICE_FILE_NAME}
    ssh root@${IP_ADDRESS} "sed -i 's/%NODE_NAME%/etcd-0$(($index+1))/g' /etc/systemd/system/${SERVICE_FILE_NAME}"
    ssh root@${IP_ADDRESS} "sed -i 's/%LOCAL_IP%/${REMOTE_IP}/g' /etc/systemd/system/${SERVICE_FILE_NAME}"
    echo "文件复制到节点：${IP_ADDRESS}完成"
    echo "节点：${IP_ADDRESS}复制数据完成"
done
echo "*****复制数据到所有etcd节点完成*****"



echo "文件复制完成,正在自动启动,如启动失败，请到每个节点上执行"
echo
echo "systemctl restart ${SERVICE_FILE_NAME} && systemctl status ${SERVICE_FILE_NAME}"
echo

CLUSTER_LIST=""
for index in "${!etcd_install_ips[@]}";
do
  IP_ADDRESS=${etcd_install_ips[${index}]}
  CLUSTER_LIST="https://${IP_ADDRESS}:2379,$CLUSTER_LIST"
done
CLUSTER_LIST="${CLUSTER_LIST%?}"
echo "/opt/etcd/etcdctl \
  --cert=${cert_dir}/etcd.pem \
  --cacert=${cert_dir}/ca.pem \
  --key=${cert_dir}/etcd-key.pem \
  -w=table --endpoints=$CLUSTER_LIST \
  endpoint status"
echo
echo "${etcd_workspace_dir}/etcdctl endpoint health"
echo
# for index in "${!etcd_install_ips[@]}";
# do
#     IP_ADDRESS=${etcd_install_ips[${index}]}
#     ssh root@${IP_ADDRESS} "systemctl daemon-reload"
#     ssh root@${IP_ADDRESS} "systemctl start ${SERVICE_FILE_NAME}"
#     ssh root@${IP_ADDRESS} "systemctl enable ${SERVICE_FILE_NAME}"
# done




  {
                "id": "1830789869849280512",
                "alarmCode": "YJ202409030292",
                "deviceid": "FF7670271",
                "alarmTime": "2024-09-03 10:08:09",
                "provinceid": "520000",
                "storeid": "1810196416619974658",
                "alarmType": "1810268480519335938",
                "alarmPhotoPath": "/s3obj/smartstore/1830789835055480834.jpg?xkt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MjU0MjE1MDAsInVzZXJuYW1lIjoiYWRtaW4ifQ.9NIDC_hYfQParnLaVZqYdRw_pZwRoXncQQbeuYOhxfo",
                "alarmDes": "未戴口罩",
                "appeal": "0",
                "appealState": "1",
                "appealResult": null,
                "alarmYear": "2024",
                "alarmMonth": "09",
                "alarmDay": "03",
                "createdBy": null,
                "createdTime": "2024-09-03",
                "updatedBy": null,
                "updatedTime": null,
                "evidence": "1",
                "appealInstId": "6090f4ee-6999-11ef-8dec-0242ac11000d",
                "alarmReceiveUser": "chengjie-test",
                "alarmReceiveUserId": null,
                "alarmVerifyRemark": null,
                "alarmSourceTable": null,
                "alarmSourceTableId": null,
                "readKnow": 0,
                "handleMode": 1,
                "provinceName": "贵州省区",
                "storeName": "丝恋甲秀楼店",
                "isAppeal": null,
                "appealStateName": null,
                "appealResultName": null,
                "alarmTypeDesc": "未戴口罩",
                "isRedirect": true,
                "relatedEmployee": null,
                "appeal_dictText": "否",
                "appealState_dictText": "未申诉",
                "evidence_dictText": "照片"
            },de