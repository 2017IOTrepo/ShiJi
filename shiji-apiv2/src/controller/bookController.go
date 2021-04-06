package controller

import (
	"apiv2/src/result"
	"github.com/gin-gonic/gin"
	"log"
	"net/http"
	"strconv"
)

func GetBookShelf(ctx *gin.Context) {
	log.Printf("获取书籍数据, IP=%s", ctx.ClientIP())
	r := result.ResUtils{Ctx: ctx}

	res, err := srv.GetBookShelfService()
	if err != nil {
		r.Response(http.StatusBadGateway, "获取失败", nil)
		return
	}

	r.ResponseSuccessful("成功获取!", res)
}

func GetChapterList(ctx *gin.Context) {
	log.Printf("获取章节列表, IP=%s", ctx.ClientIP())
	r := result.ResUtils{Ctx: ctx}

	from := ctx.Query("from")

	if from == "" {
		r.Response(http.StatusBadRequest, "参数不能为空!", nil)
		return
	}

	new, err := strconv.ParseInt(from, 10, 64)

	if err != nil {
		r.Response(http.StatusBadRequest, "参数错误!", nil)
		return
	}

	res, err := srv.GetBookChapterListService(new)

	if err != nil {
		r.Response(http.StatusExpectationFailed, "获取数据错误!", nil)
		return
	}

	r.ResponseSuccessful("成功获取!", res)
}

func GetBookChapterContent(ctx *gin.Context) {
	log.Printf("获取章节内容, IP=%s", ctx.ClientIP())
	r := result.ResUtils{Ctx: ctx}

	id := ctx.Query("id")

	if id == "" {
		r.Response(http.StatusBadRequest, "参数不能为空!", nil)
		return
	}

	new, err := strconv.ParseInt(id, 10, 64)
	if err != nil {
		r.Response(http.StatusBadRequest, "参数错误!", nil)
		return
	}

	res, err := srv.GetBookChapterContentService(new)

	if err != nil {
		r.Response(http.StatusExpectationFailed, "获取数据错误!", nil)
		return
	}

	r.ResponseSuccessful("成功获取!", res)
}

func GetBookDetail(ctx *gin.Context) {
	log.Printf("获取章节信息, IP=%s", ctx.ClientIP())
	r := result.ResUtils{Ctx: ctx}

	id := ctx.Query("id")
	if id == "" {
		r.Response(http.StatusBadRequest, "参数不能为空!", nil)
		return
	}

	new, err := strconv.ParseInt(id, 10, 64)
	if err != nil {
		r.Response(http.StatusBadRequest, "参数错误!", nil)
		return
	}

	res, err := srv.GetBookChapterDetail(new)

	if err != nil {
		r.Response(http.StatusExpectationFailed, "获取数据错误!", err.Error())
		return
	}

	r.ResponseSuccessful("成功获取!", res)
}
