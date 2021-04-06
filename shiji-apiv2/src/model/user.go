package model

type User struct {
	Id       int64
	Email    string `json:"email"`
	Password string `json:"password"`
	Roles    string `json:"roles"`
}

type UserDTO struct {
	Email  string `json:"email"`
	Token  string `json:"token"`
	Roles  string `json:"roles"`
}
