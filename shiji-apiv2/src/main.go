package main

import (
	"apiv2/src/config"
	"apiv2/src/controller"
	"apiv2/src/middleware"
	"apiv2/src/service"
	"context"
	"fmt"
	"github.com/gin-gonic/gin"
	"log"
	"net/http"
	"os"
	"os/signal"
	"time"
)

func main() {
	gin.SetMode(config.AppMode)
	engine := gin.New()

	SetupRouter(engine)

	srv := service.New()
	controller.Init(srv)
	middleware.AuthSrvInit(srv)

	server := &http.Server{
		Addr:         config.AppPort,
		Handler:      engine,
		ReadTimeout:  config.AppReadTimeout * time.Second,
		WriteTimeout: config.AppWriteTimeout * time.Second,
	}

	fmt.Println("|  Go Http Server Start Successful  |")
	fmt.Println("|    Port" + config.AppPort + "     Pid:" + fmt.Sprintf("%d", os.Getpid()) + "        |")

	go func() {
		if err := server.ListenAndServe(); err != nil && err != http.ErrServerClosed {
			log.Fatalf("HTTP server listen : %s\n", err)
		}
	}()

	// 等待中断信号以优雅地关闭服务器（设置 5 秒的超时时间）
	// 这里也是b站设计的关闭代码
	signalChan := make(chan os.Signal)
	signal.Notify(signalChan, os.Interrupt)
	sig := <-signalChan
	log.Println("Get Signal:", sig)
	log.Println("Shutdown Server ...")

	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()
	defer srv.Close()
	if err := server.Shutdown(ctx); err != nil {
		log.Fatal("Server Shutdown:", err)
	}
	log.Println("Server exiting")
}
