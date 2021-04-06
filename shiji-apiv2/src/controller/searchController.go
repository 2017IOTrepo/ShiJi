package controller

import (
	"apiv2/src/result"
	"github.com/gin-gonic/gin"
	"log"
	"net/http"
	"strconv"
)

func KWSearch(ctx *gin.Context) {
	log.Printf("开始通过时间进行关键字搜索, IP=%s", ctx.ClientIP())
	r := result.ResUtils{Ctx: ctx}

	ks := ctx.Query("sk")
	if ks == "" {
		r.Response(http.StatusBadRequest, "参数不能为空!", nil)
		return
	}

	res, err := srv.GetKWSearchListService(ks)

	if err != nil {
		r.Response(http.StatusExpectationFailed, "获取数据错误!", nil)
		return
	}

	r.ResponseSuccessful("成功获取!", res)
}

func KWSearchChpater(ctx *gin.Context) {
	log.Printf("开始通过时间进行关键字搜索获取章节信息, IP=%s", ctx.ClientIP())
	r := result.ResUtils{Ctx: ctx}

	ks := ctx.Query("sk")
	if ks == "" {
		r.Response(http.StatusBadRequest, "参数不能为空!", nil)
		return
	}

	res, err := srv.GetKWSearchChapterListService(ks)

	if err != nil {
		r.Response(http.StatusExpectationFailed, "获取数据错误!", nil)
		return
	}

	r.ResponseSuccessful("成功获取!", res)
}

func ACBookWord(ctx *gin.Context) {
	log.Printf("开始获取本书相关数据, IP=%s", ctx.ClientIP())
	r := result.ResUtils{Ctx: ctx}

	bid := ctx.Query("bid")
	if bid == "" {
		r.Response(http.StatusBadRequest, "参数不能为空!", nil)
		return
	}

	p, err := strconv.ParseInt(bid, 10, 64)
	if err != nil {
		r.Response(http.StatusBadRequest, "参数错误!", nil)
		return
	}

	res, err := srv.GetAllChapterFreqService(p)

	if err != nil {
		r.Response(http.StatusExpectationFailed, "获取数据错误!", nil)
		return
	}

	r.ResponseSuccessful("成功获取!", res)
}

func ACChapterWord(ctx *gin.Context) {
	log.Printf("开始获取本章节相关数据, IP=%s", ctx.ClientIP())
	r := result.ResUtils{Ctx: ctx}

	cid := ctx.Query("cid")
	query := ctx.Query("query")
	if cid == "" {
		r.Response(http.StatusBadRequest, "参数不能为空!", nil)
		return
	}

	p, err := strconv.ParseInt(cid, 10, 64)
	if err != nil {
		r.Response(http.StatusBadRequest, "参数错误!", nil)
		return
	}

	res, err := srv.GetChapterFreqService(p, query)

	if err != nil {
		r.Response(http.StatusExpectationFailed, "获取数据错误!", nil)
		return
	}

	r.ResponseSuccessful("成功获取!", res)
}

func BookChartData(ctx *gin.Context) {
	log.Printf("开始获取本书图表相关数据, IP=%s", ctx.ClientIP())
	r := result.ResUtils{Ctx: ctx}

	bid := ctx.Query("bid")
	query := ctx.Query("query")
	if bid == "" {
		r.Response(http.StatusBadRequest, "参数不能为空!", nil)
		return
	}

	p, err := strconv.ParseInt(bid, 10, 64)
	if err != nil {
		r.Response(http.StatusBadRequest, "参数错误!", nil)
		return
	}

	res, err := srv.GetBookChartDataService(p, query)

	if err != nil {
		r.Response(http.StatusExpectationFailed, "获取数据错误!", err.Error())
		return
	}

	r.ResponseSuccessful("成功获取!", res)
}