import moment from "moment"


// 获取资源地址
export function getFileUrl(url) {
  return process.env.VUE_APP_API_BASE_IMG_URL + url
}
// 格式化时间
export function dateFilter(value, format = 'YYYY-MM-DD HH:mm') {
  return moment(value).format(format)
}
// 格式化时间
export function getListItem(value, list, key = 'id') {
  let result = list.find(item => {
    return item[key] == value
  })
  return result
}