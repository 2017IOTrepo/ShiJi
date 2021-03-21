import request from '@/utils/request'

export function getBookShelf() {
  return request({
    url: '/book/get_book_shelf',
    method: 'get'
  })
}

export function getChapterDetail(id) {
  return request({
    url: '/book/get_chapter_detail',
    method: 'get',
    params: { id }
  })
}

export function getChapterList(from) {
  return request({
    url: '/book/get_chapter_list',
    method: 'get',
    params: { from }
  })
}
