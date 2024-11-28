source ./conf/config_base.sh
echo "当前cpu架构: $cpu"

exit
./create_cert.sh --cpu arm64 --os mac
./install_etcd.sh --cpu arm64
./install_api_server.sh --cpu arm64
./install_controller_manager.sh --cpu arm64

./create_cert.sh --cpu arm64 --os mac && ./install_etcd.sh --cpu arm64 && ./install_api_server.sh --cpu arm64

./create_cert.sh --cpu arm64 --os mac