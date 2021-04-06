package middleware

import (
	"apiv2/src/config"
	"apiv2/src/result"
	"github.com/gin-gonic/gin"
	"log"
	"net/http"
)

func SetUpExceptionHandler() gin.HandlerFunc {
	return func(c *gin.Context) {
		defer func() {
			if err := recover(); err != nil {
				utilGin := result.ResUtils{Ctx: c}
				utilGin.Response(500, "系统异常，请联系管理员！", nil)
				log.Panicf("【重要错误】%s 项目出错了！", config.AppName)
				c.Abort()
				return
			}
		}()
		c.Next()
	}

}

func NotFoundHandler(c *gin.Context) {
	resUtils := result.ResUtils{Ctx: c}
	resUtils.Response(http.StatusNotFound, "未找到相应接口", nil)
}