#!/usr/bin/env bash
set -euo pipefail
PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
LOG_FILE="$PROJECT_DIR/logs/Dinmukhamed_Ziyatbek_system_report.log"
OUTPUT_FILE="$PROJECT_DIR/logs/Dinmukhamed_Ziyatbek_log_analysis.txt"
if [[ ! -f "$LOG_FILE" ]]; then echo "Log not found: $LOG_FILE. Run the system report script first." >&2; exit 1; fi
{
  echo "Log analysis for Ziyatbek Dinmukhamed"
  echo "Generated: $(date '+%Y-%m-%d %H:%M:%S %Z')"
  echo
  echo "--- Last five lines of report ---"
  tail -n 5 "$LOG_FILE"
  echo
  echo "--- Lines matching surname/name token Dinmukhamed ---"
  grep -in 'Dinmukhamed' "$LOG_FILE" || true
} | tee "$OUTPUT_FILE"
echo "Analysis saved to $OUTPUT_FILE"
