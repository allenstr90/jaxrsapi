FROM airhacks/glassfish
COPY ./target/JaxRsApi.war ${DEPLOYMENT_DIR}
