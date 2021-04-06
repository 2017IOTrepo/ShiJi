package service

import "apiv2/src/model"

func (s *Service) GetKWSearchListService(ks string) ([]model.KW, error) {
	return s.dao.GetSearchListByKW(ks)
}

func (s *Service) GetKWSearchChapterListService(ks string) ([]model.KWChapter, error) {
	return s.dao.GetSearchChapterListByKW(ks)
}

func (s *Service) GetAllChapterFreqService(id int64) (model.ACFreq, error) {
	allcnt, err := s.dao.GetBookWordCount(id)

	if err != nil {
		return model.ACFreq{}, err
	}

	allVarCnt, err := s.dao.GetBookVarWordCount(id)

	if err != nil {
		return model.ACFreq{}, err
	}

	allModalCnt, err := s.dao.GetBookModalWordCount(id)

	if err != nil {
		return model.ACFreq{}, err
	}

	allEmptyCnt, err := s.dao.GetBookEmptyWordCount(id)

	if err != nil {
		return model.ACFreq{}, err
	}

	rw := 0
	if allcnt != 0 {
		rw = allcnt - allEmptyCnt
	}

	return model.ACFreq{
		Sum:       allcnt,
		Var:       allVarCnt,
		Modal:     allModalCnt,
		RealWord:  rw,
		EmptyWord: allEmptyCnt,
	}, nil
}

func (s *Service) GetChapterFreqService(id int64, query string) (model.ACFreq, error) {
	allcnt, err := s.dao.GetChapterWordCount(id)

	if err != nil {
		return model.ACFreq{}, err
	}

	allVarCnt, err := s.dao.GetChapterVarWordCount(id, query)

	if err != nil {
		return model.ACFreq{}, err
	}

	allModalCnt, err := s.dao.GetChapterModalWordCount(id, query)

	if err != nil {
		return model.ACFreq{}, err
	}

	allEmptyCnt, err := s.dao.GetChapterEmptyWordCount(id, query)

	if err != nil {
		return model.ACFreq{}, err
	}

	rw := 0
	if allcnt != 0 {
		rw = allcnt - allEmptyCnt
	}

	return model.ACFreq{
		Sum:       allcnt,
		Var:       allVarCnt,
		Modal:     allModalCnt,
		RealWord:  rw,
		EmptyWord: allEmptyCnt,
	}, nil
}

func (s *Service) GetBookChartDataService(id int64, query string) ([]model.ChartData, error) {
	return s.dao.GetBookChartDataListByKWBid(id, query)
}