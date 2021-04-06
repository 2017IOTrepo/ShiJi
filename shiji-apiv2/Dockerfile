ARG GO_VERSION=1.14

FROM golang:${GO_VERSION}-alpine AS builder

RUN apk update && apk add alpine-sdk git && rm -rf /var/cache/apk/*

RUN mkdir -p /api
WORKDIR /api

ENV GOROOT=/usr/local/go

COPY go.mod .
COPY go.sum .
RUN go env -w GOPROXY=https://goproxy.cn,direct
RUN go mod download

COPY . /api
RUN go build -o api -gcflags='-N -l' -ldflags='-w -s' ./src


EXPOSE 9000

ENTRYPOINT ["./api"]