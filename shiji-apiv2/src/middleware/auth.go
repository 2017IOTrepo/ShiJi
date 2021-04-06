package middleware

import (
	"apiv2/src/config"
	"apiv2/src/model"
	"apiv2/src/result"
	"apiv2/src/service"
	"errors"
	"fmt"
	"github.com/dgrijalva/jwt-go"
	"github.com/gin-gonic/gin"
	"net/http"
	"strings"
	"time"
)

var (
	srv *service.Service
)

func AuthSrvInit(s *service.Service) {
	srv = s
}

//自定义payload结构体,不建议直接使用 dgrijalva/jwt-go `jwt.StandardClaims`结构体.因为他的payload包含的用户信息太少.
type userStdClaims struct {
	jwt.StandardClaims
	*model.User
}

//实现 `type Claims interface` 的 `Valid() error` 方法,自定义校验内容
func (c userStdClaims) Valid() (err error) {
	if c.VerifyExpiresAt(time.Now().Unix(), true) == false {
		return errors.New("token is expired")
	}
	if !c.VerifyIssuer(config.APPIss, true) {
		return errors.New("token's issuer is wrong")
	}
	if c.User.Id < 1 {
		return errors.New("invalid user in jwt")
	}
	return
}

func JwtGenerateToken(m *model.User) (string, error) {
	m.Password = ""
	expireTime := time.Now().Add(config.OneDayOfHours * 365)
	stdClaims := jwt.StandardClaims{
		ExpiresAt: expireTime.Unix(),
		IssuedAt:  time.Now().Unix(),
		Id:        fmt.Sprintf("%d", m.Id),
		Issuer:    config.APPIss,
	}

	uClaims := userStdClaims{
		StandardClaims: stdClaims,
		User:           m,
	}

	token := jwt.NewWithClaims(jwt.SigningMethodHS256, uClaims)
	// Sign and get the complete encoded token as a string using the secret
	tokenString, err := token.SignedString([]byte(config.APPSecret))
	if err != nil {
		return "", err
	}
	return tokenString, err
}

//JwtParseUser 解析payload的内容,得到用户信息
//gin-middleware 会使用这个方法
func JwtParseUser(tokenString string) (*model.User, error) {
	if tokenString == "" {
		return nil, errors.New("no token is found in Authorization Bearer")
	}
	claims := userStdClaims{}
	_, err := jwt.ParseWithClaims(tokenString, &claims, func(token *jwt.Token) (interface{}, error) {
		if _, ok := token.Method.(*jwt.SigningMethodHMAC); !ok {
			return nil, fmt.Errorf("unexpected signing method: %v", token.Header["alg"])
		}
		return []byte(config.APPSecret), nil
	})
	if err != nil {
		return nil, err
	}
	return claims.User, err
}

func Auth(c *gin.Context) {
	r := result.ResUtils{Ctx: c}

	hToken := c.GetHeader("Authorization")
	if len(hToken) == 0 {
		r.Response(http.StatusPreconditionFailed, "token未验证", nil)
		c.Abort()
		return
	}
	token := strings.TrimSpace(hToken)

	res, err := srv.CheckTokenBlackList(hToken)

	if err != nil {
		r.Response(http.StatusPreconditionFailed, "寻找token失败请重试", nil)
		c.Abort()
		return
	}

	if res {
		r.Response(http.StatusPreconditionFailed, "token已无效 请重新登录！", nil)
		c.Abort()
		return
	}

	usr, err := JwtParseUser(token)
	if err != nil {
		r.Response(http.StatusPreconditionFailed, err.Error(), nil)
		c.Abort()
		return
	}

	//store the user Model in the context
	c.Set("user", *usr)
	c.Next()
	// after request
}
