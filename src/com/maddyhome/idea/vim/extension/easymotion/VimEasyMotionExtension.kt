package com.maddyhome.idea.vim.extension.easymotion

import com.intellij.openapi.actionSystem.ActionManager
import com.maddyhome.idea.vim.VimPlugin
import com.maddyhome.idea.vim.command.Command
import com.maddyhome.idea.vim.command.MappingMode
import com.maddyhome.idea.vim.extension.VimNonDisposableExtension
import com.maddyhome.idea.vim.key.Shortcut

private const val BD_S_C = "<Plug>easymotion-bd-s-c"
private const val BD_S_W = "<Plug>easymotion-bd-s-w"
private const val BD_W = "<Plug>easymotion-bd-w"
private const val BD_JK = "<Plug>easymotion-bd-jk"

/**
 * integrated from https://github.com/a690700752/KJump
 */
class VimEasyMotionExtension : VimNonDisposableExtension() {
    override fun getName() = "easymotion"

    override fun initOnce() {
        registerAction(MotionEasyMotionAction.MotionType.TO_BI_DIRECTION_WORD, Command.FLAG_MOT_EXCLUSIVE, "\\;")
        registerAction(MotionEasyMotionAction.MotionType.TO_BI_DIRECTION_LINE, Command.FLAG_MOT_LINEWISE or Command.FLAG_MOT_INCLUSIVE, "<space><cr>")
    }

    private fun registerAction(motionType: MotionEasyMotionAction.MotionType, commandFlags: Int, key: String) {
        val actionId = motionType.actionId
        val actionManager = ActionManager.getInstance()
        val action = actionManager.getAction(actionId)
        if (action == null) {
            actionManager.registerAction(actionId, MotionEasyMotionAction(motionType), VimPlugin.getPluginId())
            VimPlugin.getKey().registerAction(MappingMode.NVO, actionId, Command.Type.MOTION, commandFlags, Shortcut(key))
        }
    }
}

