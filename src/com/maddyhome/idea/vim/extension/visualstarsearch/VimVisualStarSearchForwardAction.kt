package com.maddyhome.idea.vim.extension.visualstarsearch

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorAction
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.maddyhome.idea.vim.VimPlugin
import com.maddyhome.idea.vim.action.motion.MotionEditorAction
import com.maddyhome.idea.vim.command.Argument
import com.maddyhome.idea.vim.command.Command
import com.maddyhome.idea.vim.command.CommandFlags
import com.maddyhome.idea.vim.group.MotionGroup
import com.maddyhome.idea.vim.handler.EditorActionHandlerBase
import com.maddyhome.idea.vim.handler.MotionEditorActionHandler
import java.util.*

class VimVisualStarSearchForwardAction : EditorAction(Handler()) {
    class Handler : EditorActionHandlerBase(true) {
        override fun execute(editor: Editor, caret: Caret, context: DataContext, cmd: Command): Boolean {
            val selectedText = editor.selectionModel.selectedText ?: return false
            VimPlugin.getMotion().exitVisual(editor)
            VimPlugin.getSearch().search(editor, selectedText, 1, EnumSet.of(CommandFlags.FLAG_SEARCH_FWD), true)
            return super.execute(editor, caret, context, cmd)
        }
    }
}
class VimVisualStarSearchBackwardAction : EditorAction(Handler()) {
    class Handler : EditorActionHandlerBase(true) {
        override fun execute(editor: Editor, caret: Caret, context: DataContext, cmd: Command): Boolean {
            val selectedText = editor.selectionModel.selectedText ?: return false
            val startOffset = VimPlugin.getMotion().getVisualRange(editor).startOffset
            VimPlugin.getMotion().exitVisual(editor)
            MotionGroup.moveCaret(editor, caret, startOffset);
            VimPlugin.getSearch().search(editor, selectedText, 1, EnumSet.of(CommandFlags.FLAG_SEARCH_REV), true)
            return super.execute(editor, caret, context, cmd)
        }
    }
}
