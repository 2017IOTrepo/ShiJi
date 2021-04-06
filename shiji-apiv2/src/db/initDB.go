package db

import (
	"apiv2/src/config"
	"context"
	"github.com/go-redis/redis/v8"
	_ "github.com/go-sql-driver/mysql"
	"github.com/jmoiron/sqlx"
	"github.com/neo4j/neo4j-go-driver/neo4j"
)

// dao DaoStruct interface
type Dao interface {
	Close()
	Ping(ctx context.Context) (err error)
}

// DaoStruct DaoStruct.
type DaoStruct struct {
	db    *sqlx.DB
	redis *redis.Client
	//redisExpire int32
	neo4j neo4j.Driver
}

// New new a DaoStruct and return.
func New() *DaoStruct {
	driver, err := neo4j.NewDriver(config.Neo4JUrl,
		neo4j.BasicAuth(config.Neo4JUser, config.Neo4JPwd, ""), func(c *neo4j.Config) {
			c.Encrypted = config.Neo4JEncrypted
		})
	if err != nil {
		// 这里直接panic 有兜底函数
		panic(err)
	}
	return &DaoStruct{
		// mysql
		db: sqlx.MustOpen(config.MysqlDriver, config.MysqlDatasourceUrl),
		//// redis
		redis: redis.NewClient(&redis.Options{
			Addr:     config.RedisAddr,
			Password: config.RedisPwd,
			DB:       config.RedisDB,
		}),
		//redisExpire: int32(time.Duration(rc.RedisExpire) / time.Second),
		neo4j: driver,
	}
}

func (dao *DaoStruct) Ping(ctx context.Context) (err error) {
	return nil
}

// Close close the resource.
func (dao *DaoStruct) Close() {
	dao.redis.Close()
	dao.db.Close()
	dao.neo4j.Close()
}
