#!/bin/bash

# 로그 출력
echo "Starting deployment..."

# S3 버킷과 파일 경로 설정
#S3_BUCKET_NAME=${AWS_S3_BUCKET_NAME}        # S3 버킷 이름 (GitHub Secrets에서 가져옴)
S3_BUCKET_NAME="joonggo-bucket"
S3_FILE_KEY="Joonggonara.zip"              # S3에 저장된 ZIP 파일 경로
LOCAL_FILE_PATH="/home/ubuntu/Joonggonara.zip" # 다운로드된 ZIP 파일 저장 경로

# S3에서 ZIP 파일 다운로드
echo "Downloading ZIP file from S3..."
aws s3 cp s3://$S3_BUCKET_NAME/$S3_FILE_KEY $LOCAL_FILE_PATH

if [ ! -f "$LOCAL_FILE_PATH" ]; then
    echo "Error: Failed to download ZIP file from S3"
    exit 1
fi

# 배포를 위한 임시 디렉터리 생성
TEMP_DIR="/home/ubuntu/temp_deployment"
mkdir -p $TEMP_DIR

# 임시 디렉터리로 이동
cd $TEMP_DIR || exit

# ZIP 파일 해제
echo "Unzipping the downloaded file..."
unzip -o $LOCAL_FILE_PATH

# build 파일 확인 디버깅
echo "Checking contents of static folder:"
ls -al /home/ubuntu/Joonggonara/joongo/src/main/resources/static/


# Joonggonara 디렉터리 업데이트 (필요 시)
echo "Updating Joonggonara directory..."
sudo cp -r * /home/ubuntu/Joonggonara/joongo/

# 권한 설정
echo "Setting permissions for /var/www/react..."
sudo chown -R www-data:www-data /var/www/react
sudo chmod -R 755 /var/www/react

# 기존 React 빌드 파일 삭제
echo "Removing old React build files..."
sudo rm -rf /var/www/react/*

# React 빌드 파일 이동
echo "Deploying new React build files..."
sudo cp -r joongo/src/main/resources/static/* /var/www/react/

# React 빌드 파일 삭제
echo "Removing old React build files from static..."
sudo rm -rf /home/ubuntu/Joonggonara/joongo/src/main/resources/static/*

# 임시 디렉터리 삭제
echo "Cleaning up temporary files..."
cd /home/ubuntu
rm -rf $TEMP_DIR

# Nginx 재시작
echo "Restarting Nginx..."
sudo systemctl restart nginx

# 배포 완료
echo "Deployment completed!"
