package model

type KW struct {
	Dynasty string `json:"dynasty"`
	Sum     int    `json:"sum"`
}

type KWChapter struct {
	Id       int64  `json:"id" db:"id"`
	BId      int64  `json:"b_id" db:"b_id"`
	BookName string `json:"book_name" db:"book_name"`
	Front    string `json:"front" db:"front"`
	Author   string `json:"author" db:"author"`
	Title    string `json:"title" db:"title"`
}

type ACFreq struct {
	Sum       int `json:"sum"`
	Var       int `json:"var"`
	Modal     int `json:"modal"` // 语气词
	RealWord  int `json:"real_word"`
	EmptyWord int `json:"empty_word"`
}

type ChartData struct {
	Title string  `json:"title"`
	SC    int     `json:"sc"`
	Cnt   int     `json:"cnt"`
	Freq  float32 `json:"freq"`
}
