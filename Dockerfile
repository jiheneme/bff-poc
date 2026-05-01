FROM ubuntu:latest
LABEL authors="mac7"

ENTRYPOINT ["top", "-b"]