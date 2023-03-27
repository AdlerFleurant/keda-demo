podman build . -f processor/src/main/docker/Dockerfile.multistage -t processor
podman build . -f producer/src/main/docker/Dockerfile.multistage -t producer