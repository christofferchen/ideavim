/*
 * IdeaVim - Vim emulator for IDEs based on the IntelliJ platform
 * Copyright (C) 2003-2016 The IdeaVim authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.maddyhome.idea.vim.extension.camelcasemotion;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.maddyhome.idea.vim.action.motion.TextObjectAction;
import com.maddyhome.idea.vim.command.Argument;
import com.maddyhome.idea.vim.common.CharacterPosition;
import com.maddyhome.idea.vim.common.TextRange;
import com.maddyhome.idea.vim.handler.TextObjectActionHandler;
import com.maddyhome.idea.vim.helper.CharacterHelper;
import com.maddyhome.idea.vim.helper.EditorHelper;
import com.maddyhome.idea.vim.helper.SearchHelper;
import com.maddyhome.idea.vim.helper.StringHelper;
import com.maddyhome.idea.vim.regexp.CharHelper;
import com.maddyhome.idea.vim.regexp.CharacterClasses;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 */
public class MotionInnerCamelCaseWordAction extends TextObjectAction {
  public MotionInnerCamelCaseWordAction() {
    super(new Handler());
  }

  private static class Handler extends TextObjectActionHandler {
    public Handler() {
      super(true);
    }

    @Override
    public TextRange getRange(@NotNull Editor editor, @NotNull Caret caret, @NotNull DataContext context,
                              int count, int rawCount, @Nullable Argument argument) {
      final CharSequence charsSequence = editor.getDocument().getCharsSequence();
      final int offset = caret.getOffset();
      final int max = EditorHelper.getFileSize(editor);
      final char currentChar = charsSequence.charAt(offset);
      if (!CharacterClasses.isWord(currentChar)) {
        return new TextRange(offset, offset);
      }

      boolean atTheBeginning;
      if (offset == 0) {
        atTheBeginning = true;
      } else {
        final char prevChar = charsSequence.charAt(offset - 1);
        atTheBeginning = CharacterClasses.isUpper(currentChar) && CharacterClasses.isLower(prevChar) || !CharacterClasses.isAlpha(prevChar);
      }

      boolean atTheEnding;
      if (offset == max - 1) {
        atTheEnding = true;
      } else {
        final char nextChar = charsSequence.charAt(offset + 1);
        atTheEnding = CharacterClasses.isLower(currentChar) && CharacterClasses.isUpper(nextChar) || !CharacterClasses.isAlpha(nextChar);
      }

      int start;
      if (atTheBeginning) {
        start = offset;
      } else {
        start = SearchHelper.findNextCamelStart(editor, caret, -count);
      }
      start = EditorHelper.normalizeOffset(editor, start, false);

      int end;
      if (atTheEnding) {
        end = offset;
      } else {
        end = SearchHelper.findNextCamelEnd(editor, caret, count);
      }
      end = EditorHelper.normalizeOffset(editor, end, false);
      return new TextRange(start, end);
    }
  }
}
