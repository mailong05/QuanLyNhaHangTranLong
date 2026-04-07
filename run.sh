#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

if [[ -x "./mvnw" ]]; then
  MAVEN_CMD="./mvnw"
elif command -v mvn >/dev/null 2>&1; then
  MAVEN_CMD="mvn"
else
  echo "Khong tim thay Maven. Hay cai dat Maven hoac them Maven Wrapper (mvnw)." >&2
  exit 1
fi

echo "Dang khoi dong QuanLyDatBanNhaHang..."
"$MAVEN_CMD" compile exec:java
