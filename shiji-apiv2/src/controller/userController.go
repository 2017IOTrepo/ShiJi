package controller

import (
	"apiv2/src/middleware"
	"apiv2/src/model"
	"apiv2/src/result"
	"github.com/gin-gonic/gin"
	"log"
	"net/http"
)

func UserRegister(ctx *gin.Context) {
	log.Printf("开始注册, IP=%s", ctx.ClientIP())

	r := result.ResUtils{Ctx: ctx}
	var user model.User

	err := ctx.BindJSON(&user)

	if err != nil {
		r.Response(http.StatusBadRequest, err.Error(), nil)
		return
	}
	res, err := srv.RegisterService(&user)
	if err != nil {
		r.Response(http.StatusBadRequest, err.Error(), nil)
		return
	}
	user.Id = res
	token, err := middleware.JwtGenerateToken(&user)
	if err != nil {
		r.Response(http.StatusBadRequest, "注册失败", nil)
		return
	}
	uDTO := model.UserDTO{
		Email: user.Email,
		Token: token,
		Roles: "user",
	}
	r.ResponseSuccessful("注册成功", uDTO)
}

func UserLogin(ctx *gin.Context) {
	log.Printf("开始登录, IP=%s", ctx.ClientIP())

	resUtil := result.ResUtils{Ctx: ctx}

	var user model.User

	err := ctx.BindJSON(&user)

	if err != nil {
		resUtil.Response(http.StatusBadRequest, err.Error(), nil)
		return
	}

	res, err := srv.LoginService(&user)

	if err != nil {
		resUtil.Response(http.StatusBadRequest, err.Error(), nil)
		return
	}

	token, err := middleware.JwtGenerateToken(res)

	if err != nil {
		resUtil.Response(http.StatusBadRequest, "登录失败", nil)
		return
	}

	uDTO := model.UserDTO{
		Email: res.Email,
		Token: token,
		Roles: res.Roles,
	}

	resUtil.ResponseSuccessful("登录成功", uDTO)
}

func UserInfo(ctx *gin.Context) {
	log.Printf("开始获取用户信息, IP=%s", ctx.ClientIP())

	r := result.ResUtils{Ctx: ctx}

	auth := ctx.GetHeader("Authorization")

	user, err := middleware.JwtParseUser(auth)

	if err != nil {
		r.Response(http.StatusUnauthorized, "未能识别token", nil)
		return
	}

	d := model.UserDTO{
		Email: user.Email,
		Token: auth,
		Roles: user.Roles,
	}

	log.Printf("获取用户信息 %s %s", d.Email, d.Roles)
	r.ResponseSuccessful("获取成功", d)
}

func UserLogout(ctx *gin.Context) {
	r := result.ResUtils{Ctx: ctx}

	auth := ctx.GetHeader("Authorization")

	err := srv.SaveToken2BlackList(auth)

	if err != nil {
		panic(err)
	}

	r.ResponseSuccessful("成功退出登录!", nil)
}
