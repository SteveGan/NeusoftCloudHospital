import {
  Message
} from 'element-ui'


// 成功提示框
export function successDialog(content) {
  Message({
    message: content,
    type: 'success',
    duration: 3 * 1000
  })
}

export function failDialog(content) {
  Message({
    message: content,
    type: 'warning',
    duration: 3 * 1000
  })
}
