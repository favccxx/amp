import request from '@/utils/request'

export function fetchShopDetail(query) {
  return request({
    url: '/shop/detail/' + query,
    method: 'get',
    params: query
  })
}

export function fetchShopList(query) {
  return request({
    url: '/shop/list',
    method: 'get',
    params: query
  })
}
