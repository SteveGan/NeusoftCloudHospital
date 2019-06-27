//得到url中的数据
export function getQueryString(url, name) {
  var result = url.match(new RegExp("[?&]" + name + "=([^&#]*)", "i"));
  if (result == null || result.length < 1) {
    return "";
  }
  return result[1];
}
