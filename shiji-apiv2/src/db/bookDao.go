package db

import (
	"apiv2/src/model"
)

const (
	_GET_ALL_BOOKS_LIST      = "select * from `shiji`.book"
	_GET_CHAPTER_TITLES_LIST = "select `id`, `title` from shiji.ancient_book_chapter where `from` = ?"
	_GET_CHAPTER_CONTENT     = "select `content` from shiji.ancient_book_chapter where `id` = ?"
	_GET_CHAPTER_DETAIL = "SELECT c.id, book.id AS b_id, book_name, front, author, title FROM (SELECT id,title, `from` FROM ancient_book_chapter WHERE id = ?) AS c, book WHERE  c.`from` = book.id"
)

func (dao *DaoStruct) GetAllBookList() (res []model.Book, err error) {
	rows, err := dao.db.Query(_GET_ALL_BOOKS_LIST)
	if err != nil {
		return
	}

	defer rows.Close()
	for rows.Next() {
		var tmp model.Book
		if err = rows.Scan(
			&tmp.Id,
			&tmp.BookName,
			&tmp.Author,
			&tmp.Dynasty,
			&tmp.Type,
			&tmp.Front);
			err != nil {
			return
		}

		res = append(res, tmp)
	}

	return
}

func (dao *DaoStruct) GetChapterTitlesListByFrom(from int64) (res []model.ChapterTitle, err error) {
	row, err := dao.db.Query(_GET_CHAPTER_TITLES_LIST, from)
	if err != nil {
		return
	}

	defer row.Close()
	for row.Next() {
		var tmp model.ChapterTitle
		if err = row.Scan(
			&tmp.Id,
			&tmp.Title);
			err != nil {
			return
		}
		res = append(res, tmp)
	}

	return
}

func (dao *DaoStruct) GetChapterContentByid(id int64) (res model.ChapterContent, err error) {
	err = dao.db.Get(&res, _GET_CHAPTER_CONTENT, id)
	if err != nil {
		return
	}
	return
}

func (dao *DaoStruct) GetChapterDetailById(id int64) (res model.KWChapter, err error) {
	err = dao.db.Get(&res, _GET_CHAPTER_DETAIL, id)
	if err != nil {
		return
	}
	return
}