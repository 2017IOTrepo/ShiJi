package db

import (
	"apiv2/src/model"
	"apiv2/src/utils"
	"context"
	"github.com/go-redis/redis/v8"
)

const (
	_INSERTUSER      = "insert into shiji.user (email, password) values (?, ?)"
	_FINDUSERBYEMAIL = "select * from shiji.user where `email` = ?"
	_FINDUSERBYID    = "select * from shiji.user where `id` = ?"
)

func (dao *DaoStruct) InsertUser(user *model.User) (int64, error) {
	pwd, err := utils.PwdEncode(user.Password)
	if err != nil {
		return 0, err
	}
	res, err := dao.db.Exec(_INSERTUSER, user.Email, pwd)
	if err != nil {
		return 0, err
	}
	return res.LastInsertId()
}

func (dao *DaoStruct) FindUserByEmail(email string) (*model.User, error) {
	res := dao.db.QueryRowx(_FINDUSERBYEMAIL, email)

	user := new(model.User)

	if err := res.Scan(&user.Id, &user.Email, &user.Password, &user.Roles); err != nil {
		return user, err
	}

	return user, nil
}

func (dao *DaoStruct) FindUserById(id int64) (*model.User, error) {
	res := dao.db.QueryRowx(_FINDUSERBYID, id)

	user := new(model.User)

	if err := res.Scan(&user.Id, &user.Email, &user.Password, &user.Roles); err != nil {
		return user, err
	}

	return user, nil
}

func (dao *DaoStruct) GetTokenFromRedis(ctx context.Context, token string) (string, error) {
	res, err := dao.redis.Get(ctx, token).Result()

	if err != nil {
		return "", err
	}

	return res, nil
}

func (dao DaoStruct) SaveToken2Redis(ctx context.Context, token string) (bool, error) {
	err := dao.redis.Set(ctx, token, false, redis.KeepTTL).Err()
	if err != nil {
		return false, err
	}
	return true, nil
}
