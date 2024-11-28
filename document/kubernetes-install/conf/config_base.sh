cpu=amd64
os=linux
while [[ $# -gt 0 ]]; do
  key="$1"
  case $key in
    -h|--help)
      cpu="$2"
      echo "Usage:  [options]"
      echo "  --cpu:  arm64 | amd64"
      exit 0
      ;;
    --cpu)
      cpu="$2"
      shift # Shift past argument
      shift # Shift past value
      ;;
    --os)
      os="$2"
      shift # Shift past argument
      shift # Shift past value
      ;;
    -c|--option-c)
      opt_c=1
      shift # Shift past argument
      ;;
    *)
      echo "Unknown option $key"
      echo "无效的参数 $key"
      break
      ;;
  esac
done
