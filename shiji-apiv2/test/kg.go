package main

import (
	"apiv2/src/db"
)

func main() {
	dao := db.New()
	s := *dao
	defer s.Close()
}
