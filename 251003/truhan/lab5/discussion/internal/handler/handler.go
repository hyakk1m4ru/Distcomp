package handler

import (
	"net/http"

	"github.com/Khmelov/Distcomp/251003/truhan/labX/discussion/internal/handler/http/message"
	"github.com/Khmelov/Distcomp/251003/truhan/labX/discussion/internal/handler/kafka"
	"github.com/Khmelov/Distcomp/251003/truhan/labX/discussion/internal/service"
	"github.com/gin-gonic/gin"
)

func New(svc service.Service) http.Handler {
	engine := gin.Default()

	router := engine.Group("/api")
	{
		message.New(svc).InitRoutes(router)
	}

	return engine
}

func NewKafkaHandler(srv service.Service) *kafka.KafkaConsumer {
	return kafka.NewKafkaConsumer(
		[]string{"localhost:9092"},
		"message-service-group",
		srv,
	)
}
