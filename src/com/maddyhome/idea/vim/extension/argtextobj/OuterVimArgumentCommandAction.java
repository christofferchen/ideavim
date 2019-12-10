package com.maddyhome.idea.vim.extension.argtextobj;

/**
 * OuterVimArgumentCommandAction
 *
 * @author Ryan.Zheng@aoliday.com
 * @version 1.0
 * @date 2019/10/10 15:08
 */
class OuterVimArgumentCommandAction extends VimArgumentCommandAction {

  @Override
  protected boolean isInner() {
    return false;
  }
}
