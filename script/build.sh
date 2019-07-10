#!/bin/sh
set -e
RELEASE_HOST="你自己的服务器地址"
RELEASE_ENV=prod
BASE_DIR=/root/repo_git/Histraw
cd ${BASE_DIR}
echo "pulling changes..."
git pull origin master
echo "pulling changes... finish"
echo "building..."
mvn clean
mvn package -P${RELEASE_ENV} -DskipTests docker:build
echo "building...finish"
echo "env =${RELEASE_ENV}"
#for HOST in ${RELEASE_HOST}; do
RELEASE_TARGET=root@${RELEASE_HOST}:~/release/
echo "copying to $RELEASE_TARGET..."
scp ${BASE_DIR}/target/*.jar ${RELEASE_TARGET}
echo "copying to $RELEASE_TARGET...done"
#done

