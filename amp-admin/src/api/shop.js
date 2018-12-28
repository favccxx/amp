import request from '@/utils/request'

export function fetchShopDetail(query) {
  return request({
    url: '/shop/detail/' + query,
    method: 'get',
    params: query
  })
}
