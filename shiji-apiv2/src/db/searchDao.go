package db

import "apiv2/src/model"

const (
	_GET_SEARCH_LIST_BY_KW         = "SELECT dynasty, SUM(cnt) FROM (SELECT dynasty, cnt FROM ( SELECT cnt, from_chapter FROM shiji.ancient_book_cuts WHERE word LIKE ?) AS c, shiji.book, shiji.ancient_book_chapter WHERE c.from_chapter = ancient_book_chapter.id AND ancient_book_chapter.`from` = book.id) AS d GROUP BY dynasty"
	_GET_SEARCH_CHAPTER_LIST_BY_KW = "SELECT ancient_book_chapter.id, book.id, book_name, front, author, title FROM (SELECT cnt, from_chapter FROM shiji.ancient_book_cuts WHERE word LIKE ?  ORDER BY RAND() LIMIT 10) AS c, shiji.book, shiji.ancient_book_chapter WHERE c.from_chapter = ancient_book_chapter.id AND ancient_book_chapter.`from` = book.id"
	_GET_BOOK_WORD_COUNT           = "SELECT SUM(cnt) FROM chapter_cuts_cnt WHERE from_chapter IN (SELECT id FROM ancient_book_chapter WHERE `from` = ?)"
	_GET_BOOK_VAR_WORD_COUNT       = "SELECT COUNT(cnt) FROM ancient_book_cuts WHERE from_chapter IN (SELECT id FROM ancient_book_chapter WHERE `from` = ?) AND word_type LIKE 'v%'"
	_GET_BOOK_MODAL_WORD_COUNT     = "SELECT COUNT(cnt) FROM ancient_book_cuts WHERE from_chapter IN (SELECT id FROM ancient_book_chapter WHERE `from` = ?) AND word_type = 'y'"
	_GET_BOOK_EMPTY_WORD_COUNT     = "SELECT COUNT(cnt) FROM ancient_book_cuts WHERE from_chapter IN (SELECT id FROM ancient_book_chapter WHERE `from` = ?) AND (word_type = 'd' OR word_type LIKE 'p%' OR word_type LIKE 'c%' OR word_type LIKE 'u%' OR word_type = 'e' OR word_type = 'y' OR word_type = 'o' OR word_type = 'h' OR word_type = 'k')"
	_GET_CHAPTER_WORD_COUNT        = "SELECT cnt FROM chapter_cuts_cnt WHERE from_chapter = ?;"
	_GET_CHAPTER_VAR_WORD_COUNT    = "SELECT COUNT(cnt) FROM ancient_book_cuts WHERE from_chapter = ? AND word LIKE ? AND word_type LIKE 'v%'"
	_GET_CHAPTER_MODAL_WORD_COUNT  = "SELECT COUNT(cnt) FROM ancient_book_cuts WHERE from_chapter = ? AND word LIKE ? AND word_type = 'y'"
	_GET_CHAPTER_EMPTY_WORD_COUNT  = "SELECT COUNT(cnt) FROM ancient_book_cuts WHERE from_chapter = ? AND word LIKE ? AND (word_type = 'd' OR word_type LIKE 'p%' OR word_type LIKE 'c%' OR word_type LIKE 'u%' OR word_type = 'e' OR word_type = 'y' OR word_type = 'o' OR word_type = 'h' OR word_type = 'k')"
	_GET_BOOK_CHART_DATA           = "SELECT title, COUNT(ancient_book_cuts.cnt), ca.cnt FROM (SELECT from_chapter, cnt FROM chapter_cuts_cnt WHERE from_chapter IN (SELECT id FROM ancient_book_chapter WHERE `from` = ?)) as ca, ancient_book_chapter, ancient_book_cuts WHERE ca.from_chapter = ancient_book_chapter.id AND ancient_book_cuts.from_chapter = ancient_book_chapter.id AND word LIKE ? GROUP BY ancient_book_cuts.from_chapter"
)

func (dao *DaoStruct) GetSearchListByKW(ks string) (res []model.KW, err error) {
	rows, err := dao.db.Query(_GET_SEARCH_LIST_BY_KW, "%"+ks+"%")
	if err != nil {
		return
	}

	defer rows.Close()
	for rows.Next() {
		var tmp model.KW
		if err = rows.Scan(
			&tmp.Dynasty,
			&tmp.Sum);
			err != nil {
			return
		}

		res = append(res, tmp)
	}

	return
}

func (dao *DaoStruct) GetSearchChapterListByKW(ks string) (res []model.KWChapter, err error) {
	rows, err := dao.db.Query(_GET_SEARCH_CHAPTER_LIST_BY_KW, "%"+ks+"%")
	if err != nil {
		return
	}

	defer rows.Close()
	for rows.Next() {
		var tmp model.KWChapter
		if err = rows.Scan(
			&tmp.Id,
			&tmp.BId,
			&tmp.BookName,
			&tmp.Front,
			&tmp.Author,
			&tmp.Title);
			err != nil {
			return
		}

		res = append(res, tmp)
	}

	return
}

func (dao *DaoStruct) GetBookChartDataListByKWBid(id int64, query string) (res []model.ChartData, err error) {
	rows, err := dao.db.Query(_GET_BOOK_CHART_DATA, id, "%"+query+"%")
	if err != nil {
		return
	}

	defer rows.Close()
	for rows.Next() {
		var tmp model.ChartData
		if err = rows.Scan(
			&tmp.Title,
			&tmp.SC,
			&tmp.Cnt);
			err != nil {
			return
		}
		tmp.Freq = float32(tmp.SC) / float32(tmp.Cnt)
		res = append(res, tmp)
	}

	return
}

func (dao *DaoStruct) GetBookWordCount(id int64) (res int, err error) {
	err = dao.db.Get(&res, _GET_BOOK_WORD_COUNT, id)
	if err != nil {
		return
	}
	return
}

func (dao *DaoStruct) GetBookVarWordCount(id int64) (res int, err error) {
	err = dao.db.Get(&res, _GET_BOOK_VAR_WORD_COUNT, id)
	if err != nil {
		return
	}
	return
}

func (dao *DaoStruct) GetBookModalWordCount(id int64) (res int, err error) {
	err = dao.db.Get(&res, _GET_BOOK_MODAL_WORD_COUNT, id)
	if err != nil {
		return
	}
	return
}

func (dao *DaoStruct) GetBookEmptyWordCount(id int64) (res int, err error) {
	err = dao.db.Get(&res, _GET_BOOK_EMPTY_WORD_COUNT, id)
	if err != nil {
		return
	}
	return
}

func (dao *DaoStruct) GetChapterWordCount(id int64) (res int, err error) {
	err = dao.db.Get(&res, _GET_CHAPTER_WORD_COUNT, id)
	if err != nil {
		return
	}
	return
}

func (dao *DaoStruct) GetChapterVarWordCount(id int64, query string) (res int, err error) {
	err = dao.db.Get(&res, _GET_CHAPTER_VAR_WORD_COUNT, id, "%"+query+"%")
	if err != nil {
		return
	}
	return
}

func (dao *DaoStruct) GetChapterModalWordCount(id int64, query string) (res int, err error) {
	err = dao.db.Get(&res, _GET_CHAPTER_MODAL_WORD_COUNT, id, "%"+query+"%")
	if err != nil {
		return
	}
	return
}

func (dao *DaoStruct) GetChapterEmptyWordCount(id int64, query string) (res int, err error) {
	err = dao.db.Get(&res, _GET_CHAPTER_EMPTY_WORD_COUNT, id, "%"+query+"%")
	if err != nil {
		return
	}
	return
}
