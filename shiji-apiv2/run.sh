docker build . -t go-gin:1.0
docker run -i -t -p 9000:9000 go-gin