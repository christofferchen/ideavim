package com.maddyhome.idea.vim.extension.camelcasemotion

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.maddyhome.idea.vim.command.Argument
import com.maddyhome.idea.vim.command.CommandFlags
import com.maddyhome.idea.vim.common.TextRange
import com.maddyhome.idea.vim.handler.TextObjectActionHandler
import com.maddyhome.idea.vim.helper.EditorHelper
import com.maddyhome.idea.vim.helper.SearchHelper
import com.maddyhome.idea.vim.helper.enumSetOf
import com.maddyhome.idea.vim.regexp.CharacterClasses
import java.util.*

class MotionInnerCamelCaseWordAction : TextObjectActionHandler() {
  override val flags: EnumSet<CommandFlags> = enumSetOf(CommandFlags.FLAG_MOT_CHARACTERWISE)

  override fun getRange(editor: Editor, caret: Caret, context: DataContext, count: Int, rawCount: Int, argument: Argument?): TextRange? {
    val charsSequence = editor.document.charsSequence
    val offset = caret.getOffset()
    val max = EditorHelper.getFileSize(editor)
    val currentChar = charsSequence[offset]
    if (!CharacterClasses.isWord(currentChar)) {
      return TextRange(offset, offset)
    }

    val atTheBeginning: Boolean
    if (offset == 0) {
      atTheBeginning = true
    } else {
      val prevChar = charsSequence[offset - 1]
      atTheBeginning = CharacterClasses.isUpper(currentChar) && CharacterClasses.isLower(prevChar) || !CharacterClasses.isAlpha(prevChar)
    }

    val atTheEnding: Boolean
    if (offset == max - 1) {
      atTheEnding = true
    } else {
      val nextChar = charsSequence[offset + 1]
      atTheEnding = CharacterClasses.isLower(currentChar) && CharacterClasses.isUpper(nextChar) || !CharacterClasses.isAlpha(nextChar)
    }

    var start: Int
    if (atTheBeginning) {
      start = offset
    } else {
      start = SearchHelper.findNextCamelStart(editor, caret, -count)
    }
    start = EditorHelper.normalizeOffset(editor, start, false)

    var end: Int
    if (atTheEnding) {
      end = offset
    } else {
      end = SearchHelper.findNextCamelEnd(editor, caret, count)
    }
    end = EditorHelper.normalizeOffset(editor, end, false)
    return TextRange(start, end + 1)
  }
}
