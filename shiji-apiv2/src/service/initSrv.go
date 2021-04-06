package service

import "apiv2/src/db"

type Service struct {
	dao *db.DaoStruct
}

func New() (s *Service) {
	s = &Service{
		dao: db.New(),
	}
	return
}

func (s *Service) Close() {
	s.dao.Close()
}
