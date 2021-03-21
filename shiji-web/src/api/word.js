import request from '@/utils/request'

export function searchKWChapter(sk) {
  return request({
    url: '/search/kw_book',
    method: 'get',
    params: { sk }
  })
}
