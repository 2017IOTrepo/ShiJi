package result

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

type ResUtils struct {
	Ctx *gin.Context
}

type response struct {
	Code    int         `json:"code"`
	Message string      `json:"message"`
	Data    interface{} `json:"data"`
}

func (r *ResUtils) Response(code int, msg string, data interface{}) {
	r.Ctx.AbortWithStatusJSON(
		code,
		response{
			Code:    code,
			Message: msg,
			Data:    data,
		},
	)
}

func (r *ResUtils) ResponseSuccessful(msg string, data interface{}) {
	r.Ctx.JSON(
		http.StatusOK,
		response{
			Code:    200,
			Message: msg,
			Data:    data,
		},
	)
}
