package com.maddyhome.idea.vim.extension.argtextobj;

/**
 * InnerVimArgumentCommandAction
 *
 * @author Ryan.Zheng@aoliday.com
 * @version 1.0
 * @date 2019/10/10 15:08
 */
class InnerVimArgumentCommandAction extends VimArgumentCommandAction {
  @Override
  protected boolean isInner() {
    return true;
  }
}
