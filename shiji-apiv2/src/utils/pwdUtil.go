package utils

import (
	"golang.org/x/crypto/bcrypt"
)

/**
密码处理逻辑
*/
// hash加密
func PwdEncode(pwdString string) (encodePwd string, err error) {
	hash, err := bcrypt.GenerateFromPassword([]byte(pwdString), bcrypt.DefaultCost)
	if err != nil {
		return
	}

	return string(hash), nil
}

// hash解密
func PwdDecode(sqlPwdString string, userPwdString string) (err error) {
	err = bcrypt.CompareHashAndPassword([]byte(sqlPwdString), []byte(userPwdString))
	return err
}
