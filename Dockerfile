FROM bellsoft/liberica-openjdk-alpine:17.0.10

#Install curl and jq
RUN apk add curl jq

# Workspace
WORKDIR /home/selenium-grid

# Add the required files
ADD target/docker-resources ./
ADD runner.sh runner.sh

ENTRYPOINT sh runner.sh


# Environment variables
# BROWSER
# GRID_URL
# TEST_SUITE
# THREAD_COUNT
# DATA_PROVIDER_THREAD_COUNT

#ENTRYPOINT java -cp 'libs/*' \
#            -Dpackage=docker \
#            -Dtarget=REMOTE \
#            -DgridURL=${GRID_URL} \
#            -DtestSuite=${TEST_SUITE} \
#            -Dbrowser=${BROWSER} \
#            org.testng.TestNG \
#            -dataproviderthreadcount ${DATA_PROVIDER_THREAD_COUNT} \
#            -threadcount ${THREAD_COUNT} \
#            suites/${TEST_SUITE}