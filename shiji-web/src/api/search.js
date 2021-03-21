import request from '@/utils/request'

export function allChapterCnt(bid) {
  return request({
    url: 'search/all_chapter_cnt',
    method: 'get',
    params: { bid }
  })
}

export function getChapterCnt(cid, query) {
  return request({
    url: 'search/get_chapter_cnt',
    method: 'get',
    params: { cid, query }
  })
}

export function getBookChartData(bid, query) {
  return request({
    url: 'search/book_chart_data',
    method: 'get',
    params: { bid, query }
  })
}
