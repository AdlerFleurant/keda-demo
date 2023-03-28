podman build . -f src/main/container/Containerfile.multistage -t processor --build-arg PROJECT=processor
podman build . -f src/main/container/Containerfile.multistage -t producer --build-arg PROJECT=producer
podman build . -f src/main/container/Containerfile.multistage -t processor-cl --build-arg PROJECT=processor-cl
podman build . -f src/main/container/Containerfile.multistage -t producer-cl --build-arg PROJECT=producer-cl