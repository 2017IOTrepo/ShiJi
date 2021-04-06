package main

import (
	"apiv2/src/controller"
	"apiv2/src/middleware"
	"apiv2/src/result"
	"github.com/gin-contrib/cors"
	"github.com/gin-gonic/gin"
	"time"
)

func SetupRouter(engine *gin.Engine) {
	// 全局异常处理
	engine.Use(middleware.SetUpExceptionHandler())
	// 暂时用后端方法解决跨域问题
	engine.Use(cors.New(cors.Config{
		AllowMethods:     []string{"GET", "POST", "PUT", "PATCH", "DELETE", "HEAD"},
		AllowHeaders:     []string{"Origin", "Content-Type", "Content-Length", "Accept-Encoding", "X-CSRF-Token", "Authorization"},
		AllowCredentials: true,
		MaxAge:           12 * time.Hour,
		AllowOriginFunc: func(origin string) bool {
			return true
		},
		ExposeHeaders: []string{"Content-Length"},
	}))

	engine.NoMethod(middleware.NotFoundHandler)
	engine.NoRoute(middleware.NotFoundHandler)

	engine.LoadHTMLGlob("templates/*")
	engine.Static("/static", "./static")

	engine.GET("/", func(context *gin.Context) {
		r := result.ResUtils{Ctx: context}
		r.ResponseSuccessful("ping", time.Now().Unix())
	})

	user := engine.Group("/user")
	{
		user.POST("/register", controller.UserRegister)
		user.POST("/login", controller.UserLogin)
		user.GET("/info", middleware.Auth, controller.UserInfo)
		user.POST("/logout", middleware.Auth, controller.UserLogout)
	}

	search := engine.Group("/search")
	search.Use(middleware.Auth)
	{
		search.GET("/kw_search", controller.KWSearch)
		search.GET("/kw_book", controller.KWSearchChpater)

		search.GET("/all_chapter_cnt", controller.ACBookWord)
		search.GET("/get_chapter_cnt", controller.ACChapterWord)
		search.GET("/book_chart_data", controller.BookChartData)
	}

	book := engine.Group("/book")
	book.Use(middleware.Auth)
	{
		book.GET("/get_book_shelf", controller.GetBookShelf)
		book.GET("/get_chapter_list", controller.GetChapterList)
		book.GET("/get_book_content", controller.GetBookChapterContent)
		book.GET("/get_chapter_detail", controller.GetBookDetail)
	}

	kg := engine.Group("kg")
	kg.Use(middleware.Auth)
	{
		kg.GET("/node", controller.GetNode)
		kg.GET("/relationship", controller.QueryRelationship)
		kg.GET("/all", controller.GetAll)
	}

	view := engine.Group("/html")
	{
		view.GET("/index", controller.Index)
		view.GET("/demo", controller.Demo)
		view.GET("/map", controller.Map)
	}

	dynasty := engine.Group("/dy")
	{
		dynasty.GET("/beisong", controller.BeiSong)
		dynasty.GET("/chuanshuo", controller.ChuanShuo)
		dynasty.GET("/donghan", controller.DongHan)
		dynasty.GET("/dongjin", controller.DongJin)
		dynasty.GET("/gongheguo", controller.GongHeGuo)
		dynasty.GET("/ming", controller.Ming)
		dynasty.GET("/nanbeichao", controller.NanBeiChao)
		dynasty.GET("/nansong", controller.NanSong)
		dynasty.GET("/qin", controller.Qin)
		dynasty.GET("/qing", controller.Qing)
		dynasty.GET("/shang", controller.Shang)
		dynasty.GET("/sui", controller.Sui)
		dynasty.GET("/tang", controller.Tang)
		dynasty.GET("/xia", controller.Xia)
		dynasty.GET("/xihan", controller.XiHan)
		dynasty.GET("/xijin", controller.XiJin)
		dynasty.GET("/xizhou", controller.XiZhou)
		dynasty.GET("/yuan", controller.Yuan)
	}
}
