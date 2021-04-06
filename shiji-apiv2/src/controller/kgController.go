package controller

import (
	"apiv2/src/result"
	"github.com/gin-gonic/gin"
	"log"
	"net/http"
	"strconv"
)

func GetNode(ctx *gin.Context) {
	log.Printf("查询知识图谱, IP=%s", ctx.ClientIP())
	r := result.ResUtils{Ctx: ctx}

	query := ctx.Query("query")
	if query == "" {
		r.Response(http.StatusBadRequest, "参数不能为空!", nil)
		return
	}

	limit := ctx.Query("limit")
	if limit == "" {
		r.Response(http.StatusBadRequest, "参数不能为空!", nil)
		return
	}

	limitN, err := strconv.Atoi(limit)
	if err != nil {
		r.Response(http.StatusBadRequest, "参数错误!", nil)
		return
	}

	resp, err := srv.GetNodeList(query, limitN)

	if err != nil {
		r.Response(http.StatusBadRequest, "获取数据错误!", nil)
	}

	r.ResponseSuccessful("获取成功", resp)
}

func QueryRelationship(ctx *gin.Context) {
	log.Printf("从节点扩展知识图谱知识图谱, IP=%s", ctx.ClientIP())
	r := result.ResUtils{Ctx: ctx}

	query := ctx.Query("query")
	if query == "" {
		r.Response(http.StatusBadRequest, "参数不能为空!", nil)
		return
	}

	queryN, err := strconv.Atoi(query)
	if err != nil {
		r.Response(http.StatusBadRequest, "参数错误!", nil)
		return
	}

	limit := ctx.Query("limit")
	if limit == "" {
		r.Response(http.StatusBadRequest, "参数不能为空!", nil)
		return
	}

	limitN, err := strconv.Atoi(limit)
	if err != nil {
		r.Response(http.StatusBadRequest, "参数错误!", nil)
		return
	}

	resp, err := srv.ExpandNode(queryN, limitN)

	if err != nil {
		r.Response(http.StatusBadRequest, "获取数据错误!", nil)
	}

	r.ResponseSuccessful("获取成功", resp)
}

func GetAll(ctx *gin.Context) {
	log.Printf("获取所有知识图谱, IP=%s", ctx.ClientIP())
	r := result.ResUtils{Ctx: ctx}

	r.ResponseSuccessful("", "")
}
