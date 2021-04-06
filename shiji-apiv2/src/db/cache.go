package db

import (
	"context"
	"fmt"
	"log"
	"time"
)

func (dao *DaoStruct) setToRedis(values interface{}, keys ...interface{}) error {
	log.Println("存储到redis", values, keys)
	k := fmt.Sprint(keys)
	v := fmt.Sprint(values)
	_, err := dao.redis.Set(context.Background(), k, v, 2*time.Hour).Result()
	return err
}

func (dao *DaoStruct) getFromRedis(keys ...interface{}) (value interface{}, err error) {
	log.Println("从redis中获取", keys)
	k := fmt.Sprint(keys)
	_, err = dao.redis.Get(context.Background(), k).Result()
	return
}
