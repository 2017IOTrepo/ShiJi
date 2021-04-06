package service

import (
	"apiv2/src/model"
	"apiv2/src/utils"
	"context"
	"errors"
	"github.com/go-redis/redis/v8"
)

func checkEmpty(user *model.User) bool {
	return user.Email == "" || user.Password == ""
}

func (s *Service) RegisterService(user *model.User) (int64, error) {
	if checkEmpty(user) {
		return 0, errors.New("字段错误!")
	}
	res, err := s.dao.InsertUser(user)
	if err != nil {
		return 0, err
	}

	return res, nil
}

func (s *Service) LoginService(user *model.User) (*model.User, error) {
	if checkEmpty(user) {
		return nil, errors.New("字段错误!")
	}

	res, err := s.dao.FindUserByEmail(user.Email)

	if err != nil {
		t, err := s.RegisterService(user)
		if err != nil {
			return nil, err
		}
		user.Roles = "user"
		user.Id = t
		return user, nil
	}

	err = utils.PwdDecode(res.Password, user.Password)

	if err != nil {
		return nil, err
	}

	return res, nil
}

func (s *Service) SaveToken2BlackList(token string) error {
	_, err := s.dao.SaveToken2Redis(context.Background(), token)
	if err != nil {
		return err
	}
	return nil
}

func (s *Service) CheckTokenBlackList(token string) (bool, error) {
	_, err := s.dao.GetTokenFromRedis(context.Background(), token)

	if err == redis.Nil {
		return false, nil
	}

	if err != nil {
		return false, err
	}

	return true, nil
}
