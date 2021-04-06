package model

type Book struct {
	Id       int64  `json:"id"`
	BookName string `json:"book_name"`
	Author   string `json:"author"`
	Dynasty  string `json:"dynasty"`
	Type     string `json:"type"`
	Front    string `json:"front"`
}

type ChapterTitle struct {
	Id    int64  `json:"id"`
	Title string `json:"title"`
}

type ChapterContent struct {
	Content string `json:"content"`
}
