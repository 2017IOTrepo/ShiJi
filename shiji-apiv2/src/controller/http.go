package controller

import "apiv2/src/service"

var (
	srv *service.Service
)

func Init(s *service.Service) {
	srv = s
}
