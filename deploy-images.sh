REGISTRY=
TOKEN=
ADMIN=

podman tag processor $REGISTRY/keda-demo/processor
podman tag producer $REGISTRY/keda-demo/producer
podman login -u $ADMIN https://$REGISTRY -p $TOKEN
podman push $REGISTRY/keda-demo/processor
podman push $REGISTRY/keda-demo/producer