package config

import "time"

const (
	AppMode = "debug" //debug or release
	AppPort = ":9000"
	AppName = "史迹web_api"

	// 超时时间
	AppReadTimeout  = 120
	AppWriteTimeout = 120

	// 日志文件
	AppAccessLogName = "log/" + AppName + "-access.log"
	AppErrorLogName  = "log/" + AppName + "-error.log"
	AppGrpcLogName   = "log/" + AppName + "-grpc.log"

	// jwt相关
	APPSecret     = "shiji2020"
	OneDayOfHours = time.Hour * 24
	APPIss        = "github.com/xmmmmmovo/ShiJi"

	// mysql相关
	MysqlDriver        = "mysql"
	MysqlDatasourceUrl = "root:@tcp(:)/shiji?tls=false"

	// redis相关
	RedisAddr = "localhost:6379"
	RedisPwd  = ""
	RedisDB   = 0

	// neo4j相关
	Neo4JUrl       = "bolt://:7687"
	Neo4JUser      = "neo4j"
	Neo4JPwd       = ""
	Neo4JEncrypted = false
)
