package service

import (
	"apiv2/src/model"
)

func (s *Service) GetBookShelfService() ([]model.Book, error) {
	return s.dao.GetAllBookList()
}

func (s *Service) GetBookChapterListService(from int64) ([]model.ChapterTitle, error) {
	return s.dao.GetChapterTitlesListByFrom(from)
}

func (s *Service) GetBookChapterContentService(id int64) (model.ChapterContent, error) {
	return s.dao.GetChapterContentByid(id)
}

func (s *Service) GetBookChapterDetail(id int64) (model.KWChapter, error) {
	return s.dao.GetChapterDetailById(id)
}
