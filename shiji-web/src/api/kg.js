import request from '@/utils/request'

export function getNode(limit, query) {
  return request({
    url: '/kg/node',
    method: 'get',
    params: { limit, query }
  })
}

export function expandRelationship(limit, query) {
  return request({
    url: '/kg/relationship',
    method: 'get',
    params: { limit, query }
  })
}
