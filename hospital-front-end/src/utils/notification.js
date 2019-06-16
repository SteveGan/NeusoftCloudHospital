import {
  Message,
  MessageBox
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
    type: 'error',
    duration: 3 * 1000
  })
}
