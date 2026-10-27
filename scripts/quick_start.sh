#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "${BASH_SOURCE[0]}")/.."
command -v docker >/dev/null || { echo 'Docker is required. Install Docker Engine first.' >&2; exit 1; }
docker compose version >/dev/null
docker compose up --build -d
docker compose ps
echo 'API URL: http://localhost:8080/api/students'
