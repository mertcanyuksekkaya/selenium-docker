#!/bin/bash

# Let's print what we have received
echo "-------------------------------------------"
echo "GRID_URL      : ${GRID_URL:-hub}"
echo "BROWSER       : ${BROWSER:-chrome}"
echo "DATA_PROVIDER_THREAD_COUNT  : ${DATA_PROVIDER_THREAD_COUNT:-1}"
echo "THREAD_COUNT  : ${THREAD_COUNT:-1}"
echo "TEST_SUITE    : ${TEST_SUITE}"
echo "-------------------------------------------"

# Do not start the tests immediately. Hub has to be ready with browser nodes
echo "Checking if hub is ready..!"
count=0
while [ "$( curl -s http://${GRID_URL:-hub}:4444/status | jq -r .value.ready )" != "true" ]
do
  count=$((count+1))
  echo "Attempt: ${count}"
  if [ "$count" -ge 30 ]
  then
      echo "**** HUB IS NOT READY WITHIN 30 SECONDS ****"
      exit 1
  fi
  sleep 1
done

# At this point, selenium grid should be up!
echo "Selenium Grid is up and running. Running the test...."

# Start the java command
java -cp 'libs/*' \
            -Dpackage="docker" \
            -Dtarget="REMOTE" \
            -DgridURL="http://${GRID_URL:-hub}:4444" \
            -Dbrowser="${BROWSER:-chrome}" \
            org.testng.TestNG \
            -dataproviderthreadcount "${DATA_PROVIDER_THREAD_COUNT:-1}" \
            -threadcount "${THREAD_COUNT:-1}" \
            suites/"${TEST_SUITE}"