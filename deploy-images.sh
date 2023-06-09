REGISTRY=$(oc get route default-route -n openshift-image-registry --template='{{ .spec.host }}')
TOKEN=$(oc whoami -t)
ADMIN=$(oc whoami)

podman tag processor $REGISTRY/keda-demo/processor
podman tag producer $REGISTRY/keda-demo/producer
podman tag processor-cl $REGISTRY/keda-demo/processor-cl
podman tag producer-cl $REGISTRY/keda-demo/producer-cl
podman login -u $ADMIN https://$REGISTRY -p $TOKEN
podman push $REGISTRY/keda-demo/processor
podman push $REGISTRY/keda-demo/producer
podman push $REGISTRY/keda-demo/processor-cl
podman push $REGISTRY/keda-demo/producer-cl