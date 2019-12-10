package com.maddyhome.idea.vim.extension.argtextobj;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.maddyhome.idea.vim.VimPlugin;
import com.maddyhome.idea.vim.command.Argument;
import com.maddyhome.idea.vim.command.CommandFlags;
import com.maddyhome.idea.vim.command.CommandState;
import com.maddyhome.idea.vim.common.TextRange;
import com.maddyhome.idea.vim.handler.TextObjectActionHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

/**
 * VimArgumentCommandAction
 *
 * @author Ryan.Zheng@aoliday.com
 * @version 1.0
 * @date 2019/6/24 17:21
 */
public abstract class VimArgumentCommandAction extends TextObjectActionHandler {
  @Override
  public EnumSet<CommandFlags> getFlags() {
    return EnumSet.of(CommandFlags.FLAG_MOT_CHARACTERWISE, CommandFlags.FLAG_MOT_CHARACTERWISE, CommandFlags.FLAG_TEXT_BLOCK);
  }

  @Nullable
  @Override
  public TextRange getRange(@NotNull Editor editor,
                            @NotNull Caret caret,
                            @NotNull DataContext context,
                            int count,
                            int rawCount,
                            @Nullable Argument argument) {
    final ArgBoundsFinder finder = new ArgBoundsFinder(editor.getDocument());
    int pos = caret.getOffset();
    if (CommandState.getInstance(editor).getMode() == CommandState.Mode.VISUAL) {
//          if (CaretData.getVisualStart(caret) != CaretData.getVisualEnd(caret)) {
//            pos = Math.min(CaretDataKt.getVisualStart(caret), CaretData.getVisualEnd(caret));
//          }
    }
    int left = 0;
    for (int i = 0; i < count; ++i) {
      if (!finder.findBoundsAt(pos)) {
        VimPlugin.indicateError();
        return null;
      }
      if (isInner() && (i == 0 || i == count - 1)) {
        finder.adjustForInner();
      } else {
        finder.adjustForOuter();
      }
      if (i == 0) {
        left = finder.getLeftBound();
      }
      pos = finder.getRightBound();
    }
    return new TextRange(left, finder.getRightBound());
  }

  protected abstract boolean isInner();
}
