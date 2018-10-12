package com.maddyhome.idea.vim.extension.easymotion

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.maddyhome.idea.vim.VimPlugin
import com.maddyhome.idea.vim.action.motion.MotionEditorAction
import com.maddyhome.idea.vim.command.Argument
import com.maddyhome.idea.vim.handler.MotionEditorActionHandler
import com.werfad.JumpHandler

class MotionEasyMotionAction(motionType: MotionType) : MotionEditorAction(Handler(motionType)) {
    class Handler(val motionType: MotionType) : MotionEditorActionHandler(true) {
        override fun getOffset(editor: Editor, caret: Caret, context: DataContext, count: Int, rawCount: Int, argument: Argument?): Int {
            JumpHandler.start(editor, motionType.JumpType)
            //todo: how to wait
            return VimPlugin.getMotion().moveCaretHorizontal(editor, caret, count, true)
        }
    }

    enum class MotionType(val actionId: String, val JumpType: Int) {
        TO_BI_DIRECTION_WORD("VimMotionEasyMotionToBiDirectionWord", JumpHandler.MODE_WORD0),
        TO_BI_DIRECTION_LINE("VimMotionEasyMotionToBiDirectionLine", JumpHandler.MODE_LINE);
    }
}
