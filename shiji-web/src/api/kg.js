import request from '@/utils/request'

export function getKG() {
  return request({
    url: '/book/get_book_shelf',
    method: 'get'
  })
}
