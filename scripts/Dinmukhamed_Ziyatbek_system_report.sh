#!/usr/bin/env bash
set -u

STUDENT_NAME="Ziyatbek Dinmukhamed"
PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
LOG_FILE="$PROJECT_DIR/logs/Dinmukhamed_Ziyatbek_system_report.log"

{
  echo "===== SYSTEM REPORT ====="
  echo "Student: $STUDENT_NAME"
  echo "Date/time: $(date '+%Y-%m-%d %H:%M:%S %Z')"
  echo "Current user: $(id -un)"
  echo "Hostname: $(hostname)"
  echo "Operating system: $(. /etc/os-release 2>/dev/null; echo "${PRETTY_NAME:-Unknown Linux}")"
  echo "Kernel: $(uname -srmo)"
  echo
  echo "===== CPU ====="
  if command -v lscpu >/dev/null 2>&1; then lscpu | grep -E '^(Architecture|CPU\(s\)|Model name|Thread\(s\) per core|Core\(s\) per socket|Socket\(s\)):' || true; else echo "lscpu is not installed"; fi
  echo
  echo "===== MEMORY ====="
  free -h 2>&1 || true
  echo
  echo "===== DISK USAGE ====="
  df -hT 2>&1 || true
  echo
  echo "===== AVAILABLE DISK SPACE ====="
  df -h --output=source,avail,pcent,target 2>&1 || df -h 2>&1 || true
  echo "===== END REPORT ====="
  echo
} | tee -a "$LOG_FILE"
